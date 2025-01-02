package io.github.unisim.ui;

import java.util.function.Function;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.unisim.GameState;
import io.github.unisim.achievements.AchievementManager;
import io.github.unisim.achievements.AchievementTracker;
import io.github.unisim.leaderboard.Leaderboard;
import io.github.unisim.leaderboard.LeaderboardEntry;
import io.github.unisim.scoring.ScoreTracker;

/**
 * Menu that is displayed when the timer has run out. This is where the final score
 * will be calculated in future.
 */
public class GameOverMenu {
  private Stage stage;
  private Skin skin;
  private ShapeActor bar = new ShapeActor(GameState.UISecondaryColour);
  private Table table;
  private TextButton mainMenuButton;
  private Cell<TextButton> buttonCell;
  private InputMultiplexer inputMultiplexer = new InputMultiplexer();

  private ScoreTracker scoreTracker;
  private AchievementManager achievementManager;
  private Leaderboard leaderboard;
  private Image leaderboardBackground = new Image(new Texture("ui/leaderboard_table.png"));
  private Table leaderboardTable;
  private TextField nameField;
  private TextButton submitButton;

  private boolean addedToLeaderboard;

  /**
   * Creates a new GameOverMenu and initialises all events and UI elements used in the menu.
   */
  public GameOverMenu(ScoreTracker scoreTracker, AchievementManager achievementManager, AchievementTracker achievementTracker) {
    addedToLeaderboard = false;
    leaderboard = new Leaderboard();
    this.scoreTracker = scoreTracker;
    this.achievementManager = achievementManager;

    stage = new Stage(new ScreenViewport());
    table = new Table();
    skin = GameState.defaultSkin;

    // Play button
    mainMenuButton = new TextButton("Return to Main Menu", skin);
    mainMenuButton.addListener(new ClickListener() {
      @Override
      public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
        // Switch to the game screen
        GameState.currentScreen = GameState.startScreen;
      }
    });

    leaderboardBackground.setBounds(100f, 100f, 300f, 550f);
    leaderboardTable = new Table(skin);
    leaderboardTable.setBounds(100f, 100f, 300f, 550f);
    updateLeaderboardTable();

    stage.addActor(leaderboardBackground);
    stage.addActor(leaderboardTable);

    // Add UI elements to the stage
    buttonCell = table.add(mainMenuButton).center();
    stage.addActor(bar);
    stage.addActor(table);

    inputMultiplexer.addProcessor(GameState.fullscreenInputProcessor);
    inputMultiplexer.addProcessor(stage);
  }

  private void updateLeaderboardTable() {
    leaderboardTable.add("Leaderboard").top().padTop(10).padBottom(20);
    for (LeaderboardEntry entry : leaderboard.entries()) {
      leaderboardTable.row().padTop(7);
      leaderboardTable.add(entry.name()).left().padLeft(10);
      leaderboardTable.add(Integer.toString(entry.score())).right().padRight(10);
    }
    leaderboardTable.row().padTop(7);

    nameField = new TextField("", skin);
    nameField.setMessageText("Enter your name");
    nameField.setAlignment(Align.center);
    leaderboardTable.add(nameField).bottom().padTop(10).padBottom(10).height(25);

    submitButton = new TextButton("Submit", skin);
    submitButton.addListener(new ClickListener() {
      @Override
      public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
        if (addedToLeaderboard) {
            return;
        }
        Integer finalScore = scoreTracker.getFinalScore();
        final Iterable<Function<Integer, Integer>> unlockedScoreModifiers = () -> achievementManager.getUnlockedScoreModifiers();
        for (Function<Integer, Integer> modifierFunction : unlockedScoreModifiers) {
            finalScore = modifierFunction.apply(finalScore);
        }
        LeaderboardEntry newEntry;
        try {
            newEntry = new LeaderboardEntry(nameField.getText(), finalScore);
        } catch (IllegalArgumentException e) {
            nameField.setColor(Color.RED);
            return;
        }
        addedToLeaderboard = true;
        leaderboard.addEntry(newEntry);
        leaderboard.save();
        leaderboardTable.clear();
        updateLeaderboardTable();
      }
    });
    leaderboardTable.add(submitButton).bottom().padTop(10).padBottom(10).width(50).height(25);
  }

  public void render(float delta) {
    stage.act(delta);
    stage.draw();
  }

  /**
   * Called when the game window is resized and we need to adjust the scale of the UI elements.

   * @param width - The new game window width in pixels
   * @param height - The new game window height in pixels
   */
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
    table.setBounds(0, 0, width, height * 0.1f);
    bar.setBounds(0, 0, width, height * 0.1f);
    buttonCell.width(width * 0.3f).height(height * 0.1f);
    leaderboardBackground.setBounds(width * 0.08f, height * 0.14f, width * 0.24f, height * 0.74f);
    leaderboardTable.setBounds(width * 0.08f, height * 0.14f, width * 0.24f, height * 0.74f);
  }

  public InputProcessor getInputProcessor() {
    return inputMultiplexer;
  }

}
