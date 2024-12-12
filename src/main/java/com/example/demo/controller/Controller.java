package com.example.demo.controller;

import com.example.demo.level.manager.BackgroundMusicManager;
import com.example.demo.level.*;
import com.example.demo.level.screens.GameOverScreen;
import com.example.demo.level.screens.MainMenuScreen;
import com.example.demo.level.screens.PauseScreen;
import com.example.demo.level.screens.WinScreen;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Manages the transitions and interactions between different game levels and screens.
 */
public class Controller {

	private static Controller instance;

	private final Stage stage;
	private final LevelFactory levelFactory;
	private LevelParent currentLevel;

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;

	private BackgroundMusicManager backGroundMusicManager;

	/**
	 * Initializes the Controller with the specified game stage.
	 *
	 * @param stage The primary stage for the game.
	 */
	public Controller(Stage stage) {
		backGroundMusicManager = BackgroundMusicManager.getInstance();
		backGroundMusicManager.setVolume(0.5); // Set volume to 50%
		backGroundMusicManager.play();

		this.stage = stage;
		this.levelFactory = new LevelFactory(SCREEN_WIDTH, SCREEN_HEIGHT);

		stage.setTitle("Sky Battle");
		stage.setResizable(false);
		stage.show();
	}

	public static Controller getInstance(Stage stage) {
		if (instance == null) {
			instance = new Controller(stage);
		}
		return instance;
	}

	public static Controller getInstance() {
		if (instance == null) {
			throw new IllegalStateException("Controller not initialized yet!");
		}
		return instance;
	}

	/**
	 * Transitions to a new level based on the specified {@link LevelType}.
	 *
	 * @param levelType The type of level to transition to.
	 */
	public void goToLevel(LevelType levelType) {
		try {
			if (currentLevel != null) {
				currentLevel.cleanup();
			}

			switch (levelType) {
				case MAIN_MENU -> showMainMenu();
				case WIN -> showWinScreen();
				case GAME_OVER -> showGameOverScreen();
				default -> {
					currentLevel = levelFactory.createLevel(levelType);
					System.out.println(levelType);
					currentLevel.addObserver((observable, arg) -> {
						if (arg instanceof LevelType nextLevelType) {
							handleLevelTransition(nextLevelType);
						} else {
							handleException(new IllegalArgumentException("Invalid level transition argument: " + arg));
						}
					});
					stage.setScene(currentLevel.initializeScene());
					currentLevel.startGame();
				}
			}
		} catch (Exception e) {
			handleException(e);
		}
	}

	/**
	 * Handles transitions between levels.
	 *
	 * @param nextLevel The next level to transition to.
	 */
	private void handleLevelTransition(LevelType nextLevel) {
		goToLevel(nextLevel);
	}

	/**
	 * Displays the main menu screen.
	 */
	private void showMainMenu() {
		Scene mainMenuScene = new MainMenuScreen(SCREEN_WIDTH, SCREEN_HEIGHT,
				() -> goToLevel(LevelType.LEVEL_ONE),
				() -> goToLevel(LevelType.LEVEL_INFINITY),
				stage::close
		).getScene();
		stage.setScene(mainMenuScene);
	}

	/**
	 * Displays the win screen.
	 */
	private void showWinScreen() {
		Scene winScene = new WinScreen(SCREEN_WIDTH, SCREEN_HEIGHT,
				() -> goToLevel(LevelType.LEVEL_INFINITY),
				() -> goToLevel(LevelType.MAIN_MENU)
		).getScene();
		stage.setScene(winScene);
	}

	/**
	 * Displays the game over screen.
	 */
	private void showGameOverScreen() {
		Runnable retryCallback;

		if (currentLevel instanceof LevelOne) {
			retryCallback = () -> goToLevel(LevelType.LEVEL_ONE);
		} else if (currentLevel instanceof LevelInfinity) {
			retryCallback = () -> goToLevel(LevelType.LEVEL_INFINITY);
		} else {
			retryCallback = () -> goToLevel(LevelType.LEVEL_ONE);
		}

		Scene gameOverScene = new GameOverScreen(SCREEN_WIDTH, SCREEN_HEIGHT,
				retryCallback,
				() -> goToLevel(LevelType.MAIN_MENU)
		).getScene();

		stage.setScene(gameOverScene);
	}

	/**
	 * Handles exceptions and displays an error message to the user.
	 *
	 * @param e The exception to handle.
	 */
	private void handleException(Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText("An error occurred: " + e.getMessage());
		alert.show();
		e.printStackTrace();
	}

	/**
	 * Transitions the game to the pause menu screen.
	 * <p>
	 * This method pauses the current game (if running) and displays the pause menu.
	 * The pause menu allows the player to resume the game, return to the main menu, or quit the application.
	 */
	public void goToPauseMenu() {
		if (currentLevel != null) {
			currentLevel.pauseGame(); // Pause the current game
		}

		Scene pauseScreenScene = new PauseScreen(
				1300, 750,
				this::resumeGame,                // Resume callback
				() -> goToLevel(LevelType.MAIN_MENU), // Main Menu callback
				stage::close                     // Quit callback
		).getScene();

		stage.setScene(pauseScreenScene); // Set the pause screen as the active scene
	}

	/**
	 * Pauses the game by delegating the action to the current level and transitioning to the pause menu.
	 * <p>
	 * If a current level is active, its game logic is paused, and the pause menu screen is displayed.
	 */
	public void pauseGame() {
		if (currentLevel != null) {
			currentLevel.pauseGame(); // Delegate to LevelParent
		}
		goToPauseMenu(); // Transition to pause menu
	}

	/**
	 * Resumes the game by restoring the timeline of the current level and switching back to the game scene.
	 * <p>
	 * This method ensures that the game returns to its active state after the pause menu is dismissed.
	 */
	public void resumeGame() {
		if (currentLevel != null) {
			currentLevel.resumeGame(); // Resume the game timeline
			stage.setScene(currentLevel.getScene()); // Switch back to the game scene
		}
	}

}
