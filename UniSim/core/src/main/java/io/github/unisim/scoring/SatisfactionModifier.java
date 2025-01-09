package io.github.unisim.scoring;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;
import io.github.unisim.achievements.ScoreModifierTemplate;

/**
 * A class that represents a satisfaction modifier.
 */
public class SatisfactionModifier implements Comparable<SatisfactionModifier> {
    public final float value;
    public final ScoreModifierTemplate template;
    public final Duration duration;
    Instant endTime;

    /**
     * Constructs a new satisfaction modifier with the specified template, value, and duration.
     *
     * @param template The template of the modifier.
     * @param value The value for the template to use.
     * @param duration The duration of the modifier.
     */
    public SatisfactionModifier(ScoreModifierTemplate template, float value, Duration duration) {
        this.value = value;
        this.template = template;
        this.duration = duration;
        renewEndTime();
    }

    /**
     * Renews the end time of the modifier to the current time plus the duration. This method should
     * be called whenever the modifier is activated after initialization.
     */
    public void renewEndTime() {
        endTime = Instant.now().plus(duration);
    }

    /**
     * Returns the function that modifies the score based on the template and value of the modifier.
     *
     * @return The function that modifies the score.
     */
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
