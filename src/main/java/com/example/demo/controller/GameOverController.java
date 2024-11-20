package com.example.demo.controller;

import com.example.demo.level.LevelSelector;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class GameOverController {
    private Controller mainController;

    @FXML
    private ImageView backgroundImageView;

    @FXML
    private Text gameOverText;

    @FXML
    private Button restartButton;

    @FXML
    private Button exitButton;

    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void initialize() {
        gameOverText.setText("Game Over");
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
    private void onRestartButtonClicked() {
        if (mainController != null) {
            try {
                mainController.goToLevel(LevelSelector.getFirstLevel());
            } catch (Exception e) {
                mainController.showError(e);
            }
        }
    }

    @FXML
    private void onExitButtonClicked() {
        if (mainController != null) {
            mainController.getStage().close(); // Close the application
        }
    }
}