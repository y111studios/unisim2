package io.github.unisim.leaderboard;

/**
 * Record containing the name and score for the leaderboard.
 */
public record LeaderboardEntry(String name, int score) {
    /**
     * Constructs a new leaderboard entry with the specified name and score.
     *
     * @param name The name of the player.
     * @param score The score of the player.
     * @throws IllegalArgumentException if the name is null, empty, or if the score is negative.
     */
    public LeaderboardEntry {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (score < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
    }
}
