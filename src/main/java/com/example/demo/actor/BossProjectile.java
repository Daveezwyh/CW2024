package com.example.demo.actor;

/**
 * Represents a projectile fired by the Boss character in the game.
 * The projectile moves horizontally with a predefined velocity and updates its position accordingly.
 */
public class BossProjectile extends Projectile {

	/** The image file name for the Boss projectile. */
	private static final String IMAGE_NAME = "fireball.png";

	/** The height of the projectile's image. */
	private static final int IMAGE_HEIGHT = 50;

	/** The horizontal velocity of the projectile. */
	private static final int HORIZONTAL_VELOCITY = -15;

	/** The initial X position of the projectile. */
	private static final int INITIAL_X_POSITION = 950;

	/**
	 * Constructs a new BossProjectile at a specified Y position.
	 *
	 * @param initialYPos the initial Y position of the projectile
	 */
	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
	}

	/**
	 * Updates the position of the projectile by moving it horizontally and updating its bounding box.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
		updateBoundingBox();
	}

	/**
	 * Updates the state of the projectile, including its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}