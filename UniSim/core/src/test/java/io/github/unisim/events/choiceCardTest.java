package io.giyhub.unisim.events;

import org.junit.jupiter.api.Test;

import io.github.unisim.finance.moneyTracker;

public class choiceCardTest {

    // Testing constructor
    @Test
    public void testConstructor() {
        choiceCard card = new choiceCard("Test", "Test Desc", 1, ScoreModifierTemplate.ADD, 1);
        assertEquals("Test", card.getName());
        assertEquals("Test Desc", card.getDescription());
        assertEquals(1, card.getMoneyEffect());
        assertEquals(1, card.getScore());
        assertEquals(ScoreModifierTemplate.ADD, card.getScoreModifier());
    }

    // Testing getTitle
    @Test
    public void testGetTitle() {
        choiceCard card = new choiceCard("Test", "Test Desc", 1, ScoreModifierTemplate.ADD, 1);
        assertEquals("Test", card.getName());
    }

    @Test
    public void testGetNullTitle() {
        choiceCard card = new choiceCard(null, "Test Desc", 1, ScoreModifierTemplate.ADD, 1);
        assertEquals(null, card.getName());
    }

    // Testing getDescription  
    @Test
    public void testGetDescription() {
        choiceCard card = new choiceCard("Test", "Test Desc", 1, ScoreModifierTemplate.ADD, 1);
        assertEquals("Test Desc", card.getDescription());
    }

    @Test
    public void testGetNullDescription() {
        choiceCard card = new choiceCard("Test", null, 1, ScoreModifierTemplate.ADD, 1);
        assertEquals(null, card.getDescription());
    }

    // Testing getMoneyEffect
    @Test
    public void testGetMoneyEffect() {
        choiceCard card = new choiceCard("Test", "Test Desc", 1, ScoreModifierTemplate.ADD, 1);
        assertEquals(1, card.getMoneyEffect());
    }

    @Test
    public void testGetNegativeMoneyEffect() {
        choiceCard card = new choiceCard("Test", "Test Desc", -1, ScoreModifierTemplate.ADD, 1);
        assertEquals(-1, card.getMoneyEffect());
    }

    @Test
    public void testGetZeroMoneyEffect() {
        choiceCard card = new choiceCard("Test", "Test Desc", 0, ScoreModifierTemplate.ADD, 1);
        assertEquals(0, card.getMoneyEffect());
    }

    @Test
    public void testGetNullMoneyEffect() {
        choiceCard card = new choiceCard("Test", "Test Desc", null, ScoreModifierTemplate.ADD, 1);
        assertEquals(null, card.getMoneyEffect());
    }

    // Testing getScore
    @Test
    public void testGetScore() {
        choiceCard card = new choiceCard("Test", "Test Desc", 1, ScoreModifierTemplate.ADD, 1);
        assertEquals(1, card.getScore());
    }

    @Test
    public void testGetNegativeScore() {
        choiceCard card = new choiceCard("Test", "Test Desc", 1, ScoreModifierTemplate.ADD, -1);
        assertEquals(-1, card.getScore());
    }

    @Test
    public void testGetZeroScore() {
        choiceCard card = new choiceCard("Test", "Test Desc", 1, ScoreModifierTemplate.ADD, 0);
        assertEquals(0, card.getScore());
    }

    @Test
    public void testGetNullScore() {
        choiceCard card = new choiceCard("Test", "Test Desc", 1, ScoreModifierTemplate.ADD, null);
        assertEquals(null, card.getScore());
    }

    // Testing getScoreModifier
    @Test
    public void testGetScoreModifier() {
        choiceCard card = new choiceCard("Test", "Test Desc", 1, ScoreModifierTemplate.ADD, 1);
        assertEquals(ScoreModifierTemplate.ADD, card.getScoreModifier());
    }

    @Test
    public void testGetNullScoreModifier() {
        choiceCard card = new choiceCard("Test", "Test Desc", 1, null, 1);
        assertEquals(null, card.getScoreModifier());
    }

    // Testing applyEffect
    @Test
    public void testApplyEffect() {
        choiceCard card = new choiceCard("Test", "Test Desc", 1, ScoreModifierTemplate.ADD, 1);
        moneyTracker money = new moneyTracker(0);
        card.applyEffect(money);
        assertEquals(1, money.getMoney());
    }

    @Test
    public void testApplyNegativeEffect() {
        choiceCard card = new choiceCard("Test", "Test Desc", -1, ScoreModifierTemplate.ADD, 1);
        moneyTracker money = new moneyTracker(1);
        card.applyEffect(money);
        assertEquals(0, money.getMoney());
    }

}