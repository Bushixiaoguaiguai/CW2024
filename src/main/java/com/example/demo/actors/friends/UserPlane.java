package com.example.demo.actors.friends;

import com.example.demo.actors.shared.ActiveActorDestructible;
import com.example.demo.actors.shared.FighterPlane;
import com.example.demo.level.manager.SoundEffectManager;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = 10;
	private static final double Y_LOWER_BOUND = 700.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 40;
	private static final int VERTICAL_VELOCITY = 3;
	private static final int HORIZONTAL_VELOCITY = 3;
	private static final int PROJECTILE_X_POSITION = 110;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	private int verticalVelocityMultiplier;
	private int horizontalVelocityMultiplier;
	private final IntegerProperty numberOfKills;
	private SoundEffectManager soundEffectManager;

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		this.verticalVelocityMultiplier = 0;
		this.horizontalVelocityMultiplier = 0;
		numberOfKills = new SimpleIntegerProperty(0);
		soundEffectManager = new SoundEffectManager();
	}
	
	@Override
	public void updatePosition() {
		if (isMoving()) {
			double initialTranslateY = getTranslateY();
			double initialTranslateX = getTranslateX();

			this.moveVertically(VERTICAL_VELOCITY * verticalVelocityMultiplier);
			double newPosition = getLayoutY() + getTranslateY();
			if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}

			this.moveHorizontally(HORIZONTAL_VELOCITY * horizontalVelocityMultiplier);
			double newXPosition = getLayoutX() + getTranslateX();
			if (newXPosition < 0 || newXPosition > 400) {
				this.setTranslateX(initialTranslateX);
			}
		}
	}
	
	@Override
	public void updateActor() {
		updatePosition();
	}
	
	@Override
	public ActiveActorDestructible fireProjectile() {
		soundEffectManager.playShootSound();
		double projectileXPosition = getLayoutX() + getTranslateX() + PROJECTILE_X_POSITION;
		return new UserProjectile(projectileXPosition, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	private boolean isMoving() {
		return verticalVelocityMultiplier != 0 || horizontalVelocityMultiplier != 0;
	}

	public void moveUp() {
		verticalVelocityMultiplier = -1;
	}

	public void moveDown() {
		verticalVelocityMultiplier = 1;
	}

	public void moveLeft() {
		horizontalVelocityMultiplier = -1;
	}

	public void moveRight() {
		horizontalVelocityMultiplier = 1;
	}

	public void stopVertical() {
		verticalVelocityMultiplier = 0;
	}

	public void stopHorizontal() {
		horizontalVelocityMultiplier = 0;
	}

	public int getNumberOfKills() {
		return numberOfKills.get();
	}

	public void incrementKillCount() {
		numberOfKills.set(numberOfKills.get() + 1);
	}

	public IntegerProperty numberOfKillsProperty() {
		return numberOfKills;
	}

	public int getVerticalVelocityMultiplier() {
		return verticalVelocityMultiplier;
	}

	public int getHorizontalVelocityMultiplier() {
		return horizontalVelocityMultiplier;
	}

}
