package com.example.demo.actors.shared;

/**
 * Represents an active actor in the game that can be destroyed.
 * Extends {@link ActiveActor} and implements {@link Destructible} to handle
 * damage and destruction logic.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	private boolean isDestroyed;

	/**
	 * Initializes a new ActiveActorDestructible with the specified image and position.
	 *
	 * @param imageName   The name of the image file for the actor.
	 * @param imageHeight The height of the image.
	 * @param initialXPos The initial X-coordinate of the actor.
	 * @param initialYPos The initial Y-coordinate of the actor.
	 */
	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	/**
	 * Updates the position of the actor. This method must be implemented by subclasses.
	 */
	@Override
	public abstract void updatePosition();

	/**
	 * Updates the state of the actor. This method must be implemented by subclasses.
	 */
	public abstract void updateActor();

	/**
	 * Applies damage to the actor. This method must be implemented by subclasses.
	 */
	@Override
	public abstract void takeDamage();

	/**
	 * Destroys the actor by marking it as destroyed.
	 */
	@Override
	public void destroy() {
		setDestroyed(true);
	}

	/**
	 * Sets the destroyed status of the actor.
	 *
	 * @param isDestroyed {@code true} if the actor is destroyed, {@code false} otherwise.
	 */
	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	/**
	 * Checks if the actor is destroyed.
	 *
	 * @return {@code true} if the actor is destroyed, {@code false} otherwise.
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}
}