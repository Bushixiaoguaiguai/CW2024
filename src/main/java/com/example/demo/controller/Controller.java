package com.example.demo.controller;

import com.example.demo.level.manager.BackGroundMusicManager;
import com.example.demo.level.*;
import com.example.demo.level.screens.GameOverScreen;
import com.example.demo.level.screens.MainMenu;
import com.example.demo.level.screens.WinScreen;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Manages the transitions and interactions between different game levels and screens.
 */
public class Controller {
	private final Stage stage;
	private final LevelFactory levelFactory;
	private LevelParent currentLevel;

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;

	private BackGroundMusicManager backGroundMusicManager;

	/**
	 * Initializes the Controller with the specified game stage.
	 *
	 * @param stage The primary stage for the game.
	 */
	public Controller(Stage stage) {
		backGroundMusicManager = BackGroundMusicManager.getInstance();
		backGroundMusicManager.setVolume(0.5); // Set volume to 50%
		backGroundMusicManager.play();

		this.stage = stage;
		this.levelFactory = new LevelFactory(SCREEN_WIDTH, SCREEN_HEIGHT);

		stage.setTitle("Sky Battle");
		stage.setResizable(false);
		stage.show();
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
		Scene mainMenuScene = new MainMenu(SCREEN_WIDTH, SCREEN_HEIGHT,
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
}
