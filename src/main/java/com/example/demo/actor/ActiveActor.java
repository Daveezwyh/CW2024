package com.example.demo.actor;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * An abstract base class representing an active actor in the game.
 * This class extends {@link ImageView} and provides functionality to
 * manage the actor's image, position, and movement.
 */
public abstract class ActiveActor extends ImageView {

	/**
	 * The base location for all actor images.
	 */
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	/**
	 * Constructs an ActiveActor with the specified image, size, and initial position.
	 *
	 * @param imageName    the name of the image file for the actor
	 * @param imageHeight  the height of the actor image
	 * @param initialXPos  the initial X position of the actor
	 * @param initialYPos  the initial Y position of the actor
	 */
	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		Image image = new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm());

		this.setImage(image);
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);

		double aspectRatio = image.getWidth() / image.getHeight();
		this.setFitWidth(aspectRatio * imageHeight);
	}

	/**
	 * Updates the position of the actor.
	 * This method should be implemented by subclasses to define specific behavior.
	 */
	public abstract void updatePosition();

	/**
	 * Moves the actor horizontally by the specified amount.
	 *
	 * @param horizontalMove the amount to move the actor horizontally
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves the actor vertically by the specified amount.
	 *
	 * @param verticalMove the amount to move the actor vertically
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}
}