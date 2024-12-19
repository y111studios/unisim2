package io.github.unisim.ui;

import java.time.Duration;
import java.time.Instant;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
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
    private Cell<Image> iconCell;

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
        bar = new ShapeActor(GameState.UIPrimaryColour);

        table = new Table();
        iconImage = new Image();
        iconCell = table.add(iconImage).center();

        Table rightColumn = new Table();
        titleLabel = new Label("", skin);
        rightColumn.add(titleLabel).center().row();
        descriptionLabel = new Label("", skin);
        rightColumn.add(descriptionLabel).center();

        table.add(rightColumn).expand().fill();

        resize((int) stage.getWidth(), (int) stage.getHeight());

        stage.addActor(bar);
        stage.addActor(table);
    }

    public void resize(int width, int height) {
        stageWidth = width;
        stageHeight = height;
        positionElements();
    }

    void positionElements() {
        final float yPos = isHidden() ? getHiddenY() : getShownY();

        bar.setSize(normalisedWidth * stageWidth, normalisedHeight * stageHeight);
        bar.setPosition(getX(), yPos);

        table.setSize(normalisedWidth * stageWidth, normalisedHeight * stageHeight);
        table.setPosition(getX(), yPos);

        iconCell.size(0.95f * bar.getHeight()).padLeft(0.025f * bar.getHeight());
    }

    float getX() {
        return normalisedLeftPadding * stageWidth;
    }

    float getShownY() {
        return normalisedTopPadding * stageHeight;
    }

    float getHiddenY() {
        return (1 + normalisedHeight) * stageHeight;
    }

    boolean isHidden() {
        return displayEndTime == null;
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

        bar.addAction(Actions.moveTo(getX(), getHiddenY(), 0.25f, Interpolation.slowFast));
        table.addAction(Actions.moveTo(getX(), getHiddenY(), 0.25f, Interpolation.slowFast));
    }

    private void showFor(Duration duration) {
        displayEndTime = Instant.now().plus(duration);

        bar.addAction(Actions.moveTo(getX(), getShownY(), 0.25f, Interpolation.fastSlow));
        table.addAction(Actions.moveTo(getX(), getShownY(), 0.25f, Interpolation.fastSlow));
    }

    public void update() {
        if (isHidden()) {
            return;
        }
        if (Instant.now().isAfter(displayEndTime)) {
            hide();
        }
    }

}
