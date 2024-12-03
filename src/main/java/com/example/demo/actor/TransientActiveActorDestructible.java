package com.example.demo.actor;

import java.util.Random;

/**
 * An abstract class representing a transient active actor in the game.
 * This type of actor has a limited lifespan and is automatically removed
 * after a specified duration. It provides utilities for calculating random
 * positions and checking expiration status.
 */
public abstract class TransientActiveActorDestructible extends ActiveActorDestructible {

    /**
     * The timestamp (in seconds) when the actor was created.
     */
    private final long createdTimeStampSecond;

    /**
     * The duration (in seconds) for which the actor remains active.
     */
    private long lingerTimeSecond;

    /**
     * Constructs a transient active actor with the specified parameters.
     *
     * @param imageName       the file name of the image representing the actor.
     * @param imageHeight     the height of the actor's image.
     * @param initialXPos     the initial X-coordinate of the actor.
     * @param initialYPos     the initial Y-coordinate of the actor.
     * @param lingerTimeSecond the duration (in seconds) for which the actor remains active.
     */
    public TransientActiveActorDestructible(
            String imageName,
            int imageHeight,
            double initialXPos,
            double initialYPos,
            long lingerTimeSecond
    ) {
        super(imageName, imageHeight, initialXPos, initialYPos);
        this.createdTimeStampSecond = System.currentTimeMillis() / 1000;
        this.lingerTimeSecond = lingerTimeSecond;
    }

    /**
     * Checks if the actor has expired based on the current timestamp.
     * An actor is considered expired if the time since its creation exceeds its linger time.
     *
     * @param currentTimeStampSecond the current time in seconds.
     * @return {@code true} if the actor has expired, {@code false} otherwise.
     */
    public boolean isExpired(long currentTimeStampSecond) {
        return (currentTimeStampSecond - this.getCreatedTimeStamp()) > lingerTimeSecond;
    }

    /**
     * Generates a random position within the specified bounds.
     *
     * @param lowerBound the lower bound of the position range.
     * @param upperBound the upper bound of the position range.
     * @return a random double value between {@code lowerBound} and {@code upperBound}.
     */
    protected static double getRandomPosition(double lowerBound, double upperBound) {
        Random random = new Random();
        return lowerBound + (random.nextDouble() * (upperBound - lowerBound));
    }

    /**
     * Gets the timestamp (in seconds) when the actor was created.
     *
     * @return the creation timestamp of the actor.
     */
    private long getCreatedTimeStamp() {
        return this.createdTimeStampSecond;
    }
}