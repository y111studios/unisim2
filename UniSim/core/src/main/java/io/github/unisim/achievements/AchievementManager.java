package io.github.unisim.achievements;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

/**
 * Manager class for achievements in the game.
 */
public class AchievementManager {

    /**
     * The default file path for the achievements file. This is used when the game is played.
     */
    final static String DEFAULT_FILE_PATH = "achievements.json";
    /**
     * The file handle for this manager's achievements file.
     */
    private FileHandle fileHandle;

    /**
     * A list containing all of the achievements in the game. This includes both unlocked and locked
     * achievements.
     */
    List<Achievement> achievements;
    /**
     * A set containing all of the achievements that have been unlocked in the current session.
     */
    Set<Achievement> sessionAchievements;

    /**
     * Default constructor that creates a new achievement manager with the default file path.
     */
    public AchievementManager() {
        this(Gdx.files.local(DEFAULT_FILE_PATH));
    }

    /**
     * Constructor for testing purposes
     *
     * This constructor allows for definition of a test file to be used for testing
     *
     * @param fileHandle The file handle to be used for testing
     */
    AchievementManager(FileHandle fileHandle) {
        this.fileHandle = fileHandle;
        sessionAchievements = new HashSet<>();
        if (createFile()) {
            achievements = new ArrayList<>();
            save();
        }
        load();
    }

    /**
     * Clears the session achievements set.
     */
    public void clearSessionAchievements() {
        sessionAchievements.clear();
    }

    /**
     * Returns a list of all achievements in the game.
     *
     * @return a list of all achievements in the game
     */
    public List<Achievement> getAchievements() {
        return achievements;
    }

    /**
     * Returns an iterator over all of the score modifier functions for the achievements that have
     * been unlocked in the current session.
     *
     * <p>
     * The score modifier functions are sorted by their template from {@link ScoreModifierTemplate}.
     * </p>
     *
     * @return an iterator over the unlocked achievement's score modifier functions
     */
    public Iterator<Function<Integer, Integer>> getUnlockedScoreModifiers() {
        return getUnlockedSessionAchievements()
                .sorted((a, b) -> (a.functionTemplate.compareTo(b.functionTemplate)))
                .map(Achievement::getScoreModifier).iterator();
    }

    /**
     * Unlocks the achievement
     *
     * <p>
     * This method will unlock the achievement and save the updated achievement to the file then
     * return whether the achievement was unlocked for the first time since the session started.
     * </p>
     *
     * @param achievement the achievement to unlock
     * @return if the achievement was unlocked for the first time since the session started
     */
    public boolean unlockAchievement(DefinedAchievements achievement) {
        Achievement a = getAchievement(achievement);
        if (a.unlock()) {
            save();
        }
        return sessionAchievements.add(a);
    }

    /**
     * Progresses the achievement by the given amount.
     *
     * <p>
     * This method will progress the achievement by the given amount, up to 100%. If the achievement is
     * already unlocked, this method does nothing. This function returns false if the achievement was
     * already unlocked or if the percentage is not finite.
     * </p>
     * <p>
     * This method will save the updated achievement to the file and return whether the achievement was
     * unlocked for the first time since the session started.
     * </p>
     *
     * @param achievement the achievement to progress
     * @param progress the percentage to progress by, expected to be within [0, 1]
     * @return if the achivement was unlocked for the first time since the session started
     */
    public boolean progressAchievement(DefinedAchievements achievement, float progress) {
        Achievement a = getAchievement(achievement);
        if (a.progress(progress)) {
            if (a.isUnlocked()) {
                save();
                return sessionAchievements.add(a);
            }
        }
        return false;
    }

    /**
     * Saves all of the achievements to the file as a JSON array.
     */
    public final void save() {
        JsonValue root = new JsonValue(JsonValue.ValueType.array);
        achievements.stream().map(Achievement::toJsonValue).forEach(root::addChild);
        fileHandle.writeString(root.toJson(JsonWriter.OutputType.json), false);
    }

