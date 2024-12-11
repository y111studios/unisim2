
package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.unisim.GameState;

public class ControlsScreen implements Screen {

    private Stage stage;
    private Table table;
    private Skin skin = GameState.defaultSkin;
    private TextButton backButton;
    private InputMultiplexer inputMultiplexer = new InputMultiplexer();

    public ControlsScreen() {
        stage = new Stage();
        table = new Table();

        // Back button
        backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                // Go back to the start menu
                GameState.currentScreen = GameState.startScreen;
            }
        });

        Label controlsLabel = new Label("Controls:", skin);
        Label panUpLabel = new Label("Pan Up: W / Up Arrow / Mouse", skin);
        Label panDownLabel = new Label("Pan Down: S / Down Arrow / Mouse", skin);
        Label panLeftLabel = new Label("Pan Left: A / Left Arrow / Mouse", skin);
        Label panRightLabel = new Label("Pan Right: D / Right Arrow / Mouse", skin);
        Label zoomInLabel = new Label("Zoom In: Z / Mouse Scroll Up", skin);
        Label zoomOutLabel = new Label("Zoom Out: X / Mouse Scroll Down", skin);
        Label placeBuildingLabel = new Label("Place Building: Mouse Left Click", skin);
        Label removeBuildingLabel = new Label("Remove Building: Mouse Left Click", skin);
        Label pauseLabel = new Label("Pause: Space", skin);
        Label toggleFullScreenLabel = new Label("Toggle Full Screen: F11", skin);

        table.setFillParent(true);
        table.add(controlsLabel).padBottom(20);
        table.row();
        table.add(panUpLabel).padBottom(10);
        table.row();
        table.add(panDownLabel).padBottom(10);
        table.row();
        table.add(panLeftLabel).padBottom(10);
        table.row();
        table.add(panRightLabel).padBottom(10);
        table.row();
        table.add(zoomInLabel).padBottom(10);
        table.row();
        table.add(zoomOutLabel).padBottom(10);
        table.row();
        table.add(placeBuildingLabel).padBottom(10);
        table.row();
        table.add(removeBuildingLabel).padBottom(10);
        table.row();
        table.add(pauseLabel).padBottom(10);
        table.row();
        table.add(toggleFullScreenLabel).padBottom(20);
        table.row();
        table.add(backButton).center().width(60).height(40);
        stage.addActor(table);

        inputMultiplexer.addProcessor(GameState.fullscreenInputProcessor);
        inputMultiplexer.addProcessor(stage);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // Clear the screen
        ScreenUtils.clear(GameState.UISecondaryColour);

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
