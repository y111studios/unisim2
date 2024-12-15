package io.github.unisim.scoring;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.unisim.building.Building;
import io.github.unisim.building.BuildingType;


public class SatisfactionTrackerTest {

    private SatisfactionTracker satisfactiontracker;

    @BeforeEach
    public void setUp() {
        satisfactiontracker = new SatisfactionTracker();
    }

    // Testing initial satisfaction/constructor
    @Test
    public void testInitialSatisfaction() {
        assertEquals(0, satisfactiontracker.getSatisfaction());
    }

    // Testing updateSatisfaction
    @Test
    public void testUpdateSatisfactionWithNoBuildings() {
        assertEquals(0, satisfactiontracker.getSatisfaction());
    }
    
    @Test
    public void testUpdateSatisfactionWithBuildings() {
        List<Building> buildings = new ArrayList<>();
        buildings.add(new Building(null, 0.0f, null, null, null, false, BuildingType.SLEEPING, "", 0, 100));
        buildings.add(new Building(null, 0.0f, null, null, null, false, BuildingType.LEARNING, "", 0, 50));
        buildings.add(new Building(null, 0.0f, null, null, null, false, BuildingType.EATING, "", 0, 50));
        buildings.add(new Building(null, 0.0f, null, null, null, false, BuildingType.RECREATION, "", 0, 50));
        satisfactiontracker.updateSatisfaction(buildings, null);
        float expectedSatisfaction = ((1f + 1f + 1f) / 3) /00;
        assertEquals(expectedSatisfaction, satisfactiontracker.getSatisfaction());
    }

    @Test
    public void testUpdateSatisfactionWithPreviewBuilding() {
        List<Building> buildings = new ArrayList<>();
        Building previewBuilding = new Building(null, 0.0f, null, null, null, false, BuildingType.SLEEPING, "", 0, 100);
        buildings.add(previewBuilding);
        buildings.add(new Building(null, 0.0f, null, null, null, false, BuildingType.LEARNING, "", 0, 100));
        buildings.add(new Building(null, 0.0f, null, null, null, false, BuildingType.EATING, "", 0, 100));
        buildings.add(new Building(null, 0.0f, null, null, null, false, BuildingType.RECREATION, "", 0, 100));
        satisfactiontracker.updateSatisfaction(buildings, previewBuilding);
        float expectedSatisfaction = ((1f + 1f + 1f) / 3) / 100;
        assertEquals(expectedSatisfaction, satisfactiontracker.getSatisfaction());
    }

    // Testing getScore
    @Test
    public void testGetScore() {
        List<Building> buildings = new ArrayList<>();
        buildings.add(new Building(null, 0.0f, null, null, null, false, BuildingType.SLEEPING, "", 0, 100));
        buildings.add(new Building(null, 0.0f, null, null, null, false, BuildingType.LEARNING, "", 0, 50));
        buildings.add(new Building(null, 0.0f, null, null, null, false, BuildingType.EATING, "", 0, 50));
        buildings.add(new Building(null, 0.0f, null, null, null, false, BuildingType.RECREATION, "", 0, 50));
        satisfactiontracker.updateSatisfaction(buildings, null);
        float expectedSatisfaction = ((1f + 1f + 1f) / 3) / 100;
        assertEquals(expectedSatisfaction, satisfactiontracker.getSatisfaction());
    }

    // Testing getUpdateInterval
    @Test
    public void testGetUpdateInterval() {
        assertEquals(Duration.ofSeconds(1), satisfactiontracker.getUpdateInterval());
    }
}