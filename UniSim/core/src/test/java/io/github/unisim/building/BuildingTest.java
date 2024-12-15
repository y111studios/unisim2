package io.github.unisim.building;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class BuildingTest {

    private Building building;

    // Testing constructor
    @Test
    public void testConstructor() {
        Building building = new Building(null, 0.0f, null, null, null, false, BuildingType.RECREATION, "", 0, 50);
        assertEquals(BuildingType.RECREATION, building.type);
        assertEquals(50, building.cost);
        assertEquals(false, building.flipped);
        assertEquals(0, building.capacity);
    }
}
