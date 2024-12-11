package com.example.demo.level;

import com.example.demo.display.HeartDisplay;
import javafx.scene.Group;

/**
 * Represents the visual components of a level, including the display for player health (hearts).
 */
public class LevelView {

	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;

	private final Group root;
	private final HeartDisplay heartDisplay;

	/**
	 * Constructs a new {@code LevelView} instance.
	 *
	 * @param root the root {@link Group} where visual elements of the level are added.
	 * @param heartsToDisplay the initial number of hearts to display for the player's health.
	 */
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
	}

	/**
	 * Displays the heart container on the screen by adding it to the root group.
	 */
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	/**
	 * Removes hearts from the heart display to match the specified number of remaining hearts.
	 *
	 * @param heartsRemaining the number of hearts the player currently has.
	 */
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	/**
	 * Gets the {@link HeartDisplay} instance used in this level view.
	 *
	 * @return the {@code HeartDisplay}.
	 */
	public HeartDisplay getHeartDisplay() {
		return heartDisplay;
	}
}
