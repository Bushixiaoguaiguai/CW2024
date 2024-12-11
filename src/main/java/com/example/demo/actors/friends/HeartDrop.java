package com.example.demo.actors.friends;

import com.example.demo.actors.shared.ActiveActor;

/**
 * Represents a heart drop collectible in the game.
 * Extends the {@link ActiveActor} class and defines specific behavior for
 * movement and interactions.
 */
public class HeartDrop extends ActiveActor {

    private static final String HEART_IMAGE_PATH = "heart.png";
    private static final int HEART_SIZE = 30;
    private static final double MOVE_SPEED = -3;
    private double verticalSpeed;

    /**
     * Initializes a new HeartDrop at the specified initial position.
     *
     * @param initialX The initial X-coordinate of the heart drop.
     * @param initialY The initial Y-coordinate of the heart drop.
     */
    public HeartDrop(double initialX, double initialY) {
        super(HEART_IMAGE_PATH, HEART_SIZE, initialX, initialY);
        this.verticalSpeed = 2; // Start moving down
    }

    /**
     * Updates the position of the heart drop, including horizontal and vertical movement.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(MOVE_SPEED);
        this.setLayoutY(this.getLayoutY() + verticalSpeed);

        if (this.getLayoutY() <= 0) {
            this.verticalSpeed = 2;
        } else if (this.getLayoutY() >= 720) {
            this.verticalSpeed = -2;
        }
    }
}
