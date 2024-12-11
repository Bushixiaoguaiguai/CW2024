package com.example.demo.actors.friends;

import com.example.demo.actors.shared.ActiveActorDestructible;
import com.example.demo.actors.shared.FighterPlane;
import com.example.demo.level.manager.SoundEffectManager;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents the user-controlled plane in the game.
 * Extends the {@link FighterPlane} class and provides controls for movement,
 * firing projectiles, and tracking game-related properties such as kills.
 */
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

	/**
	 * Initializes a new UserPlane with the specified initial health.
	 *
	 * @param initialHealth The initial health of the user plane.
	 */
	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		this.verticalVelocityMultiplier = 0;
		this.horizontalVelocityMultiplier = 0;
		numberOfKills = new SimpleIntegerProperty(0);
		soundEffectManager = SoundEffectManager.getInstance();
	}

	/**
	 * Updates the position of the user plane based on movement multipliers.
	 */
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

	/**
	 * Updates the state of the user plane, including its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Fires a projectile and plays a shooting sound.
	 *
	 * @return A new {@link UserProjectile} representing the fired projectile.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		soundEffectManager.playShootSound();
		double projectileXPosition = getLayoutX() + getTranslateX() + PROJECTILE_X_POSITION;
		return new UserProjectile(projectileXPosition, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	/**
	 * Checks if the user plane is currently moving.
	 *
	 * @return {@code true} if the plane is moving, otherwise {@code false}.
	 */
	private boolean isMoving() {
		return verticalVelocityMultiplier != 0 || horizontalVelocityMultiplier != 0;
	}

	/**
	 * Sets the vertical movement to upward.
	 */
	public void moveUp() {
		verticalVelocityMultiplier = -1;
	}

	/**
	 * Sets the vertical movement to downward.
	 */
	public void moveDown() {
		verticalVelocityMultiplier = 1;
	}

	/**
	 * Sets the horizontal movement to leftward.
	 */
	public void moveLeft() {
		horizontalVelocityMultiplier = -1;
	}

	/**
	 * Sets the horizontal movement to rightward.
	 */
	public void moveRight() {
		horizontalVelocityMultiplier = 1;
	}

	/**
	 * Stops vertical movement.
	 */
	public void stopVertical() {
		verticalVelocityMultiplier = 0;
	}

	/**
	 * Stops horizontal movement.
	 */
	public void stopHorizontal() {
		horizontalVelocityMultiplier = 0;
	}

	/**
	 * Retrieves the number of kills made by the user plane.
	 *
	 * @return The number of kills.
	 */
	public int getNumberOfKills() {
		return numberOfKills.get();
	}

	/**
	 * Increments the kill count for the user plane.
	 */
	public void incrementKillCount() {
		numberOfKills.set(numberOfKills.get() + 1);
	}

	/**
	 * Retrieves the kill count property.
	 *
	 * @return The {@link IntegerProperty} representing the kill count.
	 */
	public IntegerProperty numberOfKillsProperty() {
		return numberOfKills;
	}

	/**
	 * Retrieves the vertical velocity multiplier.
	 *
	 * @return The vertical velocity multiplier.
	 */
	public int getVerticalVelocityMultiplier() {
		return verticalVelocityMultiplier;
	}

	/**
	 * Retrieves the horizontal velocity multiplier.
	 *
	 * @return The horizontal velocity multiplier.
	 */
	public int getHorizontalVelocityMultiplier() {
		return horizontalVelocityMultiplier;
	}
}