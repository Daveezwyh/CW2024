package com.example.demo.actor;

/**
 * Represents a fighter plane that can take damage, repair itself, and fire projectiles.
 * This is an abstract class meant to be extended by specific types of fighter planes.
 */
public abstract class FighterPlane extends ActiveActorDestructible {

	private int health;

	/**
	 * Constructs a FighterPlane with the specified attributes.
	 *
	 * @param imageName    the name of the image file representing the fighter plane.
	 * @param imageHeight  the height of the image.
	 * @param initialXPos  the initial X-coordinate of the fighter plane.
	 * @param initialYPos  the initial Y-coordinate of the fighter plane.
	 * @param health       the initial health of the fighter plane.
	 */
	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Fires a projectile from the fighter plane.
	 * This method must be implemented by subclasses.
	 *
	 * @return the projectile fired by the fighter plane.
	 */
	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * Decreases the fighter plane's health by 1.
	 * If the health reaches zero, the plane is destroyed.
	 */
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	/**
	 * Repairs the fighter plane by increasing its health by 1.
	 */
	@Override
	public void repairDamage() {
		health++;
	}

	/**
	 * Returns the current health of the fighter plane.
	 *
	 * @return the current health of the plane.
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Calculates the X-coordinate for firing a projectile, based on a specified offset.
	 *
	 * @param xPositionOffset the offset for the projectile's X-coordinate.
	 * @return the calculated X-coordinate for the projectile.
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Calculates the Y-coordinate for firing a projectile, based on a specified offset.
	 *
	 * @param yPositionOffset the offset for the projectile's Y-coordinate.
	 * @return the calculated Y-coordinate for the projectile.
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Checks whether the health of the fighter plane has reached zero.
	 *
	 * @return {@code true} if health is zero, otherwise {@code false}.
	 */
	private boolean healthAtZero() {
		return health == 0;
	}
}