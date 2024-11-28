package com.example.demo.misc;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Objects;

/**
 * Represents a heart display used to visually indicate the player's remaining health in the game.
 * The display consists of a series of heart icons managed within an {@link HBox}.
 */
public class HeartDisplay {

	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
	private static final int HEART_HEIGHT = 30;
	private static final int INDEX_OF_FIRST_ITEM = 0;

	private HBox container;
	private final double containerXPosition;
	private final double containerYPosition;
	private final int numberOfHeartsToDisplay;

	/**
	 * Constructs a {@link HeartDisplay} with the specified position and number of hearts to display.
	 *
	 * @param xPosition        the X-coordinate of the heart display container.
	 * @param yPosition        the Y-coordinate of the heart display container.
	 * @param heartsToDisplay  the initial number of hearts to display.
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}

	/**
	 * Initializes the container (an {@link HBox}) for the heart display.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	/**
	 * Populates the container with the specified number of heart icons.
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = createHeartImage();
			container.getChildren().add(heart);
		}
	}

	/**
	 * Removes one heart from the display if there are any hearts remaining.
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty()) {
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);
		}
	}

	/**
	 * Adds a heart to the display.
	 */
	public void addHeart() {
		ImageView heart = createHeartImage();
		container.getChildren().add(heart);
	}

	/**
	 * Gets the {@link HBox} container for the heart display.
	 *
	 * @return the heart display container.
	 */
	public HBox getContainer() {
		return container;
	}

	/**
	 * Creates a new heart {@link ImageView} with predefined height and ratio settings.
	 *
	 * @return a configured {@link ImageView} for the heart icon.
	 */
	private ImageView createHeartImage() {
		ImageView heart = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(HEART_IMAGE_NAME)).toExternalForm()));
		heart.setFitHeight(HEART_HEIGHT);
		heart.setPreserveRatio(true);
		return heart;
	}
}