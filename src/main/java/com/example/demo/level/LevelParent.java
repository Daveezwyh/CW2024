package com.example.demo.level;

import java.util.*;
import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.FighterPlane;
import com.example.demo.actor.UserPlane;
import com.example.demo.util.CollisionHandler;
import com.example.demo.util.GameScore;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.util.Duration;
import javafx.scene.shape.Rectangle;

/**
 * Represents a parent class for levels in the game. Provides the framework and base functionality
 * for managing level-specific elements such as user plane, enemy planes, background animation,
 * health points, and scoring.
 *
 * <p>Concrete subclasses must implement methods for initializing friendly units, spawning
 * enemy units, checking game-over conditions, spawning health points, and instantiating the
 * level-specific view.</p>
 */
public abstract class LevelParent extends Observable {
	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;

	// Configurable constants
	private final int HP_LINGER_SEC = 5;
	private final double backgroundScrollSpeed = 2.0;

	// Final instance variables
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;
	private final Group root;
	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;
	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	protected final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;
	protected final List<ActiveActorDestructible> healthPoints;
	protected final LevelView levelView;

	// Non-final instance variables
	protected GameScore gameScore;
	private int currentNumberOfEnemies;
	private boolean isFiring = false;
	private Timeline firingTimeline;
	private double backgroundPosition = 0;
	private boolean isPaused = false;

	/**
	 * Constructs a new LevelParent instance with the specified configuration.
	 *
	 * @param backgroundImageName the path to the background image for the level.
	 * @param screenHeight        the height of the screen in pixels.
	 * @param screenWidth         the width of the screen in pixels.
	 * @param playerInitialHealth the initial health value for the player's character.
	 *
	 * @throws NullPointerException if the background image resource cannot be found.
	 */
	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();
		this.healthPoints = new ArrayList<>();

