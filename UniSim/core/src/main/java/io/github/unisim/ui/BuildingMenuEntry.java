package io.github.unisim.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class BuildingMenuEntry {

  private Table navTable;
  private Table buildingTable;
  private Table costTable;

  /**
   * creates a new holder for the images and navigation
   * pannel for a building type
   */
  public BuildingMenuEntry() {
    navTable = new Table();
    buildingTable = new Table();
    costTable = new Table();
  }

  public void addToNavTable(Actor actor) {
    navTable.add(actor);
  }

  public void addToBuildingTable(Actor actor) {
    buildingTable.add(actor);
  }

  public void addToCostTable(Actor actor) {
    costTable.add(actor);
  }

  public void addToStage(Stage stage) {
    stage.addActor(buildingTable);
    stage.addActor(navTable);
    stage.addActor(costTable);
  }

  public void removeFromStage() {
    buildingTable.remove();
    navTable.remove();
    costTable.remove();
  }

  public Table getNavTable() {
    return navTable;
  }

  public Table getBuildingTable() {
    return buildingTable;
  }

  public Table getCostTable() {
    return costTable;
  }

  /**
   * Called when the window is resized, scales the building menu images with the window size.

   * @param width - The new width of the window in pixels
   * @param height - The new height of the window in pixels
   */
  @SuppressWarnings("unchecked")
  public void resize(int width, int height) {
    costTable.setBounds(0, 0, width, height * 0.025f);
    buildingTable.setBounds(0, height * 0.015f, width, height * 0.1f);
    navTable.setBounds(0, height * 0.1f, width, height * 0.025f);
    // we must perform an unchecked type conversion here
    // this is acceptable as we know our table only contains instances of Actors
    for (Cell<Actor> cell : buildingTable.getCells()) {
      Image buildingImage = (Image) (cell.getActor());
      Vector2 textureSize = new Vector2(buildingImage.getWidth(), buildingImage.getHeight());
      cell.width(
          height * 0.1f * (textureSize.x < textureSize.y ? textureSize.x / textureSize.y : 1)
      ).height(
          height * 0.1f * (textureSize.y < textureSize.x ? textureSize.y / textureSize.x : 1)
      );
    }
    for (Cell<Actor> cell : costTable.getCells()) {
      cell.pad(width * 0.02f);
    }
  }
}
