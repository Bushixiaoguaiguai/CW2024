package com.example.demo.level.manager;

import com.example.demo.actors.shared.ActiveActorDestructible;
import com.example.demo.actors.friends.UserPlane;
import com.example.demo.effect.Explosion;
import javafx.scene.Group;
import javafx.geometry.Bounds;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CollisionDetectTest {

    private CollisionDetect collisionDetect;
    private Group root;

    @BeforeEach
    void setUp() {
        root = new Group(); // Using an actual Group instance
        collisionDetect = new CollisionDetect(root);
    }

    @Test
    void testHandleCollisions() {
        // Create two actors with intersecting bounds
        ActiveActorDestructible actor1 = new TestActor(0, 0, 10, 10);
        ActiveActorDestructible actor2 = new TestActor(5, 5, 15, 15);

        List<ActiveActorDestructible> list1 = new ArrayList<>();
        List<ActiveActorDestructible> list2 = new ArrayList<>();
        list1.add(actor1);
        list2.add(actor2);

        collisionDetect.handleCollisions(list1, list2);

        assertTrue(((TestActor) actor1).isDamaged);
        assertTrue(((TestActor) actor2).isDamaged);
    }

    @Test
    void testHandleCollisionsWithExplosion() {
        // Create two actors with intersecting bounds
        ActiveActorDestructible actor1 = new TestActor(0, 0, 10, 10);
        ActiveActorDestructible actor2 = new TestActor(5, 5, 15, 15);

        List<ActiveActorDestructible> list1 = new ArrayList<>();
        List<ActiveActorDestructible> list2 = new ArrayList<>();
        list1.add(actor1);
        list2.add(actor2);

        collisionDetect.handleCollisionsWithExplosion(list1, list2);

        assertTrue(((TestActor) actor1).isDamaged);
        assertTrue(((TestActor) actor2).isDamaged);

        assertEquals(1, collisionDetect.activeExplosions.size());
        Explosion explosion = collisionDetect.activeExplosions.get(0);
        assertNotNull(explosion);
    }

    @Test
    void testUpdateExplosions() {
        // Add two explosions: one finished, one ongoing
        TestExplosion explosion1 = new TestExplosion(false); // Not finished
        TestExplosion explosion2 = new TestExplosion(true);  // Finished

        collisionDetect.activeExplosions.add(explosion1);
        collisionDetect.activeExplosions.add(explosion2);

        collisionDetect.updateExplosions();

        assertEquals(1, collisionDetect.activeExplosions.size());
        assertTrue(collisionDetect.activeExplosions.contains(explosion1));
        assertFalse(collisionDetect.activeExplosions.contains(explosion2));
    }

    @Test
    void testHandleEnemyPenetration() {
        ActiveActorDestructible enemy = new TestActor(1100, 0, 1110, 10);
        ActiveActorDestructible userPlane = new TestActor(0, 0, 10, 10);

        List<ActiveActorDestructible> enemies = new ArrayList<>();
        enemies.add(enemy);

        collisionDetect.handleEnemyPenetration(enemies, userPlane, 1000);

        assertTrue(((TestActor) userPlane).isDamaged);
        assertTrue(((TestActor) enemy).isDestroyed);
    }

    @Test
    void testHandleAllCollisions() {
        ActiveActorDestructible friendlyUnit = new TestActor(0, 0, 10, 10);
        ActiveActorDestructible enemyUnit = new TestActor(5, 5, 15, 15);
        ActiveActorDestructible userProjectile = new TestActor(0, 0, 10, 10);
        ActiveActorDestructible enemyProjectile = new TestActor(5, 5, 15, 15);
        UserPlane user = new UserPlane(5); // Stubbed UserPlane

        List<ActiveActorDestructible> friendlyUnits = new ArrayList<>();
        List<ActiveActorDestructible> enemyUnits = new ArrayList<>();
        List<ActiveActorDestructible> userProjectiles = new ArrayList<>();
        List<ActiveActorDestructible> enemyProjectiles = new ArrayList<>();

        friendlyUnits.add(friendlyUnit);
        enemyUnits.add(enemyUnit);
        userProjectiles.add(userProjectile);
        enemyProjectiles.add(enemyProjectile);

        collisionDetect.handleAllCollisions(
                friendlyUnits,
                enemyUnits,
                userProjectiles,
                enemyProjectiles,
                user,
                1000
        );

        assertTrue(((TestActor) friendlyUnit).isDamaged || ((TestActor) enemyUnit).isDamaged);
        assertTrue(((TestActor) userProjectile).isDamaged || ((TestActor) enemyProjectile).isDamaged);
    }

    // Helper class to mock ActiveActorDestructible behavior
    static class TestActor implements ActiveActorDestructible {
        private final Bounds bounds;
        boolean isDamaged = false;
        boolean isDestroyed = false;

        TestActor(double minX, double minY, double maxX, double maxY) {
            this.bounds = new javafx.geometry.Bounds(minX, minY, maxX, maxY, 0, 0);
        }

        @Override
        public Bounds getBoundsInParent() {
            return bounds;
        }

        @Override
        public void takeDamage() {
            isDamaged = true;
        }

        @Override
        public void destroy() {
            isDestroyed = true;
        }

        @Override
        public double getTranslateX() {
            return bounds.getMinX();
        }
    }

    // Helper class to simulate Explosion behavior
    static class TestExplosion extends Explosion {
        private final boolean isFinished;

        TestExplosion(boolean isFinished) {
            super(null, 0, 0, 0, 0, 0, null);
            this.isFinished = isFinished;
        }

        @Override
        public boolean isFinished() {
            return isFinished;
        }
    }
}
