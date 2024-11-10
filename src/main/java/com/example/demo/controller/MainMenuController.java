package com.example.demo.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class MainMenuController {

    private Controller mainController;

    @FXML
    private ImageView backgroundImageView;

    // Method to set mainController reference
    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void initialize() {
        updateBackgroundImage("com/example/demo/images/background1.jpg");
    }

    private void updateBackgroundImage(String imagePath) {
        try {
            Image image = new Image(getClass().getResourceAsStream("/" + imagePath));
            backgroundImageView.setImage(image); // Set the loaded image to the ImageView
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the error (e.g., show an alert or log it)
            mainController.showError("Failed to load background image: " + e.getMessage());
        }
    }

    @FXML
    private void onStartGame() {
        if (mainController != null) {
            try {
                mainController.goToLevel(Controller.LEVEL_ONE_CLASS_NAME);
            } catch (Exception e) {
                e.printStackTrace();
                mainController.showError("Failed to start game: " + e.getMessage());
            }
        }
    }

    @FXML
    private void onExit() {
        Stage stage = (Stage) mainController.getStage();
        stage.close();
    }
}