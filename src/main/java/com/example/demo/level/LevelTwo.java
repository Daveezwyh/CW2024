package com.example.demo.level;

import com.example.demo.actor.*;
import com.example.demo.contract.EnemyVariation;
import com.example.demo.util.CollisionHandler;

/**
 * Represents the second level of the game.
 * Introduces varying enemy types and health point mechanics.
 */
public class LevelTwo extends LevelParent implements EnemyVariation {

    /**
     * Path to the background image for the level.
     */
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";

    /**
     * Total number of enemies allowed in the level at any time.
     */
    private static final int TOTAL_ENEMIES = 4;

    /**
     * Number of kills required to advance to the next level.
     */
    private static final int KILLS_TO_ADVANCE = 50;

    /**
     * Probability of spawning an enemy on each frame.
     */
    private static final double ENEMY_SPAWN_PROBABILITY = 0.2;

    /**
     * Initial health of the player's character.
     */
    private static final int PLAYER_INITIAL_HEALTH = 5;

    /**
     * Base probability of spawning a health point on each frame.
     */
    private static final double HP_SPAWN_PROBABILITY = 0.01;

    /**
     * Level selector to manage navigation to the next level.
     */
    private final LevelSelector levelSelector;

    /**
     * Constructs the second level with the given screen dimensions.
     *
     * @param screenHeight the height of the screen.
     * @param screenWidth  the width of the screen.
     */
    public LevelTwo(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        levelSelector = new LevelSelector(getClass().getName());
    }

    /**
     * Checks whether the game is over due to the player's destruction or if the kill target has been reached.
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
     * Spawns enemy units based on the current number of enemies and a spawn probability.
     * Includes logic for generating different enemy types.
     */
    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();

        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                EnemyPlane newEnemy;
                if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                    newEnemy = makeEnemyPlane(getScreenWidth(), newEnemyInitialYPosition, 1);
                } else {
                    newEnemy = makeEnemyPlane(getScreenWidth(), newEnemyInitialYPosition, 0);
                }
                addEnemyUnit(newEnemy);
            }
        }
    }

    /**
     * Spawns health points based on the player's current health and a dynamic spawn probability.
     */
    @Override
    protected void spawnHealthPoints() {
        UserPlane user = getUser();
        int currentHealth = user.getHealth();
        double adjustedProbability = (double) (PLAYER_INITIAL_HEALTH - currentHealth) / PLAYER_INITIAL_HEALTH * HP_SPAWN_PROBABILITY;

        if (currentHealth < PLAYER_INITIAL_HEALTH && Math.random() < adjustedProbability) {
            HealthPoint healthPoint = new HealthPoint(user);
            healthPoints.add(healthPoint);
            getRoot().getChildren().add(healthPoint);

            if (healthPoint.isBoundingBoxVisible()) {
                getRoot().getChildren().add(healthPoint.getBoundingBox());
            }
        }
    }

    /**
     * Handles collisions between the player and health points.
     * Ensures the player's health is updated when health points are collected.
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
     * @return the {@link LevelView} instance for this level.
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, KILLS_TO_ADVANCE);
    }

    /**
     * Checks if the player has reached the required number of kills to advance to the next level.
     *
     * @return {@code true} if the kill target is reached, {@code false} otherwise.
     */
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

    /**
     * Creates an enemy plane of the specified type at the given position.
     *
     * @param initialXPos the initial X-coordinate of the enemy plane.
     * @param initialYPos the initial Y-coordinate of the enemy plane.
     * @param type        the type of enemy plane to create.
     * @return the created {@link EnemyPlane}.
     */
    @Override
    public EnemyPlane makeEnemyPlane(double initialXPos, double initialYPos, int type) {
        EnemyPlane enemyPlane;

        switch (type) {
            case 0 -> enemyPlane = new EnemyPlane(initialXPos, initialYPos);
            case 1 -> {
                EnemyPlaneMutator enemyPlaneMutator = new EnemyPlaneMutator();
                enemyPlaneMutator.setImageHeight(100);
                enemyPlaneMutator.setProjectileYPositionOffset(40);
                enemyPlaneMutator.setInitialHealth(5);
                enemyPlane = new EnemyPlane(initialXPos, initialYPos, enemyPlaneMutator);
            }
            default -> enemyPlane = new EnemyPlane(initialXPos, initialYPos);
        }

        return enemyPlane;
    }
}