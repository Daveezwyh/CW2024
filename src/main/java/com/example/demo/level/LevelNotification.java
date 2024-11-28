package com.example.demo.level;

/**
 * Represents a notification for the level system. It contains information about the level name
 * and the next action to be taken in the game.
 *
 * @param levelName   the name of the current level.
 * @param nextAction  the action to be taken next in the game.
 */
public record LevelNotification(String levelName, LevelNotification.Action nextAction) {

    /**
     * Enum representing possible actions for a level notification.
     */
    public enum Action {
        /**
         * Indicates progression to the next level.
         */
        NEXT_LEVEL,

        /**
         * Indicates the player has won the game.
         */
        WIN_GAME,

        /**
         * Indicates the player has lost the game.
         */
        LOSE_GAME
    }
}