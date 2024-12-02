package com.example.demo.controller;

import com.example.demo.level.LevelType;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;

	@Override
	public void start(Stage stage) {

		// Initialize the controller
		Controller controller = new Controller(stage, SCREEN_WIDTH, SCREEN_HEIGHT);

		// Start the game at Level One
		controller.goToLevel(LevelType.LEVEL_ONE);
	}

	public static void main(String[] args) {
		launch();
	}
}