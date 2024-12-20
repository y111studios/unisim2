package io.github.unisim.events;

import java.lang.Math;
import java.time.Duration;
import io.github.unisim.achievements.ScoreModifierTemplate;
import io.github.unisim.finance.MoneyTracker;
import io.github.unisim.scoring.SatisfactionModifier;
import io.github.unisim.scoring.SatisfactionTracker;

public class ChoiceCard {

    private String title;
    private String description;
    private int moneyEffect;
    private SatisfactionModifier satisfactionModifier;

    public ChoiceCard(String title, String description, int moneyEffect, ScoreModifierTemplate satisfactionTemplate, float satisfactionEffect) {
        this.title = title;
        this.description = description;
        this.moneyEffect = moneyEffect;
        this.satisfactionModifier = new SatisfactionModifier(satisfactionTemplate, satisfactionEffect, Duration.ofSeconds(10));
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
