package com.example.demo.actors.shared;

/**
 * Represents a projectile in the game.
 * Extends {@link ActiveActorDestructible} and provides basic behavior for
 * destruction and position updates.
 */
public abstract class Projectile extends ActiveActorDestructible {

	/**
	 * Initializes a new Projectile with the specified image and position.
	 *
	 * @param imageName   The name of the image file for the projectile.
	 * @param imageHeight The height of the image.
	 * @param initialXPos The initial X-coordinate of the projectile.
	 * @param initialYPos The initial Y-coordinate of the projectile.
	 */
	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * Destroys the projectile upon taking damage.
	 */
	@Override
	public void takeDamage() {
		this.destroy();
	}

	/**
	 * Updates the position of the projectile. This method must be implemented by subclasses.
	 */
	@Override
	public abstract void updatePosition();

}
