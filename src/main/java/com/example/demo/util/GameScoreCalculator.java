package com.example.demo.util;

import com.example.demo.actor.UserPlane;

/**
 * Utility class for calculating game scores based on a user's position.
 */
public class GameScoreCalculator {

    /**
     * Calculates the score increment based on the user's position.
     *
     * @param userPlane the user plane whose position is used for score calculation.
     * @return the calculated score increment.
     */
    public static int calculateUserScoreByPosition(UserPlane userPlane) {
        double x = userPlane.getTranslateX();
        double lowerBound = userPlane.getXLowerBound();
        double upperBound = userPlane.getXUpperBound();

        double normalizedX = (x - upperBound) / (lowerBound - upperBound);
        int addition = (int) Math.max(0, Math.min(5, normalizedX * 5));

        return 1 + addition;
    }
}