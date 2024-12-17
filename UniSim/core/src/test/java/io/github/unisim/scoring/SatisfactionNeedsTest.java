package io.github.unisim.scoring;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.unisim.building.Building;

public class SatisfactionNeedsTest {

    @BeforeEach
    public void setUp() {
        SatisfactionNeeds satisfactionNeeds = new SatisfactionNeeds();
    }

    @Test
    public void testGetSatisfactionWithNoBuildings() {
        List<Building> buildings = new ArrayList<>();
        float satisfaction = new SatisfactionNeeds().getSatisfaction(buildings, 50);
        assertEquals(0, satisfaction);
    }
}
