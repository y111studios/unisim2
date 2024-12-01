package io.github.unisim.finance;

public class MoneyTracker {
    private int money;

    public MoneyTracker(int initialMoney) {
        money = initialMoney;
    }

    public int getMoney() {
        return money;
    }
}
