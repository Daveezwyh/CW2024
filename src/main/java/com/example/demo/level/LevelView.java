package com.example.demo.level;

import com.example.demo.misc.GameOverImage;
import com.example.demo.misc.HeartDisplay;
import com.example.demo.misc.KillCounter;
import com.example.demo.misc.WinImage;
import javafx.scene.Group;

public class LevelView {
	
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 15;
	private static final int WIN_IMAGE_X_POSITION = 355;
	private static final int WIN_IMAGE_Y_POSITION = 175;
	private static final int LOSS_SCREEN_X_POSITION = 450;
	private static final int LOSS_SCREEN_Y_POSISITION = 150;
	private static final double KILL_COUNTER_X_POSITION = 250;
	private static final double KILL_COUNTER_Y_POSITION = 30;
	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;
	private final KillCounter killCounter;

	public LevelView(Group root, int heartsToDisplay, int killsToAdvance) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSISITION);
		this.killCounter = new KillCounter(KILL_COUNTER_X_POSITION, KILL_COUNTER_Y_POSITION, killsToAdvance);
	}
	
	public LevelView showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
		return this;
	}

	 public LevelView showKillCounter() {
		root.getChildren().add(killCounter.getKillCounterText());
		return this;
	}

	public void showWinImage() {
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}
	
	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
	}

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

	public LevelView updateKillCount(int killCount){
		killCounter.setCurrentKills(killCount)
				.updateKillCounterText();
		return this;
	}
}
