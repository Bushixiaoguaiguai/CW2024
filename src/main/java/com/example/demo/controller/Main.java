package com.example.demo.controller;

import com.example.demo.level.LevelType;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage stage) {
		final double screenWidth = 1300;
		final double screenHeight = 750;

		// Initialize the controller
		Controller controller = new Controller(stage, screenWidth, screenHeight);

		// Start the game at Level One
		controller.goToLevel(LevelType.LEVEL_ONE);
	}

	public static void main(String[] args) {
		launch();
	}
}