package io.github.unisim.building;

import java.lang.reflect.Field;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import io.github.unisim.Point;

public class BuildingManagerTest {

    private BuildingManager buildingManager;
    private BuildingType buildingType;
    private Building building;
    private SpriteBatch batch;

    private static ShaderProgram mockShader;

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
        building = new Building(null, 0.0f, null, null, null, null, false, BuildingType.RECREATION, "", 0, 50);
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
        Building previewBuilding = new Building(null, 0.0f, null, null, new Point(1,1), new Point(1,1), false, BuildingType.RECREATION, "", 0, 50);
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
        Building building = new Building(null, 0.0f, null, null, new Point(1,1), new Point(1,1), false, BuildingType.RECREATION, "", 0, 50);
        buildingManager.placeBuilding(building);
        assertNotNull(buildingManager.getBuildings());
    }

    @Test
    public void testPlaceBuildingOrder() {
        Building building1 = new Building(null, 0.0f, null, null, new Point(1,1), new Point(1,1), false, BuildingType.RECREATION, "", 0, 50);
        Building building2 = new Building(null, 0.0f, null, null, new Point(1,1), new Point(2,2), false, BuildingType.SLEEPING, "", 0, 50);
        Building building3 = new Building(null, 0.0f, null, null, new Point(2,1), new Point(1,1), false, BuildingType.EATING, "", 0, 50);
        buildingManager.placeBuilding(building1);
        int count1 = buildingManager.placeBuilding(building2);
        assertEquals(1, count1);
        int count2 = buildingManager.placeBuilding(building3);
        assertEquals(2, count2);
    }

    // Testing removeBuilding
    @Test
    public void testRemoveBuilding() {
        Building building = new Building(null, 0.0f, null, null, new Point(1,1), new Point(1,1), false, BuildingType.RECREATION, "", 0, 50);
        buildingManager.placeBuilding(building);
        boolean removed = buildingManager.removeBuilding(building);
        assertTrue(removed);
        assertEquals(0, buildingManager.getBuildingCount(BuildingType.RECREATION));
    }

    @Test
    public void testRemoveBuildingWithNoBuilding() {
        boolean removed = buildingManager.removeBuilding(building);
        assertFalse(removed);
        assertEquals(0, buildingManager.getBuildingCount(BuildingType.RECREATION));
    }

    // Testing getBuildingCount
    @Test
    public void testGetBuildingCount() {
        buildingType = BuildingType.SLEEPING;
        Building building = new Building(null, 0.0f, null, null, new Point(1,1), new Point(1,1), false, BuildingType.SLEEPING, "", 0, 50);
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
        Texture texture = (Texture) getPrivateField(batch, "lastTexture"); // Texture
        buildingManager.render(batch);
        assertEquals(building.texture, texture);
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
        Texture texture = (Texture) getPrivateField(batch, "lastTexture"); // Texture
        buildingManager.render(batch);
        assertEquals(building.texture, texture);
    }

    // Testing getBuildingAt
    @Test
    public void testGetBuildingAt() {
        Building building = new Building(null, 0.0f, null, null, new Point(1,1), new Point(3,3), false, BuildingType.RECREATION, "", 0, 50);
        buildingManager.placeBuilding(building);
        Building buildingAt = buildingManager.getBuildingAt(new Point(2,2));
        assertEquals(building, buildingAt);
    }

    @Test
    public void testGetBuildingAtWithNoBuilding() {
        Building buildingAt = buildingManager.getBuildingAt(new Point(2,2));
        assertNull(buildingAt);
    }

    // Testing getBuildingCapacities
    @Test
    public void testGetBuildingCapacities() {
        Building building1 = new Building(null, 0.0f, null, null, new Point(1,1), new Point(1,1), false, BuildingType.RECREATION, "", 50, 0);
        Building building2 = new Building(null, 0.0f, null, null, new Point(1,1), new Point(2,2), false, BuildingType.SLEEPING, "", 50, 0);
        Building building3 = new Building(null, 0.0f, null, null, new Point(2,1), new Point(1,1), false, BuildingType.EATING, "", 50, 0);
        buildingManager.placeBuilding(building1);
        buildingManager.placeBuilding(building2);
        buildingManager.placeBuilding(building3);
        assertEquals(50, buildingManager.getBuildingCapacities(BuildingType.RECREATION));
        assertEquals(50, buildingManager.getBuildingCapacities(BuildingType.SLEEPING));
        assertEquals(50, buildingManager.getBuildingCapacities(BuildingType.EATING));
    }

    static class TileBuildableTests {
        static final int BUILDABLE_ID = 0;
        static final int UNBUILDABLE_ID = 1;
        static final Set<Integer> buildableIds = Set.of(BUILDABLE_ID);

        TiledMapTileLayer layer;
        BuildingManager buildingManager;

        @BeforeEach
        void setUp() {
            buildingManager = new BuildingManager(new Matrix4());
            layer = new TiledMapTileLayer(10, 10, 5, 5);
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                    TiledMapTile tile = new StaticTiledMapTile(new TextureRegion());
                    tile.setId(BUILDABLE_ID);
                    cell.setTile(tile);
                    layer.setCell(i, j, cell);
                }
            }
        }

        @Test
        void testValidPoint() {
            assertTrue(buildingManager.isBuildable(new Point(0, 0), new Point(1, 1), layer, buildableIds));
            assertTrue(buildingManager.isBuildable(new Point(8, 8), new Point(9, 9), layer, buildableIds));
            assertTrue(buildingManager.isBuildable(new Point(3, 3), new Point(8, 8), layer, buildableIds));
        }

        @Test
        void testPointOutOfBounds() {
            assertFalse(buildingManager.isBuildable(new Point(-1, 0), new Point(0, 0), layer, buildableIds));
            assertFalse(buildingManager.isBuildable(new Point(0, -1), new Point(0, 0), layer, buildableIds));
            assertFalse(buildingManager.isBuildable(new Point(0, 0), new Point(-1, 0), layer, buildableIds));
            assertFalse(buildingManager.isBuildable(new Point(0, 0), new Point(0, -1), layer, buildableIds));
        }

        @Test
        void testUnbuildableTile() {
            layer.getCell(3, 3).getTile().setId(UNBUILDABLE_ID);
            assertTrue(buildingManager.isBuildable(new Point(0, 0), new Point(2, 2), layer, buildableIds));
            assertFalse(buildingManager.isBuildable(new Point(2, 2), new Point(3, 3), layer, buildableIds));
            assertFalse(buildingManager.isBuildable(new Point(3, 3), new Point(3, 3), layer, buildableIds));
        }

        @Test
        void testBuildingOverlap() {
            Building building = new Building(null, 0.0f, null, null, new Point(1,1), new Point(2,3), false, BuildingType.RECREATION, "", 50, 0);
            buildingManager.placeBuilding(building);
            assertFalse(buildingManager.isBuildable(new Point(0, 0), new Point(1, 1), layer, buildableIds));
            assertFalse(buildingManager.isBuildable(new Point(1, 1), new Point(2, 2), layer, buildableIds));
            assertFalse(buildingManager.isBuildable(new Point(0, 0), new Point(2, 2), layer, buildableIds));
        }

        @Test
        void testPreviewBuildingOverlap() {
            Building building = new Building(null, 0.0f, null, null, new Point(1,1), new Point(2,3), false, BuildingType.RECREATION, "", 50, 0);
            buildingManager.setPreviewBuilding(building);
            assertTrue(buildingManager.isBuildable(new Point(0, 0), new Point(1, 1), layer, buildableIds));
            assertTrue(buildingManager.isBuildable(new Point(1, 1), new Point(2, 2), layer, buildableIds));
            assertTrue(buildingManager.isBuildable(new Point(0, 0), new Point(2, 2), layer, buildableIds));
        }

    }
}
