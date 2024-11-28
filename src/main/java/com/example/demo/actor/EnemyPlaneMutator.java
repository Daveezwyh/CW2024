package com.example.demo.actor;

/**
 * This class encapsulates the attributes and behaviors of an enemy plane mutator.
 * The mutator allows for configuration of various parameters related to enemy planes,
 * including image properties, health, movement, and projectile settings.
 */
public class EnemyPlaneMutator {

    private String imageName = "enemyplane.png";
    private int imageHeight = 50;
    private int initialHealth = 1;
    private int HORIZONTAL_VELOCITY = -6;
    private double PROJECTILE_X_POSITION_OFFSET = -50.0;
    private double PROJECTILE_Y_POSITION_OFFSET = 10.0;
    private double FIRE_RATE = 0.015;

    /**
     * Gets the name of the image associated with the enemy plane.
     *
     * @return the image name.
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Sets the name of the image associated with the enemy plane.
     *
     * @param imageName the image name.
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * Gets the height of the image associated with the enemy plane.
     *
     * @return the image height.
     */
    public int getImageHeight() {
        return imageHeight;
    }

    /**
     * Sets the height of the image associated with the enemy plane.
     *
     * @param imageHeight the image height.
     */
    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    /**
     * Gets the initial health of the enemy plane.
     *
     * @return the initial health.
     */
    public int getInitialHealth() {
        return initialHealth;
    }

    /**
     * Sets the initial health of the enemy plane.
     *
     * @param initialHealth the initial health.
     */
    public void setInitialHealth(int initialHealth) {
        this.initialHealth = initialHealth;
    }

    /**
     * Gets the horizontal velocity of the enemy plane.
     *
     * @return the horizontal velocity.
     */
    public int getHorizontalVelocity() {
        return HORIZONTAL_VELOCITY;
    }

    /**
     * Sets the horizontal velocity of the enemy plane.
     *
     * @param HORIZONTAL_VELOCITY the horizontal velocity.
     */
    public void setHorizontalVelocity(int HORIZONTAL_VELOCITY) {
        this.HORIZONTAL_VELOCITY = HORIZONTAL_VELOCITY;
    }

    /**
     * Gets the X-axis offset for the projectile's initial position.
     *
     * @return the X-axis offset for projectiles.
     */
    public double getProjectileXPositionOffset() {
        return PROJECTILE_X_POSITION_OFFSET;
    }

    /**
     * Sets the X-axis offset for the projectile's initial position.
     *
     * @param PROJECTILE_X_POSITION_OFFSET the X-axis offset for projectiles.
     */
    public void setProjectileXPositionOffset(double PROJECTILE_X_POSITION_OFFSET) {
        this.PROJECTILE_X_POSITION_OFFSET = PROJECTILE_X_POSITION_OFFSET;
    }

    /**
     * Gets the Y-axis offset for the projectile's initial position.
     *
     * @return the Y-axis offset for projectiles.
     */
    public double getProjectileYPositionOffset() {
        return PROJECTILE_Y_POSITION_OFFSET;
    }

    /**
     * Sets the Y-axis offset for the projectile's initial position.
     *
     * @param PROJECTILE_Y_POSITION_OFFSET the Y-axis offset for projectiles.
     */
    public void setProjectileYPositionOffset(double PROJECTILE_Y_POSITION_OFFSET) {
        this.PROJECTILE_Y_POSITION_OFFSET = PROJECTILE_Y_POSITION_OFFSET;
    }

    /**
     * Gets the fire rate of the enemy plane's projectiles.
     *
     * @return the fire rate.
     */
    public double getFireRate() {
        return FIRE_RATE;
    }

    /**
     * Sets the fire rate of the enemy plane's projectiles.
     *
     * @param FIRE_RATE the fire rate.
     */
    public void setFireRate(double FIRE_RATE) {
        this.FIRE_RATE = FIRE_RATE;
    }
}