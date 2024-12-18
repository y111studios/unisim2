package io.github.unisim.leaderboard;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class LeaderboardEntryTest {

    // Testing constructor
    @Test
    public void testConstructorWithNoName() {
        assertThrows(IllegalArgumentException.class, () -> new LeaderboardEntry("", 100));
    }

    @Test
    public void testConstructorWithNegativeScore() {
        assertThrows(IllegalArgumentException.class, () -> new LeaderboardEntry("Ben", -1));
    }

    @Test
    public void testConstructorWithInvalidImputs() {
        assertThrows(IllegalArgumentException.class, () -> new LeaderboardEntry("", -1));
    }
}
