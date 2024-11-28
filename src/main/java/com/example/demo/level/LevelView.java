package com.example.demo.level;

import com.example.demo.misc.*;
import com.example.demo.util.GameScore;
import javafx.scene.Group;

/**
 * Represents the visual elements of a game level, such as hearts, kill counters, and game score.
 * Handles displaying and updating these elements during gameplay.
 */
public class LevelView {

	// Constants for positioning visual elements
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 15;
	private static final int WIN_IMAGE_X_POSITION = 355;
	private static final int WIN_IMAGE_Y_POSITION = 175;
	private static final int LOSS_SCREEN_X_POSITION = 450;
	private static final int LOSS_SCREEN_Y_POSITION = 150;
	private static final double KILL_COUNTER_X_POSITION = 250;
	private static final double KILL_COUNTER_Y_POSITION = 30;
	private static final double GAMESCORE_COUNTER_X_POSITION = 450;
	private static final double GAMESCORE_COUNTER_Y_POSITION = 30;

	// Visual components
	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;
	private final KillCounter killCounter;
	private final GameScoreCounter gameScoreCounter;
	private final PauseOverlay pauseOverlay;

	/**
	 * Constructs a new LevelView with the given parameters.
	 *
	 * @param root            the root {@link Group} for adding visual elements.
	 * @param heartsToDisplay the initial number of hearts to display.
	 * @param killsToAdvance  the number of kills required to advance the level.
	 */
	public LevelView(Group root, int heartsToDisplay, int killsToAdvance) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);
		this.killCounter = new KillCounter(KILL_COUNTER_X_POSITION, KILL_COUNTER_Y_POSITION, killsToAdvance);
		this.gameScoreCounter = new GameScoreCounter(GAMESCORE_COUNTER_X_POSITION, GAMESCORE_COUNTER_Y_POSITION);
		this.pauseOverlay = new PauseOverlay(root);
	}

	/**
	 * Displays the heart display on the game screen.
	 *
	 * @return the current {@link LevelView} instance for method chaining.
	 */
	public LevelView showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
		return this;
	}

	/**
	 * Displays the kill counter on the game screen.
	 *
	 * @return the current {@link LevelView} instance for method chaining.
	 */
	public LevelView showKillCounter() {
		root.getChildren().add(killCounter.getKillCounterText());
		return this;
	}

	/**
	 * Displays the game score counter on the game screen.
	 *
	 * @return the current {@link LevelView} instance for method chaining.
	 */
	public LevelView showGameScoreCounter() {
		root.getChildren().add(gameScoreCounter.getGameScoreCounterText());
		return this;
	}

	/**
	 * Displays the win image on the game screen.
	 */
	public void showWinImage() {
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}

	/**
	 * Displays the game over image on the game screen.
	 */
	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
	}

	/**
	 * Shows the pause overlay on the game screen.
	 */
	public void showPauseOverlay() {
		pauseOverlay.show();
	}

	/**
	 * Hides the pause overlay from the game screen.
	 */
	public void hidePauseOverlay() {
		pauseOverlay.hide();
	}

	/**
	 * Updates the heart display to reflect the player's remaining health.
	 *
	 * @param heartsRemaining the number of hearts to display.
	 * @return the current {@link LevelView} instance for method chaining.
	 */
	public LevelView updateHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		int difference = heartsRemaining - currentNumberOfHearts;

		if (difference > 0) {
			for (int i = 0; i < difference; i++) {
				heartDisplay.addHeart();
			}
		} else if (difference < 0) {
			for (int i = 0; i < -difference; i++) {
				heartDisplay.removeHeart();
			}
		}

		return this;
	}

	/**
	 * Updates the kill counter with the current number of kills.
	 *
	 * @param killCount the current number of kills.
	 * @return the current {@link LevelView} instance for method chaining.
	 */
	public LevelView updateKillCount(int killCount) {
		killCounter.setCurrentKills(killCount)
				.updateKillCounterText();
		return this;
	}

	/**
	 * Updates the game score display with the given score.
	 *
	 * @param gameScore the {@link GameScore} object containing the updated score.
	 * @return the current {@link LevelView} instance for method chaining.
	 */
	public LevelView updateGameScore(GameScore gameScore) {
		gameScoreCounter.updateGameScoreCounterText(gameScore);
		return this;
	}
}