package io.github.unisim.achievements;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.utils.JsonValue;

public class AchievementsTest {

    // Testing constructor
    @Test
    public void testConstructor() {
        String description = "Lose all your money";
        boolean unlocked = false;
        Achievement achievement = new Achievement(DefinedAchievements.Bankruptcy);

        assertEquals("Bankruptcy", achievement.getName());
        assertEquals(description, achievement.getDescription());
        assertEquals(unlocked, achievement.isUnlocked());
    }

    // Testing getName
    @Test
    public void testGetName() {
        Achievement achievement = new Achievement(DefinedAchievements.Bankruptcy);        
        assertEquals("Bankruptcy", achievement.getName());
        Achievement achievement2 = new Achievement(DefinedAchievements.Capitalist);        
        assertEquals("Capitalist", achievement2.getName());
        Achievement achievement3 = new Achievement(DefinedAchievements.Minimalist);        
        assertEquals("Minimalist", achievement3.getName());
    }

    // Testing getDescription
    @Test
    public void testGetDescription() {
        Achievement achievement = new Achievement(DefinedAchievements.Bankruptcy);        
        assertEquals("Lose all your money", achievement.getDescription());
        Achievement achievement2 = new Achievement(DefinedAchievements.Capitalist);        
        assertEquals("Earn 10,000 money", achievement2.getDescription());
        Achievement achievement3 = new Achievement(DefinedAchievements.Minimalist);        
        assertEquals("Place 5 buildings or less", achievement3.getDescription());
    }

    // Testing isUnlcoked
    @Test
    public void testIsUnlocked() {
        Achievement achievement = new Achievement(DefinedAchievements.Bankruptcy);  
        assertEquals(false, achievement.isUnlocked());
        achievement.unlock();
        assertEquals(true, achievement.isUnlocked());
    }

    // Testing getScoreModifier
    @Test
    public void testGetScoreModifier() {
        Achievement achievement = new Achievement(DefinedAchievements.Bankruptcy);  
        assertEquals(25f, achievement.scoreModifierValue);
        Achievement achievement2 = new Achievement(DefinedAchievements.Capitalist);  
        assertEquals(100f, achievement2.scoreModifierValue);
        Achievement achievement3 = new Achievement(DefinedAchievements.Minimalist);  
        assertEquals(10f, achievement3.scoreModifierValue);
    }

    // Testing toJsonValue
    @Test
    public void testToJsonValueLocked() {
        Achievement achievement = new Achievement(DefinedAchievements.Bankruptcy);  
        JsonValue jsonValue = achievement.toJsonValue();

        assertNotNull(jsonValue);
        assertEquals("Bankruptcy", jsonValue.getString("name"));
        assertEquals(0, jsonValue.getLong("unlockTime"));
        assertEquals(0, jsonValue.getFloat("progress"));
        assertFalse(jsonValue.getBoolean("unlocked"));
    }

    @Test
    public void testToJsonValueUnlocked() {
        Achievement achievement = new Achievement(DefinedAchievements.Bankruptcy);  
        Instant unlockTime = Instant.now();
        achievement.unlock();
        JsonValue jsonValue = achievement.toJsonValue();

        assertNotNull(jsonValue);
        assertEquals("Bankruptcy", jsonValue.getString("name"));
        assertEquals(unlockTime.toEpochMilli(), jsonValue.getLong("unlockTime"));
        assertEquals(1, jsonValue.getFloat("progress"));
        assertTrue(jsonValue.getBoolean("unlocked"));
    }

    @Test
    public void testProgressUnlocked() {
        Achievement achievement = new Achievement(DefinedAchievements.Bankruptcy);  
        assertFalse(achievement.progress(0.5f));
    }

    @Test
    public void testProgressInvalidValue() {
        Achievement achievement = new Achievement(DefinedAchievements.Bankruptcy);  
        assertFalse(achievement.progress(Float.POSITIVE_INFINITY));
        assertFalse(achievement.progress(Float.NaN));
    }

    @Test
    public void testProgressPartial() {
        Achievement achievement = new Achievement(DefinedAchievements.Bankruptcy);  
        assertFalse(achievement.progress(0.5f));
        assertEquals(0.5f, achievement.progress);
    }

    @Test
    public void testProgressComplete() {
        Achievement achievement = new Achievement(DefinedAchievements.Bankruptcy);  
        assertTrue(achievement.progress(1.0f));
        assertEquals(1.0f, achievement.progress);
        assertTrue(achievement.isUnlocked());
        assertNotEquals(Instant.EPOCH, achievement.unlockTime);
    }

    @Test
    public void testProgressAccumulation() {
        Achievement achievement = new Achievement(DefinedAchievements.Bankruptcy);  
        assertFalse(achievement.progress(0.3f));
        assertFalse(achievement.progress(0.3f));
        assertFalse(achievement.progress(0.3f));
        assertTrue(achievement.progress(0.2f));
        assertTrue(achievement.isUnlocked());
        assertEquals(1.0f, achievement.progress);
    }
}
