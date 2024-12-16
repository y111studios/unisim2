package io.github.unisim.leaderboard;

public record LeaderboardEntry(String name, int score) {
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
