package com.example.demo.level.manager;

import com.example.demo.actors.friends.HeartDrop;
import com.example.demo.actors.shared.ActiveActorDestructible;
import com.example.demo.actors.friends.UserPlane;
import com.example.demo.effect.Explosion;
import com.example.demo.effect.HeartDisplay;
import javafx.scene.Group;
import javafx.util.Pair;

import java.util.*;

/**
 * The CollisionDetect class provides methods to handle collision detection and related game logic.
 *
 * <p>This class includes functionality to check collisions between two groups of game objects, detect enemies
 * breaching the player's defenses, and handle explosions triggered by collisions.</p>
 */
public class CollisionDetect {

    private final Group root;
    final List<Explosion> activeExplosions;
    private final SoundEffectManager soundEffectManager;
    private final Set<Pair<ActiveActorDestructible, ActiveActorDestructible>> processedCollisions = new HashSet<>();
    private static final double HEART_DROP_PROBABILITY = 1;
    private final List<HeartDrop> heartDrops = new ArrayList<>();
    private final HeartDisplay heartDisplay;

    /**
     * Constructs a CollisionDetect object with a reference to the JavaFX scene root.
     *
     * @param root the JavaFX Group that represents the root node of the scene graph
     */
    public CollisionDetect(Group root, HeartDisplay heartDisplay) {
        this.root = root;
        this.activeExplosions = new ArrayList<>();
        this.heartDisplay = heartDisplay;
        soundEffectManager = new SoundEffectManager();
    }

    public void handleAllCollisions(List<ActiveActorDestructible> friendlyUnits,
                                    List<ActiveActorDestructible> enemyUnits,
                                    List<ActiveActorDestructible> userProjectiles,
                                    List<ActiveActorDestructible> enemyProjectiles,
                                    UserPlane user,
                                    double screenWidth) {
        handleEnemyPenetration(enemyUnits, user, screenWidth);
        handleCollisionsWithExplosion(userProjectiles, enemyUnits);
        handleCollisions(enemyProjectiles, friendlyUnits);
        handleCollisions(friendlyUnits, enemyUnits);
        updateExplosions();
    }

    /**
     * Checks for collisions between two lists of objects and processes the results.
     *
     * <p>If a collision is detected between any objects in the two lists, both objects will take damage.</p>
     *
     * @param actors1 the first list of {@link ActiveActorDestructible} objects
     * @param actors2 the second list of {@link ActiveActorDestructible} objects
     */
    private void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor1 : actors1) {
            for (ActiveActorDestructible actor2 : actors2) {
                Pair<ActiveActorDestructible, ActiveActorDestructible> collisionPair = new Pair<>(actor1, actor2);
                if (actor1.getBoundsInParent().intersects(actor2.getBoundsInParent()) &&
                        !processedCollisions.contains(collisionPair)) {
                    System.out.println("Processing collision: " + actor1 + " with " + actor2);
                    actor1.takeDamage();
                    actor2.takeDamage();
                    processedCollisions.add(collisionPair);
                }
            }
        }
        processedCollisions.clear();
    }

    /**
     * Handles collisions between two lists of objects and triggers explosions at the point of collision.
     *
     * <p>If a collision is detected between any objects in the two lists, both objects take damage, and an explosion
     * is generated at the location of the collision.</p>
     *
     * @param actors1 the first list of {@link ActiveActorDestructible} objects
     * @param actors2 the second list of {@link ActiveActorDestructible} objects
     */
    private void handleCollisionsWithExplosion(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor1 : actors1) {
            for (ActiveActorDestructible actor2 : actors2) {
                Pair<ActiveActorDestructible, ActiveActorDestructible> collisionPair = new Pair<>(actor1, actor2);
                if (actor1.getBoundsInParent().intersects(actor2.getBoundsInParent()) &&
                        !processedCollisions.contains(collisionPair)) {
                    actor1.takeDamage();
                    actor2.takeDamage();
                    processedCollisions.add(collisionPair);

                    double heartX = actor2.getLayoutX() + actor2.getTranslateX();
                    double heartY = actor2.getLayoutY() + actor2.getTranslateY();

                    if (Math.random() < HEART_DROP_PROBABILITY) {
                        System.out.println("HeartDrop Position - X: " + heartX + ", Y: " + heartY);
                        HeartDrop heartDrop = new HeartDrop(heartX, heartY);
                        heartDrops.add(heartDrop);
                        root.getChildren().add(heartDrop);
                        System.out.println("Heart spawned!");
                    }

                    Explosion explosion = new Explosion(
                            "/com/example/demo/images/explosion.png",
                            heartX, heartY,
                            64, 64,
                            1.0,
                            root
                    );
                    explosion.start();
                    soundEffectManager.playExplosionSound();
                    activeExplosions.add(explosion);
                }
            }
        }
        processedCollisions.clear();
    }

    /**
     * Updates and removes finished explosions from the active explosions list.
     *
     * <p>This method checks each active explosion and removes those that have finished.</p>
     */
    public void updateExplosions() {
        Iterator<Explosion> iterator = activeExplosions.iterator();
        while (iterator.hasNext()) {
            Explosion explosion = iterator.next();
            if (explosion.isFinished()) {
                iterator.remove();
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

    public void updateHeartDrops(UserPlane userPlane) {
        Iterator<HeartDrop> iterator = heartDrops.iterator();
        while (iterator.hasNext()) {
            HeartDrop heart = iterator.next();
            heart.updatePosition();
            if (heart.getBoundsInParent().intersects(userPlane.getBoundsInParent())) {
                System.out.println("Heart collected!");
                userPlane.increaseHealth();
                heartDisplay.addHeart();
                iterator.remove();
                root.getChildren().remove(heart);
            } else if (heart.getTranslateX() + heart.getLayoutX() < 0) {
                iterator.remove();
                root.getChildren().remove(heart);
            }
        }
    }
}
