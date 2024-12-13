# Course Work 2024
Wong Yong Han
20604853

## GitHub
[Github Link](https://github.com/Daveezwyh/CW2024)
https://github.com/Daveezwyh/CW2024

## Compilation Instructions

> Java Development Kit Version 19

> Apache Maven 3.6+

## Implemented and Working Properly
**Game Score**

This feature calculates the game score for each game play. The game score accumulates through each level, upon game finished you will be presented your game score.

The game score is calculated such that, the nearer you are to the enemy spawn area the higher score you get. The rationale here is that, it is relatively more difficult if you stay closer to the enemy spawn area giving you lesser time to react and manoeuvre, hence higher score is given.

The score calculation logic can be found in **GameScoreCalculator.java**. A simple overview is as follows:

>     (1 Hit, 1 Score) + Score Factor
*Score Factor:*

> Normalised user's x-axis position and give a value between 0 to 4.

**Health Point Restoration**

This feature spawns health points randomly in the game screen where is *Reachable* by user. This feature is not available in all levels, by the design of the game, it is only implemented in Level Two and Level Three.

When health points is spawned, user must navigate the plane to hit the health point to restore 1 health point.

When the health point is spawned, it will not be remained in the game screen indefinitely as it will only be available for 5 seconds before it disappear, so be opportunistic to grab the health points.
 
As this class extends from **TransientActiveActorDestructible.java**, it has all the logic to determine it's expiration time. When health point is spawned, it is registered with a Unix Epoch timestamp, and for every game loop, game check timestamp to see expiration and remove from the game.

The lower the User's health the more likely this object will be spawned:

    private void spawnHealthPoints() {  
        UserPlane user = getUser();  
        int currentHealth = user.getHealth();  
        double adjustedProbability = (double) (PLAYER_INITIAL_HEALTH - currentHealth) / PLAYER_INITIAL_HEALTH * HP_SPAWN_PROBABILITY;  
      
        if (currentHealth < PLAYER_INITIAL_HEALTH && Math.random() < adjustedProbability) {  
            HealthPoint healthPoint = new HealthPoint(user, HP_LINGER_SEC);  
            addHealthPoint(healthPoint);  
        }  
    }

**Boss Fire Deactivation**

This feature is only available in Level Boss. Same as **HealthPoint.java**, it extends from **TransientActiveActorDestructible.java**. A "Boss Fire Deactivator" object will spawn randomly in the level and upon collision with User's plane, it deactivates Boss firing ability.

At any moment of game play time, only 1 Boss Fire Deactivator will be spawn:

    private void spawnFireDeactivator() {  
        if (  
              !boss.getIsFireDeactivated() &&  
                    getFireDeactivators().isEmpty() &&  
                    Math.random() < FIREDEAC_SPAWN_PROBABILITY  
      ) {  
           FireDeactivator fireDeactivator = new FireDeactivator(getUser(), FIREDEAC_LINGER_SEC);  
           addFireDeactivator(fireDeactivator);  
        }  
    }


**Game Level**

From the original source code, the game only has 2 levels, LevelOne and LevelBoss, it is now extended to 4 levels: LevelOne, LevelTwo, LevelThree and LevelBoss. The following is a simple overview of each level:

*Level One:*

> Kill 50 enemy to advance, consists of all small planes with 1 HP.

*Level Two:*

> Kill 50 enemy to advance, consists of a mix of small planes(1 HP) and
> big planes(5 HP).

*Level Three:*

> Kill 30 enemy to advance, consists of all big planes with 5 HP.

*Level Boss:*

> Kill 1 Boss to win the game, Boss has 100 HP and may activate shield
> to deny damage.


For the levels that has a mix of different enemy plane variation, it is refactored to **EnemyPlaneMutator.java**. When this class is pass to the constructor of **EnemyPlane.java**, it configures the enemy plane giving it variation.

Originally the source hard codes advancement of each level in the the Level class, it is being refactored for **LevelSelector.java** to make level selection logic.

Each level is also being configured and tested such that the game play is not too easy nor too difficult.

**Game Pause**

Game pause feature is implemented to let user pause the game play. The game listens to "ESC" key event and upon triggering stop the game play timeline and show the Pause Scene.

    private void handleKeyPress(KeyEvent e) {  
        if (isPaused) {  
           if (e.getCode() == KeyCode.ESCAPE) {  
              togglePauseGame();  
           }  
           return;  
        }  
        KeyCode kc = e.getCode();  
        if (kc == KeyCode.UP) user.moveUp();  
        if (kc == KeyCode.DOWN) user.moveDown();  
        if (kc == KeyCode.LEFT) user.moveLeft();  
        if (kc == KeyCode.RIGHT) user.moveRight();  
        if (kc == KeyCode.SPACE && !isFiring) startFiring();  
        if (kc == KeyCode.ESCAPE) togglePauseGame();  
    }
    //
    public void togglePauseGame() {  
	    if (gameLoop.isRunning()) {  
	       gameLoop.pause();  
	       if (firingTimeline != null && firingTimeline.getStatus() == Animation.Status.RUNNING) {  
	          firingTimeline.pause();  
	       }  
	       levelView.showPauseOverlay();  
	    } else {  
	       gameLoop.resume();  
	       levelView.hidePauseOverlay();  
	    }  
	}

**Game Info**

Game Info is a Scene to show game play information, game logic, mechanism etc. It is being controlled by **GameInfoController.java**

**User Plane Move Direction**

In the original source code, user is only able to move vertically. Horizontal movement is now available as well.

**Background Animation**

Animate the game background to give a motion effect.

**FXML and CSS**

Utilised Scene Builder for UI creation with CSS stylings.

**Debugging Hitbox**

This functionality provides a visible red rectangular box which encase the border boundaries of an **ActiveActorDestructible.java** class. This is not a game feature, but provides easier debugging process during development.

    public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {
    	private final boolean isBoundingBoxVisible = true;
    }

## Features Not Implemented
**Back Button in Pause Screen**

The "Back to Main Menu" button is currently not implemented due to time constraint and relatively higher complexity. The game play timeline is being controlled by **LevelParent.java** which composes of **LevelView.java**, and the Back button is in **LevelView.java**. When Back button is clicked, the clicked event needs to propagate up to **LevelParent.java** to handle game pause logic.

## New Java Classes
**EnemyPlaneMutator.java**

This class is used as a mutator to **EnemyPlane.java** to make variation of enemy plane. Construct this class and pass it to the constructor of **EnemyPlane.java**

    EnemyPlaneMutator enemyPlaneMutator = new EnemyPlaneMutator();  
    enemyPlaneMutator.setImageHeight(100);  
    enemyPlaneMutator.setProjectileYPositionOffset(40);  
    enemyPlaneMutator.setInitialHealth(5);  
    enemyPlane = new EnemyPlane(initialXPos, initialYPos, enemyPlaneMutator);

**FireDeactivator.java**

Represents a fire deactivator in the game. The fire deactivator is a transient active actor that appears for a limited time and deactivates the boss's fire when collided with the user's plane. Only available in Boss Level.

**HealthPoint.java**

Represents a health point object in the game. The health point appears randomly within specified bounds and can be collected to restore health.

**TransientActiveActorDestructible.java**

An abstract class representing a transient active actor in the game. This type of actor has a limited lifespan and is automatically removed after a specified duration. On construction a timestamp is registered with class and will be used in expiry calculation.

    public boolean isExpired(long currentTimeStampSecond) {  
        return (currentTimeStampSecond - this.getCreatedTimeStamp()) > lingerTimeSecond;  
    }

**EnemyVariation.java**

Level which consists of enemy plane variation may implement this interface which has an implementation menthod makeEnemyPlane(). LevelTwo has this implementation because LevelTwo has mix of small planes and big planes.

    public class LevelTwo extends LevelParent implements EnemyVariation
    {
	    @Override  
		public EnemyPlane makeEnemyPlane(double initialXPos, double initialYPos, int type) 
		{}
	}

**GameInfoController.java**

This controller class for the game information view. Responsible for managing the background image and navigation back to the main menu.

**GameOverController.java**

This controller class for the Game Over screen. Handles displaying the game over message, score, and navigation options.

**LevelNotification.java**

Represents a notification for the level system. It contains information about the level name and the next action to be taken in the game. It has 3 possible cases:
|Case|Description|
|--|--|
|LevelNotification.Action.NEXT_LEVEL|Advance to next level|
|LevelNotification.Action.WIN_GAME|Win the Game|
|LevelNotification.Action.LOSE_GAME|Lose the Game|

This class is used to encapsulate information to pass from the Observable to the Observer.

*Advance to next level:*

    notifyObservers(new LevelNotification(  
           levelName,  
           LevelNotification.Action.NEXT_LEVEL  
    ));

*Win the Game:*

    notifyObservers(new LevelNotification("", LevelNotification.Action.WIN_GAME));
   
   *Lose the Game:*

    notifyObservers(new LevelNotification("", LevelNotification.Action.LOSE_GAME));

*Handled by Observer:*

    @Override  
    public void update(Observable arg0, Object arg1) {  
        if (arg1 instanceof LevelNotification notification) {  
           try {  
              LevelNotification.Action levelNotificationAction = notification.nextAction();  
              switch (levelNotificationAction) {  
                 case NEXT_LEVEL -> goToLevel(notification.levelName());  
                 case WIN_GAME, LOSE_GAME -> handleWinOrLoseGame(levelNotificationAction);  
              }  
           } catch (Exception e) {  
              showError(e);  
           }  
        } else {  
           showError(new Exception("Unexpected notification type received."));  
        }  
    }

**LevelSelector.java**

This is a utility class for managing level selection in the game. The levels are constructed by their class name and this class ensure the game levels are in correct order. The game levels are stored as Strings in an ordered List.

    private static final List<String> LEVELS = List.of(  
	    PACKAGE_NAME + ".LevelOne",  
	    PACKAGE_NAME + ".LevelTwo",  
	    PACKAGE_NAME + ".LevelThree",  
	    PACKAGE_NAME + ".LevelBoss"  
	);
*Get the first level:*

    public static String getFirstLevel() {  
        return LEVELS.get(0);  
    }
   *Get the next level:*
 

    public String getNextLevel() {  
        if (currentLevelIndex < LEVELS.size() - 1) {  
            return LEVELS.get(++currentLevelIndex);  
        } else {  
            return "";  
        }  
    }

For *Testing* purpose, if you are lazy to play through the game levels, simply comment out the list until your desired level. Example, to test boss level directly, simply do the following and compile:

    public class LevelSelector {  
	  	List<String> LEVELS = List.of(  
		    //PACKAGE_NAME + ".LevelOne",  
		    //PACKAGE_NAME + ".LevelTwo",  
		    //PACKAGE_NAME + ".LevelThree",  
		    PACKAGE_NAME + ".LevelBoss"
	  	);
    }

**LevelTwo.java**

This class represents the second level of the game. It introduces varying enemy types and health point mechanics.

**LevelThree.java**

This class represents the third level of the game. It introduces more challenging enemy configurations and health point mechanics.

**GameScoreCounter.java**

This is a view object which represents a game score counter displayed on the game screen.

**KillCounter.java**

This is a view object which represents a kill counter displayed on the game screen.

**PauseOverlay.java**

This class represents a pause overlay displayed during the paused state of the game. The overlay consists of a semi-transparent background and a "PAUSED" message.

**GameLoop.java**

Game Loop singleton instance that will be used in LevelParent. Can control the game loop state like start, pause or stop. Use in **LevelParent.java** to control the JavaFX Timeline.

**CollisionHandler.java**

This class encapsulates methods responsible for handling collision logic for various kind of **ActiveActorDestructible.java**. It is refactored into this class for better management.

**GameScore.java**

This class represents a game score with functionality to track, increment, and reset the score.

**GameScoreCalculator.java**

This class is responsible for game score calculation logic. The reason why this class is not in **GameScore.java** is to allow portability, as it is used in **CollisionHandler.java** to calculate game score upon successful user projectiles collisions with enemy units.

    public static int handleUserProjectileCollisions(  
            UserPlane userPlane,  
            List<ActiveActorDestructible> userProjectiles,  
            List<ActiveActorDestructible> enemyUnits  
    ) {  
        int scoreIndex = 0;  
      
        for (ActiveActorDestructible userProjectile : userProjectiles) {  
            for (ActiveActorDestructible enemyUnit : enemyUnits) {  
                if (userProjectile.getBoundsInParent().intersects(enemyUnit.getBoundsInParent())) {  
                    userProjectile.takeDamage();  
                    enemyUnit.takeDamage();  
                    scoreIndex += GameScoreCalculator.calculateUserScoreByPosition(userPlane);  
                }  
            }  
        }  
      
        return scoreIndex;  
    }

## Modified Java Classes
**ActiveActorDestructible.java**

This represents an abstract destructible actor in the game. A new attribute being added which is:

    private boolean shouldRemove;

The reason for this new attribute is to identify between actors who left the game screen and actors who are destroyed by collisions.

In every game loop the system check for actors and their "isDestroyed" attribute, those destroyed actors are removed from the game. In the original source code, enemy units who left the game screen as well as collided with other actors are all being handled by enemy.destroy(). This causes the two cases to be indistinguishable which affects the kill count.

Therefore new logic:
|Case|method|
|--|--|
|Enemy left the game screen without being shotdown|enemy.remove();|
|Enemy being shot down or collided with other actors|enemy.destroy();|

**EnemyPlane.java**

The constructor is being modified to allow consumption of **EnemyPlaneMutator.java** to make variation of enemy plane.

*Method Overloading:*

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

## Bug Fixes
**Observer not handle properly after each level**

In the original source code, the Observer are not handled properly after each game level progression. It has been fixed by cleaning up Observer when moving to next level.

    public void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,  
           InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {  
        if (currentLevel != null) {  
           currentLevel.stopGame();  
           cleanUp();  
        }  
      
        Class<?> myClass = Class.forName(className);  
        Constructor<?> constructor = myClass.getConstructor(double.class, double.class);  
        currentLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());  
        currentLevel.addObserver(this);  
        currentLevel.addGameScore(gameScore);  
      
        Scene scene = currentLevel.initializeScene();  
        stage.setScene(scene);  
      
        currentLevel.startGame();  
    }
    private void cleanUp() {  
        currentLevel.deleteObserver(this);  
        currentLevel = null;  
        stage.setScene(null);  
    }

