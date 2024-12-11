package com.example.demo.display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Represents the "Game Over" image displayed in the game.
 * This class initializes and positions the image within the scene.
 */
public class GameOverImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";
	private static final int HEIGHT = 250;
	private static final int WIDTH = 300;

	/**
	 * Creates a new GameOverImage instance with the specified position.
	 *
	 * @param xPosition The X-coordinate of the image's position in the scene.
	 * @param yPosition The Y-coordinate of the image's position in the scene.
	 */
	public GameOverImage(double xPosition, double yPosition) {
		setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}
}
