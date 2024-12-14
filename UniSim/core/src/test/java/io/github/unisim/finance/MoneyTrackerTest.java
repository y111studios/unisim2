package io.github.unisim.finance;
import static org.junit.jupiter.api.Assertions.assertEquals;


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

    // Testing the add money method
    @org.junit.jupiter.api.Test
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
 
    @org.junit.jupiter.api.Test
    public void testAddNegativeMoney() {
        // Add -200 to 100, expectng no operation
        tracker = new MoneyTracker(100);
        tracker.addMoney(-200);
        assertEquals(100, tracker.getMoney());
    }
 
    @org.junit.jupiter.api.Test
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

    // Test the subtract money method
    @org.junit.jupiter.api.Test
    public void testSubtractMoney() {
        // Subracting 100 from 100, expecting 0
        tracker = new MoneyTracker(100);
        tracker.subtractMoney(100);
        assertEquals(100, tracker.getMoney());

        // Subtracting 200 from 100, expecting 0
        tracker = new MoneyTracker(100);
        tracker.subtractMoney(200);
        assertEquals(0, tracker.getMoney());
    }

    @org.junit.jupiter.api.Test
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

    @org.junit.jupiter.api.Test
    public void testSubtractNegativeMoney() {
        // Subtracting -100 from 100, expecting no operation
        tracker = new MoneyTracker(100);
        tracker.subtractMoney(-100);
        assertEquals(100, tracker.getMoney());
    }

    // Test the get money method
    @org.junit.jupiter.api.Test
    public void testGetMoney() {
        // Instantiate with 0
        tracker = new MoneyTracker(0);
        assertEquals(0, tracker.getMoney());
        // Instantiate with 100
        tracker = new MoneyTracker(100);
        assertEquals(100,tracker.getMoney());
    }

}
