package io.github.unisim.scoring;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScoreTrackerTest {

    private ScoreTracker scoreTracker;
    private ScoringObject mockScoringObject;

    @BeforeEach
    public void setUp() {
        scoreTracker = new ScoreTracker();
        mockScoringObject = new MockScoringObject(10.0f, Duration.ofSeconds(1));
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

    // Testing initial score/constructor
    @Test
    public void testInitialScore() {
        assertEquals(0, scoreTracker.getScore());
    }


    // Testing AddSocreObject
    @Test
    public void testAddScoreObject() {
        scoreTracker.addScoreObject(mockScoringObject);
        assertEquals(1, scoreTracker.getLastUpdateTimes().size());
        assertEquals(Instant.MIN, scoreTracker.getLastUpdateTimes().get(mockScoringObject));
    }

    // Testing getFinalScore
    @Test
    public void testGetFinalScore() {
        scoreTracker.addScoreObject(mockScoringObject);
        scoreTracker.update();
        assertEquals(10, scoreTracker.getFinalScore());
    }

    // Testing update
    @Test
    public void testUpdate() {
        scoreTracker.addScoreObject(mockScoringObject);
        scoreTracker.update();
        assertEquals(10, scoreTracker.getScore());
    }

    @Test
    public void testUpdateWhenConditionIsMet() {
        scoreTracker.addScoreObject(mockScoringObject);
        Instant lastUpdateTime = Instant.now().minus(Duration.ofMinutes(10));
        scoreTracker.getLastUpdateTimes().put(mockScoringObject, lastUpdateTime);
        scoreTracker.update();
        assertEquals(10, scoreTracker.getScore());
    }

    @Test
    public void testUpdateWhenConditionIsNotMet() {
        scoreTracker.addScoreObject(mockScoringObject);
        Instant lastUpdateTime = Instant.now().plus(Duration.ofMinutes(1));
        scoreTracker.getLastUpdateTimes().put(mockScoringObject, lastUpdateTime);
        scoreTracker.update();
        assertEquals(0, scoreTracker.getScore());
    }
}
