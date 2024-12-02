package com.example.demo.controller;

import com.example.demo.level.LevelType;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage stage) {
		Controller controller = new Controller(stage);

		controller.goToLevel(LevelType.MAIN_MENU);
	}

	public static void main(String[] args) {
		launch();
	}
}