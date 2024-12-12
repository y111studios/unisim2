import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.Matrix4;

import io.github.unisim.building.BuildingManager;


public class BuildingManagerTest {

    private BuildingManager buildingManager;

    @BeforeEach
    public void setUp() {
        Matrix4 isoTransform = new Matrix4();
        buildingManager = new BuildingManager(isoTransform);
    }

    @Test
    public void testConstructor() {
        assertNotNull(buildingManager);
        assertNotNull(buildingManager.getBuildings());
    }
}