package io.github.unisim.achievements;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.utils.JsonValue;

public class AchievementsTest {

    // Testing constructor
    @Test
    public void testConstructor() {
        String name = "Test";
        String description = "Test description";
        Instant unlockTime = Instant.EPOCH;
        ScoreModifierTemplate functionTemplate = ScoreModifierTemplate.ADD;
        float scoreModifierValue = 0;
        float progress = 0;
        boolean unlocked = false;
        boolean hidden = false;

        Achievement achievement = new Achievement(name, description, unlockTime, functionTemplate, scoreModifierValue, progress, unlocked, hidden);

        assertEquals(name, achievement.getName());
        assertEquals(description, achievement.getDescription());
        assertEquals(unlocked, achievement.isUnlocked());
    }

    // Testing getName
    @Test
    public void testGetName() {
        String name = "Test Achievement";
        Achievement achievement = new Achievement(name, "Test description", Instant.EPOCH, ScoreModifierTemplate.ADD, 0, 0, false, false);
        assertEquals(name, achievement.getName());
    }

    // Testing getDescription
    @Test
    public void testGetDescription() {
        String description = "Test description";
        Achievement achievement = new Achievement("Test", description, Instant.EPOCH, ScoreModifierTemplate.ADD, 0, 0, false, false);
        assertEquals(description, achievement.getDescription());
    }

    // Testing isUnlcoked
    @Test
    public void testIsUnlocked() {
        Achievement achievement = new Achievement("Test", "Test description", Instant.EPOCH, ScoreModifierTemplate.ADD, 0, 0, false, false);
        assertEquals(false, achievement.isUnlocked());

        achievement = new Achievement("Test", "Test description", Instant.EPOCH, ScoreModifierTemplate.ADD, 0, 0, true, false);
        assertEquals(true, achievement.isUnlocked());
    }

    // Testing getScoreModifier
    @Test
    public void testGetScoreModifier() {
        float scoreModifierValue = 10;
        Achievement achievement = new Achievement("Test", "Desciption", Instant.EPOCH, ScoreModifierTemplate.ADD, scoreModifierValue, 0, false, false);
        assertEquals(scoreModifierValue, achievement.scoreModifierValue);
    }

    @Test
    public void testGetScoreModifierZero() {
        float scoreModifierValue = 0;
        Achievement achievement = new Achievement("Test", "Desciption", Instant.EPOCH, ScoreModifierTemplate.ADD, scoreModifierValue, 0, false, false);
        assertEquals(scoreModifierValue, achievement.scoreModifierValue);
    }

    @Test
    public void testGetScoreModifierNegative() {
        float scoreModifierValue = -10;
        Achievement achievement = new Achievement("Test", "Desciption", Instant.EPOCH, ScoreModifierTemplate.ADD, scoreModifierValue, 0, false, false);
        assertEquals(scoreModifierValue, achievement.scoreModifierValue);
    }

    // Testing toJsonValue
    @Test
    public void testToJsonValue() {
        String name = "Test";
        String description = "Test description";
        Instant unlockTime = Instant.now();
        ScoreModifierTemplate functionTemplate = ScoreModifierTemplate.ADD;
        float scoreModifierValue = 0;
        float progress = 1;
        boolean unlocked = true;
        boolean hidden = false;

        Achievement achievement = new Achievement(name, description, unlockTime, functionTemplate, scoreModifierValue, progress, unlocked, hidden);
        JsonValue jsonValue = achievement.toJsonValue();

        assertNotNull(jsonValue);
        assertEquals(name, jsonValue.getString("name"));
        assertEquals(unlockTime.toEpochMilli(), jsonValue.getLong("unlockTime"));
        assertEquals(progress, jsonValue.getFloat("progress"));
        assertEquals(unlocked, jsonValue.getBoolean("unlocked"));
    }

    @Test
    public void testProgressUnlocked() {
        Achievement achievement = new Achievement("Test", "Description", Instant.EPOCH, ScoreModifierTemplate.ADD, 0, 0, true, false);
        assertFalse(achievement.progress(0.5f));
    }

    @Test
    public void testProgressInvalidValue() {
        Achievement achievement = new Achievement("Test", "Description", Instant.EPOCH, ScoreModifierTemplate.ADD, 0, 0, false, false);
        assertFalse(achievement.progress(Float.POSITIVE_INFINITY));
        assertFalse(achievement.progress(Float.NaN));
    }

    @Test
    public void testProgressPartial() {
        Achievement achievement = new Achievement("Test", "Description", Instant.EPOCH, ScoreModifierTemplate.ADD, 0, 0, false, false);
        assertFalse(achievement.progress(0.5f));
        assertEquals(0.5f, achievement.progress);
    }

    @Test
    public void testProgressComplete() {
        Achievement achievement = new Achievement("Test", "Description", Instant.EPOCH, ScoreModifierTemplate.ADD, 0, 0, false, false);
        assertTrue(achievement.progress(1.0f));
        assertEquals(1.0f, achievement.progress);
        assertTrue(achievement.isUnlocked());
        assertNotEquals(Instant.EPOCH, achievement.unlockTime);
    }

    @Test
    public void testProgressAccumulation() {
        Achievement achievement = new Achievement("Test", "Description", Instant.EPOCH, ScoreModifierTemplate.ADD, 0, 0, false, false);
        assertFalse(achievement.progress(0.3f));
        assertFalse(achievement.progress(0.3f));
        assertFalse(achievement.progress(0.3f));
        assertTrue(achievement.progress(0.2f));
        assertTrue(achievement.isUnlocked());
        assertEquals(1.0f, achievement.progress);
    }
}
