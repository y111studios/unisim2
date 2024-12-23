package io.github.unisim.building;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import io.github.unisim.Point;

public class BuildingManagerTest {

    private BuildingManager buildingManager;
    private BuildingType buildingType;
    private Building building;
    private SpriteBatch batch;

    private static ShaderProgram mockShader;
    // Precision value of IEEE 754 single-precision floating point
    private static final float EPSILON = (float) Math.pow(2, -23);

    @BeforeAll
    static void initialiseApplication() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        Gdx.app = new HeadlessApplication(new ApplicationAdapter() {}, config);

        Gdx.gl = Mockito.mock(GL20.class);
        Gdx.gl20 = Gdx.gl;

        ShaderProgram.pedantic = false;
        mockShader = Mockito.mock(ShaderProgram.class);
        Mockito.when(mockShader.isCompiled()).thenReturn(true);
        Mockito.when(mockShader.getLog()).thenReturn("Mock shader log");
    }

    @BeforeEach
    public void setUp() {
        Matrix4 isoTransform = new Matrix4();
        buildingManager = new BuildingManager(isoTransform);
        batch = new SpriteBatch(1000, mockShader);
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
        building.location = new Point(0,0);
        building.textureOffset = new Vector2(0, 0);
        building.size = new Point(1,1);
        building.texture = new Texture(Gdx.files.internal("buildings/library.png"));
        batch.begin();
        buildingManager.drawBuilding(building, batch);
        // float colorA = (float) getPrivateField(batch, "color.a"); // Alpha
        // assertEquals(1.0f, colorA);
        Texture texture = (Texture) getPrivateField(batch, "lastTexture"); // Texture
        assertEquals(building.texture, texture);
        // float x = (float) getPrivateField(batch, "invTexWidth"); // X position
        // assertEquals(0, x, EPSILON);
        // float y = (float) getPrivateField(batch, "invTexHeight");  // Y position
        // assertEquals(0, y, EPSILON);
        // float width = (float) getPrivateField(batch, "invTexWidth"); // Width
        // assertEquals(building.texture.getWidth(), width, EPSILON);
        // float height = (float) getPrivateField(batch, "invTexHeight"); // Height
        // assertEquals(building.texture.getHeight(), height, EPSILON);
        // boolean flipX = (boolean) getPrivateField(batch, "flipX"); // Flip X
        // assertEquals(false, flipX);
        // boolean flipY = (boolean) getPrivateField(batch, "flipY"); // Flip Y
        // assertEquals(false, flipY);
    }

    @Test
    public void testDrawPreviewBuilding() throws Exception {
        building.location = new Point(0,0);
        building.textureOffset = new Vector2(0, 0);
        building.size = new Point(1,1);
        building.texture = new Texture(Gdx.files.internal("buildings/library.png"));
        batch.begin();
        buildingManager.setPreviewBuilding(building);
        buildingManager.drawBuilding(building, batch);
        // float colorA = (float) getPrivateField(batch, "color.a"); // Alpha
        // assertEquals(0.5f, colorA);
        Texture texture = (Texture) getPrivateField(batch, "lastTexture"); // Texture
        assertEquals(building.texture, texture);
        // float x = (float) getPrivateField(batch, "invTexWidth"); // X position
        // assertEquals(0, x);
        // float y = (float) getPrivateField(batch, "invTexHeight");  // Y position
        // assertEquals(0, y);
        // float width = (float) getPrivateField(batch, "invTexWidth"); // Width
        // assertEquals(building.texture.getWidth(), width);
        // float height = (float) getPrivateField(batch, "invTexHeight"); // Height
        // assertEquals(building.texture.getHeight(), height);
        // boolean flipX = (boolean) getPrivateField(batch, "flipY"); // Flip X
        // assertEquals(false, flipX);
        // boolean flipY = (boolean) getPrivateField(batch, "flipY"); // Flip Y
        // assertEquals(false, flipY);
    }

    // Testing getBuildingAt
    @Test
    public void testGetBuildingAt() {
        Building building = new Building(null, 0.0f, null, new Point(1,1), new Point(1,1), false, BuildingType.RECREATION, "", 0, 50);
        buildingManager.placeBuilding(building);
        Building buildingAt = buildingManager.getBuildingAt(new Point(1,1));
        assertEquals(building, buildingAt);
    }

    // Testing decrementBuildingCount
    @Test
    public void testDecrementBuildingCount() {
        Map<BuildingType, Integer> buildingCounts = new HashMap<>();
        buildingCounts.put(buildingType.RECREATION, 1);
        Integer count = buildingCounts.get(buildingType.RECREATION);
        assertEquals(1, count);
    }

    @Test
    public void testDecrementBuildingCountWithNull() {
        Map<BuildingType, Integer> buildingCounts = new HashMap<>();
        Integer count = buildingCounts.get(buildingType.RECREATION);
        assertEquals(null, count);
    }
}
