package com.example.demo.level;

import java.util.List;

/**
 * Utility class for managing level selection in the game.
 * Provides methods to navigate between levels and retrieve level information.
 */
public class LevelSelector {

    /**
     * Package name for the level classes.
     */
    private static final String PACKAGE_NAME = LevelSelector.class.getPackageName();

    /**
     * List of all level class names in the game, ordered sequentially.
     */
    private static final List<String> LEVELS = List.of(
            PACKAGE_NAME + ".LevelOne",
            PACKAGE_NAME + ".LevelTwo",
            PACKAGE_NAME + ".LevelThree",
            PACKAGE_NAME + ".LevelBoss"
    );

    /**
     * Index of the current level in the {@code LEVELS} list.
     * Defaults to the first level.
     */
    private int currentLevelIndex = 0;

    /**
     * Default constructor. Initializes the level selector to the first level.
     */
    public LevelSelector() {
    }

    /**
     * Constructs a {@code LevelSelector} and sets the current level.
     *
     * @param currentLevel the fully qualified class name of the current level.
     * @throws IllegalArgumentException if the specified level is invalid.
     */
    public LevelSelector(String currentLevel) {
        setCurrentLevel(currentLevel);
    }

    /**
     * Returns the fully qualified class name of the next level.
     * Advances the current level index if the next level exists.
     * If no more levels exist, returns an empty string.
     *
     * @return the class name of the next level, or an empty string if no more levels exist.
     */
    public String getNextLevel() {
        if (currentLevelIndex < LEVELS.size() - 1) {
            return LEVELS.get(++currentLevelIndex);
        } else {
            return "";
        }
    }

    /**
     * Returns the fully qualified class name of the current level.
     *
     * @return the class name of the current level.
     */
    public String getCurrentLevel() {
        return LEVELS.get(currentLevelIndex);
    }

    /**
     * Returns the fully qualified class name of the first level.
     *
     * @return the class name of the first level.
     */
    public static String getFirstLevel() {
        return LEVELS.get(0);
    }

    /**
     * Sets the current level to the specified level.
     *
     * @param currentLevel the fully qualified class name of the level to set as current.
     * @throws IllegalArgumentException if the specified level is not in the {@code LEVELS} list.
     */
    public void setCurrentLevel(String currentLevel) {
        int index = LEVELS.indexOf(currentLevel);
        if (index == -1) {
            throw new IllegalArgumentException("Invalid level: " + currentLevel);
        }
        this.currentLevelIndex = index;
    }
}