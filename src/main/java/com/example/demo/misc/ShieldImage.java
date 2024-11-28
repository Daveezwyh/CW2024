package com.example.demo.misc;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Objects;

/**
 * Represents a shield image in the game. The shield can be shown or hidden as needed.
 */
public class ShieldImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/shield.png";
	private static final int SHIELD_SIZE = 100;

	/**
	 * Constructs a {@link ShieldImage} with the specified position.
	 *
	 * @param xPosition the X-coordinate position of the shield.
	 * @param yPosition the Y-coordinate position of the shield.
	 */
	public ShieldImage(double xPosition, double yPosition) {
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm(), true));
		this.setVisible(false);
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	/**
	 * Makes the shield visible in the scene.
	 */
	public void showShield() {
		this.setVisible(true);
	}

	/**
	 * Hides the shield from the scene.
	 */
	public void hideShield() {
		this.setVisible(false);
	}
}