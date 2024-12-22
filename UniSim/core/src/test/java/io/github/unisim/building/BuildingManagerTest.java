package io.github.unisim.building;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

import io.github.unisim.Point;

public class BuildingManagerTest {

    private BuildingManager buildingManager;
    private BuildingType buildingType;
    private Building building;
    private SpriteBatch batch;

    @BeforeEach
    public void setUp() {
        Matrix4 isoTransform = new Matrix4();
        buildingManager = new BuildingManager(isoTransform);
        batch = new SpriteBatch();
        building = new Building(null, 0.0f, null, null, null, false, BuildingType.RECREATION, "", 0, 50);
    }

    private Object getPrivateField(Object object, String fieldName) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
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

    // Testing drawBuildings
    @Test
    public void testDrawRegularBuilding() throws Exception {
        buildingManager.drawBuilding(building, batch);
        float colorA = (float) getPrivateField(batch, "color.a"); // Alpha
        assertEquals(1.0f, colorA);
        Texture texture = (Texture) getPrivateField(batch, "lastTexture"); // Texture
        assertEquals(building.texture, texture);
        float x = (float) getPrivateField(batch, "invTexWidth"); // X position
        assertEquals(0, x);
        float y = (float) getPrivateField(batch, "invTexHeight");  // Y position
        assertEquals(0, y); 
        float width = (float) getPrivateField(batch, "invTexWidth"); // Width
        assertEquals(building.texture.getWidth(), width);
        float height = (float) getPrivateField(batch, "invTexHeight"); // Height
        assertEquals(building.texture.getHeight(), height);
        boolean flipX = (boolean) getPrivateField(batch, "flipX"); // Flip X
        assertEquals(false, flipX);
        boolean flipY = (boolean) getPrivateField(batch, "flipY"); // Flip Y
        assertEquals(false, flipY);
    }

    @Test
    public void testDrawPreviewBuilding() throws Exception {
        buildingManager.setPreviewBuilding(building);
        buildingManager.drawBuilding(building, batch);
        float colorA = (float) getPrivateField(batch, "color.a"); // Alpha
        assertEquals(0.5f, colorA);
        Texture texture = (Texture) getPrivateField(batch, "lastTexture"); // Texture
        assertEquals(building.texture, texture);
        float x = (float) getPrivateField(batch, "invTexWidth"); // X position
        assertEquals(0, x);
        float y = (float) getPrivateField(batch, "invTexHeight");  // Y position
        assertEquals(0, y); 
        float width = (float) getPrivateField(batch, "invTexWidth"); // Width
        assertEquals(building.texture.getWidth(), width);
        float height = (float) getPrivateField(batch, "invTexHeight"); // Height
        assertEquals(building.texture.getHeight(), height);
        boolean flipX = (boolean) getPrivateField(batch, "flipY"); // Flip X
        assertEquals(false, flipX);
        boolean flipY = (boolean) getPrivateField(batch, "flipY"); // Flip Y
        assertEquals(false, flipY);
    }

    // Testing getBuildingAt
    @Test
    public void testGetBuildingAt() {
        Building building = new Building(null, 0.0f, null, new Point(1,1), new Point(1,1), false, BuildingType.RECREATION, "", 0, 50);
        buildingManager.placeBuilding(building);
        Building buildingAt = buildingManager.getBuildingAt(new Point(1,1));
        assertEquals(building, buildingAt);
    }


}