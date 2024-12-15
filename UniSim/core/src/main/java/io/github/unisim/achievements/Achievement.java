package io.github.unisim.achievements;

import java.time.Instant;
import java.util.function.Function;
import com.badlogic.gdx.utils.JsonValue;

public class Achievement {

    String name;
    String description;
    Instant unlockTime;
    ScoreModifierTemplate functionTemplate;
    float scoreModifierValue;
    float progress;
    boolean unlocked;
    boolean hidden;

    public Achievement(String name, String description, Instant unlockTime,
            ScoreModifierTemplate functionTemplate, float scoreModifierValue, float progress,
            boolean unlocked, boolean hidden) {
        this.name = name;
        this.description = description;
        this.unlockTime = unlockTime;
        this.functionTemplate = functionTemplate;
        this.scoreModifierValue = scoreModifierValue;
        this.progress = progress;
        this.unlocked = unlocked;
        this.hidden = hidden;
    }

    public JsonValue toJsonValue() {
        JsonValue json = new JsonValue(JsonValue.ValueType.object);
        json.addChild("name", new JsonValue(name));
        json.addChild("description", new JsonValue(description));
        json.addChild("unlockTime", new JsonValue(unlockTime.toEpochMilli()));
        json.addChild("functionTemplate", new JsonValue(functionTemplate.name()));
        json.addChild("scoreModifierValue", new JsonValue(scoreModifierValue));
        json.addChild("progress", new JsonValue(progress));
        json.addChild("unlocked", new JsonValue(unlocked));
        json.addChild("hidden", new JsonValue(hidden));
        return json;
    }

    enum ScoreModifierTemplate {
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

}
