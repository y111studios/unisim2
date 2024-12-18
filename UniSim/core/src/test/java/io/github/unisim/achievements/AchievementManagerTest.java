package io.github.unisim.achievements;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.files.FileHandle;

public class AchievementManagerTest {

    AchievementManager achievementManager;

    private static FileHandle testFileHandle;

    @AfterAll
    static void removeFile() {
        if (testFileHandle.exists()) {
            testFileHandle.delete();
        }
    }

    @BeforeAll
    static void initialiseHeadlessApp() {
        // Initialise a new headless application
        if (Gdx.app == null) {
            Gdx.app = new HeadlessApplication(new ApplicationListener() {
                @Override
                public void create() {}
                @Override
                public void resize(int width, int height) {}
                @Override
                public void render() {}
                @Override
                public void pause() {}
                @Override
                public void resume() {}
                @Override
                public void dispose() {}
            });
        }

        final String path = String.format("%s-%s", AchievementManagerTest.class.getSimpleName(), AchievementManager.DEFAULT_FILE_PATH);
        testFileHandle = Gdx.files.local(path);
    }

    @BeforeEach
    public void setUp() {
        achievementManager = new AchievementManager(testFileHandle);
    }

    @ParameterizedTest
    @EnumSource(DefinedAchievements.class)
    void TestAllDefinedAchievementsAreLoaded(DefinedAchievements definedAchievement) {
        // Check all achievements are loaded
        assertDoesNotThrow(() -> achievementManager.getAchievement(definedAchievement));
    }

    @ParameterizedTest
    @EnumSource(DefinedAchievements.class)
    void TestAllDefinedAchievementsAreReadded(DefinedAchievements definedAchievement) {
        achievementManager.achievements.clear();
        achievementManager.load();
        // Check all achievements are loaded
        assertDoesNotThrow(() -> achievementManager.getAchievement(definedAchievement));
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
    }

    // Testing getUnlockedScoreModifiers
    @Test
    public void testGetUnlockedachievements() {
        assertNotNull(achievementManager.getUnlockedScoreModifiers());
    }

    @Test
    void testGetDefinedAchivement() {
        DefinedAchievements definedAchievement = DefinedAchievements.values()[0];
        Achievement fetchedAchievement = achievementManager.getAchievement(definedAchievement);
        assertNotNull(fetchedAchievement);
        assertTrue(fetchedAchievement.name.equals(definedAchievement.name));
    }

    // Testing unlockAchievement
    @Test
    public void testUnlockedAchievements() {
        DefinedAchievements toUnlock = DefinedAchievements.values()[0];
        assertTrue(achievementManager.unlockAchievement(toUnlock));
        assertTrue(achievementManager.sessionAchievements.contains(achievementManager.getAchievement(toUnlock)));
    }

    @Test
    public void testUnlockAlreadyUnlockedAchievement() {
        DefinedAchievements toUnlock = DefinedAchievements.values()[0];
        assertTrue(achievementManager.unlockAchievement(toUnlock));
        boolean result = achievementManager.unlockAchievement(toUnlock);
        assertFalse(result);
    }
}
