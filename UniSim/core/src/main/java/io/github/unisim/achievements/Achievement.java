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
        this(definition, Instant.EPOCH, 0, false);
    }

    /**
     * Internal constructor for creating an achievement using the DefinedAchievements definition and
     * the JSON representation of the locking state of the achievement.
     *
     * <p>
     * This constructor enforces that the unlock time must be set to the epoch if the achievement is locked,
     * and that the progress must be 1 if the achievement is unlocked. If the unlock time is null and the
     * achievement is unlocked, the unlock time is set to the current time.
     * </p>
     *
     * @param definition the definition of the achievement
     * @param unlockTime the time the achievement was unlocked
     * @param progress the progress towards the achievement
     * @param unlocked whether the achievement is unlocked
     */
    Achievement(DefinedAchievements definition, Instant unlockTime, float progress, boolean unlocked) {
        this(definition.name, definition.description, unlockTime, definition.functionTemplate,
                definition.scoreModifierValue, progress, unlocked, definition.hidden);
    }

    /**
     * Internal all arg constructor that validates the parameters.
     *
     * <p>
     * This constructor enforces that the unlock time must be set to the epoch if the achievement is locked,
     * and that the progress must be 1 if the achievement is unlocked. If the unlock time is null and the
     * achievement is unlocked, the unlock time is set to the current time.
     * </p>
     *
     * @param name the name of the achievement
     * @param description the description of the achievement
     * @param unlockTime the time the achievement was unlocked
     * @param functionTemplate the template for the score modifier function
     * @param scoreModifierValue the value for the score modifier function
     * @param progress the progress towards the achievement
     * @param unlocked whether the achievement is unlocked
     * @param hidden whether the achievement is hidden
     *
     */
    private Achievement(String name, String description, Instant unlockTime,
            ScoreModifierTemplate functionTemplate, float scoreModifierValue, float progress,
            boolean unlocked, boolean hidden) {
        this.name = name;
        this.description = description;
        if (!unlocked) {
            this.unlockTime = Instant.EPOCH;
        } else if (unlockTime == null) {
            this.unlockTime = Instant.now();
        } else {
            this.unlockTime = unlockTime;
        }
        this.functionTemplate = functionTemplate;
        this.scoreModifierValue = scoreModifierValue;
        this.unlocked = unlocked;
        if (unlocked) {
            this.progress = 1;
        } else {
            this.progress = progress;
        }
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
        progress = 1;
        return true;
    }

    /**
     * Progresses the achivement by the specified percentage.
     *
     * <p>
     * If the achievement is already unlocked, this method does nothing. This function returns
     * false if the achivement was already unlocked or if the percentage is not finite.
     * </p>
     *
     * @param percentage the percentage to progress by, expected to be within [0, 1]
     * @return if the achievement was unlocked by this action
     */
    boolean progress(float percentage) {
        if (isUnlocked()) {
            return false;
        }
        if (!Float.isFinite(percentage)) {
            System.err.println("Achievement progress percentage is not finite: " + percentage);
            return false;
        }
        progress += percentage;
        if (progress >= 1) {
            return unlock();
        } else {
            return false;
        }
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
     * Returns a {@link JsonValue} representation of the non-final fields of this achievement.
     *
     * @return a {@link JsonValue} representation of this achievement
     */
    public JsonValue toJsonValue() {
        JsonValue json = new JsonValue(JsonValue.ValueType.object);
        json.addChild("name", new JsonValue(name));
        json.addChild("unlockTime", new JsonValue(unlockTime.toEpochMilli()));
        json.addChild("progress", new JsonValue(progress));
        json.addChild("unlocked", new JsonValue(unlocked));
        return json;
    }

}
