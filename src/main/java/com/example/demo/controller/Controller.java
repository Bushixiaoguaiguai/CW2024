package com.example.demo.controller;

import com.example.demo.level.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Controller {

	private final Stage stage;
	private final LevelFactory levelFactory;
	private LevelParent currentLevel;

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;

	public Controller(Stage stage) {
		this.stage = stage;
		this.levelFactory = new LevelFactory(SCREEN_WIDTH, SCREEN_HEIGHT);

		stage.setTitle("Sky Battle");
		stage.setResizable(false);
		stage.show();
	}

	// Transition to a new level
	public void goToLevel(LevelType levelType) {
		try {
			// Clean up the current level
			if (currentLevel != null) {
				currentLevel.cleanup();
			}

			// Handle transitions to special screens
			switch (levelType) {
				case MAIN_MENU -> showMainMenu();
				case WIN -> showWinScreen();
				case GAME_OVER -> showGameOverScreen();
				default -> {
					// Create and start a new game level
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

	// Handle level transitions
	private void handleLevelTransition(LevelType nextLevel) {
		goToLevel(nextLevel);
	}

	// Show Main Menu
	private void showMainMenu() {
		Scene mainMenuScene = new MainMenu(SCREEN_WIDTH, SCREEN_HEIGHT,
				() -> goToLevel(LevelType.LEVEL_ONE),
				() -> goToLevel(LevelType.LEVEL_INFINITY), // Start game callback
				stage::close  // Quit callback
		).getScene();
		stage.setScene(mainMenuScene);
	}

	// Show Win Screen
	private void showWinScreen() {
		Scene winScene = new WinScreen(SCREEN_WIDTH, SCREEN_HEIGHT,
				() -> goToLevel(LevelType.LEVEL_INFINITY), // Retry callback
				() -> goToLevel(LevelType.MAIN_MENU)  // Main menu callback
		).getScene();
		stage.setScene(winScene);
	}

	private void showGameOverScreen() {
		Runnable retryCallback;

		// Determine the retry callback based on the current level
		if (currentLevel instanceof LevelOne) {
			retryCallback = () -> goToLevel(LevelType.LEVEL_ONE); // Retry LevelOne
		} else if (currentLevel instanceof LevelInfinity) {
			retryCallback = () -> goToLevel(LevelType.LEVEL_INFINITY); // Retry LevelInfinity
		} else {
			retryCallback = () -> goToLevel(LevelType.LEVEL_ONE); // Default to LevelOne
		}

		// Create GameOverScreen with the determined retry callback
		Scene gameOverScene = new GameOverScreen(SCREEN_WIDTH, SCREEN_HEIGHT,
				retryCallback,  // Retry callback
				() -> goToLevel(LevelType.MAIN_MENU) // Main menu callback
		).getScene();

		stage.setScene(gameOverScene);
	}


	// Handle errors
	private void handleException(Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText("An error occurred: " + e.getMessage());
		alert.show();
		e.printStackTrace();
	}
}
