package io.github.unisim.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import io.github.unisim.achievements.ScoreModifierTemplate;
import io.github.unisim.finance.MoneyTracker;
import io.github.unisim.scoring.SatisfactionTracker;

public class choiceCardTest {

    MoneyTracker moneyTracker;
    ChoiceCard card;


    // Testing constructor
    @Test
    public void testConstructor() {
        ChoiceCard card = new ChoiceCard("Test", "Test Desc", 1, ScoreModifierTemplate.ADD, 1);
        assertEquals("Test", card.getTitle());
        assertEquals("Test Desc", card.getDescription());
        assertEquals(1, card.getMoneyEffect());
        assertEquals(ScoreModifierTemplate.ADD, card.getSatisfactionModifier());
    }

    // Testing getTitle
    @Test
    public void testGetTitle() {
        ChoiceCard card = new ChoiceCard("Test", "Test Desc", 1, ScoreModifierTemplate.ADD, 1);
        assertEquals("Test", card.getTitle());
    }

    @Test
    public void testGetNullTitle() {
        ChoiceCard card = new ChoiceCard(null, "Test Desc", 1, ScoreModifierTemplate.ADD, 1);
        assertEquals(null, card.getTitle());
    }

    // Testing getDescription  
    @Test
    public void testGetDescription() {
        ChoiceCard card = new ChoiceCard("Test", "Test Desc", 1, ScoreModifierTemplate.ADD, 1);
        assertEquals("Test Desc", card.getDescription());
    }

    @Test
    public void testGetNullDescription() {
        ChoiceCard card = new ChoiceCard("Test", null, 1, ScoreModifierTemplate.ADD, 1);
        assertEquals(null, card.getDescription());
    }

    // Testing getMoneyEffect
    @Test
    public void testGetMoneyEffect() {
        ChoiceCard card = new ChoiceCard("Test", "Test Desc", 1, ScoreModifierTemplate.ADD, 1);
        assertEquals(1, card.getMoneyEffect());
    }

    @Test
    public void testGetNegativeMoneyEffect() {
        ChoiceCard card = new ChoiceCard("Test", "Test Desc", -1, ScoreModifierTemplate.ADD, 1);
        assertEquals(-1, card.getMoneyEffect());
    }

    @Test
    public void testGetZeroMoneyEffect() {
        ChoiceCard card = new ChoiceCard("Test", "Test Desc", 0, ScoreModifierTemplate.ADD, 1);
        assertEquals(0, card.getMoneyEffect());
    }

    // Testing getScoreModifier
    @Test
    public void testGetScoreModifier() {
        ChoiceCard card = new ChoiceCard("Test", "Test Desc", 1, ScoreModifierTemplate.ADD, 1);
        assertEquals(ScoreModifierTemplate.ADD, card.getSatisfactionModifier());
    }

    @Test
    public void testGetNullScoreModifier() {
        ChoiceCard card = new ChoiceCard("Test", "Test Desc", 1, null, 1);
        assertEquals(null, card.getSatisfactionModifier());
    }

    // Testing applyEffect
    @Test
    public void testApplyEffect() {
        ChoiceCard card = new ChoiceCard("Test", "Test Desc", 1, ScoreModifierTemplate.ADD, 1);
        MoneyTracker money = new MoneyTracker(0);
        SatisfactionTracker satisfaction = new SatisfactionTracker();
        card.applyEffects(money, satisfaction);
        assertEquals(1, money.getMoney());
    }

    @Test
    public void testApplyNegativeEffect() {
        ChoiceCard card = new ChoiceCard("Test", "Test Desc", -1, ScoreModifierTemplate.ADD, 1);
        MoneyTracker money = new MoneyTracker(1);
        SatisfactionTracker satisfaction = new SatisfactionTracker();
        card.applyEffects(money, satisfaction);
        assertEquals(0, money.getMoney());
    }

}