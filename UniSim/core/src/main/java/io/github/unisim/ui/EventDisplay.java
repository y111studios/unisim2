package io.github.unisim.ui;

import java.time.Duration;
import java.time.Instant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.unisim.world.World;
import io.github.unisim.events.EventCard;
import io.github.unisim.GameState;
import io.github.unisim.events.EventBucket;

public class EventDisplay {

    private Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    private ShapeActor dialog;
    private Table table;

    private EventBucket eventBucket = new EventBucket();
    private EventCard eventCard;
    private Label eventTitleLabel;
    private Label eventDescriptionLabel;
    private TextButton choice1;
    private TextButton choice2;
    private TextButton choice3;

    private Instant displayEndTime;
    private static final Duration DISPLAY_TIME = Duration.ofSeconds(10);

    final static float normalisedWidth = 0.4f;
    final static float normalisedHeight = 0.4f;

    public EventDisplay(Stage stage) {

        float stageWidth = stage.getWidth();
        float stageHeight = stage.getHeight();
        
        this.dialog = new ShapeActor(GameState.UISecondaryColour);
        this.dialog.setPosition(stageWidth / 2, stageHeight / 2);
        this.dialog.setSize(normalisedWidth * stageWidth, normalisedHeight * stageHeight);

        this.table = new Table();
        this.table.setPosition(stageWidth / 2, stageHeight / 2);
        this.table.setSize(normalisedWidth * stageWidth, normalisedHeight * stageHeight);

        eventTitleLabel = new Label("",skin);
        eventDescriptionLabel = new Label("",skin);
        choice1 = new TextButton("", skin);
        choice2 = new TextButton("", skin);        
        choice3 = new TextButton("", skin);

        table.add(eventTitleLabel).padBottom(10);
        table.row();
        table.add(eventDescriptionLabel).padBottom(15);
        table.row();
        table.add(choice1).padBottom(5);
        table.row();
        table.add(choice2).padBottom(5);
        table.row();
        table.add(choice3).padBottom(5);

        stage.addActor(dialog);
        stage.addActor(table);

        hide();
    }

    private void hide() {
        displayEndTime = null;
        this.dialog.setVisible(false);
        this.table.setVisible(false);
    }

    public void setEvent() {
        eventCard = eventBucket.getRandomEvent();

        eventTitleLabel.setText(eventCard.getTitle());
        eventDescriptionLabel.setText(eventCard.getDescription());

        choice1.setText(eventCard.getChoices().get(0).getTitle() + " --> " + eventCard.getChoices().get(0).getDescription());
        choice1.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                eventCard.getChoices().get(0).applyEffects(World.moneyTracker, World.satisfactionTracker);
                hide();
            }
        });

        choice2.setText(eventCard.getChoices().get(1).getTitle() + " --> " + eventCard.getChoices().get(1).getDescription());
        choice2.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                eventCard.getChoices().get(1).applyEffects(World.moneyTracker, World.satisfactionTracker);
                hide();
            }
        });

        choice3.setText(eventCard.getChoices().get(2).getTitle() + " --> " + eventCard.getChoices().get(2).getDescription());
        choice3.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                eventCard.getChoices().get(2).applyEffects(World.moneyTracker, World.satisfactionTracker);
                hide();
            }
        });
    }

    public void show() {
        setEvent();
        this.dialog.setVisible(true);
        this.table.setVisible(true);
        displayEndTime = Instant.now().plus(DISPLAY_TIME);
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
