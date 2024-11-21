package com.example.demo.controller;

import com.example.demo.level.LevelSelector;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class GameOverController {
    public enum GameOverCase {
        WIN,
        LOSE
    }
    private String WIN_IMAGE = "com/example/demo/images/youwin.png";
    private String LOSE_IMAGE = "com/example/demo/images/gameover.png";
    private Controller mainController;
    @FXML
    private ImageView backgroundImageView;
    @FXML
    private ImageView gameOverImage;
    @FXML
    private Text gameOverText;
    @FXML
    private Button restartButton;
    @FXML
    private Button mainMenuButton;
    @FXML
    private Button exitButton;

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
    private void setImage(GameOverCase gameOverCase) {
        String imagePath = switch (gameOverCase) {
            case WIN -> WIN_IMAGE;
            case LOSE -> LOSE_IMAGE;
            default -> LOSE_IMAGE;
        };

        try {
            Image image = new Image(getClass().getResourceAsStream("/" + imagePath));
            gameOverImage.setImage(image);
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
    private void onMainMenuButtonClicked(){
        if (mainController != null) {
            try {
                mainController.showMainMenu();
            } catch (Exception e) {
                mainController.showError(e);
            }
        }
    }
    @FXML
    private void onExitButtonClicked() {
        if (mainController != null) {
            try {
                mainController.getStage().close();
            } catch (Exception e) {
                mainController.showError(e);
            }
        }
    }
    private void setText(String text){
        gameOverText.setText(text);
    }
    public void setMode(GameOverCase gameOverCase) {
        switch (gameOverCase) {
            case WIN:
                setText("Congratulations! You Win!");
                break;
            case LOSE:
                setText("Oops, Better luck next time.");
                break;

            default:
                throw new IllegalArgumentException("Unhandled GameOverCase: " + gameOverCase);
        }
        setImage(gameOverCase);
    }
}