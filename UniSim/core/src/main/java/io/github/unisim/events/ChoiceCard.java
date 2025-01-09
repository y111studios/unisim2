package io.github.unisim.events;

import java.lang.Math;
import java.time.Duration;
import io.github.unisim.achievements.ScoreModifierTemplate;
import io.github.unisim.finance.MoneyTracker;
import io.github.unisim.scoring.SatisfactionModifier;
import io.github.unisim.scoring.SatisfactionTracker;

/**
 * Class representing an event choice.
 */
public class ChoiceCard {

    private String title;
    private String description;
    private int moneyEffect;
    private SatisfactionModifier satisfactionModifier;

    /**
     * All arg constructor
     *
     * <p>
     * If the satisfactionTemplate is null, it will default to a modifier that is effectively a no-op.
     * The specific representation of the no-op is a satisfaction modifier with a template of ADD and
     * a value of 0 and a 0 length duration.
     * </p>
     *
     * @param title the title of the choice card
     * @param description the description of the choice card
     * @param moneyEffect the integer effect on the money tracker
     * @param satisfactionTemplate the template for the satisfaction modifier
     * @param satisfactionEffect the float effect on the satisfaction tracker
     */
    public ChoiceCard(String title, String description, int moneyEffect, ScoreModifierTemplate satisfactionTemplate, float satisfactionEffect) {
        this.title = title;
        this.description = description;
        this.moneyEffect = moneyEffect;
        Duration modifierDuration = Duration.ofSeconds(10);
        if (satisfactionTemplate == null) {
            satisfactionTemplate = ScoreModifierTemplate.ADD;
            satisfactionEffect = 0;
            modifierDuration = Duration.ZERO;
        }
        this.satisfactionModifier = new SatisfactionModifier(satisfactionTemplate, satisfactionEffect, modifierDuration);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getMoneyEffect() {
        return moneyEffect;
    }

    public SatisfactionModifier getSatisfactionModifier() {
        return satisfactionModifier;
    }

    /**
     * Apply the effects defined by the choice card.
     *
     * @param moneyTracker
     * @param satisfactionTracker
     */
    public void applyEffects(MoneyTracker moneyTracker, SatisfactionTracker satisfactionTracker) {
        if (moneyEffect < 0) {
            moneyTracker.subtractMoney(Math.abs(moneyEffect));
        } else {
            moneyTracker.addMoney(moneyEffect);
        }
        satisfactionModifier.renewEndTime();
        satisfactionTracker.addModifier(satisfactionModifier);
    }

}
