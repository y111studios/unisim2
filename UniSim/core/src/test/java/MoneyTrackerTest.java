import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.unisim.finance.MoneyTracker;


public class MoneyTrackerTest {

    private MoneyTracker tracker;

    // Testing the constructor
    @org.junit.jupiter.api.Test
    public void testConstructor() {
        tracker = new MoneyTracker(100);
        assertEquals(100, tracker.getMoney());
    }

    @org.junit.jupiter.api.Test
    public void testConstructorWithZeroMoney() {
        tracker = new MoneyTracker(0);
        assertEquals(0, tracker.getMoney());
    }

    @org.junit.jupiter.api.Test
    public void testConstructorWithNegativeMoney() {
        tracker = new MoneyTracker(-100);
        assertEquals(0, tracker.getMoney());
    }

}
