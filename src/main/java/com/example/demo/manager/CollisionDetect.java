package com.example.demo.manager;

import com.example.demo.actors.ActiveActorDestructible;
import javafx.scene.Group;

import java.util.List;

/**
 * The CollisionDetect class provides methods to handle collision detection and related game logic.
 *
 * <p>This class includes functionality to check collisions between two groups of game objects, detect enemies
 * breaching the player's defenses, and handle explosions triggered by collisions.</p>
 */
public class CollisionDetect {

    private final Group root;

    /**
     * Constructs a CollisionDetect object with a reference to the JavaFX scene root.
     *
     * @param root the JavaFX Group that represents the root node of the scene graph
     */
    public CollisionDetect(Group root) {
        this.root = root;
    }

    /**
     * Checks for collisions between two lists of objects and processes the results.
     *
     * <p>If a collision is detected between any objects in the two lists, both objects will take damage.</p>
     *
     * @param actors1 the first list of {@link ActiveActorDestructible} objects
     * @param actors2 the second list of {@link ActiveActorDestructible} objects
     */
    public void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor1 : actors1) {
            for (ActiveActorDestructible actor2 : actors2) {
                if (actor1.getBoundsInParent().intersects(actor2.getBoundsInParent())) {
                    actor1.takeDamage();
                    actor2.takeDamage();
                }
            }
        }
    }

    /**
     * Detects if any enemy objects have passed beyond the player's defensive line and processes the results.
     *
     * <p>If an enemy passes beyond the specified screen width, the player's plane takes damage, and the enemy object is destroyed.</p>
     *
     * @param enemies     the list of enemy {@link ActiveActorDestructible} objects
     * @param userPlane   the player's {@link ActiveActorDestructible} object
     * @param screenWidth the width of the screen, representing the player's defensive boundary
     */
    public void handleEnemyPenetration(List<ActiveActorDestructible> enemies, ActiveActorDestructible userPlane, double screenWidth) {
        for (ActiveActorDestructible enemy : enemies) {
            if (Math.abs(enemy.getTranslateX()) > screenWidth) {
                userPlane.takeDamage();
                enemy.destroy();
            }
        }
    }
}
