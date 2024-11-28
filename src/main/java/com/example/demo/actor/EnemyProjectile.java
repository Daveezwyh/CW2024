package com.example.demo.actor;

/**
 * Represents a projectile fired by an enemy plane.
 * This projectile moves horizontally across the screen.
 */
public class EnemyProjectile extends Projectile {

	private static final String IMAGE_NAME = "enemyFire.png";
	private static final int IMAGE_HEIGHT = 30;
	private static final int HORIZONTAL_VELOCITY = -10;

	/**
	 * Constructs an EnemyProjectile at the specified initial position.
	 *
	 * @param initialXPos the initial X-coordinate of the projectile.
	 * @param initialYPos the initial Y-coordinate of the projectile.
	 */
	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the projectile by moving it horizontally.
	 * Also updates the bounding box for collision detection.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
		updateBoundingBox();
	}

	/**
	 * Updates the state of the projectile.
	 * Calls {@link #updatePosition()} to adjust its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}