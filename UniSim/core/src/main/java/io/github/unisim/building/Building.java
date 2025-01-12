package io.github.unisim.building;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import io.github.unisim.Point;


/**
 * Represents a building that can be placed on the map.
 */
public class Building {
  public Texture texture;
  // we can save memory by storing only the top-left corner and the size of the building.
  // This works as all buildings are rectangular.
  public Point location;
  public Point size;
  public float textureScale;
  public Vector2 textureOffset;
  public Vector2 reverseOffset;
  public boolean flipped;
  public BuildingType type;
  public String name;
  public int capacity;
  public int cost;
  public int quality;

  /**
   * Create a new building to display in the building menu and place in the world.

   * @param texture - The image to draw over the space the building occupies
   * @param textureScale - The scale of the image compared to the source file
   * @param textureOffset - The offset of the texture in grid tiles
   * @param reverseOffset - The offset when the image is flipped
   * @param location - The (x, y) co-ordinates of the building on the map
   * @param size - The size (width, height) of the building in map tiles
   * @param flipped - Whether to render a flipped variant of the building
   * @param type - The category of building, must be a BuildingType
   * @param name - The name of the building to display when selected
   * @param capacity - The number of people the building can hold
   * @param cost - The cost of the building in currency
   */
  public Building(Texture texture, float textureScale, Vector2 textureOffset, Vector2 reverseOffset, Point location,
      Point size, Boolean flipped, BuildingType type, String name, int capacity, int cost, int quality) {
    this.texture = texture;
    this.location = location;
    this.size = size;
    this.textureScale = textureScale;
    this.reverseOffset = reverseOffset;
    this.textureOffset = textureOffset;
    this.flipped = flipped;
    this.type = type;
    this.name = name;
    this.capacity = capacity;
    this.cost = cost;
    this.quality = quality;
  }
}
