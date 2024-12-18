package io.github.unisim.building;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.Matrix4;

import io.github.unisim.Point;

public class BuildingManagerTest {

    private BuildingManager buildingManager;
    private BuildingType buildingType;

    @BeforeEach
    public void setUp() {
        Matrix4 isoTransform = new Matrix4();
        buildingManager = new BuildingManager(isoTransform);
    }

    // Testing the constructor
    @Test
    public void testConstructor() {
        assertNotNull(buildingManager);
        assertNotNull(buildingManager.getBuildings());
        assertNotNull(buildingManager.getIsoTransform());
    }

    // Testing setPreviewBuilding
    @Test
    public void testSetPreviewBuilding() {
        Building previewBuilding = new Building(null, 0.0f, null, new Point(1,1), new Point(1,1), false, BuildingType.RECREATION, "", 0, 50);
        buildingManager.setPreviewBuilding(previewBuilding);
        assertNotNull(buildingManager.getPreviewBuilding());
        assertEquals(previewBuilding, buildingManager.getPreviewBuilding());
    }

    @Test
    public void testSetPreviewBuildingToNull() {
        buildingManager.setPreviewBuilding(null);
        assertNull(buildingManager.getPreviewBuilding());
    }

    // Testing placeBuilding
    @Test
    public void testPlaceBuilding() {
        Building building = new Building(null, 0.0f, null, new Point(1,1), new Point(1,1), false, BuildingType.RECREATION, "", 0, 50);
        buildingManager.placeBuilding(building);
        assertNotNull(buildingManager.getBuildings());
    }

    // Testing removeBuilding
    @Test
    public void testRemoveBuilding() {
        Building building = new Building(null, 0.0f, null, new Point(1,1), new Point(1,1), false, BuildingType.RECREATION, "", 0, 50);
        buildingManager.placeBuilding(building);
        boolean removed = buildingManager.removeBuilding(building);
        assertTrue(removed);
    }

    // Testing getBuildingCount
    @Test
    public void testGetBuildingCount() {
        buildingType = BuildingType.SLEEPING;
        Building building = new Building(null, 0.0f, null, new Point(1,1), new Point(1,1), false, BuildingType.SLEEPING, "", 0, 50);
        assertEquals(0, buildingManager.getBuildingCount(buildingType));
        buildingManager.placeBuilding(building);
        assertEquals(1, buildingManager.getBuildingCount(buildingType));
    }
}
