package io.github.unisim.ui;

import java.time.Duration;
import java.time.Instant;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.github.unisim.GameState;
import io.github.unisim.achievements.Achievement;

public class AchievementBar {
    private Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    private ShapeActor bar;
    private Table table;
    private Image iconImage;
    private Label titleLabel;
    private Label descriptionLabel;

    private Instant displayEndTime;

    private static final Duration DISPLAY_TIME = Duration.ofSeconds(8);

    final static float normalisedWidth = 0.4f;
    final static float normalisedHeight = 0.1f;
    final static float normalisedLeftPadding = (1 - normalisedWidth) / 2;
    final static float normalisedTopPadding = 1 - 0.1f - normalisedHeight;

    private float stageHeight;
    private float stageWidth;

    public AchievementBar(Stage stage) {
        stageWidth = stage.getWidth();
        stageHeight = stage.getHeight();
        this.bar = new ShapeActor(GameState.UIPrimaryColour);
        this.bar.setPosition(normalisedLeftPadding * stageWidth, (1 + normalisedHeight) * stageHeight);
        this.bar.setSize(normalisedWidth * stageWidth, normalisedHeight * stageHeight);
        this.table = new Table();
        this.iconImage = new Image();
        table.add(iconImage).size(0.95f * bar.getHeight()).center().padLeft(0.025f * bar.getHeight());

        Table rightColumn = new Table();
        this.titleLabel = new Label("", skin);
        rightColumn.add(this.titleLabel).center().row();
        this.descriptionLabel = new Label("", skin);
        rightColumn.add(this.descriptionLabel).center();

        table.add(rightColumn).expand().fill();
        this.table.setPosition(bar.getX(), bar.getY());
        this.table.setSize(bar.getWidth(), bar.getHeight());

        stage.addActor(bar);
        stage.addActor(table);
    }

    public void setAchievement(Achievement achievement) {
        if (achievement == null) {
            return;
        }
        iconImage.setDrawable(achievement.getIcon().getDrawable());
        titleLabel.setText(achievement.getName());
        descriptionLabel.setText(achievement.getDescription());

        table.invalidate();
        table.layout();

        showFor(DISPLAY_TIME);
    }

    private void hide() {
        displayEndTime = null;

        bar.addAction(Actions.moveTo(normalisedLeftPadding * stageWidth, (1 + normalisedHeight) * stageHeight, 0.25f, Interpolation.slowFast));
        table.addAction(Actions.moveTo(normalisedLeftPadding * stageWidth, (1 + normalisedHeight) * stageHeight, 0.25f, Interpolation.slowFast));
    }

    private void showFor(Duration duration) {
        displayEndTime = Instant.now().plus(duration);

        bar.addAction(Actions.moveTo(normalisedLeftPadding * stageWidth, normalisedTopPadding * stageHeight, 0.25f, Interpolation.fastSlow));
        table.addAction(Actions.moveTo(normalisedLeftPadding * stageWidth, normalisedTopPadding * stageHeight, 0.25f, Interpolation.fastSlow));
    }

    public void update() {
        if (displayEndTime == null) {
            return;
        }
        if (Instant.now().isAfter(displayEndTime)) {
            hide();
        }
    }

}
