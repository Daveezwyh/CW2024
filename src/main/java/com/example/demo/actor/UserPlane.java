package com.example.demo.actor;

/**
 * Represents the player's plane in the game. The plane can move, fire projectiles,
 * and track the number of enemy kills.
 */
public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = 20;
	private static final double Y_LOWER_BOUND = 650.0;
	private static final double X_UPPER_BOUND = 0;
	private static final double X_LOWER_BOUND = 600;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 40;
	private static final int VERTICAL_VELOCITY = 13;
	private static final int HORIZONTAL_VELOCITY = 13;
	private static final int PROJECTILE_X_POSITION_OFFSET = 60;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 10;

	private int verticalVelocityMultiplier;
	private int horizontalVelocityMultiplier;
	private int numberOfKills;

	/**
	 * Constructs a user plane with the specified initial health.
	 *
	 * @param initialHealth the initial health of the plane.
	 */
	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		verticalVelocityMultiplier = 0;
	}

	/**
	 * Updates the position of the plane based on its current movement state.
	 * Ensures the plane does not move out of bounds.
	 */
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

	/**
	 * Updates the state of the plane.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Fires a projectile from the plane.
	 *
	 * @return a new instance of {@code UserProjectile}.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return new UserProjectile(
				getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET),
				getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET)
		);
	}

	/**
	 * Determines if the plane is currently moving in the specified direction.
	 *
	 * @param direction the direction to check ("vertical", "horizontal", or "any").
	 * @return {@code true} if the plane is moving in the specified direction; {@code false} otherwise.
	 */
	private boolean isMoving(String direction) {
		return switch (direction.toLowerCase()) {
			case "vertical" -> verticalVelocityMultiplier != 0;
			case "horizontal" -> horizontalVelocityMultiplier != 0;
			case "any" -> verticalVelocityMultiplier != 0 || horizontalVelocityMultiplier != 0;
			default -> throw new IllegalArgumentException("Invalid direction: " + direction);
		};
	}

	/**
	 * Moves the plane upward.
	 */
	public void moveUp() {
		verticalVelocityMultiplier = -1;
	}

	/**
	 * Moves the plane downward.
	 */
	public void moveDown() {
		verticalVelocityMultiplier = 1;
	}

	/**
	 * Moves the plane to the left.
	 */
	public void moveLeft() {
		horizontalVelocityMultiplier = -1;
	}

	/**
	 * Moves the plane to the right.
	 */
	public void moveRight() {
		horizontalVelocityMultiplier = 1;
	}

	/**
	 * Stops all movement of the plane.
	 */
	public void stop() {
		verticalVelocityMultiplier = 0;
		horizontalVelocityMultiplier = 0;
	}

	/**
	 * Gets the number of kills made by the plane.
	 *
	 * @return the number of kills.
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * Increments the kill count by one.
	 */
	public void incrementKillCount() {
		numberOfKills++;
	}

	/**
	 * Gets the upper bound for the X-axis movement.
	 *
	 * @return the upper bound for X-axis movement.
	 */
	public double getXUpperBound() {
		return X_UPPER_BOUND;
	}

	/**
	 * Gets the lower bound for the X-axis movement.
	 *
	 * @return the lower bound for X-axis movement.
	 */
	public double getXLowerBound() {
		return X_LOWER_BOUND;
	}

	/**
	 * Gets the upper bound for the Y-axis movement.
	 *
	 * @return the upper bound for Y-axis movement.
	 */
	public double getYUpperBound() {
		return Y_UPPER_BOUND;
	}

	/**
	 * Gets the lower bound for the Y-axis movement.
	 *
	 * @return the lower bound for Y-axis movement.
	 */
	public double getYLowerBound() {
		return Y_LOWER_BOUND;
	}
}