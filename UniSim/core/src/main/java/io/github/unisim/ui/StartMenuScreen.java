package io.github.unisim.ui;

import java.time.Instant;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.unisim.GameState;
import io.github.unisim.achievements.AchievementTracker;

/**
 * The start menu screen which presents the player with the option to start the
 * game
 * or access the settings menu.
 */
public class StartMenuScreen implements Screen {
  private Stage stage;
  private Table table;
  private Skin skin;
  private TextButton playButton;
  private TextButton settingsButton;
  private TextButton controlsButton;
  private InputMultiplexer inputMultiplexer = new InputMultiplexer();
  public static AchievementTracker achievementTracker = new AchievementTracker();

  /**
   * Create a new StartMenuScreen and draw the initial UI layout.
   */
  public StartMenuScreen() {
    stage = new Stage();
    table = new Table();
    skin = GameState.defaultSkin;

    // Play button
    playButton = new TextButton("Play", skin);
    playButton.addListener(new ClickListener() {
      @Override
      public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
        // Switch to the game screen
        GameState.currentScreen = GameState.gameScreen;
      }
    });

    // Settings button
    settingsButton = new TextButton("Settings", skin);
    settingsButton.addListener(new ClickListener() {
      @Override
      public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
        // Switch to the settings screen
        GameState.currentScreen = GameState.settingScreen;
      }
    });

    // Controls button
    controlsButton = new TextButton("Controls", skin);
    controlsButton.addListener(new ClickListener() {
      @Override
      public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
        // Switch to the controls screen
        GameState.currentScreen = GameState.controlsScreen;
        achievementTracker.controlsScreenOpened = Instant.now();
      }
    });

    // Add UI elements to the stage
    table.setFillParent(true);
    table.center().center();
    table.pad(100, 100, 100, 100);
    table.add(playButton).center().width(250).height(100).padBottom(10);
    table.row();
    table.add(settingsButton).center().width(250).height(67).padBottom(10);
    table.row();
    table.add(controlsButton).center().width(250).height(67).padBottom(10);
    stage.addActor(table);

    inputMultiplexer.addProcessor(GameState.fullscreenInputProcessor);
    inputMultiplexer.addProcessor(stage);
  }

  @Override
  public void show() {
  }

  @Override
  public void render(float delta) {
    // Clear the screen
    ScreenUtils.clear(GameState.UISecondaryColour);

    // Draw the stage containing buttons
    stage.act(delta);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
    Gdx.input.setInputProcessor(inputMultiplexer);
  }

  @Override
  public void hide() {
  }

  @Override
  public void dispose() {
    stage.dispose();
    skin.dispose();
  }
}
