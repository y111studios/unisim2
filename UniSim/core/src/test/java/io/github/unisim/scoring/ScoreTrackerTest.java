package io.github.unisim.scoring;

import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScoreTrackerTest {

    private ScoreTracker scoreTracker;
    private float score;
    private Map<ScoringObject, Instant> lastUpdateTimes;

    @BeforeEach
    public void setUp() {
        scoreTracker = new ScoreTracker();
    }

    // Tests initial score
    @Test
    public void testInitialScore() {
        scoreTracker = new ScoreTracker();
        assertEquals(0, scoreTracker.getScore());
    }
}
