package io.github.unisim.leaderboard;

import org.junit.jupiter.api.AfterAll;
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
}
