package io.github.unisim.finance;

import java.time.Duration;
import java.time.Instant;

/**
 * A class that keeps track of the player's money.
 */
public class MoneyTracker {
    /**
     * The interval at which money is increased.
     */
    static final Duration MONEY_UPDATE_INTERVAL = Duration.ofSeconds(1);
    /**
     * The amount of money to increase by every {@link #MONEY_UPDATE_INTERVAL} per student.
     */
    static final int MONEY_UPDATE_AMOUNT_PER_STUDENT = 5;
    /**
     * How many students are needed to increase the money by {@link #MONEY_UPDATE_AMOUNT_PER_STUDENT}.
     */
    static final int STUDENT_GROUPING = 12;

    int money;
    Instant lastUpdateTime;

    /**
     * Constructs a MoneyTracker object with the specified initial amount of money.
     *
     * @param initialMoney The initial amount of money.
     */
    public MoneyTracker(int initialMoney) {
        money = Math.max(initialMoney, 0);
        lastUpdateTime = Instant.now();
    }

    public int getMoney() {
        return money;
    }

    /**
     * Subtracts the specified amount of money from the player's money.
     *
     * <h2>Behavior:</h2>
     * <ul>
     *    <li>If the specified amount is negative, nothing happens.</li>
     *    <li>If the player does not have enough money, nothing happens.</li>
     *    <li>Otherwise, the specified amount is subtracted from the player's money.</li>
     * </ul>
     *
     * @param amount The amount of money to subtract.
     * @return true if money was successfully subtracted, false otherwise.
     */
    public boolean subtractMoney(int amount) {
        if (amount < 0) {
            return false;
        }
        if (money < amount) {
            return false;
        }
        money -= amount;
        return true;
    }

    /**
     * Tries to update the player's money.
     *
     * <h2>Behavior:</h2>
     * <ul>
     *   <li>If the game is over or paused, nothing happens.</li>
     *   <li>Otherwise, if the time since the last update is greater than or equal to
     *       {@link #MONEY_UPDATE_INTERVAL}, {@link #MONEY_UPDATE_AMOUNT_PER_STUDENT} * studentCount
     *       is added to the player's money and the last update time is updated.
     *   </li>
     * </ul>
     *
     * @param studentCount The number of students in the game.
     */
    public void updateMoney(int studentCount) {
        studentCount = Math.max(studentCount, 0); // Clamp negative values to 0
        Instant currentTime = Instant.now();
        Duration timeSinceLastUpdate = Duration.between(lastUpdateTime, currentTime);
        if (timeSinceLastUpdate.compareTo(MONEY_UPDATE_INTERVAL) >= 0) {
            addMoney(MONEY_UPDATE_AMOUNT_PER_STUDENT * studentCount / STUDENT_GROUPING);
            lastUpdateTime = currentTime;
        }
    }

    /**
     * Adds the specified amount of money to the player's money.
     *
     * <p>
     * This function is a no-op if the specified amount is negative.
     * </p>
     *
     * @param amount The amount of money to add.
     */
    public void addMoney(int amount) {
        if (amount < 0) {
            return;
        }
        money += amount;
    }
}
