package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.unisim.GameState;

/**
 * The settings screen that allows the player to adjust the volume.
 */
public class TutorialScreen implements Screen {
  private Stage stage;
  private Table table;
  private Skin skin = GameState.defaultSkin;
  private Label howToPlayLabel;
  private Label tutorialLabel;
  private TextButton backButton;
  private InputMultiplexer inputMultiplexer = new InputMultiplexer();

  /**
   * Create a new Tutorial screen and draw the initial UI layout.
   */
  public TutorialScreen() {
    stage = new Stage();
    table = new Table();

    // Title
    howToPlayLabel = new Label("How To Play:", skin);
    howToPlayLabel.setFontScale(2f);
    
    // How To Play
    String tutorial = "It's time to create your own UniSim! To begin, place a housing building. Then open the Management Menu (tab) and enroll students.\n" +
    "Once you have students, increase their satisfaction by placing other types of buildings nearby! Students prefer closer and higher quality buildings.\n" +
    "The money you make increases as you enroll more students, but make sure you have the proper facilities or your satisfaction will decrease.\n" +
    "From time to time, you may encounter an event! Different choices will have different effects, so choose wisely.";
    tutorialLabel = new Label(tutorial, skin);
    tutorialLabel.setFontScale(1.2f);

    // Back button
    backButton = new TextButton("Back", skin);
    //backButton.setPosition(150, 80);
    backButton.setSize(200, 60);
    backButton.addListener(new ClickListener() {
      @Override
      public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
        // Go back to the start menu
        GameState.currentScreen = GameState.startScreen;
      }
    });

    // Add UI elements to stage
    table.setFillParent(true);
    table.center().center();
    table.pad(100, 100, 100, 100);
    table.add(howToPlayLabel).center().padBottom(20).row();
    table.add(tutorialLabel).center().padBottom(20).row();
    table.add(backButton).center().width(250).height(67).padBottom(10);
    stage.addActor(table);

    inputMultiplexer.addProcessor(GameState.fullscreenInputProcessor);
    inputMultiplexer.addProcessor(stage);
  }

  @Override
  public void show() {}

  @Override
  public void render(float delta) {
    // Clear the screen
    ScreenUtils.clear(GameState.UIPrimaryColour);

    // Draw the stage containing the volume slider and buttons
    stage.act(delta);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {}

  @Override
  public void resume() {
    Gdx.input.setInputProcessor(inputMultiplexer);
  }

  @Override
  public void hide() {}

  @Override
  public void dispose() {
    stage.dispose();
    skin.dispose();
  }
}
