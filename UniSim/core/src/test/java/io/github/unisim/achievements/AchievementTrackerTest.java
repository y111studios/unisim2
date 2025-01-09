package io.github.unisim.achievements;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class AchievementTrackerTest {
    
    // Testing constructor
    @Test
    public void testAchievementTracker() {
        AchievementTracker achievementTracker = new AchievementTracker();
        assertFalse(achievementTracker.satisfactionHasReachedTen);
        assertFalse(achievementTracker.hadInput);
        assertNull(achievementTracker.timeSatisfactionReached75);
        assertNotNull(achievementTracker.buildingsPlacedCount);
        assertTrue(achievementTracker.buildingsPlacedCount.isEmpty());
        assertNull(achievementTracker.controlsScreenOpened);
    }
}
