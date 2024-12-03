package com.example.demo.actor;

import java.util.Random;

/**
 * Represents a health point object in the game.
 * The health point is a collectible item that appears randomly within a defined area
 * around the user's plane. When collected, it restores health to the user.
 * The health point has a limited lifespan and is destroyed after a set duration or upon taking damage.
 */
public class HealthPoint extends TransientActiveActorDestructible {

    private static final String IMAGE_NAME = "heart.png";
    private static final int IMAGE_HEIGHT = 30;
    private static final int Y_UPPER_OFFSET = 100;
    private static final int X_UPPER_OFFSET = 300;

    /**
     * Constructs a HealthPoint instance with a random position within the area around the user's plane.
     * The position is calculated using the bounds of the user's plane and offsets to ensure
     * variety in placement. The health point will linger for the specified duration.
     *
     * @param userPlane       the user's plane, used to determine bounds for the random position.
     * @param lingerTimeSecond the duration in seconds for which the health point remains active.
     */
    public HealthPoint(UserPlane userPlane, long lingerTimeSecond) {
        super(
                IMAGE_NAME,
                IMAGE_HEIGHT,
                getRandomPosition(userPlane.getXLowerBound(), userPlane.getXUpperBound() + X_UPPER_OFFSET),
                getRandomPosition(userPlane.getYLowerBound(), userPlane.getYUpperBound() + Y_UPPER_OFFSET),
                lingerTimeSecond
        );
    }

    /**
     * Updates the position of the health point.
     * The health point remains stationary, so this method does not modify its position.
     */
    @Override
    public void updatePosition() {
        // No movement logic required for HealthPoint.
    }

    /**
     * Updates the behavior of the health point.
     * This method currently has no additional behavior beyond its base class functionality.
     */
    @Override
    public void updateActor() {
        // No additional logic required for HealthPoint.
    }

    /**
     * Handles damage taken by the health point.
     * When a health point takes damage, it is destroyed and removed from the game.
     */
    @Override
    public void takeDamage() {
        this.destroy();
    }

    /**
     * Repairs the health point.
     * This method has no implementation since health points cannot be repaired.
     */
    @Override
    public void repairDamage() {
        // HealthPoint does not support repair functionality.
    }
}