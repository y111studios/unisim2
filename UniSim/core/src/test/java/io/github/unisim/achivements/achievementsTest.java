package io.github.unisim.achivements;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import io.github.unisim.achievements.Achievement;
import io.github.unisim.achievements.ScoreModifierTemplate;

public class achievementsTest {
    
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
}
