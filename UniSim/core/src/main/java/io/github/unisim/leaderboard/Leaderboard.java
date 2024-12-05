package io.github.unisim.leaderboard;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

public class Leaderboard {
    private static final String FILE_ADDRESS = "leaderboard.json";
    private static final int MAX_ENTRIES = 10;

    private List<LeaderboardEntry> entries;

    public Leaderboard() {
        if (!fileExists()) {
            createFile();
        }
        load();
    }

    public void save() {
        FileHandle f = getFile();
        JsonValue root = new JsonValue(JsonValue.ValueType.array);
        for (LeaderboardEntry entry : entries) {
            JsonValue entryJson = new JsonValue(JsonValue.ValueType.object);
            entryJson.addChild("name", new JsonValue(entry.name()));
            entryJson.addChild("score", new JsonValue(entry.score()));
            root.addChild(entryJson);
        }
        f.writeString(root.toJson(JsonWriter.OutputType.json), false);
    }

    public void load() {
        entries = new ArrayList<>(MAX_ENTRIES);
        FileHandle f = getFile();
        JsonValue root = new JsonReader().parse(f);
        for (JsonValue entry : root) {
            entries.add(new LeaderboardEntry(entry.getString("name"), entry.getInt("score")));
        }
    }

    private FileHandle getFile() {
        return Gdx.files.local(FILE_ADDRESS);
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
