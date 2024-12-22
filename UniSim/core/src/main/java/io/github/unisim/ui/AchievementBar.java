package io.github.unisim.ui;

import java.time.Duration;
import java.util.function.Function;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.github.unisim.GameState;
import io.github.unisim.achievements.Achievement;
import io.github.unisim.utils.ResizableComponents;

public class AchievementBar {
    private Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    private ShapeActor bar;
    private Table table;
    private Image iconImage;
    private Label titleLabel;
    private Label descriptionLabel;
    private Cell<Image> iconCell;

    private static final Duration DISPLAY_TIME = Duration.ofSeconds(8);

    final static float normalisedWidth = 0.4f;
    final static float normalisedHeight = 0.1f;
    final static float normalisedLeftPadding = (1 - normalisedWidth) / 2;
    final static float normalisedTopPadding = 1 - 0.1f - normalisedHeight;

    private ResizableComponents resizableComponents;

    public AchievementBar(Stage stage) {
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

        ResizableComponents.Builder resizeBuilder = new ResizableComponents.Builder(stage);
        resizableComponents = resizeBuilder
            .addActor((Actor) bar)
            .addActor(table)
            .setNormalisedWidth(normalisedWidth)
            .setNormalisedHeight(normalisedHeight)
            .setNormalisedX(normalisedLeftPadding)
            .setNormalisedY(1)
            .build();

        stage.addActor(bar);
        stage.addActor(table);
    }

    public void resize(int width, int height) {
        resizableComponents.resize(width, height);
        iconCell.size(0.95f * resizableComponents.getHeight()).padLeft(0.025f * resizableComponents.getHeight());
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

    private void showFor(Duration duration) {
        Function<Void, Action> actionGenerator = (Void) -> Actions.sequence(
            Actions.moveTo(resizableComponents.getX(), normalisedTopPadding * resizableComponents.getStageHeight(), 0.25f, Interpolation.fastSlow),
            Actions.run(() -> {
                resizableComponents.setNormalisedY(normalisedTopPadding);
            }),
            Actions.delay(duration.toMillis() / 1000f),
            Actions.moveTo(resizableComponents.getX(), resizableComponents.getStageHeight(), 0.25f, Interpolation.fastSlow),
            Actions.run(() -> {
                resizableComponents.setNormalisedY(1);
            })
        );
        resizableComponents.addAction(actionGenerator);
    }

}
