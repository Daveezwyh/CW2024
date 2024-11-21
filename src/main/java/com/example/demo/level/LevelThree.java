package com.example.demo.level;

import com.example.demo.actor.*;

public class LevelThree extends LevelParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
    private static final int TOTAL_ENEMIES = 3;
    private static final int KILLS_TO_ADVANCE = 20;
    private static final double ENEMY_SPAWN_PROBABILITY = .20;
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final double HP_SPAWN_PROBABILITY = 0.01;
    private final LevelSelector levelSelector;

    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        levelSelector = new LevelSelector(getClass().getName());
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
        else if (userHasReachedKillTarget())
            goToNextLevel(levelSelector.getNextLevel());
    }

    @Override
    protected void initializeFriendlyUnits() {
        UserPlane user = getUser();

        getRoot().getChildren().add(getUser());
        if(user.isBoundingBoxVisible()){
            getRoot().getChildren().add(getUser().getBoundingBox());
        }
    }

    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                EnemyPlaneMutator enemyPlaneMutator = new EnemyPlaneMutator();
                enemyPlaneMutator.setImageHeight(100);
                enemyPlaneMutator.setProjectileYPositionOffset(40);
                enemyPlaneMutator.setInitialHealth(5);
                enemyPlaneMutator.setFireRate(0.02);
                ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition, enemyPlaneMutator);
                addEnemyUnit(newEnemy);
            }
        }
    }

    @Override
    protected void spawnHealthPoints(){
        UserPlane user = getUser();

        if(user.getHealth() < PLAYER_INITIAL_HEALTH && Math.random() < HP_SPAWN_PROBABILITY
        ){
            HealthPoint healthPoint = new HealthPoint(user);
            healthPoints.add(healthPoint);
            getRoot().getChildren().add(healthPoint);

            if(healthPoint.isBoundingBoxVisible()){
                getRoot().getChildren().add(healthPoint.getBoundingBox());
            }
        }
    }

    @Override
    protected void repairUserDamage(ActiveActorDestructible userPlane){
        if(getUser().getHealth() < PLAYER_INITIAL_HEALTH){
            userPlane.repairDamage();
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }
}