package com.example.demo.level;

import java.util.List;

public class LevelSelector {
    private static final String PACKAGE_NAME = LevelSelector.class.getPackageName();
    private static final List<String> LEVELS = List.of(
            PACKAGE_NAME + ".LevelOne",
            PACKAGE_NAME + ".LevelTwo",
            PACKAGE_NAME + ".LevelBoss"
    );
    private int currentLevelIndex = 0; // Default to the first level

    public LevelSelector() {
    }

    public LevelSelector(String currentLevel) {
        setCurrentLevel(currentLevel);
    }

    public String getNextLevel() {
        if (currentLevelIndex < LEVELS.size() - 1) {
            return LEVELS.get(++currentLevelIndex);
        } else {
            return "";
        }
    }

    public String getCurrentLevel() {
        return LEVELS.get(currentLevelIndex);
    }

    public static String getFirstLevel() {
        return LEVELS.get(0);
    }

    public void setCurrentLevel(String currentLevel) {
        int index = LEVELS.indexOf(currentLevel);
        if (index == -1) {
            throw new IllegalArgumentException("Invalid level: " + currentLevel);
        }
        this.currentLevelIndex = index;
    }
}