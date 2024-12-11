package com.example.demo.actors.shared;

import javafx.scene.image.*;

import java.net.URL;
import java.util.Objects;

/**
 * Represents an active actor in the game that can move and has an image representation.
 * Extends the {@link ImageView} class and provides basic movement functionality.
 */
public abstract class ActiveActor extends ImageView {

	private static final String IMAGE_LOCATION = "/com/example/demo/images/";
	private final ImageView imageView;

	/**
	 * Initializes a new ActiveActor with the specified image and position.
	 *
	 * @param imageName   The name of the image file for the actor.
	 * @param imageHeight The height of the image.
	 * @param initialXPos The initial X-coordinate of the actor.
	 * @param initialYPos The initial Y-coordinate of the actor.
	 */
	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		URL resourceUrl = getClass().getResource(IMAGE_LOCATION + imageName);
		this.imageView = new ImageView(new Image(Objects.requireNonNull(resourceUrl).toExternalForm()));

		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_LOCATION + imageName)).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}

	/**
	 * Updates the position of the actor. This method must be implemented by subclasses.
	 */
	public abstract void updatePosition();

	/**
	 * Moves the actor horizontally by a specified distance.
	 *
	 * @param horizontalMove The distance to move horizontally.
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves the actor vertically by a specified distance.
	 *
	 * @param verticalMove The distance to move vertically.
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}
}
