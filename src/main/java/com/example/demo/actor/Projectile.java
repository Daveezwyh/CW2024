package com.example.demo.actor;

/**
 * Represents a projectile in the game.
 * Projectiles are destructible and follow specific movement patterns.
 */
public abstract class Projectile extends ActiveActorDestructible {

	/**
	 * Constructs a projectile with the specified image and initial position.
	 *
	 * @param imageName    the name of the image file for the projectile.
	 * @param imageHeight  the height of the image.
	 * @param initialXPos  the initial X position of the projectile.
	 * @param initialYPos  the initial Y position of the projectile.
	 */
	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * Handles the damage taken by the projectile.
	 * The projectile is destroyed upon taking damage.
	 */
	@Override
	public void takeDamage() {
		this.destroy();
	}

	/**
	 * Repairs the projectile.
	 * No implementation is needed as projectiles cannot be repaired.
	 */
	@Override
	public void repairDamage() {}

	/**
	 * Updates the position of the projectile.
	 * This method must be implemented by concrete subclasses to define the projectile's movement.
	 */
	@Override
	public abstract void updatePosition();
}