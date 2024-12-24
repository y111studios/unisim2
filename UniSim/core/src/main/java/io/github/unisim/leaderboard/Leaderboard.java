package io.github.unisim.leaderboard;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

/**
 * A class that represents the leaderboard.
 */
public class Leaderboard {
    static final String FILE_ADDRESS = "leaderboard.json";
    private static final int MAX_ENTRIES = 10;

    private List<LeaderboardEntry> entries;

    public List<LeaderboardEntry> entries() {
        return entries;
    }

    /**
     * Constructs a new leaderboard, loading the entries from the file if it exists, otherwise
     * creating a new file.
     */
    public Leaderboard() {
        if (createFile()) {
            entries = new ArrayList<>(MAX_ENTRIES);
            save();
        } else {
            load();
        }
    }

    /**
     * Saves the leaderboard to the file as a JSON array.
     */
    public final void save() {
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

    /**
     * Adds a new entry to the leaderboard if it is within the top {@value #MAX_ENTRIES} entries.
     * If the entry is not added, the method returns false.
     *
     * @param newEntry The new entry to add.
     * @return true if the entry was added, false otherwise.
     */
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

    /**
     * Loads the leaderboard from the file.
     */
    public final void load() {
        entries = new ArrayList<>(MAX_ENTRIES);
        FileHandle f = getFile();
        JsonValue root = new JsonReader().parse(f);
        for (JsonValue entry : root) {
            entries.add(new LeaderboardEntry(entry.getString("name"), entry.getInt("score")));
        }
        sort();
    }

    /**
     * Sorts the entries in descending order by score.
     */
    private final void sort() {
        entries.sort((a, b) -> Integer.compare(b.score(), a.score()));
    }

    private static FileHandle getFile() {
        return Gdx.files.local(FILE_ADDRESS);
    }

    static boolean fileExists() {
        return getFile().exists();
    }

    /**
     * Creates an empty file for the leaderboard if it does not exist.
     *
     * @return true if the file was created, false otherwise
     */
    private static boolean createFile() {
        try {
            return getFile().file().createNewFile();
        } catch (Exception e) {
            Gdx.app.error("Leaderboard file creation", "Failed to create leaderboard file", e);
        }
        return true;
    }
}
