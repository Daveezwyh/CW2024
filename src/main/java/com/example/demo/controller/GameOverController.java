package com.example.demo.controller;

import com.example.demo.level.LevelSelector;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.util.Objects;

/**
 * The controller class for the Game Over screen.
 * Handles displaying the game over message, score, and navigation options.
 */
public class GameOverController {

    /**
     * Enum representing the possible Game Over cases: WIN or LOSE.
     */
    public enum GameOverCase {
        WIN,
        LOSE
    }

    private static final String WIN_IMAGE = "com/example/demo/images/youwin.png";
    private static final String LOSE_IMAGE = "com/example/demo/images/gameover.png";

    private Controller mainController;

    @FXML
    private ImageView backgroundImageView;

    @FXML
    private ImageView gameOverImage;

    @FXML
    private Text gameOverText;

    @FXML
    private Text gameScoreText;

    /**
     * Sets the main controller for the application.
     *
     * @param mainController the main controller instance.
     */
    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }

    /**
     * Sets the game score to be displayed on the Game Over screen.
     *
     * @param gameScore the score to display.
     */
    public void setGameScore(int gameScore) {
        gameScoreText.setText("Score: " + gameScore);
    }

    /**
     * Configures the Game Over screen based on the game outcome.
     *
     * @param gameOverCase the outcome of the game (WIN or LOSE).
     */
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

    /**
     * Initializes the Game Over screen by updating the background image.
     * Automatically called after the FXML file is loaded.
     */
    @FXML
    private void initialize() {
        updateBackgroundImage();
    }

    /**
     * Restarts the game by navigating to the first level.
     * Called when the Restart button is clicked.
     */
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

    /**
     * Navigates back to the main menu.
     * Called when the Main Menu button is clicked.
     */
    @FXML
    private void onMainMenuButtonClicked() {
        if (mainController != null) {
            try {
                mainController.showMainMenu();
            } catch (Exception e) {
                mainController.showError(e);
            }
        }
    }

    /**
     * Exits the game application.
     * Called when the Exit button is clicked.
     */
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

    /**
     * Updates the background image of the Game Over screen.
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
     * Sets the Game Over image (WIN or LOSE) based on the game outcome.
     *
     * @param gameOverCase the outcome of the game (WIN or LOSE).
     */
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

    /**
     * Sets the Game Over message text.
     *
     * @param text the message to display.
     */
    private void setText(String text) {
        gameOverText.setText(text);
    }
}