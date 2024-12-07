package com.example.demo.level;

import com.example.demo.actors.enemies.Boss;
import com.example.demo.effect.HpBarDisplay;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
    private HpBarDisplay hpBarDisplay;

	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss();
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
	protected void initializeEnemyUnits(){
		getRoot().getChildren().add(boss.getShieldImage());
		hpBarDisplay = new HpBarDisplay(boss);
		hpBarDisplay.setLayoutX((getScreenWidth() - HpBarDisplay.getBarWidth()) / 2);
		hpBarDisplay.setLayoutY(0);
		getRoot().getChildren().add(hpBarDisplay);
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
        return new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	@Override
	protected void onCleanup() {
		getRoot().getChildren().remove(boss.getShieldImage());
		getRoot().getChildren().remove(hpBarDisplay);
		System.out.println("LevelTwo-specific cleanup complete.");
	}
}
