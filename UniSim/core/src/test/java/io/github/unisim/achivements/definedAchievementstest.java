package io.github.unisim.achivements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import io.github.unisim.achievements.Achievement;
import io.github.unisim.achievements.DefinedAchievements;
import io.github.unisim.achievements.ScoreModifierTemplate;
import io.github.unisim.achievements.AchievementManager;

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
        HashSet<String> names = DefinedAchievements.getNames();
        assertNotNull(names);
        assertTrue(names.contains("Bankruptcy"));
        assertTrue(names.contains("Capitalist"));
    }
}
