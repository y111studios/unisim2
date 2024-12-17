package io.github.unisim.achivements;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import io.github.unisim.achievements.AchievementManager;
import io.github.unisim.achievements.DefinedAchievements;

public class definedAchievementsTest {

    private AchievementManager achievementManager;

    // Testing constructor
    @Test
    public void testConstructor() {
        DefinedAchievements[] values = DefinedAchievements.values();
        assertNotNull(values);
        assertEquals(2, values.length); // Change expected value as achievements are added
        assertEquals(DefinedAchievements.Bankruptcy, values[0]);
        assertEquals(DefinedAchievements.Capitalist, values[1]);
    }

    @Test
    public void testGetNames() {
        HashSet<String> names = new HashSet<>();
        for (DefinedAchievements achievement : DefinedAchievements.values()) {
            names.add(achievement.name());
        }
        assertNotNull(names);
        assertTrue(names.contains("Bankruptcy"));
        assertTrue(names.contains("Capitalist"));
    }
}
