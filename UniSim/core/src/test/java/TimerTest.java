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

    @org.junit.jupiter.api.Test
    public void testTick() {
        timer = new Timer(5000);
    }
}
