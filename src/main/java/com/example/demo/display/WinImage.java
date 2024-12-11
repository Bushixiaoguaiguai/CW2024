package com.example.demo.display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Represents the "You Win" image displayed in the game.
 * This class initializes and positions the image within the scene.
 */
public class WinImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";
	private static final int HEIGHT = 250;
	private static final int WIDTH = 300;

	/**
	 * Creates a new WinImage instance with the specified position.
	 *
	 * @param xPosition The X-coordinate of the image's position in the scene.
	 * @param yPosition The Y-coordinate of the image's position in the scene.
	 */
	public WinImage(double xPosition, double yPosition) {
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}
}
