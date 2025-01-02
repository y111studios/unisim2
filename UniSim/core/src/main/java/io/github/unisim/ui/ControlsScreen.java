package io.github.unisim.ui;

import java.time.Duration;
import java.time.Instant;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.unisim.GameState;
import io.github.unisim.achievements.AchievementManager;
import io.github.unisim.achievements.AchievementTracker;
import io.github.unisim.achievements.DefinedAchievements;

public class ControlsScreen implements Screen {

    private Stage stage;
    private Table table;
    private Skin skin = GameState.defaultSkin;
    private TextButton backButton;
    private InputMultiplexer inputMultiplexer = new InputMultiplexer();

    private Texture tableBackgroundTexture;
    private Texture titleTexture;
    private Image titleImage;

    public ControlsScreen(AchievementTracker achievementTracker) {
        stage = new Stage();
        table = new Table();

        titleTexture = new Texture("ui/banner_modern_controls.png");
        titleImage = new Image(titleTexture);
        titleImage.setPosition((Gdx.graphics.getWidth() - titleImage.getWidth()) / 2, Gdx.graphics.getHeight() - titleImage.getHeight() - 40);

        tableBackgroundTexture = new Texture("ui/tile_0003.png");

        Label actionHeader = new Label("Action", skin);
        actionHeader.setFontScale(1.3f);
        Label controlHeader = new Label("Control", skin);
        controlHeader.setFontScale(1.3f);

        table.setSize(500, 500);
        table.setPosition((Gdx.graphics.getWidth() - table.getWidth()) / 2, ((Gdx.graphics.getHeight() - table.getHeight()) / 2));
        table.setBackground(new TextureRegionDrawable(tableBackgroundTexture));

        table.add(actionHeader).padRight(15);
        table.add(controlHeader).padLeft(15);

        table.row().padTop(10);
        table.add(new Label("Pan Up", skin)).align(Align.left).padRight(15);
        table.add(new Label("W / Up Arrow / Mouse", skin)).align(Align.left).padLeft(15);

        table.row().padTop(10);
        table.add(new Label("Pan Down", skin)).align(Align.left).padRight(15);
        table.add(new Label("S / Down Arrow / Mouse", skin)).align(Align.left).padLeft(15);

        table.row().padTop(10);
        table.add(new Label("Pan Left", skin)).align(Align.left).padRight(15);
        table.add(new Label("A / Left Arrow / Mouse", skin)).align(Align.left).padLeft(15);

        table.row().padTop(10);
        table.add(new Label("Pan Right", skin)).align(Align.left).padRight(15);
        table.add(new Label("D / Right Arrow / Mouse", skin)).align(Align.left).padLeft(15);

        table.row().padTop(10);
        table.add(new Label("Zoom In", skin)).align(Align.left).padRight(15);
        table.add(new Label("Z / Mouse Wheel Up", skin)).align(Align.left).padLeft(15);

        table.row().padTop(10);
        table.add(new Label("Zoom Out", skin)).align(Align.left).padRight(15);
        table.add(new Label("X / Mouse Wheel Down", skin)).align(Align.left).padLeft(15);

        table.row().padTop(10);
        table.add(new Label("Deselect Building", skin)).align(Align.left).padRight(15);
        table.add(new Label("F", skin)).align(Align.left).padLeft(15);

        table.row().padTop(10);
        table.add(new Label("Rotate Building", skin)).align(Align.left).padRight(15);
        table.add(new Label("R", skin)).align(Align.left).padLeft(15);

        table.row().padTop(10);
        table.add(new Label("Place Building", skin)).align(Align.left).padRight(15);
        table.add(new Label("Mouse Left Click", skin)).align(Align.left).padLeft(15);

        table.row().padTop(10);
        table.add(new Label("Remove Building", skin)).align(Align.left).padRight(15);
        table.add(new Label("Mouse Right Click", skin)).align(Align.left).padLeft(15);

        table.row().padTop(10);
        table.add(new Label("Pause Game", skin)).align(Align.left).padRight(15);
        table.add(new Label("Space", skin)).align(Align.left).padLeft(15);

        table.row().padTop(10);
        table.add(new Label("Toggle Management Menu", skin)).align(Align.left).padRight(15);
        table.add(new Label("Tab", skin)).align(Align.left).padLeft(15);

        table.row().padTop(10);
        table.add(new Label("Toggle Full Screen", skin)).align(Align.left).padRight(15);
        table.add(new Label("F11", skin)).align(Align.left).padLeft(15);

        // Back button
        backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                // Go back to the start menu
                GameState.currentScreen = GameState.startScreen;
                if (Duration.between(achievementTracker.controlsScreenOpened, Instant.now()).toSeconds() < 1) {
                    AchievementManager achievementManager = new AchievementManager();
                    achievementManager.unlockAchievement(DefinedAchievements.Tried);
                }
            }
        });
        table.row().padTop(20);
        table.add(backButton).colspan(2).center().width(75).height(35);

        stage.addActor(titleImage);
        stage.addActor(table);

        inputMultiplexer.addProcessor(GameState.fullscreenInputProcessor);
        inputMultiplexer.addProcessor(stage);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // Clear the screen
        ScreenUtils.clear(0.4f, 0.5f, 0.6f, 1.0f);

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
        tableBackgroundTexture.dispose();
        titleTexture.dispose();
    }

}
