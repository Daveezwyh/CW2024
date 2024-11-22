package com.example.demo.level;

import java.util.*;
import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.FighterPlane;
import com.example.demo.actor.HealthPoint;
import com.example.demo.actor.UserPlane;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.util.Duration;
import javafx.scene.shape.Rectangle;

public abstract class LevelParent extends Observable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;
	private final Group root;
	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;
	protected final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;
	protected final List<ActiveActorDestructible> healthPoints;
	private int currentNumberOfEnemies;
	protected LevelView levelView;
	private boolean isFiring = false;
	private Timeline firingTimeline;
	private final int HP_LINGER_SEC = 5;
	private final double backgroundScrollSpeed = 2.0;
	private double backgroundPosition = 0;
	private boolean isPaused = false;

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

		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		initializeTimeline();
		friendlyUnits.add(user);
	}

	protected abstract void initializeFriendlyUnits();

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();
	protected abstract void spawnHealthPoints();
	protected void destroyExpiredHealthPoints(){
		long currentTimeSec = System.currentTimeMillis() / 1000;

		healthPoints.forEach(healthPoint -> {
			if ((currentTimeSec - healthPoint.getCreatedTimeStamp()) > HP_LINGER_SEC) {
				healthPoint.destroy();
			}
		});
	}
	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay()
				.showKillCounter();
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	public void stopGame() {
		timeline.stop();
	}

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

	public void goToNextLevel(String levelName) {
		setChanged();
		notifyObservers(new LevelNotification(
				levelName,
				LevelNotification.Action.NEXT_LEVEL
		));
	}

	private void updateScene() {
		spawnEnemyUnits();
		spawnHealthPoints();
		updateActors();
		generateEnemyFire();
		updateNumberOfEnemies();
		handleEnemyPenetration();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		handleUserHealthPointCollisions();
		destroyExpiredHealthPoints();
		removeAllDestroyedActors();
		removeOffScreenProjectiles();
		updateLevelView();
		animateBackground();
		checkIfGameOver();
	}

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				if (isPaused) {
					if (e.getCode() == KeyCode.ESCAPE) {
						pauseGame(); // Allow only pause/resume during pause
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
		});
		background.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				if (isPaused) return;
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP || kc == KeyCode.DOWN || kc == KeyCode.LEFT || kc == KeyCode.RIGHT){
					user.stop();
				}
				if (kc == KeyCode.SPACE) {
					fireProjectile();
					stopFiring();
				}
			}
		});
		root.getChildren().add(0, background);

		ImageView backgroundCopy = new ImageView(background.getImage());
		backgroundCopy.setFitHeight(screenHeight);
		backgroundCopy.setFitWidth(screenWidth);
		backgroundCopy.setTranslateX(screenWidth);
		root.getChildren().add(1, backgroundCopy);
	}
	private void startFiring() {
		isFiring = true;

		firingTimeline = new Timeline(new KeyFrame(Duration.millis(400), e -> fireProjectile()));
		firingTimeline.setCycleCount(Timeline.INDEFINITE);
		firingTimeline.play();
	}
	private void stopFiring() {
		if (firingTimeline != null) {
			firingTimeline.stop();
		}
		isFiring = false;
	}
	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		if(projectile.isBoundingBoxVisible()){
			root.getChildren().add(projectile.getBoundingBox());
		}
		userProjectiles.add(projectile);
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			if(projectile.isBoundingBoxVisible()){
				root.getChildren().add(projectile.getBoundingBox());
			}
			enemyProjectiles.add(projectile);
		}
	}

	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}

	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
		removeDestroyedActors(healthPoints);
	}

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

	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

	private void handleUserProjectileCollisions() {
		handleCollisions(userProjectiles, enemyUnits);
	}

	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

	private void handleUserHealthPointCollisions() {
		handleCollisions(friendlyUnits, healthPoints);
	}
	private void handleCollisions(List<ActiveActorDestructible> actors1,
								  List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors1) {
			for (ActiveActorDestructible otherActor : actors2) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					if(actors1 == friendlyUnits && actors2 == enemyUnits){
						actor.takeDamage();
						otherActor.destroy();
					}else if(actors1 == friendlyUnits && actors2 == healthPoints){
						repairUserDamage(actor);
						otherActor.takeDamage();
					}else{
						actor.takeDamage();
						otherActor.takeDamage();
					}
				}
			}
		}
	}

	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.remove();
			}
		}
	}

	protected abstract void repairUserDamage(ActiveActorDestructible userPlane);

	protected void updateLevelView() {
		levelView.updateHearts(user.getHealth())
				.updateKillCount(user.getNumberOfKills());
	}

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	protected void winGame() {
		stopGame();
		//levelView.showWinImage();
		setChanged();
		notifyObservers(new LevelNotification(
				"",
				LevelNotification.Action.WIN_GAME
		));
	}

	protected void loseGame() {
		stopGame();
		//levelView.showGameOverImage();
		setChanged();
		notifyObservers(new LevelNotification(
				"",
				LevelNotification.Action.LOSE_GAME
		));
	}

	protected UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
		if(enemy.isBoundingBoxVisible()){
			root.getChildren().add(enemy.getBoundingBox());
		}
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}
	protected void animateBackground() {
		backgroundPosition -= backgroundScrollSpeed;

		if (backgroundPosition <= -screenWidth) {
			backgroundPosition = 0;
		}

		background.setTranslateX(backgroundPosition);
		root.getChildren().get(1).setTranslateX(backgroundPosition + screenWidth);
	}
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
}