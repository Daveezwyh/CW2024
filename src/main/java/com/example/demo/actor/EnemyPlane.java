package com.example.demo.actor;

public class EnemyPlane extends FighterPlane {
	private final int HORIZONTAL_VELOCITY;
	private final double PROJECTILE_X_POSITION_OFFSET;
	private final double PROJECTILE_Y_POSITION_OFFSET;
	private final double FIRE_RATE;

	public EnemyPlane(double initialXPos, double initialYPos) {
		this(initialXPos, initialYPos, new EnemyPlaneMutator());
	}

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

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
		updateBoundingBox();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPostion);
		}
		return null;
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

}
