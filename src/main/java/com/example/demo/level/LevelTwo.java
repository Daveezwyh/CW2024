package com.example.demo.level;

import com.example.demo.actor.*;
import com.example.demo.contracts.EnemyVariation;

public class LevelTwo extends LevelParent implements EnemyVariation {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
    private static final int TOTAL_ENEMIES = 4;
    private static final int KILLS_TO_ADVANCE = 50;
    private static final double ENEMY_SPAWN_PROBABILITY = 0.2;
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final double HP_SPAWN_PROBABILITY = 0.01 ;
    private final LevelSelector levelSelector;

    public LevelTwo(double screenHeight, double screenWidth) {
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
                EnemyPlane newEnemy;
                if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                    newEnemy = makeEnemyPlane(getScreenWidth(), newEnemyInitialYPosition, 1);
                }else{
                    newEnemy = makeEnemyPlane(getScreenWidth(), newEnemyInitialYPosition, 0);
                }
                addEnemyUnit(newEnemy);
            }
        }
    }

    @Override
    protected void spawnHealthPoints() {
        UserPlane user = getUser();

        int currentHealth = user.getHealth();
        double adjustedProbability = (double) (PLAYER_INITIAL_HEALTH - currentHealth) / PLAYER_INITIAL_HEALTH * HP_SPAWN_PROBABILITY;

        if (currentHealth < PLAYER_INITIAL_HEALTH && Math.random() < adjustedProbability) {
            HealthPoint healthPoint = new HealthPoint(user);
            healthPoints.add(healthPoint);
            getRoot().getChildren().add(healthPoint);

            if (healthPoint.isBoundingBoxVisible()) {
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
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, KILLS_TO_ADVANCE);
    }

    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }
    @Override
    public EnemyPlane makeEnemyPlane(double initialXPos, double initialYPos, int type) {
        EnemyPlane enemyPlane;

        switch (type) {
            case 0:
                enemyPlane = new EnemyPlane(initialXPos, initialYPos);
                break;
            case 1:
                EnemyPlaneMutator enemyPlaneMutator = new EnemyPlaneMutator();
                enemyPlaneMutator.setImageHeight(100);
                enemyPlaneMutator.setProjectileYPositionOffset(40);
                enemyPlaneMutator.setInitialHealth(5);
                enemyPlane = new EnemyPlane(initialXPos, initialYPos, enemyPlaneMutator);
                break;
            default:
                enemyPlane = new EnemyPlane(initialXPos, initialYPos);
        }

        return enemyPlane;
    }
}
