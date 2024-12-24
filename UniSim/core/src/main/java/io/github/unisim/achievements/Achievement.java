package io.github.unisim.achievements;

import java.time.Instant;
import java.util.function.Function;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Class representing an achievement's state in the game.
 */
public class Achievement {

    final String name;
    final String description;
    Instant unlockTime;
    final ScoreModifierTemplate functionTemplate;
    final float scoreModifierValue;
    float progress;
    boolean unlocked;
    final boolean hidden;

    private static String MISSING_ICON_PATH = "achievements/missing_icon.png";

    /**
     * Internal constructor for creating a locked achievement from a definition in
     * {@link DefinedAchievements}.
     *
     * @param definition the definition of the achievement
     */
    Achievement(DefinedAchievements definition) {
        this(definition.name, definition.description, Instant.EPOCH, definition.functionTemplate,
                definition.scoreModifierValue, 0, false, definition.hidden);
    }

    /**
     * Internal all arg constructor
     *
     * @param name the name of the achievement
     * @param description the description of the achievement
     * @param unlockTime the time the achievement was unlocked
     * @param functionTemplate the template for the score modifier function
     * @param scoreModifierValue the value for the score modifier function
     * @param progress the progress towards the achievement
     * @param unlocked whether the achievement is unlocked
     * @param hidden whether the achievement is hidden
     */
    Achievement(String name, String description, Instant unlockTime,
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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Gets the icon for this achievement.
     *
     * <p>
     * If the icon is not found, a default icon is returned.
     * </p>
     *
     * @return an {@link Image} to be used as the icon
     */
    public Image getIcon() {
        final String path = String.format("achievements/%s.png", name);
        FileHandle imageFile = Gdx.files.internal(path);
        Texture texture;
        if (imageFile.exists()) {
            texture = new Texture(imageFile);
        } else {
            texture = new Texture(Gdx.files.internal(MISSING_ICON_PATH));
        }
        return new Image(texture);
    }

    /**
     * Sets the achievement to be unlocked
     *
     * <p>
     * If the achievement is already unlocked, this method does nothing.
     * If the achievement is not unlocked, it is set to unlocked and the unlock time is set to the
     * current time.
     * </p>
     * <p>
     * This method returns whether the unlock value was changed. After this method is called, the
     * unlock value is always true.
     * </p>
     *
     * @return if the unlock value was changed
     */
    boolean unlock() {
        if (isUnlocked()) {
            return false;
        }
        unlocked = true;
        unlockTime = Instant.now();
        return true;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    /**
     * Gets the defined function that modifies the score.
     *
     * @return the function that modifies the score
     */
    public Function<Integer, Integer> getScoreModifier() {
        return functionTemplate.getFunction(scoreModifierValue);
    }

    /**
     * Returns a {@link JsonValue} representation of this achievement.
     *
     * @return a {@link JsonValue} representation of this achievement
     */
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
