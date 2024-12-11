package com.example.demo.controller;

import com.example.demo.level.LevelType;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main entry point for the application.
 * Initializes the game and transitions to the main menu.
 */
public class Main extends Application {

	/**
	 * Starts the JavaFX application and initializes the controller.
	 *
	 * @param stage The primary stage for the application.
	 */
	@Override
	public void start(Stage stage) {
		Controller controller = new Controller(stage);

		controller.goToLevel(LevelType.MAIN_MENU);
	}

	/**
	 * The main method, which serves as the entry point of the application.
	 *
	 * @param args Command-line arguments.
	 */
	public static void main(String[] args) {
		launch();
	}
}
