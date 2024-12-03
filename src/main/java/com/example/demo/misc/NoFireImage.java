package com.example.demo.misc;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Objects;

/**
 * Represents an image used to indicate that the boss's ability to fire projectiles is deactivated.
 * The image is positioned on the game screen but is initially hidden and can be toggled
 * to show or hide based on game events.
 */
public class NoFireImage extends ImageView {

    /**
     * The file path to the "No Fire" image asset.
     */
    private static final String IMAGE_NAME = "/com/example/demo/images/NoFireImage.png";

    /**
     * The size of the image (height in pixels).
     * The width is adjusted automatically to maintain the aspect ratio.
     */
    private static final int IMG_SIZE = 40;

    /**
     * Constructs a {@code NoFireImage} at the specified position on the screen.
     * The image is loaded, resized, and initially hidden.
     *
     * @param xPosition the X-coordinate for positioning the image.
     * @param yPosition the Y-coordinate for positioning the image.
     */
    public NoFireImage(double xPosition, double yPosition) {
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
        this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm(), true));
        this.setVisible(false); // Image is initially hidden
        this.setFitHeight(IMG_SIZE);
        this.setPreserveRatio(true); // Maintains aspect ratio
    }

    /**
     * Makes the "No Fire" image visible on the game screen.
     * This method is called when the boss's ability to fire projectiles is deactivated.
     */
    public void showNoFire() {
        this.setVisible(true);
    }

    /**
     * Hides the "No Fire" image from the game screen.
     * This method is called when the boss regains its ability to fire projectiles.
     */
    public void hideNoFire() {
        this.setVisible(false);
    }
}