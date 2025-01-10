package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import io.github.unisim.GameState;
import io.github.unisim.Point;
import io.github.unisim.building.Building;
import io.github.unisim.building.BuildingType;
import io.github.unisim.world.World;
import java.util.ArrayList;

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
  private BuildingNavMenu navMenu;
  private Label buildingInfoLabel = new Label(
      "", new Skin(Gdx.files.internal("ui/uiskin.json")));
  private Table buildingInfoTable = new Table();
  private BuildingPreviewMenu previewMenu;
  private int currMenuKey = 1;

  /**
   * Create a Building Menu and attach its actors and components to the provided stage.
   * Also handles drawing buildings and their flipped variants

   * @param stage - The stage on which to draw the menu.
   */
  public BuildingMenu(Stage stage, World world) {
    this.world = world;
    // Set building images and sizes
    buildings.add(new Building(
        new Texture(Gdx.files.internal("buildings/cafe.png")),
        0.0055f,
        new Vector2(0.5f, -0.9f),
        new Point(),
        new Point(6, 6),
        false,
        BuildingType.EATING,
        "Cafe",
        15,
        100
    ));
    buildings.add(new Building(
        new Texture(Gdx.files.internal("buildings/shop.png")),
        0.0035f,
        new Vector2(-0.1f, 0.5f),
        new Point(),
        new Point(5, 4),
        false,
        BuildingType.EATING,
        "Shop",
        30,
        300
    ));
    buildings.add(new Building(
        new Texture(Gdx.files.internal("buildings/lectureBuilding.png")),
        0.005f,
        new Vector2(1.9f, -4.1f),
        new Point(),
        new Point(10, 18),
        false,
        BuildingType.LEARNING,
        "Lecture building",
        100,
        1000
    ));
    buildings.add(new Building(
        new Texture(Gdx.files.internal("buildings/labs.png")),
        0.0075f,
        new Vector2(0.3f, 1.4f),
        new Point(),
        new Point(31, 13),
        false,
        BuildingType.LEARNING,
        "Laboratory",
        150,
        1500
    ));
    buildings.add(new Building(
        new Texture(Gdx.files.internal("buildings/library.png")),
        0.0075f,
        new Vector2(-0.7f, -1.5f),
        new Point(),
        new Point(9, 9),
        true,
        BuildingType.LEARNING,
        "Library",
        50,
        500
    ));
    buildings.add(new Building(
        new Texture(Gdx.files.internal("buildings/basketballCourt.png")),
        0.0025f,
        new Vector2(1.2f, -2.4f),
        new Point(),
        new Point(7, 12),
        false,
        BuildingType.RECREATION,
        "Basketball Court",
        10,
        500
    ));
    buildings.add(new Building(
        new Texture(Gdx.files.internal("buildings/pool.png")),
        0.01f,
        new Vector2(-1.0f, -2.0f),
        new Point(),
        new Point(7, 14),
        false,
        BuildingType.RECREATION,
        "Swimming pool",
        5,
        250
    ));
    buildings.add(new Building(
        new Texture(Gdx.files.internal("buildings/tennisCourt.png")),
        0.0025f,
        new Vector2(-0.3f, -1.5f),
        new Point(),
        new Point(7, 11),
        false,
        BuildingType.RECREATION,
        "Tennis Court",
        10,
        500
    ));
    buildings.add(new Building(
        new Texture(Gdx.files.internal("buildings/studentHousing.png")),
        0.005f,
        new Vector2(1.5f, -2.0f),
        new Point(),
        new Point(12, 12),
        false,
        BuildingType.SLEEPING,
        "Student Accomodation",
        100,
        1000
    ));
    buildings.add(new Building(
        new Texture(Gdx.files.internal("buildings/sleep_white.png")),
        0.005f,
        new Vector2(0.7f, -1.0f),
        new Point(),
        new Point(12, 10),
        false,
        BuildingType.SLEEPING,
        "Student Accomodation",
        150,
        1500
    ));

    navMenu = new BuildingNavMenu(buildings);

    // Register the buildings with the achievementTracker
    for (Building building : buildings) {
      world.achievementTracker.buildingsPlacedCount.put(building.texture, 0);
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
      navMenu.addBuilding(buildings.get(i).type, buildingImages.get(i), buildings.get(i).cost);
    }

    navMenu.createTable();

    buildingInfoTable.add(buildingInfoLabel).expandX().align(Align.center).padBottom(25);

    stage.addActor(bar);
    stage.addActor(navMenu.getTable());
    stage.addActor(buildingInfoTable);
    previewMenu = new BuildingPreviewMenu(stage);
  }

  /**
   * Called when the window is resized, scales the building menu images with the window size.

   * @param width - The new width of the window in pixels
   * @param height - The new height of the window in pixels
   */
  public void resize(int width, int height) {
    bar.setBounds(0, 0, width, height * 0.16f);
    buildingInfoTable.setBounds(0, height * 0.16f, width, height * 0.025f);
    navMenu.resize(width, height);
    buildingInfoLabel.setFontScale(height * 0.0015f);
    previewMenu.resize(width, height);
  }

  /**
   * Called when the building menu needs to be redrawn with new values in the labels.
   */
  public void update() {
    if (GameState.gameOver) {
      buildingInfoLabel.setText("Game Over!");
    } else {
      if (world.menuKey != currMenuKey) {
        currMenuKey = world.menuKey;
        navMenu.changeBuildingType(currMenuKey);
      }
      if (world.selectedBuilding == null) {
        buildingInfoLabel.setText("");
        previewMenu.hideContent();
      } else {
        previewMenu.showContent(world.selectedBuilding);
      }
    }
  }

  public void reset() {
    buildingInfoLabel.setText("");
    previewMenu.hideContent();
  }

  public void changeBuildingType(int i) {
    navMenu.changeBuildingType(i);
  }
}