    /**
     * Loads all of the achievements from the file.
     *
     * <p>
     * This method will load all achievements from the file and store them into the achievements
     * list. If any achievements are missing, they will be added to the list and saved to the file.
     * </p>
     */
    public final void load() {
        achievements = new ArrayList<>();
        JsonValue root = new JsonReader().parse(fileHandle);
        for (JsonValue json : root) {
            String name = json.getString("name");
            String description = json.getString("description");
            Instant unlockTime = Instant.ofEpochMilli(json.getLong("unlockTime"));
            ScoreModifierTemplate functionTemplate =
                    ScoreModifierTemplate.valueOf(json.getString("functionTemplate"));
            float scoreModifierValue = json.getFloat("scoreModifierValue");
            float progress = json.getFloat("progress");
            boolean unlocked = json.getBoolean("unlocked");
            boolean hidden = json.getBoolean("hidden");
            Achievement achievement = new Achievement(name, description, unlockTime,
                    functionTemplate, scoreModifierValue, progress, unlocked, hidden);
            achievements.add(achievement);
        }
        if (updateAchievementsToDefinitions(achievements)) {
            // Save the updated achievements since there was a change
            save();
        }
        Optional<List<DefinedAchievements>> undefinedAchievements =
                DefinedAchievements.getMissingAchievements(achievements);
        if (undefinedAchievements.isPresent()) {
            undefinedAchievements.get().forEach(a -> achievements.add(new Achievement(a)));
            save();
        }
    }

    /**
     * Updates the fixed fields of the achievements to match the definitions.
     *
     * <p>
     * This function works in place and modifies the list of achievements internally. This creates a
     * new instance of the achievement if any of the fixed fields do not match the definitions that
     * replace the old achievement.
     * </p>
     * <p>
     * This method will update the achievements to match the definitions. This uses the names of
     * the achievements to match them to the definitions, so the names must be unique and not change.
     * </p>
     * <p>
     * This method will update the fixed fields defined in the {@link DefinedAchievements} enum. This
     * function will not modify the changing fields of the achievements, such as the progress or
     * unlock fields.
     * </p>
     *
     * @param achievements the list of achievements to update
     * @return if any achievements were updated
     */
    private static final boolean updateAchievementsToDefinitions(List<Achievement> achievements) {
        boolean hadUpdate = false;
        int index = 0;
        for (Achievement a : achievements) {
            Optional<DefinedAchievements> definitionOptional = DefinedAchievements.getByName(a.name);
            if (definitionOptional.isEmpty()) {
                continue;
            }
            DefinedAchievements definition = definitionOptional.get();
            boolean shouldUpdate = !(a.description.equals(definition.description)
                    && a.functionTemplate.equals(definition.functionTemplate)
                    && a.scoreModifierValue == definition.scoreModifierValue
                    && a.hidden == definition.hidden);
            if (shouldUpdate) {
                hadUpdate = true;
                Achievement newAchievement = new Achievement(definition);
                // Maintain the tracked fields states
                newAchievement.progress = a.progress;
                newAchievement.unlocked = a.unlocked;
                newAchievement.unlockTime = a.unlockTime;
                achievements.set(index, newAchievement);
                Gdx.app.log("AchievementManager update",
                        "Updated achievement " + a.name + " to match definition");
            }
            index += 1;
        }
        // Log if there was an update
        if (hadUpdate) {
            Gdx.app.log("AchievementManager update", "Updated achievements to match definitions");
            return true;
        }
        return false;
    }

    /**
     * Creates an empty file for the achievements if it does not exist.
     *
     * @return true if the file was created, false otherwise
     */
    private boolean createFile() {
        try {
            return fileHandle.file().createNewFile();
        } catch (Exception e) {
            Gdx.app.error("AchievementManager file creation", "Failed to create achievements file",
                    e);
        }
        return true;
    }

    /**
     * Gets the achievement from the list of achievements by its definition.
     *
     * @param achievement the definition of the achievement
     * @return the achievement
     */
    public Achievement getAchievement(DefinedAchievements achievement) {
        return getAchievement(achievement.name).get();
    }

    /**
     * Gets the achievement from the list of achievements by its name.
     *
     * @param string the name of the achievement
     * @return the achievement if it exists, otherwise an empty optional
     */
    private Optional<Achievement> getAchievement(String string) {
        return achievements.stream().filter((a) -> a.name.equals(string)).findFirst();
    }

    /**
     * Returns a stream of all the achievements unlocked during the current session.
     *
     * @return a stream of all the achievements unlocked during the current session
     */
    public Stream<Achievement> getUnlockedSessionAchievements() {
        return sessionAchievements.stream().filter(Achievement::isUnlocked);
    }
}
