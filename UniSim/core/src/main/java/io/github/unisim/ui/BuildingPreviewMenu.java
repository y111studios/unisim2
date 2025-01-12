package io.github.unisim.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
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
        pixmap.setColor(184 /255.0f,165/255.0f, 243/255.0f, 1.0f);
        pixmap.fill();
        Pixmap borderPixmap = new Pixmap(110, 110, Pixmap.Format.RGBA8888);
        borderPixmap.setColor(Color.BLACK); // Set the border color
        borderPixmap.fillRectangle(0, 0, 110, 110); // Fill the entire box
        borderPixmap.setColor(184 / 255.0f, 165 / 255.0f, 243 / 255.0f, 1.0f); // Set inner background color
        borderPixmap.fillRectangle(5, 5, 100, 100); // Fill the inner area to simulate a border
        Drawable borderDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(borderPixmap)));
        borderPixmap.dispose(); // Dispose after use to prevent memory leaks
        previewTable.setBackground(borderDrawable);

        background = new Image(new Texture(pixmap));

        final float boxSize = Math.min(stage.getWidth(), stage.getHeight());

        nameLabel = new Label("", new Label.LabelStyle(GameState.iconTextFont, Color.BLACK));
        previewTable.add(nameLabel).padLeft(2.5f).padTop(8f).colspan(2).center().row();

        previewImage = new Image();
        previewCell = previewTable.add(previewImage).left().padLeft(2.5f).width(boxSize * normalisedWidth / 2.1f).height(boxSize * normalisedHeight / 1.5f);

        rightColumn = new Table();
        previewTable.add(rightColumn).expand().fill().row();

        sizeLabel = new Label("", new Label.LabelStyle(GameState.iconTextFont, Color.BLACK));
        capacityLabel = new Label("", new Label.LabelStyle(GameState.iconTextFont, Color.BLACK));
        costLabel = new Label("", new Label.LabelStyle(GameState.iconTextFont, Color.BLACK));

        rightColumn.add(sizeLabel).left().padLeft(2.3f).row();
        rightColumn.add(capacityLabel).left().padLeft(2.3f).row();
        rightColumn.add(costLabel).left().padLeft(2.3f).row();

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
        BitmapFont font = new BitmapFont();
        font.getData().setScale(0.8f);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);
        nameLabel.setStyle(labelStyle);
        sizeLabel.setStyle(labelStyle);
        capacityLabel.setStyle(labelStyle);
        costLabel.setStyle(labelStyle);
        previewImage.setDrawable(new TextureRegionDrawable(building.texture));
        nameLabel.setText(building.name);
        sizeLabel.setText("Size: " + building.size.x + "x" + building.size.y);
        capacityLabel.setText("Capacity: " + building.capacity);
        costLabel.setText("Cost: $" + building.cost);
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
    }

    public void resize(int width, int height) {
        int boxSize = Math.min(width, height);
        resizableComponents.resize(boxSize, boxSize);
        previewCell.width(boxSize * normalisedWidth / 2.1f).height(boxSize * normalisedHeight / 1.5f);
    }
}
