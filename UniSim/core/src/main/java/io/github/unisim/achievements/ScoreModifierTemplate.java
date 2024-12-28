package io.github.unisim.achievements;

import java.util.function.Function;

/**
 * Enum representing the different types of score modifiers.
 */
public enum ScoreModifierTemplate {
    ADD, MUL;

    /**
     * Get a function that applies the score modifier template using the given value.
     *
     * <h2>Example</h2>
     *
     * <pre>{@java
     * ScoreModifierTemplate.ADD.getFunction(5).apply(10); // 15
     * ScoreModifierTemplate.MUL.getFunction(2).apply(10); // 20
     * }</pre>
     *
     * @param value The value to apply the score modifier with.
     * @return A function that applies the score modifier.
     */
    public Function<Integer, Integer> getFunction(float value) {
        switch (this) {
            case ADD:
                return x -> x + (int) value;
            case MUL:
                return x -> (int) (x * value);
            default:
                throw new IllegalArgumentException("Invalid score modifier");
        }
    }
}
