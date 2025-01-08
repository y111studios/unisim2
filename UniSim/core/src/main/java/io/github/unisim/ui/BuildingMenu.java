package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import io.github.unisim.GameState;
import io.github.unisim.Point;
import io.github.unisim.building.Building;
import io.github.unisim.building.BuildingType;
import io.github.unisim.world.World;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Menu used to place buildings in the world by clicking and dragging them
 * from the list onto the map.
 */
@SuppressWarnings({"MemberName", "AbbreviationAsWordInName"})
public class BuildingMenu {
  private World world;
  private ShapeActor bar = new ShapeActor(GameState.UISecondaryColour);
  private ArrayList<Building> buildings = new ArrayList<>();
  private ArrayList<Image> buildingImages = new ArrayList<>();
  private Map<BuildingType, BuildingMenuEntry> buildingTypes = new HashMap<>();
  private HorizontalGroup group = new HorizontalGroup();
  Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
  private Table mainTable = new Table();
  private Label buildingInfoLabel = new Label(
      "", skin);
  private Table buildingInfoTable = new Table();
  private BuildingPreviewMenu previewMenu;

  /**
   * Create a Building Menu and attach its actors and components to the provided stage.
   * Also handles drawing buildings and their flipped variants

   * @param stage - The stage on which to draw the menu.
   */
  public BuildingMenu(Stage stage, World world) {
    this.world = world;
    // Set building images and sizes
    buildings.add(new Building(
        new Texture(Gdx.files.internal("buildings/restaurant.png")),
        0.01f,
        new Vector2(0.35f, -0.9f),
        new Point(),
        new Point(3, 3),
        false,
        BuildingType.EATING,
        "Canteen",
        15,
        100
    ));
    buildings.add(new Building(
        new Texture(Gdx.files.internal("buildings/library.png")),
        0.0075f,
        new Vector2(1.8f, -4.6f),
        new Point(),
        new Point(20, 12),
        false,
        BuildingType.LEARNING,
        "Library",
        100,
        1000
    ));
    buildings.add(new Building(
        new Texture(Gdx.files.internal("buildings/basketballCourt.png")),
        0.0025f,
        new Vector2(1f, -2.4f),
        new Point(),
        new Point(6, 9),
        false,
        BuildingType.RECREATION,
        "Basketball Court",
        10,
        500
    ));
    buildings.add(new Building(
        new Texture(Gdx.files.internal("buildings/tennisCourt.png")),
        0.0025f,
        new Vector2(1f, -1.0f),
        new Point(),
        new Point(7, 10),
        false,
        BuildingType.RECREATION,
        "Tennis Court",
        10,
        500
    ));
    buildings.add(new Building(
        new Texture(Gdx.files.internal("buildings/studentHousing.png")),
        0.108f,
        new Vector2(1.4f, -2.8f),
        new Point(),
        new Point(11, 11),
        false,
        BuildingType.SLEEPING,
        "Student Accomodation",
        100,
        1000
    ));
    buildings.add(new Building(
        new Texture(Gdx.files.internal("buildings/sleep_white.png")),
        0.108f,
        new Vector2(1.0f, -0.9f),
        new Point(),
        new Point(10, 10),
        false,
        BuildingType.SLEEPING,
        "Student Accomodation",
        100,
        1000
    ));

    final Button eatB = new TextButton("Eating", skin, "toggle");
    final Button sleepB = new TextButton("Sleeping", skin, "toggle");
    final Button recB = new TextButton("Recreation", skin, "toggle");
    final Button learnB = new TextButton("Learning", skin, "toggle");
    group.addActor(eatB);
    group.addActor(sleepB);
    group.addActor(recB);
    group.addActor(learnB);
    mainTable.addActor(group);
    mainTable.row();

    // Register the buildings with the achievementTracker
    for (Building building : buildings) {
      world.achievementTracker.buildingsPlacedCount.put(building.texture, 0);
    }

    for (BuildingType type : BuildingType.values()) {
      buildingTypes.put(type, new BuildingMenuEntry());
    }

    // Add buildings to the table
    for (int i = 0; i < buildings.size(); i++) {
      buildingImages.add(new Image(buildings.get(i).texture));
      final int buildingIndex = i;
      buildingImages.get(i).addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent e, float x, float y) {
          if (world.selectedBuilding == buildings.get(buildingIndex)) {
            world.selectedBuilding = null;
          } else {
            world.selectedBuilding = buildings.get(buildingIndex);
            buildingInfoLabel.setAlignment(Align.center);
            buildingInfoLabel.setText(world.selectedBuilding.name + " - Press 'R' to rotate\nPress 'F' to deselect");
            if (world.selectedBuilding.flipped) {
              world.selectedBuilding.flipped = false;
              int temp = world.selectedBuilding.size.x;
              world.selectedBuilding.size.x = world.selectedBuilding.size.y;
              world.selectedBuilding.size.y = temp;
              world.selectedBuildingUpdated = true;
            }
          }
        }
      });
      Label costLabel = new Label("$" + String.valueOf(buildings.get(i).cost), skin);
      costLabel.setAlignment(Align.center);
      buildingTypes.get(buildings.get(i).type).addEntry(buildingImages.get(i), costLabel);
    }

    buildingInfoTable.add(buildingInfoLabel).expandX().align(Align.center).padBottom(25);

    for (BuildingMenuEntry e : buildingTypes.values()) {
      e.addToTable();
    }

    Stack content = new Stack();
    final Table eatTable = buildingTypes.get(BuildingType.EATING).getTable();
    final Table recTable = buildingTypes.get(BuildingType.RECREATION).getTable();
    final Table sleepTable = buildingTypes.get(BuildingType.SLEEPING).getTable();
    final Table learnTable = buildingTypes.get(BuildingType.LEARNING).getTable();
    content.addActor(eatTable);
    content.addActor(recTable);
    content.addActor(sleepTable);
    content.addActor(learnTable);

    mainTable.add(content).expand().fill();

    ChangeListener listener = new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        eatTable.setVisible(eatB.isChecked());
        recTable.setVisible(recB.isChecked());
        sleepTable.setVisible(sleepB.isChecked());
        learnTable.setVisible(learnB.isChecked());
      }
    };

    eatB.addListener(listener);
    recB.addListener(listener);
    sleepB.addListener(listener);
    learnB.addListener(listener);

    ButtonGroup<Button> tabs = new ButtonGroup<>();
    tabs.setMinCheckCount(1);
    tabs.setMaxCheckCount(1);
    tabs.add(eatB);
    tabs.add(recB);
    tabs.add(sleepB);
    tabs.add(learnB);

    stage.addActor(bar);
    stage.addActor(mainTable);
    stage.addActor(buildingInfoTable);
    previewMenu = new BuildingPreviewMenu(stage);
  }

  /**
   * Called when the window is resized, scales the building menu images with the window size.

   * @param width - The new width of the window in pixels
   * @param height - The new height of the window in pixels
   */
  public void resize(int width, int height) {
    bar.setBounds(0, 0, width, height * 0.125f);
    buildingInfoTable.setBounds(0, height * 0.125f, width, height * 0.025f);
    // for (BuildingType menuEntry : navTableMap.values()) {
    //   menuEntry.resize(width, height);
    // }
    buildingInfoLabel.setFontScale(height * 0.0015f);
    previewMenu.resize(width, height);
  }

  /**
   * Called when the building menu needs to be redrawn with new values in the labels.
   */
  public void update() {
    if (GameState.gameOver) {
      buildingInfoLabel.setText("Game Over!");
    } else if (world.selectedBuilding == null) {
      buildingInfoLabel.setText("");
      previewMenu.hideContent();
    } else {
      previewMenu.showContent(world.selectedBuilding);
    }
  }

  public void reset() {
    buildingInfoLabel.setText("");
    previewMenu.hideContent();
  }
}
