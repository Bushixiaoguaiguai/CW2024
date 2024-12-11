package com.example.demo.actors.enemies;

import com.example.demo.actors.shared.Projectile;

/**
 * Represents a projectile fired by the boss enemy in the game.
 * Extends the {@link Projectile} class and defines specific behavior for
 * the boss's projectiles.
 */
public class BossProjectile extends Projectile {

	private static final String IMAGE_NAME = "fireball.png";
	private static final int IMAGE_HEIGHT = 60;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final int INITIAL_X_POSITION = 950;

	/**
	 * Initializes a new BossProjectile with the specified initial Y position.
	 *
	 * @param initialYPos The initial Y-coordinate of the projectile.
	 */
	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
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
