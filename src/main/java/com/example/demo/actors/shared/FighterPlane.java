package com.example.demo.actors.shared;

/**
 * Represents a fighter plane in the game that can attack and take damage.
 * Extends {@link ActiveActorDestructible} and includes health management and projectile firing.
 */
public abstract class FighterPlane extends ActiveActorDestructible {

	private int health;

	/**
	 * Initializes a new FighterPlane with the specified image, position, and health.
	 *
	 * @param imageName   The name of the image file for the plane.
	 * @param imageHeight The height of the image.
	 * @param initialXPos The initial X-coordinate of the plane.
	 * @param initialYPos The initial Y-coordinate of the plane.
	 * @param health      The initial health of the plane.
	 */
	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Fires a projectile from the plane.
	 *
	 * @return A new {@link ActiveActorDestructible} representing the projectile.
	 */
	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * Reduces the health of the plane by 1. If health reaches 0, the plane is destroyed.
	 */
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	/**
	 * Calculates the X-coordinate for a projectile's initial position.
	 *
	 * @param xPositionOffset The offset from the plane's X-coordinate.
	 * @return The calculated X-coordinate for the projectile.
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Calculates the Y-coordinate for a projectile's initial position.
	 *
	 * @param yPositionOffset The offset from the plane's Y-coordinate.
	 * @return The calculated Y-coordinate for the projectile.
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Checks if the plane's health has reached 0.
	 *
	 * @return {@code true} if health is 0, otherwise {@code false}.
	 */
	private boolean healthAtZero() {
		return health == 0;
	}

	/**
	 * Retrieves the current health of the plane.
	 *
	 * @return The current health value.
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Increases the health of the plane by 1, up to a maximum of 8.
	 */
	public void increaseHealth() {
		if (getHealth() + 1 <= 8) {
			System.out.println("Health increased by 1");
			health++;
		}
	}
}
