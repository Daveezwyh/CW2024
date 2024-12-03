package com.example.demo.level;

import com.example.demo.actor.*;
import com.example.demo.util.CollisionHandler;

/**
 * Represents the third level of the game.
 * This level increases the challenge by introducing more frequent enemy spawns
 * and dynamic health point mechanics. The player must survive and reach a specified
 * kill target to advance to the next level.
 */
public class LevelThree extends LevelParent {

    /**
     * Path to the background image used in this level.
     */
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";

    /**
     * Maximum number of enemies allowed to exist simultaneously in the level.
     */
    private static final int TOTAL_ENEMIES = 4;

    /**
     * Number of kills required to advance to the next level.
     */
    private static final int KILLS_TO_ADVANCE = 30;

    /**
     * Probability of spawning a new enemy on each frame.
     */
    private static final double ENEMY_SPAWN_PROBABILITY = 0.20;

    /**
     * Initial health of the player's character at the start of the level.
     */
    private static final int PLAYER_INITIAL_HEALTH = 5;

    /**
     * Base probability of spawning a health point on each frame.
     */
    private static final double HP_SPAWN_PROBABILITY = 0.01;

    /**
     * Duration (in seconds) for which a health point remains active after spawning.
     */
    private final int HP_LINGER_SEC = 5;

    /**
     * Manages level transitions for navigating to the next level.
     */
    private final LevelSelector levelSelector;

    /**
     * Constructs the third level with the specified screen dimensions.
     *
     * @param screenHeight the height of the game screen.
     * @param screenWidth  the width of the game screen.
     */
    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        levelSelector = new LevelSelector(getClass().getName());
    }

    /**
     * Checks whether the game is over.
     * The game ends if the player's character is destroyed, resulting in a loss,
     * or if the player reaches the required kill target, advancing to the next level.
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
     * Initializes the player's character and adds it to the game scene.
     * If the player's bounding box is visible, it is also added to the scene.
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
     * Spawns enemy units based on the current number of enemies and a random spawn probability.
     * Ensures the total number of enemies does not exceed the maximum allowed.
     */
    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();

        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                EnemyPlaneMutator enemyPlaneMutator = new EnemyPlaneMutator();
                enemyPlaneMutator.setImageHeight(100);
                enemyPlaneMutator.setProjectileYPositionOffset(40);
                enemyPlaneMutator.setInitialHealth(5);
                enemyPlaneMutator.setFireRate(0.02);
                ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition, enemyPlaneMutator);
                addEnemyUnit(newEnemy);
            }
        }
    }

    /**
     * Spawns transient objects in the level, specifically health points.
     * Adjusts the spawn probability dynamically based on the player's current health.
     */
    @Override
    protected void spawnTransientObjects() {
        spawnHealthPoints();
    }

    /**
     * Spawns health points based on the player's current health and a dynamic spawn probability.
     * Health points are more likely to spawn when the player's health is lower.
     */
    private void spawnHealthPoints() {
        UserPlane user = getUser();
        int currentHealth = user.getHealth();
        double adjustedProbability = (double) (PLAYER_INITIAL_HEALTH - currentHealth) / PLAYER_INITIAL_HEALTH * HP_SPAWN_PROBABILITY;

        if (currentHealth < PLAYER_INITIAL_HEALTH && Math.random() < adjustedProbability) {
            HealthPoint healthPoint = new HealthPoint(user, HP_LINGER_SEC);
            addHealthPoint(healthPoint);
        }
    }

    /**
     * Handles collisions between the player's character and health points.
     * Restores health when health points are collected.
     */
    @Override
    protected void handleUserHealthPointCollisions() {
        CollisionHandler.handleUserHealthPointCollisions(
                PLAYER_INITIAL_HEALTH,
                getUser(),
                getHealthPoints()
        );
    }

    /**
     * Creates and returns the level view for this level.
     *
     * @return the {@link LevelView} instance configured for this level.
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, KILLS_TO_ADVANCE);
    }

    /**
     * Checks if the player has achieved the required number of kills to advance to the next level.
     *
     * @return {@code true} if the player has reached the kill target, {@code false} otherwise.
     */
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }
}