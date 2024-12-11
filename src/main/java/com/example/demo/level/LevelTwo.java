package com.example.demo.level;

import com.example.demo.actors.enemies.Boss;
import com.example.demo.display.HpBarDisplay;

/**
 * Represents the second level of the game. This level introduces a boss enemy with a health bar display.
 * It extends {@link LevelParent} and implements its abstract methods to define specific behavior for Level Two.
 */
public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;

	private final Boss boss;
	private HpBarDisplay hpBarDisplay;

	/**
	 * Constructs the second level with the specified screen dimensions.
	 *
	 * @param screenHeight the height of the screen.
	 * @param screenWidth the width of the screen.
	 */
	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss();
	}

	/**
	 * Checks if the game is over by determining if the player or the boss has been destroyed.
	 * If the player is destroyed, the game is lost. If the boss is destroyed, the player wins.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (boss.isDestroyed()) {
			winGame();
		}
	}

	/**
	 * Initializes the enemy units for Level Two by adding the boss's shield image and its health bar display
	 * to the scene's root group.
	 */
	@Override
	protected void initializeEnemyUnits() {
		getRoot().getChildren().add(boss.getShieldImage());
		hpBarDisplay = new HpBarDisplay(boss);
		hpBarDisplay.setLayoutX((getScreenWidth() - HpBarDisplay.getBarWidth()) / 2);
		hpBarDisplay.setLayoutY(0);
		getRoot().getChildren().add(hpBarDisplay);
	}

	/**
	 * Spawns the enemy units for Level Two. The boss is added to the level if no enemies are currently present.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	/**
	 * Instantiates the {@link LevelView} for Level Two. This method provides a specialized view for this level.
	 *
	 * @return the {@link LevelViewLevelTwo} instance for Level Two.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Performs cleanup specific to Level Two, such as removing the boss's shield image and health bar display
	 * from the scene's root group.
	 */
	@Override
	protected void onCleanup() {
		getRoot().getChildren().remove(boss.getShieldImage());
		getRoot().getChildren().remove(hpBarDisplay);
		System.out.println("LevelTwo-specific cleanup complete.");
	}
}
