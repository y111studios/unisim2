package io.github.unisim.events;

import java.lang.Math;

import io.github.unisim.finance.MoneyTracker;
import io.github.unisim.scoring.SatisfactionTracker;

public class ChoiceCard {

    private String title;
    private String description;
    private int moneyEffect;
    private float satisfactionEffect;

    public ChoiceCard(String title, String description, int moneyEffect, float satisfactionEffect) {
        this.title = title;
        this.description = description;
        this.moneyEffect = moneyEffect;
        this.satisfactionEffect = satisfactionEffect;
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

    public float getSatisfactionEffect() {
        return satisfactionEffect;
    }

    public void applyEffects(MoneyTracker moneyTracker, SatisfactionTracker satisfactionTracker) {
        if (moneyEffect < 0) {
            moneyTracker.subtractMoney(Math.abs(moneyEffect));
        } else {
            moneyTracker.addMoney(moneyEffect);
        }
        satisfactionTracker.changeSatisfaction(satisfactionEffect);
    }

}
