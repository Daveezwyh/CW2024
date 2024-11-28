package com.example.demo.controller;

import com.example.demo.level.LevelSelector;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Controller for the Main Menu screen.
 * Handles user interactions to start the game, view game information, or exit the application.
 */
public class MainMenuController {

    private Controller mainController;

    @FXML
    private ImageView backgroundImageView;

    @FXML
    private Button gameInfoButton;

    /**
     * Sets the main controller for the application.
     *
     * @param mainController the main controller instance.
     */
    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }

    /**
     * Initializes the Main Menu screen by updating the background image.
     * Automatically called after the FXML file is loaded.
     */
    @FXML
    private void initialize() {
        updateBackgroundImage();
    }

    /**
     * Updates the background image of the Main Menu screen.
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

    /**
     * Starts the game by navigating to the first level.
     * Called when the "Start Game" button is clicked.
     */
    @FXML
    private void onStartGame() {
        if (mainController != null) {
            try {
                mainController.goToLevel(LevelSelector.getFirstLevel());
            } catch (Exception e) {
                mainController.showError(e);
            }
        }
    }

    /**
     * Displays the Game Info screen.
     * Called when the "Game Info" button is clicked.
     */
    @FXML
    private void onGameInfo() {
        if (mainController != null) {
            try {
                mainController.showGameInfo();
            } catch (Exception e) {
                mainController.showError(e);
            }
        }
    }

    /**
     * Exits the game application.
     * Called when the "Exit" button is clicked.
     */
    @FXML
    private void onExit() {
        if (mainController != null) {
            mainController.getStage().close();
        }
    }
}