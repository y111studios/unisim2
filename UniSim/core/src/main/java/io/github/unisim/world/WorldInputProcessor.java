package io.github.unisim.world;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import io.github.unisim.GameState;
import io.github.unisim.achievements.AchievementTracker;
import io.github.unisim.ui.ManagementMenu;

/**
 * Handles input events related to the world, after they have passed through the UiInputProcessor.
 */
public class WorldInputProcessor implements InputProcessor {
  private World world;
  private ManagementMenu managementMenu;
  private int[] cursorPos = new int[2];
  private int[] cursorPosWhenClicked = new int[2];
  private boolean clickedOnWorld = false;
  private boolean draggedSinceClick = true;
  private boolean moveUp = false;
  private boolean moveDown = false;
  private boolean moveLeft = false;
  private boolean moveRight = false;
  private boolean zoomIn = false;
  private boolean zoomOut = false;

  public AchievementTracker achievementTracker;


  public WorldInputProcessor(World world, ManagementMenu managementMenu) {
    this.world = world;
    this.managementMenu = managementMenu;
    this.achievementTracker = world.achievementTracker;
  }


  @Override
  public boolean keyDown(int keycode) {
    achievementTracker.hadInput = keycode != Keys.SPACE;
    switch (keycode) {
      case Keys.SPACE:
    	 if (GameState.paused) {
    		 GameState.paused = !GameState.paused;
    	 }
    	 else {
    		 GameState.currentScreen = GameState.pauseScreen;
    	 }


        break;
    case Keys.TAB:
        managementMenu.toggleVisibility();
        break;
      case Keys.R:
        // Flip the selected building
        if (world.selectedBuilding != null) {
          world.selectedBuilding.flipped = !world.selectedBuilding.flipped;
          int temp = world.selectedBuilding.size.x;
          world.selectedBuilding.size.x = world.selectedBuilding.size.y;
          world.selectedBuilding.size.y = temp;
          world.selectedBuildingUpdated = true;
        }
        break;
      case Keys.F:
        // Deselect the building
        if (world.selectedBuilding != null) {
          world.selectedBuilding = null;
          world.selectedBuildingUpdated = true;
        }
        break;
      case Keys.W:
      case Keys.UP:
        moveUp = true;
        break;
      case Keys.S:
      case Keys.DOWN:
        moveDown = true;
        break;
      case Keys.A:
      case Keys.LEFT:
        moveLeft = true;
        break;
      case Keys.D:
      case Keys.RIGHT:
        moveRight = true;
        break;
      case Keys.Z:
        zoomIn = true;
        break;
      case Keys.X:
        zoomOut = true;
        break;
      case Keys.NUM_1:
        world.menuKey = 1;
        break;
      case Keys.NUM_2:
        world.menuKey = 2;
        break;
      case Keys.NUM_3:
        world.menuKey = 3;
        break;
      case Keys.NUM_4:
        world.menuKey = 4;
        break;
      default:
        break;
    }
    return false;
  }


  @Override
  public boolean keyUp(int keycode) {
    switch (keycode) {
      case Keys.W:
      case Keys.UP:
        moveUp = false;
        break;
      case Keys.S:
      case Keys.DOWN:
        moveDown = false;
        break;
      case Keys.A:
      case Keys.LEFT:
        moveLeft = false;
        break;
      case Keys.D:
      case Keys.RIGHT:
        moveRight = false;
        break;
      case Keys.Z:
        zoomIn = false;
        break;
      case Keys.X:
        zoomOut = false;
        break;
      default:
        break;
    }
    return false;
  }

  @Override
  public boolean keyTyped(char character) {
    return false;
  }

  /**
   * Detect when the mouse has been clicked and record the cursor postion.
   * Sets the clickedOnWorld flag, if the mouse has been clicked in a valid
   * start location.
   */
  @Override
  public boolean touchDown(int x, int y, int pointer, int button) {
    achievementTracker.hadInput = true;
    clickedOnWorld = true;
    draggedSinceClick = false;
    cursorPos[0] = cursorPosWhenClicked[0] = x;
    cursorPos[1] = cursorPosWhenClicked[1] = y;
    if (world.selectedBuilding == null && world.cursorOverBuilding() && button == Input.Buttons.RIGHT) {
        if (world.removeBuilding(world.getCursorGridPos())) {
            managementMenu.updateElements();
        }
    }
    return true;
  }

  /**
   * When the mouse is released, stop tracking the dragging events.
   */
  @Override
  public boolean touchUp(int x, int y, int pointer, int button) {
    clickedOnWorld = false;
    if (!draggedSinceClick && world.selectedBuilding != null && button == Input.Buttons.LEFT) {
      if (world.placeBuilding()) {
        managementMenu.updateElements();
        draggedSinceClick = true;
      }
    }
    return false;
  }

  /**
   * If the mouse has been clicked in a valid location, allow the map to be panned
   * by clicking and holding the mouse button.
   */
  @Override
  public boolean touchDragged(int x, int y, int pointer) {
    if (clickedOnWorld) {
      if (Math.max(Math.abs(cursorPos[0] - cursorPosWhenClicked[0]),
          Math.abs(cursorPos[1] - cursorPosWhenClicked[1])) > 5) {
        draggedSinceClick = true;
      }
      world.pan(cursorPos[0] - x, y - cursorPos[1]);
      cursorPos[0] = x;
      cursorPos[1] = y;
      return true;
    }
    return false;
  }

  @Override
  public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean mouseMoved(int x, int y) {
    return false;
  }

  /**
   * Zoom in on the map when the mouse wheel is scrolled.
   */
  @Override
  public boolean scrolled(float amountX, float amountY) {
    achievementTracker.hadInput = true;
    world.zoom(amountY);
    return true;
  }


  public void update(float dt) {
    if (moveUp) {
      world.pan(0, 8);
    }
    if (moveDown) {
      world.pan(0, -8);
    }
    if (moveLeft) {
      world.pan(-8, 0);
    }
    if (moveRight) {
      world.pan(8, 0);
    }

    if (zoomIn) {
      world.zoom(-1);
    }
    if (zoomOut) {
      world.zoom(1);
    }
  }
}
