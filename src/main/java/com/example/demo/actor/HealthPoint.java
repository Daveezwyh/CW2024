package com.example.demo.actor;

import java.util.Random;

public class HealthPoint extends ActiveActorDestructible {
    private static final String IMAGE_NAME = "heart.png";
    private static final int IMAGE_HEIGHT = 30;
    private static final int Y_UPPER_OFFSET = 100; // So that it does not appear in the User HeartDisplay
    private static final int X_UPPER_OFFSET = 300; // So it is not too close to the User
    private long createdTimeStamp;
    public HealthPoint(UserPlane userPlane) {
        super(
                IMAGE_NAME,
                IMAGE_HEIGHT,
                getRandomPosition(userPlane.getXLowerBound(), userPlane.getXUpperBound() + X_UPPER_OFFSET),
                getRandomPosition(userPlane.getYLowerBound(), userPlane.getYUpperBound() + Y_UPPER_OFFSET)
        );
        createdTimeStamp = System.currentTimeMillis() / 1000;
    }

    private static double getRandomPosition(double lowerBound, double upperBound) {
        Random random = new Random();
        return lowerBound + (random.nextDouble() * (upperBound - lowerBound));
    }

    @Override
    public void updatePosition() {}

    @Override
    public void updateActor() {}

    @Override
    public void takeDamage() {
        this.destroy();
    }

    @Override
    public void repairDamage(){}

    @Override
    public long getCreatedTimeStamp(){
        return createdTimeStamp;
    }
}
