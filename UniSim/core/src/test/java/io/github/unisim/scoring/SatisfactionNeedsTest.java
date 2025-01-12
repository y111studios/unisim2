package io.github.unisim.scoring;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import io.github.unisim.building.Building;
import io.github.unisim.building.BuildingType;

public class SatisfactionNeedsTest {

    @Test
    public void testGetSatisfactionWithNoBuildings() {
        List<Building> buildings = new ArrayList<>();
        float satisfaction = new SatisfactionNeeds().getSatisfaction(buildings, 50);
        assertEquals(0, satisfaction);
    }

    @Test
    public void testGetSatisfactionWithZeroCapacityBuildings() {
        List<Building> buildings = new ArrayList<>();
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.SLEEPING, "", 0, 0, 3));
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.LEARNING, "", 0, 0, 3));
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.EATING, "", 0, 0, 3));
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.RECREATION, "", 0, 0, 3));
        float satisfaction = new SatisfactionNeeds().getSatisfaction(buildings, 50);
        assertEquals(0, satisfaction);
    }

    @Test
    public void testGetSatisfactionWithSomeCapacityBuildings() {
        List<Building> buildings = new ArrayList<>();
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.SLEEPING, "", 100, 0, 3));
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.LEARNING, "", 100, 0, 3));
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.EATING, "", 100, 0, 3));
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.RECREATION, "", 100, 0, 3));
        float satisfaction = new SatisfactionNeeds().getSatisfaction(buildings, 100);
        assertEquals(1, satisfaction);
    }

    @Test
    public void testGetSatisfactionWithZeroStudents() {
        List<Building> buildings = new ArrayList<>();
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.SLEEPING, "", 100, 0, 3));
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.LEARNING, "", 100, 0, 3));
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.EATING, "", 100, 0, 3));
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.RECREATION, "", 100, 0, 3));
        float satisfaction = new SatisfactionNeeds().getSatisfaction(buildings, 0);
        assertEquals(0, satisfaction);
    }

    @Test
    public void testGetSatisfactionWithMixedCapcities() {
        List<Building> buildings = new ArrayList<>();
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.SLEEPING, "", 100, 0, 3));
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.LEARNING, "", 50, 0, 3));
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.EATING, "", 25, 0, 3));
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.RECREATION, "", 100, 0, 3));
        float satisfaction = new SatisfactionNeeds().getSatisfaction(buildings, 100);
        assertEquals(0.25, satisfaction);
    }

    @Test
    public void testGetSatisfactionWithZeroHousingCapacity() {
        List<Building> buildings = new ArrayList<>();
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.SLEEPING, "", 0, 0, 3));
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.LEARNING, "", 100, 0, 3));
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.EATING, "", 100, 0, 3));
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.RECREATION, "", 100, 0, 3));
        float satisfaction = new SatisfactionNeeds().getSatisfaction(buildings, 100);
        assertEquals(0, satisfaction);
    }

    @Test
    public void testGetSatisfactionWithZeroCateringCapacity() {
        List<Building> buildings = new ArrayList<>();
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.SLEEPING, "", 100, 0, 3));
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.LEARNING, "", 100, 0, 3));
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.EATING, "", 0, 0, 3));
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.RECREATION, "", 100, 0, 3));
        float satisfaction = new SatisfactionNeeds().getSatisfaction(buildings, 100);
        assertEquals(0, satisfaction);
    }

    @Test
    public void testGetSatisfactionWithZeroTeachingCapacity() {
        List<Building> buildings = new ArrayList<>();
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.SLEEPING, "", 100, 0, 3));
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.LEARNING, "", 0, 0, 3));
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.EATING, "", 100, 0, 3));
        buildings.add(new Building(null, 0.0f, null, null, null, null, false, BuildingType.RECREATION, "", 100, 0, 3));
        float satisfaction = new SatisfactionNeeds().getSatisfaction(buildings, 100);
        assertEquals(0, satisfaction);
    }
}
