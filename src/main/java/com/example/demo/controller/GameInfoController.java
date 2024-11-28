package com.example.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * The controller class for the game information view.
 * Responsible for managing the background image and navigation back to the main menu.
 */
public class GameInfoController {

    /**
     * Reference to the main application controller.
     */
    private Controller mainController;

    /**
     * The ImageView component for displaying the background image.
     */
    @FXML
    private ImageView backgroundImageView;

    /**
     * Sets the reference to the main application controller.
     *
     * @param mainController the main application controller.
     */
    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }

    /**
     * Initializes the game information view by updating the background image.
     * This method is automatically called after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
        updateBackgroundImage();
    }

    /**
     * Navigates back to the main menu.
     * Called when the user interacts with the corresponding UI element.
     */
    @FXML
    private void backMainMenu() {
        if (mainController != null) {
            mainController.showMainMenu();
        }
    }

    /**
     * Updates the background image of the view.
     * If an error occurs during image loading, it will be reported through the main controller.
     */
    private void updateBackgroundImage() {
        try {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/demo/images/background1.jpg")));
            backgroundImageView.setImage(image);
        } catch (Exception e) {
            mainController.showError(e);
        }
    }
}