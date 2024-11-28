package com.example.demo.level;

import com.example.demo.misc.ShieldImage;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

/**
 * A specialized {@link LevelView} for the boss level.
 * Includes features for displaying the boss's health and shield.
 */
public class LevelViewLevelBoss extends LevelView {

	// Constants for positioning elements
	private static final int SHIELD_X_POSITION = 1000;
	private static final int SHIELD_Y_POSITION = -5;
	private static final double BOSS_HEALTH_TEXT_X_POSITION = 1100;
	private static final double BOSS_HEALTH_TEXT_Y_POSITION = 40;
	private static final String BOSS_HP_LABEL = "Boss HP: ";

	// Visual components
	private final Group root;
	private final ShieldImage shieldImage;
	private final Text bossHealthText;

	/**
	 * Constructs a new {@link LevelViewLevelBoss} with the specified parameters.
	 *
	 * @param root            the root {@link Group} for adding visual elements.
	 * @param heartsToDisplay the initial number of hearts to display.
	 */
	public LevelViewLevelBoss(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay, 1);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		this.bossHealthText = new Text(BOSS_HP_LABEL);
		makeBossHPText();
		addImagesToRoot();
	}

	/**
	 * Configures the text element used to display the boss's health.
	 */
	private void makeBossHPText() {
		bossHealthText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		bossHealthText.setFill(Color.RED);

		DropShadow dropShadow = new DropShadow();
		dropShadow.setColor(Color.BLACK);

		bossHealthText.setEffect(dropShadow);
		bossHealthText.setX(BOSS_HEALTH_TEXT_X_POSITION);
		bossHealthText.setY(BOSS_HEALTH_TEXT_Y_POSITION);
	}

	/**
	 * Adds the shield image and boss health text to the root group.
	 */
	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage, bossHealthText);
	}

	/**
	 * Displays the shield image on the game screen.
	 */
	public void showShield() {
		shieldImage.showShield();
	}

	/**
	 * Hides the shield image from the game screen.
	 */
	public void hideShield() {
		shieldImage.hideShield();
	}

	/**
	 * Updates the boss health text with the current health value.
	 *
	 * @param health the current health of the boss.
	 */
	public void updateBossHealth(int health) {
		bossHealthText.setText(BOSS_HP_LABEL + health);
	}
}