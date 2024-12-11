package com.example.demo.display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Represents a shield image for a game entity.
 * The shield image can be shown or hidden dynamically.
 */
public class ShieldImage extends ImageView {

	private static final int SHIELD_SIZE = 200;

	/**
	 * Creates a new ShieldImage at the specified position.
	 *
	 * @param xPosition The X-coordinate of the shield's position in the scene.
	 * @param yPosition The Y-coordinate of the shield's position in the scene.
	 */
	public ShieldImage(double xPosition, double yPosition) {
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource("/com/example/demo/images/shield.png")).toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	/**
	 * Makes the shield visible.
	 */
	public void showShield() {
		this.setVisible(true);
	}

	/**
	 * Hides the shield.
	 */
	public void hideShield() {
		this.setVisible(false);
	}
}