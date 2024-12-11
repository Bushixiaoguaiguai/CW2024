package com.example.demo.actors.enemies;

import com.example.demo.actors.shared.ActiveActorDestructible;
import com.example.demo.actors.shared.FighterPlane;
import com.example.demo.display.ShieldImage;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.*;

/**
 * Represents the boss enemy in the game.
 * Extends the {@link FighterPlane} class and includes unique behaviors such as
 * shield activation and firing projectiles.
 */
public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 1000.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	private static final double BOSS_FIRE_RATE = 0.01;
	private static final double BOSS_SHIELD_PROBABILITY = 0.002;
	private static final int IMAGE_HEIGHT = 70;
	private static final int VERTICAL_VELOCITY = 5;
	private static final int HEALTH = 10;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_UPPER_BOUND = -10;
	private static final int Y_POSITION_LOWER_BOUND = 650;
	private static final int MAX_FRAMES_WITH_SHIELD = 30;

	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;

	private final ShieldImage shieldImage;
	private final DoubleProperty currentHealth = new SimpleDoubleProperty(HEALTH);

	/**
	 * Initializes a new Boss object with default settings and behaviors.
	 */
	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		initializeMovePattern();

		shieldImage = new ShieldImage(INITIAL_X_POSITION, INITIAL_Y_POSITION);
	}

	/**
	 * Updates the boss's position based on the current move pattern.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();

		shieldImage.setLayoutX(getLayoutX() - 80);
		shieldImage.setLayoutY(currentPosition - 100);

		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
	}

	/**
	 * Updates the boss's state, including position and shield status.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	/**
	 * Fires a projectile if the boss decides to shoot in the current frame.
	 *
	 * @return A new {@link BossProjectile} if firing, otherwise {@code null}.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}

	/**
	 * Reduces the boss's health if it is not shielded.
	 */
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
			currentHealth.set(getHealth());
		}
	}

	/**
	 * Initializes the movement pattern for the boss.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * Updates the shield's state, including activation and deactivation.
	 */
	private void updateShield() {
		if (isShielded) {
			framesWithShieldActivated++;
		} else if (shieldShouldBeActivated()) {
			activateShield();
			shieldImage.showShield();
		}
		if (shieldExhausted()) {
			deactivateShield();
			shieldImage.hideShield();
		}
	}

	/**
	 * Determines the next movement direction for the boss.
	 *
	 * @return The next movement value.
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	/**
	 * Determines if the boss should fire a projectile in the current frame.
	 *
	 * @return {@code true} if the boss fires, otherwise {@code false}.
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * Calculates the initial position for the boss's projectile.
	 *
	 * @return The y-coordinate for the projectile's starting position.
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	/**
	 * Checks if the shield should be activated.
	 *
	 * @return {@code true} if the shield should activate, otherwise {@code false}.
	 */
	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	/**
	 * Checks if the shield duration has been exhausted.
	 *
	 * @return {@code true} if the shield duration is over, otherwise {@code false}.
	 */
	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	/**
	 * Activates the boss's shield.
	 */
	private void activateShield() {
		isShielded = true;
	}

	/**
	 * Deactivates the boss's shield and resets the activation duration.
	 */
	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
	}

	/**
	 * Retrieves the shield image associated with the boss.
	 *
	 * @return The {@link ShieldImage} object.
	 */
	public ShieldImage getShieldImage() {
		return shieldImage;
	}

	/**
	 * Retrieves the current health property of the boss.
	 *
	 * @return A {@link DoubleProperty} representing the current health.
	 */
	public DoubleProperty currentHealthProperty() {
		return currentHealth;
	}

	/**
	 * Retrieves the maximum health of the boss.
	 *
	 * @return The maximum health value.
	 */
	public double getMaxHealth() {
		return HEALTH;
	}
}
