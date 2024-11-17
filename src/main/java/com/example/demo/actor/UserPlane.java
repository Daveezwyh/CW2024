package com.example.demo.actor;

public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = -20;
	private static final double Y_LOWER_BOUND = 600.0;
	private static final double X_UPPER_BOUND = 0;
	private static final double X_LOWER_BOUND = 600;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 100;
	private static final int VERTICAL_VELOCITY = 12;
	private static final int HORIZONTAL_VELOCITY = 8;
	private static final int PROJECTILE_X_POSITION_OFFSET = 20;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	private int verticalVelocityMultiplier;
	private int horizontalVelocityMultiplier;
	private int numberOfKills;

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		verticalVelocityMultiplier = 0;
	}

	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		double initialTranslateX = getTranslateX();

		if (isMoving("vertical")) {
			this.moveVertically(VERTICAL_VELOCITY * verticalVelocityMultiplier);
			double newPositionY = getLayoutY() + getTranslateY();
			if (newPositionY < Y_UPPER_BOUND || newPositionY > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		} else if (isMoving("horizontal")) {
			this.moveHorizontally(HORIZONTAL_VELOCITY * horizontalVelocityMultiplier);
			double newPositionX = getLayoutX() + getTranslateX();
			if (newPositionX < X_UPPER_BOUND || newPositionX > X_LOWER_BOUND) {
				this.setTranslateX(initialTranslateX);
			}
		}

		updateBoundingBox();
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		return new UserProjectile(getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET), getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	private boolean isMoving(String direction) {
		switch (direction.toLowerCase()) {
			case "vertical":
				return verticalVelocityMultiplier != 0;
			case "horizontal":
				return horizontalVelocityMultiplier != 0;
			case "any":
				return verticalVelocityMultiplier != 0 || horizontalVelocityMultiplier != 0;
			default:
				throw new IllegalArgumentException("Invalid direction: " + direction);
		}
	}

	public void moveUp() { verticalVelocityMultiplier = -1; }

	public void moveDown() {
		verticalVelocityMultiplier = 1;
	}

	public void moveLeft(){ horizontalVelocityMultiplier = -1; }

	public void moveRight(){ horizontalVelocityMultiplier = 1; }

	public void stop() {
		verticalVelocityMultiplier = 0;
		horizontalVelocityMultiplier = 0;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}

}