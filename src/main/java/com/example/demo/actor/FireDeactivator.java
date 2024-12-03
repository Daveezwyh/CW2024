package com.example.demo.actor;

/**
 * Represents a fire deactivator in the game. The fire deactivator is a transient
 * active actor that appears for a limited time and deactivates the boss's fire when collided with the user's plane.
 */
public class FireDeactivator extends TransientActiveActorDestructible {

    private static final String IMAGE_NAME = "NoFireImage.png";
    private static final int IMAGE_HEIGHT = 30;
    private static final int Y_UPPER_OFFSET = 100;
    private static final int X_UPPER_OFFSET = 300;

    /**
     * Constructs a FireDeactivator instance with a random position near the user's plane.
     *
     * @param userPlane       the user's plane, used to determine bounds for placing the fire deactivator.
     * @param lingerTimeSecond the time in seconds the fire deactivator will remain active.
     */
    public FireDeactivator(UserPlane userPlane, long lingerTimeSecond) {
        super(
                IMAGE_NAME,
                IMAGE_HEIGHT,
                getRandomPosition(userPlane.getXLowerBound(), userPlane.getXUpperBound() + X_UPPER_OFFSET),
                getRandomPosition(userPlane.getYLowerBound(), userPlane.getYUpperBound() + Y_UPPER_OFFSET),
                lingerTimeSecond
        );
    }

    /**
     * Updates the position of the fire deactivator.
     * Currently, the fire deactivator remains stationary, so this method is a no-op.
     */
    @Override
    public void updatePosition() {
        // No movement logic required for the fire deactivator.
    }

    /**
     * Updates the state of the fire deactivator.
     * Currently, there is no additional behavior for the fire deactivator.
     */
    @Override
    public void updateActor() {
        // No additional state update logic required for the fire deactivator.
    }

    /**
     * Handles damage taken by the fire deactivator.
     * Currently, fire deactivators do not respond to damage, so this method is a no-op.
     */
    @Override
    public void takeDamage() {
        // Fire deactivators are not destructible via damage.
    }

    /**
     * Repairs damage to the fire deactivator.
     * Currently, fire deactivators do not have repairable damage, so this method is a no-op.
     */
    @Override
    public void repairDamage() {
        // Fire deactivators do not support damage repair.
    }
}