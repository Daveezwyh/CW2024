package com.example.demo.level;

import com.example.demo.Boss;
import com.example.demo.ShieldImage;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class LevelViewLevelTwo extends LevelView {

	private static final int SHIELD_X_POSITION = 0;
	private static final int SHIELD_Y_POSITION = 0;
	private static final double BOSS_HEALTH_TEXT_X_POSITION = 1100;
	private static final double BOSS_HEALTH_TEXT_Y_POSITION = 40;
	private static final String BOSS_HP_LABEL = "Boss HP: ";
	private final Group root;
	private final ShieldImage shieldImage;
	private final Text bossHealthText;

	public LevelViewLevelTwo(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		this.bossHealthText = new Text(BOSS_HP_LABEL);
		makeBossHPText();
		addImagesToRoot();
	}

	private void makeBossHPText(){
		bossHealthText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		bossHealthText.setFill(Color.RED);
		DropShadow dropShadow = new DropShadow();
		dropShadow.setColor(Color.BLACK);
		bossHealthText.setEffect(dropShadow);
		bossHealthText.setX(BOSS_HEALTH_TEXT_X_POSITION);
		bossHealthText.setY(BOSS_HEALTH_TEXT_Y_POSITION);
	}

	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage, bossHealthText);
	}

	public void showShield() {
		shieldImage.showShield();
	}

	public void hideShield() {
		shieldImage.hideShield();
	}

	public void updateBossHealth(int health) {
		bossHealthText.setText(BOSS_HP_LABEL + health);
	}
}