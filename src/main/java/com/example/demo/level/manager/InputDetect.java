package com.example.demo.level.manager;

import com.example.demo.actors.shared.ActiveActorDestructible;
import com.example.demo.actors.friends.UserPlane;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.List;

/**
 * The InputDetect class manages user input to control the player's plane and handle actions
 * such as movement and firing projectiles.
 */
public class InputDetect {

    private final UserPlane user;
    private final Group root;
    private final List<ActiveActorDestructible> userProjectiles;
    private boolean isSpacePressed = false;

    /**
     * Constructs an InputDetect object to manage input events for the user's plane.
     *
     * @param user            the {@link UserPlane} representing the player's plane
     * @param root            the JavaFX {@link Group} that serves as the scene root node
     * @param userProjectiles the list of projectiles ({@link ActiveActorDestructible}) fired by the user
     */
    public InputDetect(UserPlane user, Group root, List<ActiveActorDestructible> userProjectiles) {
        this.user = user;
        this.root = root;
        this.userProjectiles = userProjectiles;
    }

    /**
     * Handles key press events to control the user's plane or initiate specific actions.
     *
     * <p>Supported keys and their actions:
     * <ul>
     *     <li><b>UP</b>: Moves the user's plane upward.</li>
     *     <li><b>DOWN</b>: Moves the user's plane downward.</li>
     *     <li><b>LEFT</b>: Moves the user's plane leftward.</li>
     *     <li><b>RIGHT</b>: Moves the user's plane rightward.</li>
     *     <li><b>SPACE</b>: Fires a projectile if the space key is pressed.</li>
     * </ul>
     *
     * @param e the {@link KeyEvent} triggered by a key press
     */
    public void handlePressed(KeyEvent e) {
        KeyCode kc = e.getCode();
        switch (kc) {
            case UP -> user.moveUp();
            case DOWN -> user.moveDown();
            case LEFT -> user.moveLeft();
            case RIGHT -> user.moveRight();
            case SPACE -> {
                if (!isSpacePressed) {
                    fireProjectile();
                    isSpacePressed = true;
                }
            }
        }
    }

    /**
     * Handles key release events to stop the user's plane or reset actions.
     *
     * <p>Supported keys and their actions:
     * <ul>
     *     <li><b>UP</b> or <b>DOWN</b>: Stops the user's plane movement.</li>
     *     <li><b>SPACE</b>: Resets the projectile firing state.</li>
     * </ul>
     *
     * @param e the {@link KeyEvent} triggered by a key release
     */
    public void handleReleased(KeyEvent e) {
        KeyCode kc = e.getCode();
        switch (kc) {
            case UP, DOWN -> user.stopVertical();
            case LEFT, RIGHT -> user.stopHorizontal();
            case SPACE -> isSpacePressed = false;
        }
    }

    /**
     * Fires a projectile from the user's plane and adds it to the scene graph and projectile list.
     *
     * <p>The projectile is created by the user's plane and added to both the JavaFX scene graph
     * and the list of active projectiles managed by the game.</p>
     */
    private void fireProjectile() {
        ActiveActorDestructible projectile = user.fireProjectile();
        root.getChildren().add(projectile);
        userProjectiles.add(projectile);
    }

    public boolean isSpacePressed() {
        return isSpacePressed;
    }
}
