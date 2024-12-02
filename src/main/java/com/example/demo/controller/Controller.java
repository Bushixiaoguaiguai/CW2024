package com.example.demo.controller;

import com.example.demo.level.LevelFactory;
import com.example.demo.level.LevelParent;
import com.example.demo.level.LevelType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Controller {

	private final Stage stage;
	private final LevelFactory levelFactory;
	private LevelParent currentLevel;

	public Controller(Stage stage, double screenWidth, double screenHeight) {
		this.stage = stage;
		this.levelFactory = new LevelFactory(screenWidth, screenHeight);

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

			// Create and start the new level
			currentLevel = levelFactory.createLevel(levelType);
			currentLevel.addObserver((observable, arg) -> handleLevelTransition((LevelType) arg));
			stage.setScene(currentLevel.initializeScene());
			currentLevel.startGame();
		} catch (Exception e) {
			handleException(e);
		}
	}

	// Handle level transitions
	private void handleLevelTransition(LevelType nextLevel) {
		goToLevel(nextLevel);
	}

	// Handle errors
	private void handleException(Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText("An error occurred: " + e.getMessage());
		alert.show();
		e.printStackTrace();
	}
}
