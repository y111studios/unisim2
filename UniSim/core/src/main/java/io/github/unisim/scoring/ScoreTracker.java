package io.github.unisim.scoring;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ScoreTracker {

    private float score;
    private Map<ScoringObject, Instant> lastUpdateTimes;

    public ScoreTracker() {
        score = 0;
        lastUpdateTimes = new HashMap<>();
    }

    public ScoreTracker addScoreObject(ScoringObject scoringObject) {
        lastUpdateTimes.put(scoringObject, Instant.now());
        return this;
    }

    public int getFinalScore() {
        return Math.round(score);
    }

    public float getScore() {
        return score;
    }

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

    public Map<ScoringObject, Instant> getLastUpdateTimes() {
        return lastUpdateTimes;
    }

}
