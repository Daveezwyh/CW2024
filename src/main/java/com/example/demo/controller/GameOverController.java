package com.example.demo.controller;

import com.example.demo.level.LevelSelector;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.util.Objects;

public class GameOverController {
    public enum GameOverCase {
        WIN,
        LOSE
    }
    private final String WIN_IMAGE = "com/example/demo/images/youwin.png";
    private final String LOSE_IMAGE = "com/example/demo/images/gameover.png";
    private Controller mainController;
    @FXML
    private ImageView backgroundImageView;
    @FXML
    private ImageView gameOverImage;
    @FXML
    private Text gameOverText;
    @FXML
    private Text gameScoreText;


    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }
    @FXML
    private void initialize() {
        updateBackgroundImage();
    }
    private void updateBackgroundImage() {
        try {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/demo/images/background1.jpg")));
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
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/" + imagePath)));
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
    public void setGameScore(int gameScore){
        gameScoreText.setText("Score: " + gameScore);
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