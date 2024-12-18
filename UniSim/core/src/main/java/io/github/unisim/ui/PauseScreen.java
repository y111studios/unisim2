package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.unisim.GameState;

/**
 * The pause screen that allows users to return to the menu or resume the game
 */
public class PauseScreen implements Screen {

    private Stage stage;
    private Table table;
    private Skin skin = GameState.defaultSkin;
    private Slider volumeSlider;
    private Label volumeLabel;
    private TextButton mainMenuButton;
    private InputMultiplexer inputMultiplexer = new InputMultiplexer();
    private TextButton resumeButton;

    public PauseScreen() {
        stage = new Stage();
        table = new Table();

        resumeButton = new TextButton("Resume", skin);
        resumeButton.setPosition(150, 150);
        resumeButton.setSize(200, 60);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x,
                    float y) {
                GameState.paused = false;
                GameState.currentScreen = GameState.gameScreen;
            }
        });

        mainMenuButton = new TextButton("Exit to Main Menu", skin);
        mainMenuButton.setPosition(150, 80);
        mainMenuButton.setSize(200, 60);
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x,
                    float y) {
                final Dialog confirmDialog = new Dialog("Confirm", skin) {
                    @Override
                    protected void result(Object object) {
                        if ((Boolean) object) {
                            GameState.gameOver = true;
                            GameState.currentScreen = GameState.startScreen;
                        }
                    }
                };
                confirmDialog.text("Are you sure you want to return to the main menu?");
                confirmDialog.button("Yes", true);
                confirmDialog.button("No", false);
                confirmDialog.show(stage);
            }
        });

        table.setFillParent(true);
        table.center().center();
        table.pad(100, 100, 100, 100);
        table.add(mainMenuButton).center().width(250).height(67).padBottom(10);
        table.row();
        table.add(resumeButton).center().width(250).height(67).padBottom(10);
        stage.addActor(table);

        inputMultiplexer.addProcessor(GameState.fullscreenInputProcessor);
        inputMultiplexer.addProcessor(stage);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(GameState.UISecondaryColour);

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
