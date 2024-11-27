package com.example.demo.level;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.Boss;
import com.example.demo.actor.UserPlane;
import com.example.demo.misc.CollisionHandler;

public class LevelBoss extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelViewLevelBoss levelView;

	public LevelBoss(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss();
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
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed()) {
			winGame();
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	@Override
	protected void spawnHealthPoints(){
    }

	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelBoss(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelView;
	}

	@Override
	protected void animateBackground() {
    }

	@Override
	protected void handleScoreableCollisions(){
		int scoreIncrement = CollisionHandler.handleUserProjectileBossCollisions(getUser(), userProjectiles, boss);
		gameScore.increaseScoreBy(scoreIncrement);
	}

	@Override
	protected void updateLevelView(){
		super.updateLevelView();
		levelView.updateBossHealth(boss.getHealth());
		if(boss.getIsShielded()){
			levelView.showShield();
		}else{
			levelView.hideShield();
		}
	}
}
