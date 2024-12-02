package io.github.unisim.finance;

import java.time.Duration;
import java.time.Instant;
import io.github.unisim.GameState;

public class MoneyTracker {
    private static final Duration MONEY_UPDATE_INTERVAL = Duration.ofSeconds(1);
    private static final int MONEY_UPDATE_AMOUNT = 100;

    private int money;
    private Instant lastUpdateTime;

    public MoneyTracker(int initialMoney) {
        money = initialMoney;
        lastUpdateTime = Instant.now();
    }

    public int getMoney() {
        return money;
    }

    public boolean subtractMoney(int amount) {
        if (money < amount) {
            return false;
        }
        money -= amount;
        return true;
    }

    public void updateMoney() {
        if (GameState.paused) {
            return;
        }
        Instant currentTime = Instant.now();
        Duration timeSinceLastUpdate = Duration.between(lastUpdateTime, currentTime);
        if (timeSinceLastUpdate.compareTo(MONEY_UPDATE_INTERVAL) >= 0) {
            System.out.println("Updating money");
            money += MONEY_UPDATE_AMOUNT;
            System.out.println("Money: " + money);
            lastUpdateTime = currentTime;
        }
    }
}
