package io.github.unisim.achievements;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

public class AchievementManager {

    final static String DEFAULT_FILE_PATH = "achievements.json";
    private FileHandle fileHandle;

    List<Achievement> achievements;
    Set<Achievement> sessionAchievements;

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

    public void clearSessionAchievements() {
        sessionAchievements.clear();
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public Iterator<Function<Integer, Integer>> getUnlockedScoreModifiers() {
        return sessionAchievements.stream()
            .sorted((a, b) -> (a.functionTemplate.compareTo(b.functionTemplate)))
            .map(Achievement::getScoreModifier)
            .iterator();
    }

    public boolean unlockAchievement(DefinedAchievements achievement) {
        Achievement a = getAchievement(achievement);
        if (a.unlock()) {
            save();
        }
        return sessionAchievements.add(a);
    }

    public final void save() {
        JsonValue root = new JsonValue(JsonValue.ValueType.array);
        achievements.stream().map(Achievement::toJsonValue).forEach(root::addChild);
        fileHandle.writeString(root.toJson(JsonWriter.OutputType.json), false);
    }

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
            Achievement achievement = new Achievement(name, description, unlockTime, functionTemplate,
                    scoreModifierValue, progress, unlocked, hidden);
            achievements.add(achievement);
        }
        Optional<List<DefinedAchievements>> undefinedAchievements =
                DefinedAchievements.getMissingAchievements(achievements);
        if (undefinedAchievements.isPresent()) {
            undefinedAchievements.get().forEach(a -> achievements.add(new Achievement(a)));
            save();
        }
    }

    private boolean createFile() {
        try {
            return fileHandle.file().createNewFile();
        } catch (Exception e) {
            Gdx.app.error("AchievementManager file creation", "Failed to create achievements file", e);
        }
        return true;
    }

    public Achievement getAchievement(DefinedAchievements achievement) {
        return getAchievement(achievement.name).get();
    }

    @Deprecated
    public Optional<Achievement> getAchievement(String string) {
        return achievements.stream().filter((a) -> a.name.equals(string)).findFirst();
    }

    public Set<Achievement> getSessionAchievements() {
        return sessionAchievements;
    }
}
