package com.example.demo.actor;

public class EnemyPlane extends FighterPlane {
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final double FIRE_RATE = .01;

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
