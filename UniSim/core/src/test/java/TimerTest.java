import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.unisim.Timer;

public class TimerTest {

    private Timer timer;
    
    //* Testing the constructor */
    @org.junit.jupiter.api.Test
    public void testConstructor() {
        timer = new Timer(5000);
        assertEquals(5000, timer.getInitialTime());
        assertEquals("00:05", timer.getRemainingTime());
        assertTrue(timer.isRunning());
    }

    @org.junit.jupiter.api.Test
    public void testConstructorWithZeroTime() {
        timer = new Timer(0);
        assertEquals(0, timer.getInitialTime());
        assertEquals("00:00", timer.getRemainingTime());
        assertFalse(timer.isRunning());
    }

    @org.junit.jupiter.api.Test
    public void testConstructorWithNegativeTime() {
        timer = new Timer(-5000);
        assertEquals(-5000, timer.getInitialTime());
        assertEquals("00:00", timer.getRemainingTime());
        assertFalse(timer.isRunning());
    }

    //*Testing the timer tick method */
    @org.junit.jupiter.api.Test
    public void testTick() {
        timer = new Timer(5000);
        timer.tick(1000);
        assertTrue(timer.isRunning());
        assertEquals("00:04", timer.getRemainingTime());
        assertTrue(timer.isRunning());
    }

    @org.junit.jupiter.api.Test
    public void testTickToCompletion() {
        timer = new Timer(5000);
        timer.tick(5000);
        assertFalse(timer.isRunning());
        assertEquals("00:00", timer.getRemainingTime());
        assertFalse(timer.isRunning());
    }

    @org.junit.jupiter.api.Test
    public void testTickAfterCompletion() {
        timer = new Timer(5000);
        timer.tick(6000);
        assertFalse(timer.isRunning());
        assertEquals("00:00", timer.getRemainingTime());
        assertFalse(timer.isRunning());
    }

    @org.junit.jupiter.api.Test
    public void testTickWithNegativeDeltaTime() {
        timer = new Timer(5000);
        timer.tick(-1000);
        assertTrue(timer.isRunning());
        assertEquals("00:05", timer.getRemainingTime());
        assertTrue(timer.isRunning());
    }

    @org.junit.jupiter.api.Test
    public void testReset() {
        timer = new Timer(5000);
        timer.tick(1000);
        timer.reset();
        assertEquals(5000, timer.getInitialTime());
        assertEquals("00:05", timer.getRemainingTime());
        assertTrue(timer.isRunning());
    }

    @org.junit.jupiter.api.Test
    public void testResetAfterCompletion() {
        timer = new Timer(5000);
        timer.tick(5000);
        timer.reset();
        assertEquals(5000, timer.getInitialTime());
        assertEquals("00:05", timer.getRemainingTime());
        assertTrue(timer.isRunning());
    }

    @org.junit.jupiter.api.Test
    public void testTickWithLargeDeltaTime() {
        Timer timer = new Timer(3000);
        timer.tick(5000);
        assertEquals("00:00", timer.getRemainingTime());
        assertFalse(timer.isRunning());
    }

    @org.junit.jupiter.api.Test
    public void testMultipleTicksAfterCompletion() {
        Timer timer = new Timer(1000);
        timer.tick(1000);
        timer.tick(1000);
        assertEquals("00:00", timer.getRemainingTime());
        assertFalse(timer.isRunning());
    }

    //* Testing the getting time methods  */
    @org.junit.jupiter.api.Test
    public void testGetRemainingTime() {
        timer = new Timer(5000);
        assertEquals("00:05", timer.getRemainingTime());
        timer.tick(1000);
        assertEquals("00:04", timer.getRemainingTime());
        timer.tick(1000);
        assertEquals("00:03", timer.getRemainingTime());
        timer.tick(1000);
        assertEquals("00:02", timer.getRemainingTime());
        timer.tick(1000);
        assertEquals("00:01", timer.getRemainingTime());
        timer.tick(1000);
        assertEquals("00:00", timer.getRemainingTime());
    }

    @org.junit.jupiter.api.Test
    public void testFormattingSingleDigitTimes() {
        Timer timer = new Timer(0); // 0 seconds
        assertEquals("00:00", timer.getRemainingTime());
        Timer timer2 = new Timer(5000); // 5 seconds
        assertEquals("00:05", timer2.getRemainingTime());
        Timer timer3 = new Timer(10000); // 10 seconds
        assertEquals("00:10", timer3.getRemainingTime());
        Timer timer4 = new Timer(60000); // 1 mimute
        assertEquals("01:00", timer4.getRemainingTime());
    }

    // Testing the timer runmnig method
    @org.junit.jupiter.api.Test
    public void testIsRunningWhileCountingDown() {
        timer = new Timer(5000);
        timer.tick(1000);
        assertTrue(timer.isRunning());
    }

    @org.junit.jupiter.api.Test
    public void testIsRunningAfterCompletion() {
        timer = new Timer(5000);
        timer.tick(5000);
        assertFalse(timer.isRunning());
    }

    @org.junit.jupiter.api.Test
    public void testIsRunningAfterReset() {
        Timer timer = new Timer(5000);
        timer.tick(5000);
        timer.reset();
        assertTrue(timer.isRunning());
    }
}