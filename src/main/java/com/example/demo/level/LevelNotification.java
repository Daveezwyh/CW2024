package com.example.demo.level;

public class LevelNotification {

    public enum Action {
        NEXT_LEVEL, WIN_GAME, LOSE_GAME
    }

    private final String levelName;
    private final Action nextAction;

    public LevelNotification(String levelName, Action nextAction) {
        this.levelName = levelName;
        this.nextAction = nextAction;
    }

    public String getLevelName() {
        return levelName;
    }

    public Action getNextAction() {
        return nextAction;
    }
}