package io.github.unisim.achievements;

import java.time.Instant;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.files.FileHandle;

public class AchievementManagerTest {

    AchievementManager achievementManager;
    Achievement achievementUnlocked;
    Achievement achievementLocked;

    @AfterAll
    static void removeFile() {
        FileHandle file = AchievementManager.getFile();
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
    public void setUp() {
        achievementManager = new AchievementManager();
        achievementUnlocked = new Achievement("Test1", "Description", Instant.EPOCH, ScoreModifierTemplate.ADD, 0, 0, false, false);
        achievementLocked = new Achievement("Test2", "Description", Instant.EPOCH, ScoreModifierTemplate.ADD, 0, 0, true, false);

    }

    // Testing constructor
    @Test
    public void testAchievementManager() {
        assertNotNull(achievementManager);
    }

    // Testing clearSessionAchievements
    @Test
    public void testClearSessionAchievements() {
        achievementManager.clearSessionAchievements();
        assertTrue(achievementManager.getSessionAchievements().isEmpty());
    }

    // Testing getAchievements
    @Test
    public void testGetAchievements() {
        assertNotNull(achievementManager.getAchievements());
        System.out.println(achievementManager.getAchievements());
    }

    // Testing getUnlockedScoreModifiers
    @Test
    public void testGetUnlockedachievements() {
        assertNotNull(achievementManager.getUnlockedScoreModifiers());
    }

    // Testing unlockAchievement
    @Test
    public void testUnlockedAchievements() {
        achievementManager.unlockAchievement("Test2");
        assertFalse(achievementManager.getAchievements().contains(achievementLocked));
    }

    // Testing
    @Test
    public void testUnlockNonExistentAchievement() {
        boolean result = achievementManager.unlockAchievement("Non-existent");
        assertFalse(result);
    }

    @Test
    public void testUnlockAlreadyUnlockedAchievement() {
        achievementManager.unlockAchievement("Test1");
        boolean result = achievementManager.unlockAchievement("Test1");
        assertFalse(result);
    }
}
