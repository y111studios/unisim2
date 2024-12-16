package io.github.unisim.achievements;

import java.util.function.Function;

public enum ScoreModifierTemplate {
    ADD, MUL;

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
