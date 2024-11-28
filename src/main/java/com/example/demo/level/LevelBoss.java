package com.example.demo.level;

import com.example.demo.actor.Boss;
import com.example.demo.actor.UserPlane;
import com.example.demo.util.CollisionHandler;

/**
 * Represents the boss level in the game. This level includes a boss fight
 * with specific behaviors and updates for the level view and collisions.
 */
public class LevelBoss extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;

	private final Boss boss;
	private LevelViewLevelBoss levelView;

	/**
	 * Constructs the boss level with the specified screen dimensions.
	 *
	 * @param screenHeight the height of the game screen.
	 * @param screenWidth  the width of the game screen.
	 */
	public LevelBoss(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss();
	}

	/**
	 * Initializes the friendly units (e.g., user plane) in the level.
	 * Adds the user plane and its bounding box to the scene if visible.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		UserPlane user = getUser();
		getRoot().getChildren().add(user);
		if (user.isBoundingBoxVisible()) {
			getRoot().getChildren().add(user.getBoundingBox());
		}
	}

	/**
	 * Spawns the enemy units, ensuring the boss is added when no enemies are present.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	/**
	 * Checks if the game is over, either by the user plane being destroyed
	 * or the boss being defeated.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (boss.isDestroyed()) {
			winGame();
		}
	}

	/**
	 * Handles scoreable collisions, such as user projectiles hitting the boss.
	 * Updates the game score based on collision outcomes.
	 */
	@Override
	protected void handleScoreableCollisions() {
		int scoreIncrement = CollisionHandler.handleUserProjectileBossCollisions(getUser(), userProjectiles, boss);
		gameScore.increaseScoreBy(scoreIncrement);
	}

	/**
	 * Spawns health points in the level. No implementation for this level.
	 */
	@Override
	protected void spawnHealthPoints() {
	}

	/**
	 * Instantiates the level view specific to the boss level.
	 *
	 * @return the level view for the boss level.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelBoss(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelView;
	}

	/**
	 * Updates the level view, including the boss's health and shield status.
	 */
	@Override
	protected void updateLevelView() {
		super.updateLevelView();
		levelView.updateBossHealth(boss.getHealth());
		if (boss.getIsShielded()) {
			levelView.showShield();
		} else {
			levelView.hideShield();
		}
	}

	/**
	 * Animates the background of the level. No implementation for this level.
	 */
	@Override
	protected void animateBackground() {
	}
}