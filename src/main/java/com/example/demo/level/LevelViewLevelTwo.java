package com.example.demo.level;

import com.example.demo.display.ShieldImage;
import javafx.scene.Group;

/**
 * Represents the visual components of Level Two, extending the functionality of {@link LevelView}.
 * This class adds a shield image specific to Level Two.
 */
public class LevelViewLevelTwo extends LevelView {

	private static final int SHIELD_X_POSITION = 1150;
	private static final int SHIELD_Y_POSITION = 500;

	private final Group root;
	private final ShieldImage shieldImage;

	/**
	 * Constructs a new {@code LevelViewLevelTwo} instance.
	 *
	 * @param root the root {@link Group} where visual elements of the level are added.
	 * @param heartsToDisplay the initial number of hearts to display for the player's health.
	 */
	public LevelViewLevelTwo(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		addImagesToRoot();
	}

	/**
	 * Adds the shield image to the root group for Level Two.
	 */
	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage);
	}
}
