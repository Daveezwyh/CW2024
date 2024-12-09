package com.example.demo.level;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.EnemyPlane;
import com.example.demo.actor.EnemyPlaneMutator;
import com.example.demo.actor.UserPlane;

public class LevelThree extends LevelParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 10;
    private static final double ENEMY_SPAWN_PROBABILITY = .20;
    private static final int PLAYER_INITIAL_HEALTH = 5;
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
                ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition, enemyPlaneMutator);
                addEnemyUnit(newEnemy);
            }
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