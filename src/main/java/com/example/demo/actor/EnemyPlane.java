package com.example.demo.actor;

/**
 * Represents an enemy plane in the game, which moves horizontally and can fire projectiles at a specified rate.
 */
public class EnemyPlane extends FighterPlane {

	private final int HORIZONTAL_VELOCITY;
	private final double PROJECTILE_X_POSITION_OFFSET;
	private final double PROJECTILE_Y_POSITION_OFFSET;
	private final double FIRE_RATE;

	/**
	 * Constructs an {@code EnemyPlane} at the specified initial position with default settings.
	 *
	 * @param initialXPos the initial X-coordinate of the enemy plane.
	 * @param initialYPos the initial Y-coordinate of the enemy plane.
	 */
	public EnemyPlane(double initialXPos, double initialYPos) {
		this(initialXPos, initialYPos, new EnemyPlaneMutator());
	}

	/**
	 * Constructs an {@code EnemyPlane} at the specified initial position with settings provided by an {@code EnemyPlaneMutator}.
	 *
	 * @param initialXPos         the initial X-coordinate of the enemy plane.
	 * @param initialYPos         the initial Y-coordinate of the enemy plane.
	 * @param enemyPlaneMutator   an {@code EnemyPlaneMutator} providing the configuration for the enemy plane.
	 */
	public EnemyPlane(double initialXPos, double initialYPos, EnemyPlaneMutator enemyPlaneMutator) {
		super(
				enemyPlaneMutator.getImageName(),
				enemyPlaneMutator.getImageHeight(),
				initialXPos,
				initialYPos,
				enemyPlaneMutator.getInitialHealth()
		);
		this.HORIZONTAL_VELOCITY = enemyPlaneMutator.getHorizontalVelocity();
		this.PROJECTILE_X_POSITION_OFFSET = enemyPlaneMutator.getProjectileXPositionOffset();
		this.PROJECTILE_Y_POSITION_OFFSET = enemyPlaneMutator.getProjectileYPositionOffset();
		this.FIRE_RATE = enemyPlaneMutator.getFireRate();
	}

	/**
	 * Updates the position of the enemy plane by moving it horizontally and updating its bounding box.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
		updateBoundingBox();
	}

	/**
	 * Updates the enemy plane's state, including its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Fires a projectile if a random value is less than the firing rate.
	 *
	 * @return a new {@code EnemyProjectile} if the enemy fires, or {@code null} if it does not.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPostion);
		}
		return null;
	}
}