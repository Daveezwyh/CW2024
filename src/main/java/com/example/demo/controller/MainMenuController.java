package com.example.demo.controller;

import com.example.demo.level.LevelSelector;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class MainMenuController {

    private Controller mainController;

    @FXML
    private ImageView backgroundImageView;

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
            backgroundImageView.setImage(image);
        } catch (Exception e) {
            mainController.showError(e);
        }
    }

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

    @FXML
    private void onExit() {
        Stage stage = mainController.getStage();
        stage.close();
    }
}