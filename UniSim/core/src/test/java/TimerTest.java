import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.unisim.Timer;

public class TimerTest {

    private Timer timer;
    //* @org.junit.jupiter.api.Test
    // public void shouldAnswerTrue() {
    //     assertTrue(true);
    // } */

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

}
