package io.github.unisim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.unisim.ui.GameScreen;
import io.github.unisim.ui.PauseScreen;
import io.github.unisim.ui.SettingsScreen;
import io.github.unisim.ui.StartMenuScreen;
import io.github.unisim.ui.ControlsScreen;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Contains a collection of settings and references that should be available globally.
 */
public class GameState {
  public static final Color UIPrimaryColour = new Color(0.250f, 0.326f, 0.865f, 1.0f);
  public static final Color UISecondaryColour = new Color(0.722f, 0.646f, 0.953f, 1.0f);
  public static final Skin defaultSkin = new Skin(Gdx.files.internal("ui/uiskin.json"));
  public static final BitmapFont iconTextFont = loadTtfFont("ui/MartianMonoNerdFont-Medium.ttf", 13);
  public static final Settings settings = new Settings();
  public static final InputProcessor fullscreenInputProcessor = new FullscreenInputProcessor();
  public static final Screen gameScreen = new GameScreen();
  public static final Screen startScreen = new StartMenuScreen();
  public static final Screen settingScreen = new SettingsScreen();
  public static final Screen controlsScreen = new ControlsScreen(StartMenuScreen.achievementTracker);
  public static final Screen pauseScreen = new PauseScreen();
  // Create an unmodifiable set containing the IDs of all buildable tiles
  // we use a set to make searching more efficient
  public static final Set<Integer> buildableTiles = Stream.of(
  14, 15).collect(Collectors.toSet()
  );
  public static boolean gameOver = false;
  public static boolean paused = true;
  public static Screen currentScreen;

  private static final BitmapFont loadTtfFont(String path, int size) {
    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(path));
    FreeTypeFontParameter parameter = new FreeTypeFontParameter();

    parameter.size = size;
    parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "";

    BitmapFont font = generator.generateFont(parameter);
    generator.dispose();
    return font;
  }
}
