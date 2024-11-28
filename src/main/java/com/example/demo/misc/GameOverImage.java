package com.example.demo.misc;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Represents an image displayed on the screen when the game is over.
 */
public class GameOverImage extends ImageView {

	// Path to the "Game Over" image resource
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";

	/**
	 * Constructs a {@link GameOverImage} with specified position on the screen.
	 *
	 * @param xPosition the X-coordinate of the image's position.
	 * @param yPosition the Y-coordinate of the image's position.
	 */
	public GameOverImage(double xPosition, double yPosition) {
		setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		setFitHeight(400);
		setLayoutX(xPosition);
		setLayoutY(yPosition);
		setPreserveRatio(true);
	}
}