package io.github.unisim.scoring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SatisfactionTrackerTest {

    private SatisfactionTracker satisfactiontracker;

    @BeforeEach
    public void setUp() {
        satisfactiontracker = new SatisfactionTracker();
    }

    @Test
    public void testInitialSatisfaction() {
        assertEquals(0, satisfactiontracker.getSatisfaction());
    }
}