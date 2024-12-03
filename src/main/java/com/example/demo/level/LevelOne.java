package com.example.demo.level;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.EnemyPlane;
import com.example.demo.actor.UserPlane;

/**
 * Represents the first level of the game.
 * This level involves the user defeating a specified number of enemies to advance to the next level.
 */
public class LevelOne extends LevelParent {

	/** Path to the background image for this level. */
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";

	/** Total number of enemies that can exist in the level at once. */
	private static final int TOTAL_ENEMIES = 5;

	/** Number of kills required to advance to the next level. */
	private static final int KILLS_TO_ADVANCE = 50;

	/** Probability of spawning an enemy each frame. */
	private static final double ENEMY_SPAWN_PROBABILITY = 0.20;

	/** Initial health for the player's character in this level. */
	private static final int PLAYER_INITIAL_HEALTH = 5;

	/** Level selector to determine the next level. */
	private final LevelSelector levelSelector;

	/**
	 * Constructs the LevelOne instance with the specified screen dimensions.
	 *
	 * @param screenHeight the height of the game screen.
	 * @param screenWidth the width of the game screen.
	 */
	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		levelSelector = new LevelSelector(getClass().getName());
	}

	/**
	 * Initializes the player's character and adds it to the game root.
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
	 * Checks if the game is over based on the user's health or kill count.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (userHasReachedKillTarget()) {
			goToNextLevel(levelSelector.getNextLevel());
		}
	}

	/**
	 * Spawns new enemies if there are fewer enemies than the allowed total.
	 */
	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	/**
	 * This level does not include health points.
	 */
	@Override
	protected void spawnTransientObjects() {
		// No health points in this level.
		return;
	}

	/**
	 * Creates and initializes the level view for this level.
	 *
	 * @return the initialized level view.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, KILLS_TO_ADVANCE);
	}

	/**
	 * Determines if the player has achieved the required number of kills to advance.
	 *
	 * @return true if the player has reached the kill target; false otherwise.
	 */
	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}
}