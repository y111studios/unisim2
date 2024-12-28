package io.github.unisim.scoring;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that represents a score tracker.
 */
public class ScoreTracker {

    /**
     * The current cumulative score.
     */
    private float score;
    /**
     * A map of scoring objects and their last update times.
     */
    Map<ScoringObject, Instant> lastUpdateTimes;

    /**
     * Initialises a new score tracker with a score of 0 and an empty map of scoring objects.
     */
    public ScoreTracker() {
        score = 0;
        lastUpdateTimes = new HashMap<>();
    }

    /**
     * Adds a scoring object to the score tracker with an initial last update time of {@link Instant#MIN}.
     *
     * @param scoringObject The scoring object to add.
     * @return The score tracker.
     */
    public ScoreTracker addScoreObject(ScoringObject scoringObject) {
        lastUpdateTimes.put(scoringObject, Instant.now());
        return this;
    }

    /**
     * Returns the final score as an integer.
     * @return The final score as an integer.
     */
    public int getFinalScore() {
        return Math.round(score);
    }

    public float getScore() {
        return score;
    }

    /**
     * Updates the score tracker by trying to update the score using each score object.
     * If the last update time of a scoring object plus its update interval is less than
     * the current time then the score is updated and the last update time is set to the
     * current time.
     */
    public void update() {
        for (Map.Entry<ScoringObject, Instant> entry : lastUpdateTimes.entrySet()) {
            ScoringObject scoringObject = entry.getKey();
            Instant lastUpdateTime = entry.getValue();
            final Instant now = Instant.now();
            if (lastUpdateTime.plus(scoringObject.getUpdateInterval()).isBefore(now)) {
                float durationMultiples =  (float) ((now.toEpochMilli() - lastUpdateTime.toEpochMilli()) / scoringObject.getUpdateInterval().toMillis());
                incrementScore(scoringObject, durationMultiples);
                lastUpdateTimes.put(scoringObject, now);
            }
        }
    }

    void incrementScore(ScoringObject scoringObject, float multiplier) {
        score += scoringObject.getScore() * multiplier;
    }

}
