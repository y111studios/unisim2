package io.github.unisim.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * A class to manage the resizing of components in a stage.
 */
public class ResizableComponents {

    float stageWidth;
    float stageHeight;

    List<Actor> actors;

    float normalisedX;
    float normalisedY;
    float normalisedWidth;
    float normalisedHeight;

    /**
     * Initialises the ResizableComponents with the width and height of the stage with
     * no actors.
     *
     * @param width The width of the stage
     * @param height The height of the stage
     */
    ResizableComponents(float width, float height) {
        stageWidth = width;
        stageHeight = height;
        actors = new ArrayList<>();
    }

    /**
     * Adds an actor to the list of actors to be resized.
     *
     * @param actor The actor to be added
     */
    public void addActor(Actor actor) {
        actors.add(actor);
        positionComponent(actor);
    }

    /**
     * Resizes the components to the new width and height of the stage.
     *
     * @param width
     * @param height
     */
    public void resize(int width, int height) {
        stageWidth = width;
        stageHeight = height;
        positionComponents();
    }

    /**
     * Positions the components based on the normalised values of the components.
     */
    void positionComponents() {
        actors.forEach(this::positionComponent);
    }

    /**
     * Positions the actor based on the normalised values of the component.
     *
     * @param actor
     */
    void positionComponent(Actor actor) {
        actor.setPosition(getX(), getY());
        actor.setSize(getWidth(), getHeight());
    }

    /**
     * Checks if the component is on the screen.
     *
     * @return true if the component is on the screen, false otherwise
     */
    public boolean onScreen() {
        return normalisedX > 0
            && normalisedX + normalisedWidth <= 1
            && normalisedY > 0
            && normalisedY + normalisedHeight <= 1;
    }

    /**
     * Adds an action to all the actors.
     *
     * @param actionGenerator A function that generates an action
     */
    public void addAction(Function<Void, Action> actionGenerator) {
        actors.forEach(actor -> actor.addAction(actionGenerator.apply(null)));
    }

    /**
     * Updates the normalised x position of the component.
     * This results in an update to the position of all components.
     * @param x
     */
    public void setNormalisedX(float x) {
        normalisedX = x;
        positionComponents();
    }

    /**
     * Updates the normalised y position of the component.
     * This results in an update to the position of all components.
     * @param y
     */
    public void setNormalisedY(float y) {
        normalisedY = y;
        positionComponents();
    }

    /**
     * Updates the normalised width of the component.
     * This results in an update to the width of all components.
     * @param width
     */
    public void setNormalisedWidth(float width) {
        normalisedWidth = width;
        positionComponents();
    }

    /**
     * Updates the normalised height of the component.
     * This results in an update to the height of all components.
     * @param height
     */
    public void setNormalisedHeight(float height) {
        normalisedHeight = height;
        positionComponents();
    }

    public float getX() {
        return normalisedX * stageWidth;
    }

    public float getY() {
        return normalisedY * stageHeight;
    }

    public float getWidth() {
        return normalisedWidth * stageWidth;
    }

    public float getHeight() {
        return normalisedHeight * stageHeight;
    }

    public float getNormalisedX() {
        return normalisedX;
    }

    public float getNormalisedY() {
        return normalisedY;
    }

    public float getNormalisedWidth() {
        return normalisedWidth;
    }

    public float getNormalisedHeight() {
        return normalisedHeight;
    }

    public float getStageWidth() {
        return stageWidth;
    }

    public float getStageHeight() {
        return stageHeight;
    }

    public static class Builder {
        ResizableComponents internal;

        public Builder(Stage stage) {
            internal = new ResizableComponents(stage.getWidth(), stage.getHeight());
        }

        public ResizableComponents build() {
            return internal;
        }

        public Builder addActor(Actor actor) {
            internal.addActor(actor);
            return this;
        }

        public Builder setNormalisedX(float x) {
            if (x < 0 || x > 1) {
                throw new IllegalArgumentException("Normalised X must be between 0 and 1");
            }
            internal.normalisedX = x;
            return this;
        }

        public Builder setNormalisedY(float y) {
            if (y < 0 || y > 1) {
                throw new IllegalArgumentException("Normalised Y must be between 0 and 1");
            }
            internal.normalisedY = y;
            return this;
        }

        public Builder setNormalisedWidth(float width) {
            if (width < 0 || width > 1) {
                throw new IllegalArgumentException("Normalised width must be between 0 and 1");
            }
            internal.normalisedWidth = width;
            return this;
        }

        public Builder setNormalisedHeight(float height) {
            if (height < 0 || height > 1) {
                throw new IllegalArgumentException("Normalised height must be between 0 and 1");
            }
            internal.normalisedHeight = height;
            return this;
        }
    }
}
