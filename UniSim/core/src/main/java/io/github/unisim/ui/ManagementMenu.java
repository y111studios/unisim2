package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import io.github.unisim.GameState;
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

    private float stageWidth;
    private float stageHeight;

    private Label studentEnrollmentLabel;
    private TextField studentEnrollmentField;

    public ManagementMenu(Stage stage, World world) {
        this.world = world;

        stageWidth = stage.getWidth();
        stageHeight = stage.getHeight();

        background = new ShapeActor(GameState.UIPrimaryColour);
        background.setSize(normalisedWidth * stage.getWidth(), normalisedHeight * stage.getHeight());
        background.setPosition(stageWidth, normalisedTopPadding * stageHeight);
        table = new Table();
        table.setSize(normalisedWidth * stage.getWidth(), normalisedHeight * stage.getHeight());
        table.setPosition(stageWidth, normalisedTopPadding * stageHeight);

        studentEnrollmentLabel = new Label("Student Enrollment", skin);
        table.add(studentEnrollmentLabel).left();
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

        stage.addActor(background);
        stage.addActor(table);

        hide();
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

    public void hide() {
        background.addAction(Actions.moveTo(stageWidth, normalisedTopPadding * stageHeight, 0.33f, Interpolation.slowFast));
        table.addAction(Actions.moveTo(stageWidth, normalisedTopPadding * stageHeight, 0.33f, Interpolation.slowFast));
    }

    public void show() {
        background.addAction(Actions.moveTo(normalisedLeftPadding * stageWidth, normalisedTopPadding * stageHeight, 0.33f, Interpolation.fastSlow));
        table.addAction(Actions.moveTo(normalisedLeftPadding * stageWidth, normalisedTopPadding * stageHeight, 0.33f, Interpolation.fastSlow));
    }

    public void toggleVisibility() {
        if (background.getX() == stageWidth) {
            show();
        } else {
            hide();
        }
    }
}
