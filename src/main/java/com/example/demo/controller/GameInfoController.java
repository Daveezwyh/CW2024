package com.example.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class GameInfoController {

    private Controller mainController;
    @FXML
    private ImageView backgroundImageView;

    @FXML
    private void initialize() {
        updateBackgroundImage();
    }

    @FXML
    private void backMainMenu() {
        if (mainController != null) {
            mainController.showMainMenu();
        }
    }

    private void updateBackgroundImage() {
        try {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/demo/images/background1.jpg")));
            backgroundImageView.setImage(image);
        } catch (Exception e) {
            mainController.showError(e);
        }
    }

    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }
}