package com.example.demo.actors.shared;

import javafx.scene.image.*;

import java.net.URL;
import java.util.Objects;

public abstract class ActiveActor extends ImageView {
	
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";
	ImageView imageView;

	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		URL resourceUrl = getClass().getResource(IMAGE_LOCATION + imageName);
		this.imageView = new ImageView(new Image(Objects.requireNonNull(resourceUrl).toExternalForm()));

		//this.setImage(new Image(IMAGE_LOCATION + imageName));
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_LOCATION + imageName)).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}

	public abstract void updatePosition();

	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

}
