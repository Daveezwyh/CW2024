package com.example.demo.actor;

public class EnemyPlaneMutator {
    private String imageName = "enemyplane.png";
    private int imageHeight = 50;
    private int initialHealth = 1;
    private int HORIZONTAL_VELOCITY = -6;
    private double PROJECTILE_X_POSITION_OFFSET = -50.0;
    private double PROJECTILE_Y_POSITION_OFFSET = 10.0;
    private double FIRE_RATE = 0.01;

    public int getHorizontalVelocity() {
        return HORIZONTAL_VELOCITY;
    }
    public void setHorizontalVelocity(int HORIZONTAL_VELOCITY) {
        this.HORIZONTAL_VELOCITY = HORIZONTAL_VELOCITY;
    }
    public double getProjectileXPositionOffset() {
        return PROJECTILE_X_POSITION_OFFSET;
    }
    public void setProjectileXPositionOffset(double PROJECTILE_X_POSITION_OFFSET) {
        this.PROJECTILE_X_POSITION_OFFSET = PROJECTILE_X_POSITION_OFFSET;
    }
    public double getProjectileYPositionOffset() {
        return PROJECTILE_Y_POSITION_OFFSET;
    }
    public void setProjectileYPositionOffset(double PROJECTILE_Y_POSITION_OFFSET) {
        this.PROJECTILE_Y_POSITION_OFFSET = PROJECTILE_Y_POSITION_OFFSET;
    }
    public double getFireRate() {
        return FIRE_RATE;
    }
    public void setFireRate(double FIRE_RATE) {
        this.FIRE_RATE = FIRE_RATE;
    }
    public String getImageName() {
        return imageName;
    }
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    public int getImageHeight() {
        return imageHeight;
    }
    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }
    public int getInitialHealth() {
        return initialHealth;
    }
    public void setInitialHealth(int initialHealth) {
        this.initialHealth = initialHealth;
    }
}