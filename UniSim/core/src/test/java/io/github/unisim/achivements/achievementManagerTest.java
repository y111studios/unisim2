package io.github.unisim.achivements;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import io.github.unisim.achievements.AchievementManager;

public class achievementManagerTest {

    AchievementManager achievementManager;
    
    // Testing constructor
    @Test
    public void testAchievementManager() {
        achievementManager = new AchievementManager();
        assertNotNull(achievementManager);
    }

    // Testing getAchievements
    @Test
    public void testGetAchievements() {
        achievementManager = new AchievementManager();
        assertNotNull(achievementManager.getAchievements());
        System.out.println(achievementManager.getAchievements());
    }
}
