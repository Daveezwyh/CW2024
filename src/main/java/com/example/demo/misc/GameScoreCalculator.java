package com.example.demo.misc;

import com.example.demo.actor.UserPlane;

public class GameScoreCalculator {
    public static int calculateUserScoreByPosition(UserPlane userPlane) {
        double x = userPlane.getTranslateX();
        double lowerBound = userPlane.getXLowerBound();
        double upperBound = userPlane.getXUpperBound();

        if (upperBound >= lowerBound) {
            throw new IllegalArgumentException("Invalid bounds: upperBound must be less than lowerBound.");
        }

        double normalizedX = (x - upperBound) / (lowerBound - upperBound);
        int addition = (int) Math.max(0, Math.min(5, normalizedX * 5));

        return 1 + addition;
    }
}