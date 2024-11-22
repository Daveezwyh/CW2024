package com.example.demo.controller;

import com.example.demo.level.LevelSelector;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class MainMenuController {

    private Controller mainController;

    @FXML
    private ImageView backgroundImageView;

    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void initialize() {
        updateBackgroundImage();
    }

    private void updateBackgroundImage() {
        try {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/" + "com/example/demo/images/background1.jpg")));
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
        if (mainController != null) {
            mainController.getStage().close();
        }
    }
}