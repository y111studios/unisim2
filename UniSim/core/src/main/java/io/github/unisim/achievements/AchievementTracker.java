package io.github.unisim.achievements;

/**
 * Class to track achievements conditions that do not follow the standard
 * progression system on the Achievement screen.
 */
public class AchievementTracker {

    /**
     * Maintains a count of how many buildings have been place
     */
    public int buildingsPlaced;
    /**
     * Tracks whether the satisfaction has reached 10 in this session
     */
    public boolean satisfactionHasReachedTen;
    /**
     * Tracks whether an input has been received in this session
     */
    public boolean hadInput;

    /**
     * Creates a new AchievementTracker and initialises all fields to their default values.
     */
    public AchievementTracker() {
        buildingsPlaced = 0;
        satisfactionHasReachedTen = false;
    }

}
