package io.github.unisim.finance;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


public class MoneyTrackerTest {

    private MoneyTracker tracker;

    // Testing constructor
    @Test
    public void testConstructor() {
        tracker = new MoneyTracker(100);
        assertEquals(100, tracker.getMoney());
    }

    @Test
    public void testConstructorWithZeroMoney() {
        tracker = new MoneyTracker(0);
        assertEquals(0, tracker.getMoney());
    }

    @Test
    public void testConstructorWithNegativeMoney() {
        tracker = new MoneyTracker(-100);
        assertEquals(0, tracker.getMoney());
    }

    // Testing addMoney method
    @Test
    public void testaddMoney() {
        // Add 100 to 100, expecting 200
        tracker = new MoneyTracker(100);
        tracker.addMoney(100);
        assertEquals(200, tracker.getMoney());

        // Add 100 to 0, expecting 100
        tracker = new MoneyTracker(0);
        tracker.addMoney(100);
        assertEquals(100, tracker.getMoney());
    }

    @Test
    public void testAddNegativeMoney() {
        // Add -200 to 100, expectng no operation
        tracker = new MoneyTracker(100);
        tracker.addMoney(-200);
        assertEquals(100, tracker.getMoney());
    }

    @Test
    public void testAddZeroMoney() {
        // Add 0 to 100, expecting 100
        tracker = new MoneyTracker(100);
        tracker.addMoney(0);
        assertEquals(100,tracker.getMoney());

        // Add 0 to 0, expecting 0
        tracker = new MoneyTracker(0);
        tracker.addMoney(0);
        assertEquals(0,tracker.getMoney());
    }

    // Testing subtractMoney method
    @Test
    public void testSubtractMoney() {
        // Subracting 100 from 100, expecting 0
        tracker = new MoneyTracker(100);
        assertTrue(tracker.subtractMoney(100));
        assertEquals(0, tracker.getMoney());

        // Subtracting 200 from 100, expecting no operation
        tracker = new MoneyTracker(100);
        assertFalse(tracker.subtractMoney(200));
        assertEquals(100, tracker.getMoney());
    }

    @Test
    public void testSubtractZeroMoney() {
        // Subtracting 0 from 0, expecting 0
        tracker = new MoneyTracker(0);
        tracker.subtractMoney(0);
        assertEquals(0, tracker.getMoney());

        // Subtracting 0 from 100, expecting 100
        tracker = new MoneyTracker(100);
        tracker.subtractMoney(0);
        assertEquals(100, tracker.getMoney());
    }

    @Test
    public void testSubtractNegativeMoney() {
        // Subtracting -100 from 100, expecting no operation
        tracker = new MoneyTracker(100);
        tracker.subtractMoney(-100);
        assertEquals(100, tracker.getMoney());
    }

    // Testing getMoney method
    @Test
    public void testGetMoney() {
        // Instantiate with 0
        tracker = new MoneyTracker(0);
        assertEquals(0, tracker.getMoney());
        // Instantiate with 100
        tracker = new MoneyTracker(100);
        assertEquals(100,tracker.getMoney());
    }

    @Test
    public void testUpdateMoneyTimeSinceLastUpdateIsLessThanInterval() {
        tracker = new MoneyTracker(0);
        tracker.lastUpdateTime = Instant.now();
        int money = tracker.getMoney();
        tracker.updateMoney(100);
        assertEquals(money, tracker.getMoney());
    }

    @Test
    public void testUpdateMoneyTimeSinceLastUpdateIsGreaterThanOrEqualToInterval() {
        tracker = new MoneyTracker(0);
        tracker.lastUpdateTime = Instant.now().minus(MoneyTracker.MONEY_UPDATE_INTERVAL);
        tracker.updateMoney(1);
        assertEquals(MoneyTracker.MONEY_UPDATE_AMOUNT_PER_STUDENT, tracker.getMoney());
    }

    @Test
    public void testUpdateMoneyWithZeroStudentCount() {
        tracker = new MoneyTracker(0);
        tracker.lastUpdateTime = Instant.now().minus(MoneyTracker.MONEY_UPDATE_INTERVAL);
        tracker.updateMoney(0);
        assertEquals(0, tracker.getMoney());
    }

    @Test
    public void testUpdateMoneyWithNegativeStudentCount() {
        tracker = new MoneyTracker(0);
        tracker.lastUpdateTime = Instant.now().minus(MoneyTracker.MONEY_UPDATE_INTERVAL);
        tracker.updateMoney(-1);
        assertEquals(0, tracker.getMoney());
    }
}
