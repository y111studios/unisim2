package io.github.unisim.building;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Matrix4;

public class BuildingManagerTest {

    private BuildingManager buildingManager;
    private Building previewBuilding;
    private TiledMapTileLayer tileLayer;

    @BeforeEach
    public void setUp() {
        Matrix4 isoTransform = new Matrix4();
        buildingManager = new BuildingManager(isoTransform);
        tileLayer = new TiledMapTileLayer(10, 10, 32, 32);
    }

    @Test
    public void testConstructor() {
        assertNotNull(buildingManager);
        assertNotNull(buildingManager.getBuildings());
        assertNotNull(buildingManager.getIsoTransform());
    }

    // Tests setPreviewBuilding
    @Test
    public void testSetPreviewBuilding() {
        buildingManager.setPreviewBuilding(previewBuilding);
        assertEquals(previewBuilding, buildingManager.getPreviewBuilding());
    }

    @Test
    public void testSetPreviewBuildingToNull() {
        buildingManager.setPreviewBuilding(previewBuilding);
        buildingManager.setPreviewBuilding(null);
        assertNull(buildingManager.getPreviewBuilding());
    }
}
