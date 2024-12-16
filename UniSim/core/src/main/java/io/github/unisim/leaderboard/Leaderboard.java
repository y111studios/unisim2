package io.github.unisim.leaderboard;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

public class Leaderboard {
    static final String FILE_ADDRESS = "leaderboard.json";
    private static final int MAX_ENTRIES = 10;

    private List<LeaderboardEntry> entries;

    public List<LeaderboardEntry> entries() {
        return entries;
    }

    public Leaderboard() {
        if (fileExists()) {
            load();
        } else {
            createFile();
            entries = new ArrayList<>(MAX_ENTRIES);
            save();
        }
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

    public boolean addEntry(LeaderboardEntry newEntry) {
        boolean shouldAdd = entries.size() < MAX_ENTRIES;
        shouldAdd |= entries.stream().anyMatch(e -> e.score() < newEntry.score());
        if (!shouldAdd) {
            return false;
        }
        if (entries.size() == MAX_ENTRIES) {
            entries.remove(MAX_ENTRIES - 1);
        }
        entries.add(newEntry);
        sort();
        return true;
    }

    public void load() {
        entries = new ArrayList<>(MAX_ENTRIES);
        FileHandle f = getFile();
        JsonValue root = new JsonReader().parse(f);
        for (JsonValue entry : root) {
            entries.add(new LeaderboardEntry(entry.getString("name"), entry.getInt("score")));
        }
        sort();
    }

    private void sort() {
        entries.sort((a, b) -> Integer.compare(b.score(), a.score()));
    }

    private static FileHandle getFile() {
        return Gdx.files.local(FILE_ADDRESS);
    }

    static boolean fileExists() {
        return getFile().exists();
    }

    private static void createFile() {
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
