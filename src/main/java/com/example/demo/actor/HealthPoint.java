package com.example.demo.actor;

import java.util.Random;

/**
 * Represents a health point object in the game.
 * The health point appears randomly within specified bounds and can be collected to restore health.
 */
public class HealthPoint extends ActiveActorDestructible {

    private static final String IMAGE_NAME = "heart.png";
    private static final int IMAGE_HEIGHT = 30;
    private static final int Y_UPPER_OFFSET = 100;
    private static final int X_UPPER_OFFSET = 300;
    private final long createdTimeStamp;

    /**
     * Constructs a HealthPoint instance at a random position within specified bounds.
     *
     * @param userPlane the user plane to determine bounds for the random position.
     */
    public HealthPoint(UserPlane userPlane) {
        super(
                IMAGE_NAME,
                IMAGE_HEIGHT,
                getRandomPosition(userPlane.getXLowerBound(), userPlane.getXUpperBound() + X_UPPER_OFFSET),
                getRandomPosition(userPlane.getYLowerBound(), userPlane.getYUpperBound() + Y_UPPER_OFFSET)
        );
        createdTimeStamp = System.currentTimeMillis() / 1000;
    }

    /**
     * Calculates a random position within the given bounds.
     *
     * @param lowerBound the lower bound for the random position.
     * @param upperBound the upper bound for the random position.
     * @return a random position within the specified bounds.
     */
    private static double getRandomPosition(double lowerBound, double upperBound) {
        Random random = new Random();
        return lowerBound + (random.nextDouble() * (upperBound - lowerBound));
    }

    /**
     * Updates the position of the health point.
     * No movement is applied to HealthPoint, so this method is empty.
     */
    @Override
    public void updatePosition() {}

    /**
     * Updates the actor logic for the health point.
     * This method is empty since HealthPoint does not have additional behavior.
     */
    @Override
    public void updateActor() {}

    /**
     * Handles the damage taken by the health point.
     * The health point is destroyed upon taking damage.
     */
    @Override
    public void takeDamage() {
        this.destroy();
    }

    /**
     * Repairs the health point.
     * No implementation since HealthPoint cannot repair itself.
     */
    @Override
    public void repairDamage() {}

    /**
     * Returns the creation timestamp of the health point.
     *
     * @return the creation timestamp in seconds since the epoch.
     */
    @Override
    public long getCreatedTimeStamp() {
        return createdTimeStamp;
    }
}