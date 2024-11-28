package com.example.demo.misc;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Represents a pause overlay displayed during the paused state of the game.
 * The overlay consists of a semi-transparent background and a "PAUSED" message.
 */
public class PauseOverlay {

    private final Rectangle overlay;
    private final Text pauseText;
    private final Group container;

    /**
     * Constructs a {@link PauseOverlay} with the specified {@link Group} container.
     *
     * @param container the {@link Group} container where the overlay will be displayed.
     */
    public PauseOverlay(Group container) {
        this.container = container;

        overlay = new Rectangle();
        overlay.setFill(Color.rgb(0, 0, 0, 0.5));
        overlay.setVisible(false);

        pauseText = new Text("PAUSED");
        pauseText.setFont(new Font("Arial", 50));
        pauseText.setFill(Color.WHITE);
        pauseText.setTextAlignment(TextAlignment.CENTER);
        pauseText.setVisible(false);
    }

    /**
     * Displays the pause overlay, covering the entire scene.
     * The overlay consists of a semi-transparent background and a "PAUSED" message.
     */
    public void show() {
        double width = container.getScene().getWidth();
        double height = container.getScene().getHeight();

        overlay.setWidth(width);
        overlay.setHeight(height);

        pauseText.setX(width / 2 - pauseText.getLayoutBounds().getWidth() / 2);
        pauseText.setY(height / 2);

        container.getChildren().removeAll(overlay, pauseText);
        container.getChildren().addAll(overlay, pauseText);

        overlay.setVisible(true);
        pauseText.setVisible(true);
    }

    /**
     * Hides the pause overlay and removes it from the container.
     */
    public void hide() {
        overlay.setVisible(false);
        pauseText.setVisible(false);

        container.getChildren().removeAll(overlay, pauseText);
    }
}