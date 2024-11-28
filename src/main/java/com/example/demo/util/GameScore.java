package com.example.demo.util;

/**
 * Represents a game score with functionality to track, increment, and reset the score.
 */
public class GameScore {

    private int score;
    private int lastIncrement;

    /**
     * Constructs a GameScore instance with the specified initial score.
     *
     * @param initialScore the initial score to set.
     */
    public GameScore(int initialScore) {
        this.score = initialScore;
    }

    /**
     * Retrieves the current score.
     *
     * @return the current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Resets the score to zero.
     */
    public void resetScore() {
        this.score = 0;
    }

    /**
     * Increases the score by the specified increment.
     *
     * @param increment the amount to add to the score.
     */
    public void increaseScoreBy(int increment) {
        this.lastIncrement = increment;
        this.score += increment;
    }

    /**
     * Retrieves the last increment value that was added to the score.
     *
     * @return the last increment value.
     */
    public int getLastIncrement() {
        return lastIncrement;
    }
}