		this.background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		initializeTimeline();
		friendlyUnits.add(user);
	}

	/**
	 * Initializes the level's scene by setting up the background, friendly units, and user interface elements.
	 *
	 * @return the initialized {@link Scene} object for the level.
	 */
	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay()
				.showKillCounter()
				.showGameScoreCounter();
		return scene;
	}

	/**
	 * Starts the game by focusing on the background and starting the timeline animation.
	 */
	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	/**
	 * Stops the game by halting the timeline animation and unbinding any key listeners.
	 */
	public void stopGame() {
		timeline.stop();
		if (firingTimeline != null) {
			firingTimeline.stop();
		}
		unbindKeyListeners();
	}

	/**
	 * Toggles the game's paused state. If the game is running, it pauses the timeline and displays the pause overlay.
	 * If the game is paused, it resumes the timeline and hides the pause overlay.
	 */
	public void pauseGame() {
		if (timeline.getStatus() == Animation.Status.RUNNING) {
			isPaused = true;
			timeline.pause();
			if (firingTimeline != null && firingTimeline.getStatus() == Animation.Status.RUNNING) {
				firingTimeline.pause();
			}
			levelView.showPauseOverlay();
		} else {
			isPaused = false;
			timeline.play();
			if (firingTimeline != null) {
				firingTimeline.play();
			}
			levelView.hidePauseOverlay();
		}
	}

	/**
	 * Notifies observers to transition to the specified next level.
	 *
	 * @param levelName the name of the next level to transition to.
	 */
	public void goToNextLevel(String levelName) {
		setChanged();
		notifyObservers(new LevelNotification(
				levelName,
				LevelNotification.Action.NEXT_LEVEL
		));
	}

	/**
	 * Sets the game score tracker for the level.
	 *
	 * @param gameScore the {@link GameScore} object to be used for tracking the game's score.
	 */
	public void addGameScore(GameScore gameScore) {
		this.gameScore = gameScore;
	}

	/**
	 * Initializes the friendly units for the level. Must be implemented by subclasses.
	 */
	protected abstract void initializeFriendlyUnits();

	/**
	 * Checks whether the game has ended based on specific conditions. Must be implemented by subclasses.
	 */
	protected abstract void checkIfGameOver();

	/**
	 * Spawns enemy units in the level. Must be implemented by subclasses.
	 */
	protected abstract void spawnEnemyUnits();

	/**
	 * Spawns health points in the level. Must be implemented by subclasses.
	 */
	protected abstract void spawnHealthPoints();

	/**
	 * Instantiates the view object for the level. Must be implemented by subclasses.
	 *
	 * @return the {@link LevelView} object for the level.
	 */
	protected abstract LevelView instantiateLevelView();

	/**
	 * Removes health points that have lingered on the screen for longer than the configured duration.
	 */
	protected void destroyExpiredHealthPoints() {
		long currentTimeSec = System.currentTimeMillis() / 1000;
		healthPoints.forEach(healthPoint -> {
			if ((currentTimeSec - healthPoint.getCreatedTimeStamp()) > HP_LINGER_SEC) {
				healthPoint.destroy();
			}
		});
	}

	/**
	 * Updates the level's user interface elements to reflect the current game state.
	 */
	protected void updateLevelView() {
		levelView.updateHearts(user.getHealth())
				.updateKillCount(user.getNumberOfKills())
				.updateGameScore(gameScore);
	}

	/**
	 * Handles the win condition for the level by stopping the game, displaying a win overlay,
	 * and notifying observers to transition to the win state.
	 */
	protected void winGame() {
		stopGame();
		levelView.showWinImage();
		PauseTransition pause = new PauseTransition(Duration.seconds(1));
		pause.setOnFinished(event -> {
			setChanged();
			notifyObservers(new LevelNotification("", LevelNotification.Action.WIN_GAME));
		});
		pause.play();
	}

	/**
	 * Handles the lose condition for the level by stopping the game, displaying a game-over overlay,
	 * and notifying observers to transition to the lose state.
	 */
	protected void loseGame() {
		stopGame();
		levelView.showGameOverImage();
		PauseTransition pause = new PauseTransition(Duration.seconds(1));
		pause.setOnFinished(event -> {
			setChanged();
			notifyObservers(new LevelNotification("", LevelNotification.Action.LOSE_GAME));
		});
		pause.play();
	}

	/**
	 * Processes collisions that result in score increments.
	 */
	protected void handleScoreableCollisions() {
		int scoreIncrement = CollisionHandler.handleUserProjectileCollisions(user, userProjectiles, enemyUnits);
		gameScore.increaseScoreBy(scoreIncrement);
	}

	/**
	 * Handles collisions involving the user and health points.
	 * This method provides no default implementation and is intended to be overridden by subclasses.
	 */
	protected void handleUserHealthPointCollisions() {
		// No default implementation
	}

	/**
	 * Retrieves the user's plane in the level.
	 *
	 * @return the {@link UserPlane} object representing the user's plane.
	 */
	protected UserPlane getUser() {
		return user;
	}

	/**
	 * Retrieves the root node of the level's scene graph.
	 *
	 * @return the {@link Group} object representing the root node.
	 */
	protected Group getRoot() {
		return root;
	}

	/**
	 * Retrieves the current number of enemy units in the level.
	 *
	 * @return the number of enemy units.
	 */
	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	/**
	 * Retrieves the list of friendly units in the level.
	 *
	 * @return a list of {@link ActiveActorDestructible} objects representing friendly units.
	 */
	protected List<ActiveActorDestructible> getFriendlyUnits() {
		return friendlyUnits;
	}

	/**
	 * Retrieves the list of health points present in the level.
	 *
	 * @return a list of {@link ActiveActorDestructible} objects representing health points.
	 */
	protected List<ActiveActorDestructible> getHealthPoints() {
		return healthPoints;
	}

	/**
	 * Adds an enemy unit to the level and attaches it to the scene graph.
	 *
	 * @param enemy the {@link ActiveActorDestructible} object representing the enemy unit to add.
	 */
	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
		if (enemy.isBoundingBoxVisible()) {
			root.getChildren().add(enemy.getBoundingBox());
		}
	}

	/**
	 * Retrieves the maximum Y position an enemy can occupy in the level.
	 *
	 * @return the maximum Y coordinate for enemy units.
	 */
	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	/**
	 * Retrieves the width of the screen for the level.
	 *
	 * @return the screen width in pixels.
	 */
	protected double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Checks whether the user's plane is destroyed.
	 *
	 * @return {@code true} if the user's plane is destroyed; {@code false} otherwise.
	 */
	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	/**
	 * Updates the level by performing actions such as spawning enemies, updating actors, handling collisions,
	 * and checking game state conditions.
	 */
	private void updateScene() {
		spawnEnemyUnits();
		spawnHealthPoints();
		updateActors();
		generateEnemyFire();
		updateNumberOfEnemies();
		handleEnemyPenetration();
		handleGenericCollisions();
		handleUserHealthPointCollisions();
		destroyExpiredHealthPoints();
		removeAllDestroyedActors();
		removeOffScreenProjectiles();
		updateLevelView();
		animateBackground();
		checkIfGameOver();
	}

	/**
	 * Initializes the timeline for the game loop. The timeline runs indefinitely and updates the scene at a fixed interval.
	 */
	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	/**
	 * Sets up the level's background, including key press and release handlers.
	 * Adds a secondary background image to simulate a scrolling effect.
	 */
	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(this::handleKeyPress);
		background.setOnKeyReleased(this::handleKeyRelease);
		root.getChildren().add(0, background);

		ImageView backgroundCopy = new ImageView(background.getImage());
		backgroundCopy.setFitHeight(screenHeight);
		backgroundCopy.setFitWidth(screenWidth);
		backgroundCopy.setTranslateX(screenWidth);
		root.getChildren().add(1, backgroundCopy);
	}

	/**
	 * Unbinds key press and release listeners from the background to disable user input.
	 */
	private void unbindKeyListeners() {
		background.setOnKeyPressed(null);
		background.setOnKeyReleased(null);
	}

	/**
	 * Starts a timeline for firing projectiles continuously while the user holds the fire key.
	 */
	private void startFiring() {
		isFiring = true;
		firingTimeline = new Timeline(new KeyFrame(Duration.millis(400), e -> fireProjectile()));
		firingTimeline.setCycleCount(Timeline.INDEFINITE);
		firingTimeline.play();
	}

	/**
	 * Stops the timeline for firing projectiles when the user releases the fire key.
	 */
	private void stopFiring() {
		if (firingTimeline != null) {
			firingTimeline.stop();
		}
		isFiring = false;
	}

	/**
	 * Fires a projectile from the user's plane and adds it to the scene and tracking list.
	 */
	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		if (projectile.isBoundingBoxVisible()) {
			root.getChildren().add(projectile.getBoundingBox());
		}
		userProjectiles.add(projectile);
	}

	/**
	 * Iterates over enemy units and generates projectiles for those capable of firing.
	 */
	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	/**
	 * Adds a projectile fired by an enemy to the scene and tracking list.
	 *
	 * @param projectile the enemy's fired projectile to add.
	 */
	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			if (projectile.isBoundingBoxVisible()) {
				root.getChildren().add(projectile.getBoundingBox());
			}
			enemyProjectiles.add(projectile);
		}
	}

	/**
	 * Updates all actors in the game, including friendly units, enemies, and projectiles.
	 */
	private void updateActors() {
		friendlyUnits.forEach(ActiveActorDestructible::updateActor);
		enemyUnits.forEach(ActiveActorDestructible::updateActor);
		userProjectiles.forEach(ActiveActorDestructible::updateActor);
		enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
	}

	/**
	 * Removes all destroyed actors from the scene and their respective tracking lists.
	 */
	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
		removeDestroyedActors(healthPoints);
	}

	/**
	 * Removes actors that are marked as destroyed or should be removed from the scene and their tracking list.
	 *
	 * @param actors the list of actors to process.
	 */
	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream()
				.filter(actor -> actor.isDestroyed() || actor.getShouldRemove())
				.toList();
		for (ActiveActorDestructible actor : destroyedActors) {
			Rectangle boundingBox = actor.getBoundingBox();
			root.getChildren().removeAll(actor, boundingBox);
			if (actors == enemyUnits && actor.isDestroyed()) {
				user.incrementKillCount();
			}
		}
		actors.removeAll(destroyedActors);
	}

	/**
	 * Handles collisions between enemy projectiles and friendly units, as well as between planes.
	 */
	private void handleGenericCollisions() {
		CollisionHandler.handleEnemyProjectileCollisions(enemyProjectiles, friendlyUnits);
		CollisionHandler.handlePlaneCollisions(friendlyUnits, enemyUnits);
		handleScoreableCollisions();
	}

	/**
	 * Processes enemy penetration past the user's defenses. Damages the user and removes the offending enemy.
	 */
	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.remove();
			}
		}
	}

	/**
	 * Checks if an enemy unit has passed beyond the user's defensive boundary.
	 *
	 * @param enemy the enemy unit to check.
	 * @return {@code true} if the enemy has penetrated the defenses; {@code false} otherwise.
	 */
	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	/**
	 * Updates the count of currently active enemy units in the game.
	 */
	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

	/**
	 * Animates the background to create a scrolling effect.
	 */
	protected void animateBackground() {
		backgroundPosition -= backgroundScrollSpeed;
		if (backgroundPosition <= -screenWidth) {
			backgroundPosition = 0;
		}
		background.setTranslateX(backgroundPosition);
		root.getChildren().get(1).setTranslateX(backgroundPosition + screenWidth);
	}

	/**
	 * Removes projectiles that have moved off the screen from the scene and their respective tracking lists.
	 */
	private void removeOffScreenProjectiles() {
		userProjectiles.removeIf(projectile -> {
			boolean isOffScreen = projectile.getTranslateX() > screenWidth;
			if (isOffScreen) {
				root.getChildren().removeAll(projectile, projectile.getBoundingBox());
			}
			return isOffScreen;
		});
		enemyProjectiles.removeIf(projectile -> {
			boolean isOffScreen = projectile.getTranslateX() > screenWidth;
			if (isOffScreen) {
				root.getChildren().removeAll(projectile, projectile.getBoundingBox());
			}
			return isOffScreen;
		});
	}

	/**
	 * Handles user key presses for controlling the user's plane and firing projectiles.
	 *
	 * @param e the {@link KeyEvent} representing the key press.
	 */
	private void handleKeyPress(KeyEvent e) {
		if (isPaused) {
			if (e.getCode() == KeyCode.ESCAPE) {
				pauseGame();
			}
			return;
		}
		KeyCode kc = e.getCode();
		if (kc == KeyCode.UP) user.moveUp();
		if (kc == KeyCode.DOWN) user.moveDown();
		if (kc == KeyCode.LEFT) user.moveLeft();
		if (kc == KeyCode.RIGHT) user.moveRight();
		if (kc == KeyCode.SPACE && !isFiring) startFiring();
		if (kc == KeyCode.ESCAPE) pauseGame();
	}

	/**
	 * Handles user key releases to stop actions like movement or continuous firing.
	 *
	 * @param e the {@link KeyEvent} representing the key release.
	 */
	private void handleKeyRelease(KeyEvent e) {
		if (isPaused) return;
		KeyCode kc = e.getCode();
		if (kc == KeyCode.UP || kc == KeyCode.DOWN || kc == KeyCode.LEFT || kc == KeyCode.RIGHT) {
			user.stop();
		}
		if (kc == KeyCode.SPACE) {
			fireProjectile();
			stopFiring();
		}
	}
}