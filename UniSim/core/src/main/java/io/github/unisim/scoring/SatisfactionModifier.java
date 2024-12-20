package io.github.unisim.scoring;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;
import io.github.unisim.achievements.ScoreModifierTemplate;

public class SatisfactionModifier implements Comparable<SatisfactionModifier> {
    private final float value;
    private final ScoreModifierTemplate template;
    private final Instant endTime;

    public SatisfactionModifier(ScoreModifierTemplate template, float value, Duration duration) {
        this.value = value;
        this.template = template;
        this.endTime = Instant.now().plus(duration);
    }

    public Function<Float, Float> getModifierFunction() {
        switch (template) {
            case ADD:
                return (score) -> score + value;
            case MUL:
                return (score) -> score * value;
        }
        throw new IllegalStateException("Unknown template: " + template);
    }

    public boolean isActive() {
        return Instant.now().isBefore(endTime);
    }

    public boolean isExpired() {
        return Instant.now().isAfter(endTime);
    }

    @Override
    public int compareTo(SatisfactionModifier other) {
        return template.compareTo(other.template);
    }

}
