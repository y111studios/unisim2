package io.github.unisim.scoring;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.unisim.achievements.ScoreModifierTemplate;
import io.github.unisim.building.Building;
import io.github.unisim.building.BuildingType;


public class SatisfactionTrackerTest {

    private SatisfactionTracker satisfactiontracker;
    private List<Building> buildings;

    @BeforeEach
    public void setUp() {
        satisfactiontracker = new SatisfactionTracker();
        buildings = new ArrayList<>();
        buildings.add(new Building(null, 0.0f, null, null, null, false, BuildingType.SLEEPING, "", 100, 0));
        buildings.add(new Building(null, 0.0f, null, null, null, false, BuildingType.LEARNING, "", 100, 0));
        buildings.add(new Building(null, 0.0f, null, null, null, false, BuildingType.EATING, "", 100, 0));
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

    @Test
    public void testUpdateSatisfactionWithLargeStudentCount() {
        satisfactiontracker.updateSatisfaction(buildings, null, 10000);
        assertEquals(1, satisfactiontracker.getSatisfaction());
    }

    @Test
    public void testUpdateSatisfactionWithModifier() {
        satisfactiontracker.addModifier(ScoreModifierTemplate.ADD, 5, Duration.ofSeconds(1));
        satisfactiontracker.updateSatisfaction(buildings, null, 100);
        assertEquals(105, satisfactiontracker.getSatisfaction());
    }

    // Testing getScore
    @Test
    public void testGetScore() {
        satisfactiontracker.updateSatisfaction(buildings, null, 100);
        assertEquals(100, satisfactiontracker.getScore());

        satisfactiontracker.updateSatisfaction(buildings, null, 0);
        assertEquals(0, satisfactiontracker.getScore());
    }

    // Testing updateInterval
    @Test
    public void testUpdateInterval() {
        satisfactiontracker.getUpdateInterval();
        Duration expectedInterval = Duration.ofSeconds(1);
        assertEquals(expectedInterval, satisfactiontracker.getUpdateInterval());
    }

    // Testing addModifier
    @Test
    public void testAddModifierWithDuration() {
        satisfactiontracker.addModifier(ScoreModifierTemplate.ADD, 5, Duration.ofSeconds(1));
        satisfactiontracker.updateSatisfaction(buildings, null, 100);
        assertEquals(105, satisfactiontracker.getSatisfaction());
    }

    @Test
    public void testAddModifierWithZeroValueWtihDuration() {
        satisfactiontracker.addModifier(ScoreModifierTemplate.ADD, 0, Duration.ofSeconds(1));
        satisfactiontracker.updateSatisfaction(buildings, null, 100);
        assertEquals(100, satisfactiontracker.getSatisfaction());
    }

    @Test
    public void testAddModifierWithNegativeValueWithDuration() {
        satisfactiontracker.addModifier(ScoreModifierTemplate.ADD, -5, Duration.ofSeconds(1));
        satisfactiontracker.updateSatisfaction(buildings, null, 100);
        assertEquals(95, satisfactiontracker.getSatisfaction());
    }

    @Test
    public void testAddModifierWithZeroDuration() {
        satisfactiontracker.addModifier(ScoreModifierTemplate.ADD, 5, Duration.ofSeconds(0));
        satisfactiontracker.updateSatisfaction(buildings, null, 100);
        assertEquals(100, satisfactiontracker.getSatisfaction());
    }

    @Test
    public void testAddModifierWithNegativeDuration() {
        satisfactiontracker.addModifier(ScoreModifierTemplate.ADD, 5, Duration.ofSeconds(-1));
        satisfactiontracker.updateSatisfaction(buildings, null, 100);
        assertEquals(100, satisfactiontracker.getSatisfaction());
    }

    @Test
    public void testAddModifier() {
        satisfactiontracker.addModifier(new SatisfactionModifier(ScoreModifierTemplate.ADD, 5, Duration.ofSeconds(1)));
        satisfactiontracker.updateSatisfaction(buildings, null, 100);
        assertEquals(105, satisfactiontracker.getSatisfaction());
    }

    @Test
    public void testAddModifierWithZeroValue() {
        satisfactiontracker.addModifier(new SatisfactionModifier(ScoreModifierTemplate.ADD, 0, Duration.ofSeconds(1)));
        satisfactiontracker.updateSatisfaction(buildings, null, 100);
        assertEquals(100, satisfactiontracker.getSatisfaction());
    }

    @Test
    public void testAddModifierWithNegativeValue() {
        satisfactiontracker.addModifier(new SatisfactionModifier(ScoreModifierTemplate.ADD, -5, Duration.ofSeconds(1)));
        satisfactiontracker.updateSatisfaction(buildings, null, 100);
        assertEquals(95, satisfactiontracker.getSatisfaction());
    }
}
