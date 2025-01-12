package io.github.unisim.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.unisim.GameState;
import io.github.unisim.building.Building;
import io.github.unisim.utils.ResizableComponents;

public class BuildingPreviewMenu {

    private final Table previewTable;
    private final Image background;

    private final ResizableComponents resizableComponents;

    private static final float normalisedWidth = 0.3f;
    private static final float normalisedHeight = 0.25f;
    private static final float normalisedLeftPadding = 0.01f;
    private static final float normalisedTopPadding = 0.01f;

    private final Table rightColumn;
    private final Image previewImage;
    private final Cell<Image> previewCell;
    private final Label nameLabel;
    private final Label sizeLabel;
    private final Label capacityLabel;
    private final Label costLabel;
    private final Label qualityLabel;

    private Building preview;

    /**
     * Creates a new BuildingPreviewMenu and attaches it's components to the provided stage
     *
     * @param stage The stage to attach the components to
     */
    public BuildingPreviewMenu(Stage stage) {
        previewTable = new Table();

        // Create solid white background
        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        background = new Image(new Texture(pixmap));

        final float boxSize = Math.min(stage.getWidth(), stage.getHeight());

        nameLabel = new Label("", new Label.LabelStyle(GameState.iconTextFont, Color.BLACK));
        previewTable.add(nameLabel).padLeft(2.5f).colspan(2).center().row();

        previewImage = new Image();
        previewCell = previewTable.add(previewImage).left().padLeft(2.5f).width(boxSize * normalisedWidth / 2.1f).height(boxSize * normalisedHeight / 1.5f);

        rightColumn = new Table();
        previewTable.add(rightColumn).expand().fill().row();

        sizeLabel = new Label("", new Label.LabelStyle(GameState.iconTextFont, Color.BLACK));
        capacityLabel = new Label("", new Label.LabelStyle(GameState.iconTextFont, Color.BLACK));
        costLabel = new Label("", new Label.LabelStyle(GameState.iconTextFont, Color.BLACK));
        qualityLabel = new Label("", new Label.LabelStyle(GameState.iconTextFont, Color.BLACK));

        rightColumn.add(sizeLabel).left().padLeft(2.5f).row();
        rightColumn.add(capacityLabel).left().padLeft(2.5f).row();
        rightColumn.add(costLabel).left().padLeft(2.5f).row();
        rightColumn.add(qualityLabel).left().padLeft(2.5f).row();

        resizableComponents = new ResizableComponents.Builder(boxSize, boxSize)
                .addActor(previewTable)
                .addActor(background)
                .setNormalisedX(normalisedLeftPadding)
                .setNormalisedY(normalisedTopPadding)
                .setNormalisedWidth(normalisedWidth)
                .setNormalisedHeight(normalisedHeight)
                .build();

        stage.addActor(background);
        stage.addActor(previewTable);

        preview = null;
    }

    /**
     * Shows the information of the building in the preview menu
     *
     * <p>
     * If the building is null, the content of the preview menu is hidden.
     * </p>
     *
     * @param building The building to show the information of
     */
    public void showContent(Building building) {
        if (building == null) {
            hideContent();
            return;
        }
        if (building == preview) {
            return;
        }
        preview = building;
        previewImage.setDrawable(new TextureRegionDrawable(building.texture));
        nameLabel.setText(building.name);
        sizeLabel.setText("Size: " + building.size.x + "x" + building.size.y);
        capacityLabel.setText("Capacity: " + building.capacity);
        costLabel.setText("Cost: $" + building.cost);
        qualityLabel.setText("Quality: " + building.quality);
    }

    /**
     * Hides the content of the preview menu
     */
    public void hideContent() {
        if (preview == null) {
            return;
        }
        preview = null;
        previewImage.setDrawable(null);
        nameLabel.setText("");
        sizeLabel.setText("");
        capacityLabel.setText("");
        costLabel.setText("");
        qualityLabel.setText("");
    }

    public void resize(int width, int height) {
        int boxSize = Math.min(width, height);
        resizableComponents.resize(boxSize, boxSize);
        previewCell.width(boxSize * normalisedWidth / 2.1f).height(boxSize * normalisedHeight / 1.5f);
    }
}