**ActiveActorDestructible not removed when leave Screen**

In the original source code, projectiles which left the game screen are not removed from memory, resulting in inefficient memory usage. Hence a new method is implemented:

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

**Unable to fire continuously while moving**

Previously, the game loop simply listens for key press events for navigation and firing, making it single-functional resulting in user unable to hold down the space bar for continuous firing while moving.

The solution to this problem is to introduce a new firing timeline which runs in another thread not interfering with the movement handler.

    private void handleKeyPress(KeyEvent e) {  
        if (isPaused) {  
           if (e.getCode() == KeyCode.ESCAPE) {  
              togglePauseGame();  
           }  
           return;  
        }  
        KeyCode kc = e.getCode();  
        if (kc == KeyCode.UP) user.moveUp();  
        if (kc == KeyCode.DOWN) user.moveDown();  
        if (kc == KeyCode.LEFT) user.moveLeft();  
        if (kc == KeyCode.RIGHT) user.moveRight();  
        if (kc == KeyCode.SPACE && !isFiring) startFiring();  
        if (kc == KeyCode.ESCAPE) togglePauseGame();  
    }
    //
    private void startFiring() {  
        isFiring = true;  
        firingTimeline = new Timeline(new KeyFrame(Duration.millis(400), e -> fireProjectile()));  
        firingTimeline.setCycleCount(Timeline.INDEFINITE);  
        firingTimeline.play();  
    } 

and many more bug fixes...

## Unexpected Problems
**JavaFX Graphics Problem with Junit Test**

 When writing Junit tests in this project, many of the game logic are coupled with the JavaFX graphics. There has been some problems initialising JavaFX graphic threads when running the Junit test.
 
 Solution to this is to work around using an external package name "Mockito", which does not initialised the JavaFX graphics but simply creates a mock class for testing the game logic.

*Package Name:*

> org.mockito.Mockito

