package io.github.unisim.achievements;

import java.time.Instant;
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

    // Testing getMissingAchievements
    @Test
    public void testGetMissingAchievements() {
        Achievement achievement1 = new Achievement("Test1", "Desc1", Instant.EPOCH, ScoreModifierTemplate.ADD, 0, 0, false, false);
        Achievement achievement2 = new Achievement("Test2", "Desc2", Instant.EPOCH, ScoreModifierTemplate.ADD, 0, 0, false, false);

        Iterable <Achievement> achievements = List.of(achievement1, achievement2);

        List<DefinedAchievements> missingAchievements = DefinedAchievements.getMissingAchievements(achievements).get();
        assertEquals(2, missingAchievements.size());
        System.out.println("Missing achievements: " + missingAchievements);
        assertTrue(missingAchievements.contains(DefinedAchievements.Bankruptcy));
        assertTrue(missingAchievements.contains(DefinedAchievements.Capitalist));
    }

}
