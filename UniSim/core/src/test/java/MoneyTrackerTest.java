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
         // Add -200 to 100, expectng 0
         tracker = new MoneyTracker(100);
         tracker.addMoney(-200);
         assertEquals(0, tracker.getMoney());
 
         // Add -200 to 0, expecting 0
         tracker = new MoneyTracker(0);
         tracker.addMoney(-200);
         assertEquals(0, tracker.getMoney());
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
 
}
