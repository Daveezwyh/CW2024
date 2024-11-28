package com.example.demo.actor;

import com.example.demo.contract.Destructible;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents an abstract destructible actor in the game.
 * Extends {@link ActiveActor} and implements {@link Destructible}.
 * Provides functionality for handling destruction, removal, and bounding box updates.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	private boolean isDestroyed;
	private boolean shouldRemove;
	protected final Rectangle boundingBox;
	@SuppressWarnings("FieldCanBeLocal")
	private final boolean isBoundingBoxVisible = false;

	/**
	 * Constructs an {@code ActiveActorDestructible} with the specified parameters.
	 *
	 * @param imageName    the name of the image file associated with the actor
	 * @param imageHeight  the height of the image
	 * @param initialXPos  the initial X position of the actor
	 * @param initialYPos  the initial Y position of the actor
	 */
	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;

		boundingBox = new Rectangle(getFitWidth(), getFitHeight());
		boundingBox.setStroke(Color.RED);
		boundingBox.setFill(Color.TRANSPARENT);
		boundingBox.setX(initialXPos);
		boundingBox.setY(initialYPos);
	}

	/**
	 * Updates the position of the actor.
	 */
	@Override
	public abstract void updatePosition();

	/**
	 * Updates the state of the actor.
	 */
	public abstract void updateActor();

	/**
	 * Applies damage to the actor.
	 */
	public abstract void takeDamage();

	/**
	 * Repairs damage to the actor.
	 */
	public abstract void repairDamage();

	/**
	 * Marks the actor as destroyed.
	 */
	@Override
	public void destroy() {
		setDestroyed(true);
	}

	/**
	 * Checks if the actor is destroyed.
	 *
	 * @return {@code true} if the actor is destroyed, otherwise {@code false}.
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}

	/**
	 * Sets the destruction status of the actor.
	 *
	 * @param isDestroyed {@code true} to mark the actor as destroyed, otherwise {@code false}.
	 */
	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	/**
	 * Marks the actor for removal.
	 */
	@Override
	public void remove() {
		setShouldRemove(true);
	}

	/**
	 * Checks if the actor should be removed.
	 *
	 * @return {@code true} if the actor should be removed, otherwise {@code false}.
	 */
	public boolean getShouldRemove() {
		return shouldRemove;
	}

	/**
	 * Sets the removal status of the actor.
	 *
	 * @param shouldRemove {@code true} to mark the actor for removal, otherwise {@code false}.
	 */
	public void setShouldRemove(boolean shouldRemove) {
		this.shouldRemove = shouldRemove;
	}

	/**
	 * Retrieves the bounding box of the actor.
	 *
	 * @return the bounding box of the actor.
	 */
	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	/**
	 * Checks if the bounding box is visible.
	 *
	 * @return {@code true} if the bounding box is visible, otherwise {@code false}.
	 */
	public boolean isBoundingBoxVisible() {
		return this.isBoundingBoxVisible;
	}

	/**
	 * Retrieves the timestamp when the actor was created.
	 *
	 * @return the creation timestamp of the actor.
	 */
	public long getCreatedTimeStamp() {
		return 0;
	}

	/**
	 * Updates the bounding box to match the actor's current position and size.
	 */
	protected void updateBoundingBox() {
		boundingBox.setX(getLayoutX() + getTranslateX());
		boundingBox.setY(getLayoutY() + getTranslateY());
		boundingBox.setWidth(getFitWidth());
		boundingBox.setHeight(getFitHeight());
	}
}