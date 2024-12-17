package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

    private Label studentEnrollmentLabel;
    private TextField studentEnrollmentField;

    public ManagementMenu(Stage stage, World world) {
        this.world = world;

        background = new ShapeActor(GameState.UIPrimaryColour);
        background.setSize(normalisedWidth * stage.getWidth(), normalisedHeight * stage.getHeight());
        background.setPosition(normalisedLeftPadding * stage.getWidth(), normalisedTopPadding * stage.getHeight());
        table = new Table();
        table.setSize(normalisedWidth * stage.getWidth(), normalisedHeight * stage.getHeight());
        table.setPosition(normalisedLeftPadding * stage.getWidth(), normalisedTopPadding * stage.getHeight());

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
        background.setVisible(false);
        table.setVisible(false);
    }

    public void show() {
        background.setVisible(true);
        table.setVisible(true);
    }

    public void toggleVisibility() {
        if (background.isVisible()) {
            hide();
        } else {
            show();
        }
    }
}
