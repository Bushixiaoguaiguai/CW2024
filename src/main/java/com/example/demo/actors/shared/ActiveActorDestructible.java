package com.example.demo.actors.shared;

public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	private boolean isDestroyed;
	private Runnable onDestroyedCallback;

	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	@Override
	public abstract void updatePosition();

	public abstract void updateActor();

	@Override
	public abstract void takeDamage();

	@Override
	public void destroy() {
		setDestroyed(true);
		if (onDestroyedCallback != null) {
			onDestroyedCallback.run(); // Trigger the callback
		}
	}

	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public void setOnDestroyed(Runnable onDestroyedCallback) {
		this.onDestroyedCallback = onDestroyedCallback;
	}
}
