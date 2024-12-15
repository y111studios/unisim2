package io.github.unisim.achievements;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

public class AchievementManager {

    final static String FILE_PATH = "achievements.json";

    List<Achievement> achievements;

    public AchievementManager() {
        if (fileExists()) {
            load();
        } else {
            createFile();
            achievements = new ArrayList<>();
            save();
        }
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void save() {
        JsonValue root = new JsonValue(JsonValue.ValueType.array);
        achievements.stream().map(Achievement::toJsonValue).forEach(root::addChild);
        getFile().writeString(root.toJson(JsonWriter.OutputType.json), false);
    }

    public void load() {
        achievements = new ArrayList<>();
        FileHandle f = getFile();
        JsonValue root = new JsonReader().parse(f);
        for (JsonValue json : root) {
            Achievement achievement = new Achievement(
                json.getString("name"),
                json.getString("description"),
                Instant.ofEpochMilli(json.getLong("unlockTime")),
                Achievement.ScoreModifierTemplate.valueOf(json.getString("functionTemplate")),
                json.getFloat("scoreModifierValue"),
                json.getFloat("progress"),
                json.getBoolean("unlocked"),
                json.getBoolean("hidden")
            );
            achievements.add(achievement);
        }
    }

    private FileHandle getFile() {
        return new FileHandle(FILE_PATH);
    }

    private boolean fileExists() {
        return getFile().exists();
    }

    private void createFile() {
        try {
            getFile().file().createNewFile();
        } catch (Exception e) {
            // Log the error
            Gdx.app.error("Leaderboard file creation", "Failed to create leaderboard file", e);
            // Exit the program as the file is required
            System.exit(1);
        }
    }

}
