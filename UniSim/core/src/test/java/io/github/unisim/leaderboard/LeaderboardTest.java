package io.github.unisim.leaderboard;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class LeaderboardTest {

    private Leaderboard leaderboard;
    private static final String FILE_ADDRESS = "leaderboard.json";

    @BeforeEach
    // Delete the file before each test
    public void setUp() {
        FileHandle file = Gdx.files.local(FILE_ADDRESS);
        if (file.exists()) {
            file.delete();
        }
        leaderboard = new Leaderboard();
    }

    // Testing the constructor
    @Test
    public void testConstructorWithFile() {
        // Create the file manually
        FileHandle file = Gdx.files.local(FILE_ADDRESS);
        file.writeString("[]", false);
        leaderboard = new Leaderboard();
        assertNotNull(leaderboard.entries());
        assertTrue(file.exists());
    }
}