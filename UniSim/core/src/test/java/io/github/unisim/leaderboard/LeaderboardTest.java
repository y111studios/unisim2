package io.github.unisim.leaderboard;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.files.FileHandle;

public class LeaderboardTest {

    private Leaderboard leaderboard;

    @AfterAll
    static void cleanUp() {
        // Clean up the test files
        FileHandle file = Gdx.files.local(Leaderboard.FILE_ADDRESS);
        if (file.exists()) {
            file.delete();
        }
    }

    @BeforeAll
    static void initialiseHeadlessApp() {
        // Initialise a new headless application
        if (Gdx.app == null) {
            Gdx.app = new HeadlessApplication(new ApplicationListener() {
                @Override
                public void create() {
                    throw new UnsupportedOperationException("Unimplemented method 'create'");
                }
                @Override
                public void resize(int width, int height) {
                    throw new UnsupportedOperationException("Unimplemented method 'resize'");
                }
                @Override
                public void render() {
                    throw new UnsupportedOperationException("Unimplemented method 'render'");
                }
                @Override
                public void pause() {
                    throw new UnsupportedOperationException("Unimplemented method 'pause'");
                }
                @Override
                public void resume() {
                    throw new UnsupportedOperationException("Unimplemented method 'resume'");
                }
                @Override
                public void dispose() {
                    throw new UnsupportedOperationException("Unimplemented method 'dispose'");
                }
            });
        }
    }

    @BeforeEach
    // Delete the file before each test
    public void setUp() {
        FileHandle file = Gdx.files.local(Leaderboard.FILE_ADDRESS);
        if (file.exists()) {
            file.delete();
        }
        leaderboard = new Leaderboard();
    }

    // Testing the constructor
    @Test
    public void testConstructorWithFile() {
        // Create the file manually
        FileHandle file = Gdx.files.local(Leaderboard.FILE_ADDRESS);
        file.writeString("[]", false);
        leaderboard = new Leaderboard();
        assertNotNull(leaderboard.entries());
        assertTrue(file.exists());
    }

    @Test
    public void testConstructorWithoutFile() {
        FileHandle file = Gdx.files.local(Leaderboard.FILE_ADDRESS);
        leaderboard = new Leaderboard();
        assertNotNull(leaderboard.entries());
        assertTrue(file.exists());
    }

    @Test
    public void testAddEntry() {
        leaderboard.addEntry(new LeaderboardEntry("Alice", 42));
        assertEquals(1, leaderboard.entries().size());
        assertEquals("Alice", leaderboard.entries().get(0).name());
        assertEquals(42, leaderboard.entries().get(0).score());
    }

    // Definition of example JSON strings
    private static final String EMPTY_JSON = "[]";
    private static final String SINGLE_ENTRY_JSON = "[{\"name\":\"Alice\",\"score\":42}]";
    private static final String MULTIPLE_ENTRIES_JSON = "[{\"name\":\"Alice\",\"score\":42},{\"name\":\"Bob\",\"score\":23},{\"name\":\"Charlie\",\"score\":15}]";

    @Test
    public void testLoad() {
        FileHandle file = Gdx.files.local(Leaderboard.FILE_ADDRESS);
        // Test loading 0 entries
        file.writeString(EMPTY_JSON, false);
        leaderboard.load();
        assertEquals(0, leaderboard.entries().size());
        // Test loading 1 entry
        file.writeString(SINGLE_ENTRY_JSON, false);
        leaderboard.load();
        assertEquals(1, leaderboard.entries().size());
        assertEquals("Alice", leaderboard.entries().get(0).name());
        assertEquals(42, leaderboard.entries().get(0).score());
        // Test loading multiple entries
        file.writeString(MULTIPLE_ENTRIES_JSON, false);
        leaderboard.load();
        assertEquals(3, leaderboard.entries().size());
        assertEquals("Alice", leaderboard.entries().get(0).name());
        assertEquals(42, leaderboard.entries().get(0).score());
        assertEquals("Bob", leaderboard.entries().get(1).name());
        assertEquals(23, leaderboard.entries().get(1).score());
        assertEquals("Charlie", leaderboard.entries().get(2).name());
        assertEquals(15, leaderboard.entries().get(2).score());
    }

    @Test
    public void testSave() {
        FileHandle file = Gdx.files.local(Leaderboard.FILE_ADDRESS);
        // Test saving 0 entries
        leaderboard.save();
        assertEquals(EMPTY_JSON, file.readString());
        // Test saving 1 entry
        leaderboard.addEntry(new LeaderboardEntry("Alice", 42));
        leaderboard.save();
        assertEquals(SINGLE_ENTRY_JSON, file.readString());
        // Test saving multiple entries
        leaderboard.addEntry(new LeaderboardEntry("Bob", 23));
        leaderboard.addEntry(new LeaderboardEntry("Charlie", 15));
        leaderboard.save();
        assertEquals(MULTIPLE_ENTRIES_JSON, file.readString());
    }


}
