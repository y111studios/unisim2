package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.github.unisim.GameState;

public class AchievementBar {
    private Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    private ShapeActor bar;
    private Table table;
    private Cell<Image> iconCell;
    private Cell<Label> titleCell;
    private Cell<Label> descriptionCell;

    final static float normalisedWidth = 0.4f;
    final static float normalisedHeight = 0.1f;
    final static float normalisedLeftPadding = (1 - normalisedWidth) / 2;
    final static float normalisedTopPadding = 1 - 0.1f - normalisedHeight;

    public AchievementBar(Stage stage) {
        float stageWidth = stage.getWidth();
        float stageHeight = stage.getHeight();
        this.bar = new ShapeActor(GameState.UIPrimaryColour);
        this.bar.setPosition(normalisedLeftPadding * stageWidth, normalisedTopPadding * stageHeight);
        this.bar.setSize(normalisedWidth * stageWidth, normalisedHeight * stageHeight);
        this.table = new Table();
        this.iconCell = table.add(new Image());

        Table rightColumn = new Table();
        this.titleCell = rightColumn.add(new Label("", skin)).center();
        rightColumn.row();
        this.descriptionCell = rightColumn.add(new Label("", skin)).center();

        table.add(rightColumn).expand().fill();

        stage.addActor(bar);
        stage.addActor(table);
    }

}
