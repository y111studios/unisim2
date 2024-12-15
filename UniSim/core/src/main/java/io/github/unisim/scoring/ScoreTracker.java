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
        lastUpdateTimes.put(scoringObject, Instant.MIN);
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
            if (lastUpdateTime.plus(scoringObject.getUpdateInterval()).compareTo(now) <= 0) {
                score += scoringObject.getScore();
                lastUpdateTimes.put(scoringObject, now);
            }
        }
    }

    public Map<ScoringObject, Instant> getLastUpdateTimes() {
        return lastUpdateTimes;
    }

}
