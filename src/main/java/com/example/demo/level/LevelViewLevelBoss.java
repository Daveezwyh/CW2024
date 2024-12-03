package com.example.demo.level;

import com.example.demo.misc.NoFireImage;
import com.example.demo.misc.ShieldImage;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

/**
 * A specialized {@link LevelView} for the boss level in the game.
 * This class extends the base level view to include additional features
 * specific to the boss, such as displaying the boss's health, shield status,
 * and fire deactivation state.
 */
public class LevelViewLevelBoss extends LevelView {

	// Constants for positioning elements
	private static final int SHIELD_X_POSITION = 1000;
	private static final int SHIELD_Y_POSITION = -5;
	private static final int NOFIRE_X_POSITION = 970;
	private static final int NOFIRE_Y_POSITION = 10;
	private static final double BOSS_HEALTH_TEXT_X_POSITION = 1100;
	private static final double BOSS_HEALTH_TEXT_Y_POSITION = 40;
	private static final String BOSS_HP_LABEL = "Boss HP: ";

	// Visual components
	private final Group root;
	private final ShieldImage shieldImage;
	private final NoFireImage noFireImage;
	private final Text bossHealthText;

	/**
	 * Constructs a new {@link LevelViewLevelBoss} with the specified parameters.
	 * Initializes visual elements such as the boss health text, shield indicator,
	 * and fire deactivation indicator.
	 *
	 * @param root            the root {@link Group} for adding visual elements to the game screen.
	 * @param heartsToDisplay the initial number of hearts to display for the player's health.
	 */
	public LevelViewLevelBoss(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay, 1);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		this.noFireImage = new NoFireImage(NOFIRE_X_POSITION, NOFIRE_Y_POSITION);
		this.bossHealthText = new Text(BOSS_HP_LABEL);
		makeBossHPText();
		addImagesToRoot();
	}

	/**
	 * Configures the text element used to display the boss's health.
	 * Sets font, color, position, and a drop shadow effect for better visibility.
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
	 * Adds the shield image, fire deactivation image, and boss health text
	 * to the root group for display on the game screen.
	 */
	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage, noFireImage, bossHealthText);
	}

	/**
	 * Displays the shield image on the game screen to indicate the boss is shielded.
	 */
	public void showShield() {
		shieldImage.showShield();
	}

	/**
	 * Hides the shield image from the game screen to indicate the boss is no longer shielded.
	 */
	public void hideShield() {
		shieldImage.hideShield();
	}

	/**
	 * Displays the fire deactivation image on the game screen
	 * to indicate the boss's ability to fire projectiles is deactivated.
	 */
	public void showNoFire() {
		noFireImage.showNoFire();
	}

	/**
	 * Hides the fire deactivation image from the game screen
	 * to indicate the boss's ability to fire projectiles is active.
	 */
	public void hideNoFire() {
		noFireImage.hideNoFire();
	}

	/**
	 * Updates the boss health text to reflect the current health of the boss.
	 *
	 * @param health the current health value of the boss.
	 */
	public void updateBossHealth(int health) {
		bossHealthText.setText(BOSS_HP_LABEL + health);
	}
}