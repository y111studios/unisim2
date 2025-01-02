package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.unisim.GameState;
import io.github.unisim.Timer;
import io.github.unisim.achievements.Achievement;
import io.github.unisim.achievements.DefinedAchievements;
import io.github.unisim.world.UiInputProcessor;
import io.github.unisim.world.World;
import io.github.unisim.world.WorldInputProcessor;

/**
 * Game screen where the main game is rendered and controlled.
 * Supports pausing the game with a pause menu.
 */
public class GameScreen implements Screen {
  private World world;
  private Stage stage = new Stage(new ScreenViewport());
  private AchievementBar achievementBar;
  private InfoBar infoBar;
  private BuildingMenu buildingMenu;
  private Timer timer;
  private InputProcessor uiInputProcessor = new UiInputProcessor(stage);
  private InputProcessor worldInputProcessor;
  private InputMultiplexer inputMultiplexer = new InputMultiplexer();
  private GameOverMenu gameOverMenu;
  private EventDisplay eventDisplay;
  private float eventTimer;
  private final static float EVENT_INTERVAL = 45f;
  private ManagementMenu managementMenu;

  /**
   * Constructor for the GameScreen.
   */
  public GameScreen() {
    achievementBar = new AchievementBar(stage);
    world = new World(achievementBar);
    managementMenu = new ManagementMenu(stage, world);
    worldInputProcessor = new WorldInputProcessor(world, managementMenu);
    timer = new Timer(1_000);
    infoBar = new InfoBar(stage, timer, world);
    buildingMenu = new BuildingMenu(stage, world);
    eventDisplay = new EventDisplay(stage, world.moneyTracker, world.satisfactionTracker);
    eventTimer = 0;

    inputMultiplexer.addProcessor(GameState.fullscreenInputProcessor);
    inputMultiplexer.addProcessor(stage);
    inputMultiplexer.addProcessor(uiInputProcessor);
    inputMultiplexer.addProcessor(worldInputProcessor);

    gameOverMenu = new GameOverMenu(world.scoreTracker, world.achievementManager, world.achievementTracker);
  }

  @Override
  public void show() {
  }

  @Override
  public void render(float delta) {
    world.render();
    float dt = Gdx.graphics.getDeltaTime();

    if (!GameState.paused && !GameState.gameOver) {
      if (!timer.tick(dt * 1000)) {
        GameState.gameOver = true;
        if (world.achievementTracker.buildingsPlaced <= 5) {
          if (world.achievementManager.unlockAchievement(DefinedAchievements.Minimalist)) {
              Achievement achievement = world.achievementManager.getAchievement(DefinedAchievements.Minimalist);
              achievementBar.setAchievement(achievement);
          }
        }
        if (world.achievementTracker.hadInput == false) {
            world.achievementManager.unlockAchievement(DefinedAchievements.Useless);
        }
        Gdx.input.setInputProcessor(gameOverMenu.getInputProcessor());
      }

      eventTimer += dt;
      if (eventTimer >= EVENT_INTERVAL) {
        if (!GameState.paused) {
          eventDisplay.show();
          eventTimer = 0;
        }
      }
    }

    eventDisplay.update();
    ((WorldInputProcessor) worldInputProcessor).update(dt);
    stage.act(dt);
    infoBar.update();
    buildingMenu.update();
    stage.draw();

    if (GameState.gameOver) {
      world.zoom((world.getMaxZoom() - world.getZoom()) * 2f);
      world.pan((150 - world.getCameraPos().x) / 10, -world.getCameraPos().y / 10);
      gameOverMenu.render(delta);
    }

  }

  @Override
  public void resize(int width, int height) {
    world.resize(width, height);
    stage.getViewport().update(width, height, true);
    infoBar.resize(width, height);
    buildingMenu.resize(width, height);
    gameOverMenu.resize(width, height);
    managementMenu.resize(width, height);
    achievementBar.resize(width, height);
    eventDisplay.resize(width, height);
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
    Gdx.input.setInputProcessor(inputMultiplexer);

    if (GameState.gameOver) {
      GameState.gameOver = false;
      GameState.paused = true;
      timer.reset();
      world.reset();
      infoBar.reset();
      buildingMenu.reset();
    }
  }

  @Override
  public void hide() {
  }

  @Override
  public void dispose() {
    world.dispose();
    stage.dispose();
  }

}
