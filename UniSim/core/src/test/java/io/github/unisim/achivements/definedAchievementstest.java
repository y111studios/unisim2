package io.github.unisim.achivements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import io.github.unisim.achievements.DefinedAchievements;

public class definedAchievementstest {

    // Testing constructor
    @Test
    public void testConstructor() {
        DefinedAchievements[] values = DefinedAchievements.values();
        assertNotNull(values);
        assertEquals(2, values.length); // Change expected value as achievements are added
        assertEquals(DefinedAchievements.Bankruptcy, values[0]);
        assertEquals(DefinedAchievements.Capitalist, values[1]);
    }
}
