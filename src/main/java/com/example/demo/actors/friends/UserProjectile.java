package com.example.demo.actors.friends;

import com.example.demo.actors.shared.Projectile;

/**
 * Represents a projectile fired by the user-controlled plane in the game.
 * Extends the {@link Projectile} class and defines specific behavior for
 * user projectiles.
 */
public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_HEIGHT = 10;
	private static final int HORIZONTAL_VELOCITY = 8;

	/**
	 * Initializes a new UserProjectile with the specified initial position.
	 *
	 * @param initialXPos The initial X-coordinate of the projectile.
	 * @param initialYPos The initial Y-coordinate of the projectile.
	 */
	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the projectile by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the state of the projectile, including its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}