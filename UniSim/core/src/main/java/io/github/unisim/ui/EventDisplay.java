package io.github.unisim.ui;

import java.time.Duration;
import java.time.Instant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.unisim.events.EventCard;
import io.github.unisim.finance.MoneyTracker;
import io.github.unisim.scoring.SatisfactionTracker;
import io.github.unisim.GameState;
import io.github.unisim.events.EventBucket;

public class EventDisplay {

    private Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    private ShapeActor dialog;
    private ShapeActor timerBar;
    private Table table;

    private MoneyTracker moneyTracker;
    private SatisfactionTracker satisfactionTracker;

    private EventBucket eventBucket = new EventBucket();
    private EventCard eventCard;
    private Label eventTitleLabel;
    private Label eventDescriptionLabel;
    private TextButton choice1;
    private TextButton choice2;
    private TextButton choice3;

    private Instant displayEndTime;
    private static final Duration DISPLAY_TIME = Duration.ofSeconds(8);

    final static float normalisedWidth = 0.4f;
    final static float normalisedHeight = 0.4f;

    private float stageWidth;
    private float stageHeight;

    public EventDisplay(Stage stage, MoneyTracker moneyTracker, SatisfactionTracker satisfactionTracker) {

        this.moneyTracker = moneyTracker;
        this.satisfactionTracker = satisfactionTracker;

        this.stageWidth = stage.getWidth();
        this.stageHeight = stage.getHeight();

        this.dialog = new ShapeActor(GameState.UISecondaryColour);

        this.table = new Table();

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

        timerBar = new ShapeActor(Color.GREEN);


        stage.addActor(dialog);
        stage.addActor(timerBar);
        stage.addActor(table);

        hide();
    }

    private void hide() {
        displayEndTime = null;
        positionElements();
    }

    private void setEvent() {
        eventCard = eventBucket.getRandomEvent();

        eventTitleLabel.setText(eventCard.getTitle());
        eventDescriptionLabel.setText(eventCard.getDescription());

        choice1.setText(eventCard.getChoices().get(0).getTitle() + " --> " + eventCard.getChoices().get(0).getDescription());
        choice1.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                eventCard.getChoices().get(0).applyEffects(moneyTracker, satisfactionTracker);
                hide();
            }
        });

        choice2.setText(eventCard.getChoices().get(1).getTitle() + " --> " + eventCard.getChoices().get(1).getDescription());
        choice2.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                eventCard.getChoices().get(1).applyEffects(moneyTracker, satisfactionTracker);
                hide();
            }
        });

        choice3.setText(eventCard.getChoices().get(2).getTitle() + " --> " + eventCard.getChoices().get(2).getDescription());
        choice3.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                eventCard.getChoices().get(2).applyEffects(moneyTracker, satisfactionTracker);
                hide();
            }
        });
    }

    public void show() {
        setEvent();
        displayEndTime = Instant.now().plus(DISPLAY_TIME);
        positionElements();
    }

    public void update() {
        if (displayEndTime == null) {
            return;
        }
        if (Instant.now().isAfter(displayEndTime)) {
            hide();
        } else {
            long remainingTime = Duration.between(Instant.now(), displayEndTime).toMillis();
            float width = (remainingTime / (float) DISPLAY_TIME.toMillis()) * dialog.getWidth();
            timerBar.setSize(width, 8f);
        }
    }

    void positionElements() {
        final float xPos = isHidden() ? getHiddenX() : getShownX();
        final float yPos = getY();
        dialog.setPosition(xPos, yPos);
        dialog.setSize(normalisedWidth * stageWidth, normalisedHeight * stageHeight);
        table.setPosition(xPos, yPos);
        table.setSize(normalisedWidth * stageWidth, normalisedHeight * stageHeight);
        timerBar.setPosition(xPos, yPos + dialog.getHeight());
        timerBar.setSize(dialog.getWidth(), 8f);
    }

    public void resize(int width, int height) {
        this.stageWidth = width;
        this.stageHeight = height;
        positionElements();
    }

    private float getY() {
        return stageHeight / 2;
    }

    private float getShownX() {
        return (stageWidth * (1 - normalisedWidth)) / 2;
    }

    private float getHiddenX() {
        return (1 + normalisedWidth) * stageWidth;
    }

    private boolean isHidden() {
        return displayEndTime == null;
    }

}
