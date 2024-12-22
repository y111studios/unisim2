package io.github.unisim.ui;

import java.time.Duration;
import java.time.Instant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.unisim.events.EventCard;
import io.github.unisim.finance.MoneyTracker;
import io.github.unisim.scoring.SatisfactionTracker;
import io.github.unisim.utils.ResizableComponents;
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
    final static float normalisedLeftPadding = (1 - normalisedWidth) / 2;
    final static float normalisedTopPadding = 0.5f;
    static final float normalisedHiddenPadding = 1;

    private ResizableComponents resizableComponents;
    private ResizableComponents timerComponent;

    public EventDisplay(Stage stage, MoneyTracker moneyTracker, SatisfactionTracker satisfactionTracker) {
        this.moneyTracker = moneyTracker;
        this.satisfactionTracker = satisfactionTracker;

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

        resizableComponents = new ResizableComponents.Builder(stage)
            .addActor((Actor) dialog)
            .addActor(table)
            .setNormalisedWidth(normalisedWidth)
            .setNormalisedHeight(normalisedHeight)
            .setNormalisedX(normalisedHiddenPadding)
            .setNormalisedY(normalisedTopPadding)
            .build();

        timerComponent = new ResizableComponents.Builder(stage)
            .addActor((Actor) timerBar)
            .setNormalisedWidth(normalisedWidth)
            .setNormalisedHeight(0.015f)
            .setNormalisedX(normalisedHiddenPadding)
            .setNormalisedY(normalisedTopPadding + normalisedHeight)
            .build();

        stage.addActor(dialog);
        stage.addActor(timerBar);
        stage.addActor(table);
    }

    private void hide() {
        displayEndTime = null;
        resizableComponents.setNormalisedX(normalisedHiddenPadding);
        timerComponent.setNormalisedX(normalisedHiddenPadding);
        timerComponent.setNormalisedWidth(0);
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
        resizableComponents.setNormalisedX(normalisedLeftPadding);
        timerComponent.setNormalisedX(normalisedLeftPadding);
        timerComponent.setNormalisedWidth(normalisedWidth);
    }

    public void update() {
        if (displayEndTime == null) {
            return;
        }
        if (Instant.now().isAfter(displayEndTime)) {
            hide();
        } else {
            adjustTimerWidth();
        }
    }

    public void resize(int width, int height) {
        resizableComponents.resize(width, height);
        timerComponent.resize(width, height);
        adjustTimerWidth();
    }

    private void adjustTimerWidth() {
        if (displayEndTime == null) {
            timerComponent.setNormalisedWidth(0);
            return;
        }
        long remainingTime = Duration.between(Instant.now(), displayEndTime).toMillis();
        timerComponent.setNormalisedWidth((remainingTime / (float) DISPLAY_TIME.toMillis()) * normalisedWidth);
    }
}
