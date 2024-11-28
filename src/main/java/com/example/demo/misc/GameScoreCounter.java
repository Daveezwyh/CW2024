package com.example.demo.misc;

import com.example.demo.util.GameScore;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

/**
 * Represents a game score counter displayed on the screen.
 * The counter shows the player's current score and updates dynamically.
 */
public class GameScoreCounter {

    private Text gameScoreCounterText;
    private GameScore gameScore;

    /**
     * Constructs a {@link GameScoreCounter} with a specified initial score and position.
     *
     * @param x            the X-coordinate of the score counter.
     * @param y            the Y-coordinate of the score counter.
     * @param initGameScore the initial score to display.
     */
    public GameScoreCounter(double x, double y, int initGameScore) {
        this.gameScore = new GameScore(initGameScore);
        init(x, y);
    }

    /**
     * Constructs a {@link GameScoreCounter} with an initial score of 0 and a specified position.
     *
     * @param x the X-coordinate of the score counter.
     * @param y the Y-coordinate of the score counter.
     */
    public GameScoreCounter(double x, double y) {
        this.gameScore = new GameScore(0);
        init(x, y);
    }

    /**
     * Initializes the game score counter text with styling and position.
     *
     * @param x the X-coordinate of the score counter text.
     * @param y the Y-coordinate of the score counter text.
     */
    public void init(double x, double y) {
        this.gameScoreCounterText = new Text(x, y, formatGameScoreCounterText());
        this.gameScoreCounterText.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));
        this.gameScoreCounterText.setFill(Color.YELLOW);
        this.gameScoreCounterText.setStroke(Color.BLACK);
        this.gameScoreCounterText.setStrokeWidth(1);
    }

    /**
     * Gets the {@link Text} object representing the game score counter.
     *
     * @return the game score counter text.
     */
    public Text getGameScoreCounterText() {
        return gameScoreCounterText;
    }

    /**
     * Updates the game score counter with a new {@link GameScore} value.
     *
     * @param gameScore the new {@link GameScore} object to display.
     */
    public void updateGameScoreCounterText(GameScore gameScore) {
        this.gameScore = gameScore;
        gameScoreCounterText.setText(formatGameScoreCounterText());
    }

    /**
     * Formats the game score into a displayable string.
     *
     * @return the formatted game score string.
     */
    private String formatGameScoreCounterText() {
        return "Score: " + gameScore.getScore();
    }
}