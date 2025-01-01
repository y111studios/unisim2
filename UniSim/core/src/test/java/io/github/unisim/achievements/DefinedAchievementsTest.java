package io.github.unisim.achievements;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class DefinedAchievementsTest {

    // Testing constructor
    @Test
    public void testConstructor() {
        DefinedAchievements[] values = DefinedAchievements.values();
        assertNotNull(values);
        assertTrue(values.length > 0);
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

    // Testing getMissingAchievements
    @Test
    public void testGetMissingAchievements() {
        Iterable <Achievement> achievements = List.of();

        List<DefinedAchievements> missingAchievements = DefinedAchievements.getMissingAchievements(achievements).get();
        // Test all definied achievements are missing from empty list
        assertEquals(DefinedAchievements.values().length, missingAchievements.size());
        for (DefinedAchievements achievement : DefinedAchievements.values()) {
            assertTrue(missingAchievements.contains(achievement));
        }
    }

}
