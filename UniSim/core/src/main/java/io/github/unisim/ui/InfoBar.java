package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import io.github.unisim.GameState;
import io.github.unisim.Timer;
import io.github.unisim.world.World;

/**
 * Create a Title bar with basic info.
 */
public class InfoBar {
  private ShapeActor bar;
  private Table infoTable = new Table();
  private Table titleTable = new Table();
  private Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
  private Label satisfactionLabel = new Label("", skin);
  private Label titleLabel = new Label("UniSim", skin);
  private Label moneyLabel;
  private Label timerLabel;
  private Label scoreLabel;
  private Texture pauseTexture = new Texture("ui/pause.png");
  private Texture playTexture = new Texture("ui/play.png");
  private Image pauseImage = new Image(pauseTexture);
  private Image playImage = new Image(playTexture);
  private Timer timer;
  private Cell<Label> timerLabelCell;
  private Cell<Label> satisfacationLabelCell;
  private Cell<Label> scoreLabelCell;
  private Cell<Image> pauseButtonCell;
  private Cell<Label> moneyLabelCell;
  private World world;
  /**
   * Create a new infoBar and draws its' components onto the provided stage.

   * @param stage - The stage on which to draw the InfoBar.
   */
  public InfoBar(Stage stage, Timer timer, World world) {
    this.timer = timer;
    this.world = world;

    satisfactionLabel = new Label(world.satisfactionTracker.getStringSatisfaction() + "%", skin);
    moneyLabel = new Label("Money: $" + world.moneyTracker.getMoney(), skin);
    scoreLabel = new Label("Score: " + world.scoreTracker.getFinalScore(), skin);

    // Info Table
    timerLabel = new Label(timer.getRemainingTime(), skin);
    infoTable.center().center();
    pauseButtonCell = infoTable.add(playImage).align(Align.left);
    timerLabelCell = infoTable.add(timerLabel).align(Align.left);
    satisfacationLabelCell = infoTable.add(satisfactionLabel).align(Align.left);
    moneyLabelCell = infoTable.add(moneyLabel).align(Align.left);
    scoreLabelCell = infoTable.add(scoreLabel).align(Align.left);

    // Pause button
    pauseImage.addListener(new ClickListener() {
      @Override
      public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
        GameState.paused = true;
        GameState.currentScreen = GameState.pauseScreen;
      }
    });

    // Play button
    playImage.addListener(new ClickListener() {
      @Override
      public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
        GameState.paused = false;
        pauseButtonCell.setActor(pauseImage);
      }
    });

    titleTable.add(titleLabel).expandX().align(Align.center);

    bar = new ShapeActor(GameState.UIPrimaryColour);
    stage.addActor(bar);
    stage.addActor(infoTable);
    stage.addActor(titleTable);
    resize((int) stage.getWidth(), (int) stage.getHeight());
  }

  /**
   * Called when the UI needs to be updated, usually on every frame.
   */
  public void update() {
    satisfactionLabel.setText(world.satisfactionTracker.getStringSatisfaction() + "%");
    scoreLabel.setText("Score: " + world.scoreTracker.getFinalScore());
    moneyLabel.setText("Money: $" + world.moneyTracker.getMoney());
    timerLabel.setText(timer.getRemainingTime());
    pauseButtonCell.setActor(GameState.paused ? playImage : pauseImage);
  }

  /**
   * Update the bounds of the background & table actors to fit the new size of the screen.

   * @param width - The new width of the screen in pixels.
   * @param height - The enw height of the screen in pixels.
   */
  public void resize(int width, int height) {
    bar.setBounds(0, height * 0.95f, width, height * 0.05f);
    infoTable.setBounds(-width * 0.18f, height * 0.95f, width, height * 0.05f);
    titleTable.setBounds(0, height * 0.95f, width, height * 0.05f);

    timerLabel.setFontScale(height * 0.002f);
    timerLabelCell.width(height * 0.08f).height(height * 0.05f);
    timerLabelCell.padLeft(height * 0.005f);
    satisfactionLabel.setFontScale(height * 0.002f);
    satisfacationLabelCell.width(height * 0.04f).height(height * 0.05f);
    satisfacationLabelCell.padLeft(Math.min(width, height * 2) * 0.10f);
    scoreLabel.setFontScale(height * 0.002f);
    scoreLabelCell.width(height * 0.04f).height(height * 0.05f);
    scoreLabelCell.padLeft(Math.min(width, height * 2) * 0.08f);
    moneyLabel.setFontScale(height * 0.002f);
    moneyLabelCell.width(height * 0.04f).height(height * 0.05f);
    moneyLabelCell.padLeft(Math.min(width, height * 2) * 0.08f);
    moneyLabelCell.padRight(Math.min(width, height * 2) * 0.2f);
    pauseButtonCell.width(height * 0.03f).height(height * 0.03f)
    .padLeft(height * 0.01f).padRight(height * 0.01f);

    titleLabel.setFontScale(height * 0.003f);
  }

  public void reset() {
    pauseButtonCell.setActor(playImage);
  }
}
