package com.example.demo.level;

public record LevelNotification(String levelName, com.example.demo.level.LevelNotification.Action nextAction) {

    public enum Action {
        NEXT_LEVEL, WIN_GAME, LOSE_GAME
    }

}