package com.example.demo.actors.enemies;

import com.example.demo.actors.shared.ActiveActorDestructible;
import com.example.demo.actors.shared.FighterPlane;

/**
 * Represents an enemy plane in the game.
 * Extends the {@link FighterPlane} class and defines specific behaviors for enemy planes,
 * such as firing projectiles and moving horizontally.
 */
public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = -2;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = 0.01;

	/**
	 * Initializes a new EnemyPlane with the specified initial position.
	 *
	 * @param initialXPos The initial X-coordinate of the enemy plane.
	 * @param initialYPos The initial Y-coordinate of the enemy plane.
	 */
	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	/**
	 * Updates the position of the enemy plane by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Fires a projectile if the enemy decides to shoot in the current frame.
	 *
	 * @return A new {@link EnemyProjectile} if firing, otherwise {@code null}.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPosition);
		}
		return null;
	}

	/**
	 * Updates the state of the enemy plane, including its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}
