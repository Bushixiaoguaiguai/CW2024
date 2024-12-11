package com.example.demo.level.manager;

import com.example.demo.JavaFXTestBase;
import com.example.demo.actors.friends.UserPlane;
import com.example.demo.actors.shared.ActiveActorDestructible;
import com.example.demo.actors.shared.FighterPlane;
import javafx.scene.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UnitManagerTest extends JavaFXTestBase {

    private UnitManager unitManager;
    private Group root;
    private UserPlane userPlane;

    @BeforeEach
    void setUp() {
        root = new Group();
        unitManager = new UnitManager(root);
        userPlane = new UserPlane(5);
    }

    @Test
    void testAddEnemyUnit() {
        ActiveActorDestructible enemy = new TestActorDestructible();
        unitManager.addEnemyUnit(enemy);

        // Verify the enemy is added to the enemy list and scene graph
        assertTrue(unitManager.getEnemyUnits().contains(enemy), "Enemy should be added to enemy units");
        assertTrue(root.getChildren().contains(enemy), "Enemy should be added to the scene graph");
    }

    @Test
    void testUpdateKillCount() {
        ActiveActorDestructible enemy1 = new TestActorDestructible();
        ActiveActorDestructible enemy2 = new TestActorDestructible();

        unitManager.addEnemyUnit(enemy1);
        unitManager.addEnemyUnit(enemy2);

        // Remove an enemy to simulate a kill
        enemy1.destroy();
        unitManager.removeAllDestroyedActors();

        // Update the kill count
        unitManager.updateKillCount(userPlane, 2);

        // Verify the kill count is incremented
        assertEquals(1, userPlane.getNumberOfKills(), "Kill count should be incremented when an enemy is destroyed");
    }

    @Test
    void testRemoveAllDestroyedActors() {
        ActiveActorDestructible friendlyUnit = new TestActorDestructible();
        ActiveActorDestructible enemyUnit = new TestActorDestructible();
        unitManager.getFriendlyUnits().add(friendlyUnit);
        unitManager.addEnemyUnit(enemyUnit);

        // Mark units as destroyed
        friendlyUnit.destroy();
        enemyUnit.destroy();

        // Remove destroyed actors
        unitManager.removeAllDestroyedActors();

        // Verify they are removed from their respective lists and the scene graph
        assertFalse(unitManager.getFriendlyUnits().contains(friendlyUnit), "Destroyed friendly unit should be removed");
        assertFalse(unitManager.getEnemyUnits().contains(enemyUnit), "Destroyed enemy unit should be removed");
        assertFalse(root.getChildren().contains(friendlyUnit), "Friendly unit should be removed from the scene graph");
        assertFalse(root.getChildren().contains(enemyUnit), "Enemy unit should be removed from the scene graph");
    }

    @Test
    void testGenerateEnemyFire() {
        FighterPlane enemyPlane = new TestFighterPlane();
        unitManager.addEnemyUnit(enemyPlane);

        // Generate fire
        unitManager.generateEnemyFire();

        // Verify that a projectile was added
        List<ActiveActorDestructible> enemyProjectiles = unitManager.getEnemyProjectiles();
        assertEquals(1, enemyProjectiles.size(), "Enemy should fire one projectile");
        assertTrue(root.getChildren().contains(enemyProjectiles.get(0)), "Projectile should be added to the scene graph");
    }

    @Test
    void testUpdateActors() {
        ActiveActorDestructible actor = new TestActorDestructible();
        unitManager.getFriendlyUnits().add(actor);

        // Update actors
        unitManager.updateActors();

        // Verify the actor's `updateActor` method was called
        assertTrue(((TestActorDestructible) actor).isUpdated(), "Actor's updateActor method should be called");
    }

    private static class TestActorDestructible extends ActiveActorDestructible {
        private boolean updated = false;

        public TestActorDestructible() {
            super("", 10, 0, 0); // Provide an empty string for the resource path to avoid loading
        }

        @Override
        public void updatePosition() {
            // No-op for testing
        }

        @Override
        public void updateActor() {
            updated = true;
        }

        @Override
        public void takeDamage() {
            destroy();
        }

        public boolean isUpdated() {
            return updated;
        }
    }


    private static class TestFighterPlane extends FighterPlane {
        public TestFighterPlane() {
            super("", 10, 0, 0, 1); // Provide an empty string for the resource path to avoid loading
        }

        @Override
        public ActiveActorDestructible fireProjectile() {
            return new TestActorDestructible();
        }

        @Override
        public void updatePosition() {
            // No-op for testing
        }

        @Override
        public void updateActor() {
            // No-op for testing
        }
    }
}
