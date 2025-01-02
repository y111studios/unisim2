package io.github.unisim.achievements;

import java.time.Instant;
import java.util.HashMap;
import com.badlogic.gdx.graphics.Texture;

/**
 * Class to track achievements conditions that do not follow the standard
 * progression system on the Achievement screen.
 */
public class AchievementTracker {

    /**
     * Tracks whether the satisfaction has reached 10 in this session
     */
    public boolean satisfactionHasReachedTen;
    /**
     * Tracks whether an input has been received in this session
     */
    public boolean hadInput;
    /**
     * Tracks how long the satisfaction has been above 75
     */
    public Instant timeSatisfactionReached75;
    /**
     * Maintains a count of how many of each building instance has been placed
     */
    public HashMap<Texture, Integer> buildingsPlacedCount;
    /**
     * Tracks when the controls screen was opened
     */
    public Instant controlsScreenOpened;

    /**
     * Creates a new AchievementTracker and initialises all fields to their default values.
     */
    public AchievementTracker() {
        satisfactionHasReachedTen = false;
        hadInput = false;
        timeSatisfactionReached75 = null;
        buildingsPlacedCount = new HashMap<>();
        controlsScreenOpened = null;
    }

}
