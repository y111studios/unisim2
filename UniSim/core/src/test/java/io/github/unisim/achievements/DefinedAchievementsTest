package io.github.unisim.achievements;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.files.FileHandle;

public class DefinedAchievementsTest {

    private AchievementManager achievementManager;
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

        final String path = String.format("%s-%s", DefinedAchievementsTest.class.getSimpleName(), AchievementManager.DEFAULT_FILE_PATH);
        testFileHandle = Gdx.files.local(path);
    }

    // Testing constructor
    @Test
    public void testConstructor() {
        DefinedAchievements[] values = DefinedAchievements.values();
        assertNotNull(values);
        assertEquals(2, values.length); // Change expected value as achievements are added
        assertEquals(DefinedAchievements.Bankruptcy, values[0]);
        assertEquals(DefinedAchievements.Capitalist, values[1]);
    }

    @Test
    public void testGetNames() {
        HashSet<String> names = new HashSet<>();
        for (DefinedAchievements achievement : DefinedAchievements.values()) {
            names.add(achievement.name());
        }
        assertNotNull(names);
        assertTrue(names.contains("Bankruptcy"));
        assertTrue(names.contains("Capitalist"));
    }

    // Testing getMissingAchievements
    @Test
    public void testGetMissingAchievements() {
        Achievement achievement1 = new Achievement("Test1", "Desc1", Instant.EPOCH, ScoreModifierTemplate.ADD, 0, 0, false, false);
        Achievement achievement2 = new Achievement("Test2", "Desc2", Instant.EPOCH, ScoreModifierTemplate.ADD, 0, 0, false, false);

        Iterable <Achievement> achievements = List.of(achievement1, achievement2);
        achievementManager = new AchievementManager(testFileHandle);

        List<DefinedAchievements> missingAchievements = DefinedAchievements.getMissingAchievements(achievements).get();
        assertEquals(2, missingAchievements.size());
        System.out.println("Missing achievements: " + missingAchievements);
        assertTrue(missingAchievements.contains(DefinedAchievements.Bankruptcy));
        assertTrue(missingAchievements.contains(DefinedAchievements.Capitalist));
    }

}
