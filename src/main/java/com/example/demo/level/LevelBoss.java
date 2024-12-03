package com.example.demo.level;

import com.example.demo.actor.Boss;
import com.example.demo.actor.FireDeactivator;
import com.example.demo.actor.UserPlane;
import com.example.demo.util.CollisionHandler;

/**
 * Represents the boss level in the game. This level features a challenging boss fight,
 * specific behaviors for the boss and user interactions, and updates to the level view.
 * It includes mechanisms for spawning enemies, handling collisions, and managing the game's outcome.
 */
public class LevelBoss extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final double FIREDEAC_SPAWN_PROBABILITY = 0.005;
	private final int FIREDEAC_LINGER_SEC = 5;
	private final Boss boss;
	private LevelViewLevelBoss levelView;

	/**
	 * Constructs a new boss level with the specified screen dimensions.
	 *
	 * @param screenHeight the height of the game screen.
	 * @param screenWidth  the width of the game screen.
	 */
	public LevelBoss(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss();
	}

	/**
	 * Initializes the friendly units in the level, such as the user's plane.
	 * Adds the user plane and its bounding box to the game scene if the bounding box is visible.
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
	 * Spawns enemy units in the level.
	 * Ensures the boss is added when there are no enemy units currently present.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	/**
	 * Checks if the game is over.
	 * The game ends if the user's plane is destroyed (resulting in a loss)
	 * or if the boss is defeated (resulting in a win).
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
	 * Handles scoreable collisions in the level, such as user projectiles hitting the boss.
	 * Updates the game score based on the outcomes of the collisions.
	 */
	@Override
	protected void handleScoreableCollisions() {
		int scoreIncrement = CollisionHandler.handleUserProjectileBossCollisions(getUser(), getUserProjectiles(), boss);
		gameScore.increaseScoreBy(scoreIncrement);

		handleBossEffectCollisions();
	}

	/**
	 * Spawns transient objects in the level, such as the fire deactivator.
	 */
	@Override
	protected void spawnTransientObjects() {
		spawnFireDeactivator();
	}

	/**
	 * Handles collisions between the user and boss-specific effects, such as the fire deactivator.
	 */
	private void handleBossEffectCollisions() {
		CollisionHandler.handleUserBossFireDeactivatorCollisions(getUser(), boss, getFireDeactivators());
	}

	/**
	 * Spawns a fire deactivator if the boss's fire is not already deactivated,
	 * no fire deactivators are present, and a random probability check passes.
	 */
	private void spawnFireDeactivator() {
		if (
				!boss.getIsFireDeactivated() &&
						getFireDeactivators().isEmpty() &&
						Math.random() < FIREDEAC_SPAWN_PROBABILITY
		) {
			FireDeactivator fireDeactivator = new FireDeactivator(getUser(), FIREDEAC_LINGER_SEC);
			addFireDeactivator(fireDeactivator);
		}
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
	 * Updates the level view with information specific to the boss level,
	 * such as the boss's health, shield status, and fire deactivation state.
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
		if (boss.getIsFireDeactivated()) {
			levelView.showNoFire();
		} else {
			levelView.hideNoFire();
		}
	}

	/**
	 * Animates the background of the level.
	 * This method currently has no implementation for the boss level.
	 */
	@Override
	protected void animateBackground() {
		// No background animation for the boss level.
	}
}