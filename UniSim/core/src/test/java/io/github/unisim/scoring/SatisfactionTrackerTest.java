package io.github.unisim.scoring;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.unisim.building.Building;
import io.github.unisim.building.BuildingType;


public class SatisfactionTrackerTest {

    private SatisfactionTracker satisfactiontracker;
    private List<Building> buildings;

    @BeforeEach
    public void setUp() {
        satisfactiontracker = new SatisfactionTracker();
        buildings = new ArrayList<>();
        buildings.add(new Building(null, 0.0f, null, null, null, false, BuildingType.SLEEPING, "", 0, 100));
        buildings.add(new Building(null, 0.0f, null, null, null, false, BuildingType.LEARNING, "", 0, 100));
        buildings.add(new Building(null, 0.0f, null, null, null, false, BuildingType.EATING, "", 0, 100));
    }

    // Testing initial satisfaction/constructor
    @Test
    public void testInitialSatisfaction() {
        assertEquals(0, satisfactiontracker.getSatisfaction());
    }

    // // Testing updateSatisfaction
    @Test
    public void testUpdateSatisfactionWithBuildings() {
        satisfactiontracker.updateSatisfaction(buildings, null, 50);
        assertEquals(100, satisfactiontracker.getSatisfaction());
    }

    @Test
    public void testUpdateSatisfactionWithNoBuildings() {
        List<Building> buildings = new ArrayList<>();
        satisfactiontracker.updateSatisfaction(buildings, null, 50);
        assertEquals(0, satisfactiontracker.getSatisfaction());
    }

    @Test 
    public void testUpdateSatisfactionWithPreviewBuilding() {
        Building previewBuilding = new Building(null, 0.0f, null, null, null, false, BuildingType.SLEEPING, "", 0, 100);
        buildings.add(previewBuilding);
        satisfactiontracker.updateSatisfaction(buildings, previewBuilding, 100);
        assertEquals(100, satisfactiontracker.getSatisfaction());
    }

    @Test
    public void testUpdateSatisfactionWithNullBuildings() {
        satisfactiontracker.updateSatisfaction(null, null, 50);
        assertEquals(0, satisfactiontracker.getSatisfaction());
    }

    @Test
    public void testUpdateSatisfactionWithNoStudents() {
        satisfactiontracker.updateSatisfaction(buildings,null ,0);
        assertEquals(0, satisfactiontracker.getSatisfaction());
    }

    // Testing getScore
    @Test
    public void testGetScore() {
        satisfactiontracker.updateSatisfaction(buildings, null, 100);
        assertEquals(100, satisfactiontracker.getScore());
    }
}