package com.example.demo.level.manager;

import com.example.demo.actors.shared.ActiveActorDestructible;
import com.example.demo.actors.shared.FighterPlane;
import com.example.demo.actors.friends.UserPlane;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * The UnitManager class manages all game units, including friendly and enemy units, as well as projectiles.
 *
 * <p>This class handles tasks such as updating actors, managing destroyed units, generating enemy fire,
 * and keeping track of kill counts for the user's plane.</p>
 */
public class UnitManager {

    private final Group root;
    private final List<ActiveActorDestructible> friendlyUnits = new ArrayList<>();
    private final List<ActiveActorDestructible> enemyUnits = new ArrayList<>();
    private final List<ActiveActorDestructible> userProjectiles = new ArrayList<>();
    private final List<ActiveActorDestructible> enemyProjectiles = new ArrayList<>();

    /**
     * Constructs a UnitManager to manage all game actors within the provided JavaFX root node.
     *
     * @param root the JavaFX {@link Group} that serves as the root node of the scene graph
     */
    public UnitManager(Group root) {
        this.root = root;
    }

    /**
     * Updates all actors in the game, including friendly units, enemy units, and projectiles.
     *
     * <p>This method calls the {@code updateActor} method on each actor to refresh their state.</p>
     */
    public void updateActors() {
        friendlyUnits.forEach(ActiveActorDestructible::updateActor);
        enemyUnits.forEach(ActiveActorDestructible::updateActor);
        userProjectiles.forEach(ActiveActorDestructible::updateActor);
        enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
    }

    /**
     * Adds a new enemy unit to the scene graph and the internal list of enemy units.
     *
     * @param enemy the {@link ActiveActorDestructible} enemy unit to be added
     */
    public void addEnemyUnit(ActiveActorDestructible enemy) {
        if (!enemyUnits.contains(enemy)) {
            enemyUnits.add(enemy);
            root.getChildren().add(enemy);
        }
    }

    /**
     * Updates the kill count for the user's plane based on the number of destroyed enemy units.
     *
     * <p>The method calculates the difference between the previous number of enemies and the
     * current number of enemies to determine how many kills to register.</p>
     *
     * @param user                  the {@link UserPlane} object representing the player's plane
     * @param currentNumberOfEnemies the number of enemies currently present
     */
    public void updateKillCount(UserPlane user, int currentNumberOfEnemies) {
        for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
            user.incrementKillCount(); // Increment one kill at a time
        }
    }

    /**
     * Removes all destroyed actors from the scene graph and their respective lists.
     *
     * <p>Destroyed actors are identified by calling their {@code isDestroyed} method.
     * Any destroyed actor is removed from both the JavaFX root node and the internal list.</p>
     */
    public void removeAllDestroyedActors() {
        removeDestroyedActors(friendlyUnits);
        removeDestroyedActors(enemyUnits);
        removeDestroyedActors(userProjectiles);
        removeDestroyedActors(enemyProjectiles);
    }

    /**
     * Removes destroyed actors from a specific list and the JavaFX root node.
     *
     * @param actors the list of actors to check for destruction
     */
    private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream()
                .filter(ActiveActorDestructible::isDestroyed)
                .toList();
        root.getChildren().removeAll(destroyedActors);
        actors.removeAll(destroyedActors);
    }

    /**
     * Triggers enemy units to fire projectiles.
     *
     * <p>This method calls the {@code fireProjectile} method on each enemy unit and adds the resulting
     * projectiles to the scene graph and the internal list of enemy projectiles.</p>
     */
    public void generateEnemyFire() {
        enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
    }

    /**
     * Spawns a new enemy projectile and adds it to the scene graph and the internal list of enemy projectiles.
     *
     * @param projectile the {@link ActiveActorDestructible} projectile to be added
     */
    private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
        if (projectile != null) {
            root.getChildren().add(projectile);
            enemyProjectiles.add(projectile);
        }
    }

    /**
     * Returns the list of friendly units.
     *
     * @return a list of friendly {@link ActiveActorDestructible} units
     */
    public List<ActiveActorDestructible> getFriendlyUnits() {
        return friendlyUnits;
    }

    /**
     * Returns the list of enemy units.
     *
     * @return a list of enemy {@link ActiveActorDestructible} units
     */
    public List<ActiveActorDestructible> getEnemyUnits() {
        return enemyUnits;
    }

    /**
     * Returns the list of enemy projectiles.
     *
     * @return a list of enemy {@link ActiveActorDestructible} projectiles
     */
    public List<ActiveActorDestructible> getEnemyProjectiles() {
        return enemyProjectiles;
    }

    /**
     * Returns the list of user projectiles.
     *
     * @return a list of user {@link ActiveActorDestructible} projectiles
     */
    public List<ActiveActorDestructible> getUserProjectiles() {
        return userProjectiles;
    }
}
