package com.example.demo.controller;


import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;
import com.example.demo.LevelOne;
import com.example.demo.LevelTwo;
import javafx.stage.Stage;
import com.example.demo.LevelParent;

public class Controller implements Observer {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.level.LevelOne";
	private final Stage stage;
	private final double screenHeight;
	private final double screenWidth;
	private LevelParent currentLevel;

	public Controller(Stage stage, double screenHeight, double screenWidth) {
		this.stage = stage;
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
	}

	// Start Level One
	public void startLevelOne() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		LevelOne levelOne = new LevelOne(screenHeight, screenWidth);
		goToLevel(levelOne);
	}

	// Start Level Two
	public void startLevelTwo() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		LevelTwo levelTwo = new LevelTwo(screenHeight, screenWidth);
		goToLevel(levelTwo);
	}

	private void goToLevel(LevelParent level) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// Stop any existing level
		if (currentLevel != null) {
			currentLevel.stopGame();
		}

		// Set the new level and observe it
		currentLevel = level;
		currentLevel.addObserver(this);

		// Set the scene for the stage
		stage.setScene(currentLevel.initializeScene());

		// Start the new level
		currentLevel.startGame();

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof String) {
			String nextLevel = (String) arg1;
			if (nextLevel.equals("com.example.demo.level.LevelTwo")) {
				try {
					startLevelTwo();
				} catch (ClassNotFoundException e) {
					throw new RuntimeException(e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(e);
				} catch (InstantiationException e) {
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
			else if (nextLevel.equals("com.example.demo.level.LevelOne")){
				try {
					startLevelOne();
				} catch (ClassNotFoundException e) {
					throw new RuntimeException(e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(e);
				} catch (InstantiationException e) {
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

}
