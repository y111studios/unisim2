package io.github.unisim.scoring;

import java.time.Duration;

/**
 * An interface that represents an object that can be used with the {@link ScoreTracker}.
 */
public interface ScoringObject {
    float getScore();

    Duration getUpdateInterval();
}
