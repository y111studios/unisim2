package io.github.unisim.achivements;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.unisim.achievements.Achievement;
import io.github.unisim.achievements.AchievementManager;
import io.github.unisim.achievements.ScoreModifierTemplate;

public class achievementManagerTest {

    AchievementManager achievementManager;
    Achievement achievementUnlocked;
    Achievement achievementLocked;

    @BeforeEach
    public void setUp() {
        achievementManager = new AchievementManager();
        achievementUnlocked = new Achievement("Test1", "Description", Instant.EPOCH, ScoreModifierTemplate.ADD, 0, 0, false, false);
        achievementLocked = new Achievement("Test2", "Description", Instant.EPOCH, ScoreModifierTemplate.ADD, 0, 0, true, false);

    }
    
    // Testing constructor
    @Test
    public void testAchievementManager() {
        assertNotNull(achievementManager);
    }

    // Testing clearSessionAchievements
    @Test
    public void testClearSessionAchievements() {
        achievementManager.clearSessionAchievements();
        assertTrue(achievementManager.getSessionAchievements().isEmpty());
    }

    // Testing getAchievements
    @Test
    public void testGetAchievements() {
        assertNotNull(achievementManager.getAchievements());
        System.out.println(achievementManager.getAchievements());
    }

    // Testing getUnlockedScoreModifiers
    @Test
    public void testGetUnlockedachievements() {
        assertNotNull(achievementManager.getUnlockedScoreModifiers());
    }
}
