package io.github.unisim.scoring;

import java.time.Duration;

public interface ScoringObject {
    float getScore();
    Duration getUpdateInterval();
}
