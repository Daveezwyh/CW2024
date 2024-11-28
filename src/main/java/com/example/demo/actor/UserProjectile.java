package com.example.demo.actor;

/**
 * Represents a projectile fired by the user's plane.
 * The projectile moves horizontally across the screen.
 */
public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = 15;

	/**
	 * Constructs a new UserProjectile with the specified initial positions.
	 *
	 * @param initialXPos the initial X position of the projectile.
	 * @param initialYPos the initial Y position of the projectile.
	 */
	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the projectile, moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
		updateBoundingBox();
	}

	/**
	 * Updates the state of the projectile by updating its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}