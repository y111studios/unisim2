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

    // Tests for initial satisfaction
    @Test
    public void testInitialSatisfaction() {
        assertEquals(0, satisfactiontracker.getSatisfaction());
    }

    // Tests for updateSatisfaction
    @Test
    public void testUpdateSatisfactionWithNoBuildings() {
        List<Building> buildings = new ArrayList<>();
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

    // Tests for getScore
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

    // Tests for getUpdateInterval
    @Test
    public void testGetUpdateInterval() {
        assertEquals(Duration.ofSeconds(1), satisfactiontracker.getUpdateInterval());
    }
}