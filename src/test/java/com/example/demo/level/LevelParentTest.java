package com.example.demo.level;

import com.example.demo.JavaFXTestBase;
import com.example.demo.actors.shared.ActiveActorDestructible;
import javafx.scene.Scene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LevelParentTest extends JavaFXTestBase {

    private TestLevelParent levelParent;

    @BeforeEach
    void setUp() {
        levelParent = new TestLevelParent("/com/example/demo/images/background1.jpg", 800, 600, 5);
    }

    @Test
    void testInitializeScene() {
        Scene scene = levelParent.initializeScene();
        assertNotNull(scene, "Scene should not be null");
        assertEquals(800, scene.getHeight(), "Scene height should be 800");
        assertEquals(600, scene.getWidth(), "Scene width should be 600");
    }

    @Test
    void testStartGame() {
        levelParent.startGame();
        assertTrue(levelParent.getTimeline().getStatus() == javafx.animation.Animation.Status.RUNNING, "Timeline should be running after starting the game");
    }

    @Test
    void testCleanup() {
        levelParent.cleanup();
        assertTrue(levelParent.getRoot().getChildren().isEmpty(), "Root children should be cleared after cleanup");
        assertEquals(0, levelParent.getFriendlyUnits().size(), "Friendly units should be cleared after cleanup");
        assertEquals(0, levelParent.getEnemyUnits().size(), "Enemy units should be cleared after cleanup");
    }

    @Test
    void testGoToNextLevel() {
        levelParent.goToNextLevel(LevelType.WIN);
        assertEquals(LevelType.WIN, levelParent.getNextLevel(), "Next level should be WIN after transition");
    }

    // Helper concrete implementation for testing
    private static class TestLevelParent extends LevelParent {

        private LevelType nextLevel;

        public TestLevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
            super(backgroundImageName, screenHeight, screenWidth, playerInitialHealth);
        }

        @Override
        protected void checkIfGameOver() {
            // No-op for testing
        }

        @Override
        protected void spawnEnemyUnits() {
            // No-op for testing
        }

        @Override
        protected LevelView instantiateLevelView() {
            return new LevelView(getRoot(), 3);
        }

        @Override
        public void goToNextLevel(LevelType levelType) {
            super.goToNextLevel(levelType);
            nextLevel = levelType;
        }

        public LevelType getNextLevel() {
            return nextLevel;
        }
    }

    // Test utility class for ActiveActorDestructible
    private static class TestActorDestructible extends ActiveActorDestructible {

        public TestActorDestructible() {
            super("/test/resources/dummy_image.png", 50, 0, 0);
            // Ensure the dummy_image.png exists in the test resources directory
        }

        @Override
        public void updatePosition() {
            // No-op for testing
        }

        @Override
        public void updateActor() {
            // No-op for testing
        }

        @Override
        public void takeDamage() {
            destroy();
        }
    }

}
