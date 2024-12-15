package io.github.unisim.scoring;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScoreTrackerTest {

    private ScoreTracker scoreTracker;
    private float score;
    private Map<ScoringObject, Instant> lastUpdateTimes;
    private ScoringObject mockScoringObject;

    @BeforeEach
    public void setUp() {
        scoreTracker = new ScoreTracker();
        mockScoringObject = new MockScoringObject(10.0f, Duration.ofSeconds(0));
    }

    public class MockScoringObject implements ScoringObject {
        private final float score;
        private final Duration updateInterval;

        public MockScoringObject(float score, Duration updateInterval) {
            this.score = score;
            this.updateInterval = updateInterval;
        }

        @Override
        public float getScore() {
            return score;
        }

        @Override
        public Duration getUpdateInterval() {
            return updateInterval;
        }

    }

    // Tests initial score
    @Test
    public void testInitialScore() {
        assertEquals(0, scoreTracker.getScore());
    }


    // Tests AddSocreObject
    @Test
    public void testAddScoreObject() {
        scoreTracker.addScoreObject(mockScoringObject);
        assertEquals(1, scoreTracker.getLastUpdateTimes().size());
        assertEquals(Instant.MIN, scoreTracker.getLastUpdateTimes().get(mockScoringObject));
    }
}
