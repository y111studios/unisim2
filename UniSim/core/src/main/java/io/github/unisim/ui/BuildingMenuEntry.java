package io.github.unisim.ui;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class BuildingMenuEntry {

  private Table table;
  private Map<Image, Label> entries;
  /**
   * creates a new holder for the images and navigation
   * pannel for a building type
   */
  public BuildingMenuEntry() {
    table = new Table();
    entries = new HashMap<>();
  }

  public void addEntry(Image img, Label cost) {
    entries.put(img, cost);
  }

  public void addToTable() {
    for (Image img : entries.keySet()) {
      table.addActor(img);
    }
    table.row();
    for (Label cost : entries.values()) {
      table.addActor(cost);
    }
  }

  public Table getTable() {
    return table;
  }

  /**
   * Called when the window is resized, scales the building menu images with the window size.

   * @param width - The new width of the window in pixels
   * @param height - The new height of the window in pixels
   */
  @SuppressWarnings("unchecked")
  public void resize(int width, int height) {
    // we must perform an unchecked type conversion here
    // this is acceptable as we know our table only contains instances of Actors
    for (Cell<Actor> cell : table.getCells()) {
      Actor buildingImage = cell.getActor();
      Vector2 textureSize = new Vector2(buildingImage.getWidth(), buildingImage.getHeight());
      cell.width(
          height * 0.1f * (textureSize.x < textureSize.y ? textureSize.x / textureSize.y : 1)
      ).height(
          height * 0.1f * (textureSize.y < textureSize.x ? textureSize.y / textureSize.x : 1)
      );
    }
  }
}
