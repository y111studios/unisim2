package io.github.unisim.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import io.github.unisim.GameState;
import io.github.unisim.building.BuildingType;
import io.github.unisim.utils.ResizableComponents;
import io.github.unisim.world.World;

public class ManagementMenu {
    private Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    private ShapeActor background;
    private Table table;

    private World world;

    private static final float normalisedWidth = 0.35f;
    private static final float normalisedHeight = 0.85f;
    private static final float normalisedLeftPadding = (0.975f - normalisedWidth);
    private static final float normalisedTopPadding = (1 - normalisedHeight) / 2;
    private static final float normalisedHiddenPadding = 1;

    private Label studentEnrollmentLabel;
    private TextField studentEnrollmentField;
    private Table buildingCapacityTable;
    private Map<BuildingType, Label> buildingCapacityLabels;
    private Map<BuildingType, ProgressBar> buildingCapacityBars;

    private ResizableComponents resizableComponents;

    public ManagementMenu(Stage stage, World world) {
        this.world = world;

        background = new ShapeActor(GameState.UIPrimaryColour);
        table = new Table();

        buildingCapacityTable = new Table();
        Label capacityTitleLabel = new Label("Building Capacities", skin);
        Table leftColumnTable = new Table();
        Table rightColumnTable = new Table();
        buildingCapacityTable.add(capacityTitleLabel).colspan(2).center().padBottom(10).row();
        buildingCapacityTable.add(leftColumnTable).padRight(10);
        buildingCapacityTable.add(rightColumnTable).row();
        buildingCapacityLabels = new HashMap<>(BuildingType.values().length);
        buildingCapacityBars = new HashMap<>(BuildingType.values().length);
        for (BuildingType type : BuildingType.values()) {
            buildingCapacityLabels.put(type, new Label("0", skin));
            buildingCapacityBars.put(type, new ProgressBar(0, 1, 0.001f, false, skin));
        }
        // Initialise the left column
        leftColumnTable.add(new Label("Accomodation Capacity", skin)).left().colspan(2).center();
        leftColumnTable.row().padTop(10);
        leftColumnTable.add(buildingCapacityBars.get(BuildingType.SLEEPING));
        leftColumnTable.add(buildingCapacityLabels.get(BuildingType.SLEEPING));
        leftColumnTable.row().padTop(10);
        leftColumnTable.add(new Label("Catering Capacity", skin)).left().colspan(2).center();
        leftColumnTable.row().padTop(10);
        leftColumnTable.add(buildingCapacityBars.get(BuildingType.EATING));
        leftColumnTable.add(buildingCapacityLabels.get(BuildingType.EATING));
        // Initialise the right column
        rightColumnTable.add(new Label("Teaching Capacity", skin)).left().colspan(2).center();
        rightColumnTable.row().padTop(10);
        rightColumnTable.add(buildingCapacityBars.get(BuildingType.LEARNING));
        rightColumnTable.add(buildingCapacityLabels.get(BuildingType.LEARNING));
        rightColumnTable.row().padTop(10);
        rightColumnTable.add(new Label("Recreational Capacity", skin)).left().colspan(2).center();
        rightColumnTable.row().padTop(10);
        rightColumnTable.add(buildingCapacityBars.get(BuildingType.RECREATION));
        rightColumnTable.add(buildingCapacityLabels.get(BuildingType.RECREATION));

        table.add(buildingCapacityTable).colspan(2).expandX().row();
        table.row().padTop(10);

        studentEnrollmentLabel = new Label("Student Enrollment", skin);
        table.add(studentEnrollmentLabel);
        studentEnrollmentField = new TextField(Integer.toString(world.numberOfStudents), skin);
        studentEnrollmentField.setMessageText("Student Number");
        studentEnrollmentField.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
        studentEnrollmentField.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    submitToWorld();
                    stage.setKeyboardFocus(null);
                    return true;
                }
                if (keycode == Input.Keys.TAB) {
                    submitToWorld();
                    hide();
                    stage.setKeyboardFocus(null);
                    return true;
                }
                return false;
            }
        });
        table.add(studentEnrollmentField).padLeft(25);

        ResizableComponents.Builder resizeBuilder = new ResizableComponents.Builder(stage);
        resizableComponents = resizeBuilder
            .addActor((Actor) background)
            .addActor(table)
            .setNormalisedWidth(normalisedWidth)
            .setNormalisedHeight(normalisedHeight)
            .setNormalisedX(normalisedLeftPadding)
            .setNormalisedY(normalisedTopPadding)
            .build();

        resizableComponents.setNormalisedX(1);

        stage.addActor(background);
        stage.addActor(table);
    }

    void updateCapacityTable() {
        final Map<BuildingType, Integer> capacities = new HashMap<>(BuildingType.values().length);
        int max = 0;
        int min = 0;
        for (BuildingType type : BuildingType.values()) {
            Integer capacity = world.getBuildingCapacities(type);
            capacities.put(type, capacity);
            if (capacity > max) {
                max = capacity;
            } else if (capacity < min) {
                min = capacity;
            }
        }
        int range = max - min;
        for (BuildingType type : BuildingType.values()) {
            buildingCapacityLabels.get(type).setText(capacities.get(type).toString());
            ProgressBar bar = buildingCapacityBars.get(type);
            bar.setValue((float) (range - capacities.get(type)) / range);
        }
    }

    void submitToWorld() {
        try {
            world.setStudentEnrollment(Integer.parseInt(studentEnrollmentField.getText()));
        } catch (NumberFormatException e) {
            // If the input is not a number default back to the previous value
            studentEnrollmentField.setText(Integer.toString(world.numberOfStudents));
            return;
        }
    }

    public void resize(int width, int height) {
        resizableComponents.resize(width, height);
    }

    public void hide() {
        Function<Void, Action> actionGenerator = (Void) -> Actions.sequence(
            Actions.moveTo(resizableComponents.getStageWidth(), resizableComponents.getY(), 0.33f, Interpolation.slowFast),
            Actions.run(() -> {
                resizableComponents.setNormalisedX(normalisedHiddenPadding);
            })
        );
        resizableComponents.addAction(actionGenerator);
    }

    public void show() {
        updateCapacityTable();
        Function<Void, Action> actionGenerator = (Void) -> Actions.sequence(
            Actions.moveTo(normalisedLeftPadding * resizableComponents.getStageWidth(), resizableComponents.getY(), 0.33f, Interpolation.fastSlow),
            Actions.run(() -> {
                resizableComponents.setNormalisedX(normalisedLeftPadding);
            })
        );
        resizableComponents.addAction(actionGenerator);
    }

    public void toggleVisibility() {
        if (resizableComponents.onScreen()) {
            hide();
        } else {
            show();
        }
    }
}
