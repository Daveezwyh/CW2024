package com.example.demo.misc;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Represents a "You Win" image displayed upon winning the game.
 */
public class WinImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";
	private static final int HEIGHT = 500;
	private static final int WIDTH = 600;

	/**
	 * Constructs a {@link WinImage} with the specified position.
	 *
	 * @param xPosition the X-coordinate position of the "You Win" image.
	 * @param yPosition the Y-coordinate position of the "You Win" image.
	 */
	public WinImage(double xPosition, double yPosition) {
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}

	/**
	 * Displays the "You Win" image in the scene.
	 */
	public void showWinImage() {
		this.setVisible(true);
	}
}