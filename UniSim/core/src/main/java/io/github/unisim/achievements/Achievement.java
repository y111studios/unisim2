package io.github.unisim.achievements;

import java.time.Instant;
import java.util.function.Function;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
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

    private static String MISSING_ICON_PATH = "achievements/missing_icon.png";

    public Achievement(DefinedAchievements definition) {
        this(definition.name, definition.description, Instant.EPOCH, definition.functionTemplate,
                definition.scoreModifierValue, 0, false, definition.hidden);
    }

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

    public Texture getIcon() {
        final String path = String.format("achievements/%s.png", name);
        FileHandle imageFile = Gdx.files.internal(path);
        if (imageFile.exists()) {
            return new Texture(imageFile);
        } else {
            return new Texture(Gdx.files.internal(MISSING_ICON_PATH));
        }
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public Function<Integer, Integer> getScoreModifier() {
        return functionTemplate.getFunction(scoreModifierValue);
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

}
