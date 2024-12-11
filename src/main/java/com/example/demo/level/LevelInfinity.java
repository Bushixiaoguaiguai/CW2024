package com.example.demo.level;

import com.example.demo.actors.shared.ActiveActorDestructible;
import com.example.demo.actors.enemies.EnemyPlane;
import com.example.demo.display.DestroyPlanesCounter;

/**
 * Represents the "Infinity" level in the game, where enemies spawn continuously.
 * This level ends when the player's plane is destroyed.
 */
public class LevelInfinity extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int TOTAL_ENEMIES = 8;
    private static final double ENEMY_SPAWN_PROBABILITY = 0.4;
    private DestroyPlanesCounter destroyPlanesCounter;

    /**
     * Constructs a LevelInfinity instance with the specified screen dimensions.
     *
     * @param screenHeight The height of the game screen.
     * @param screenWidth  The width of the game screen.
     */
    public LevelInfinity(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
    }

    /**
     * Checks if the game is over by determining if the player's plane is destroyed.
     * If the player's plane is destroyed, the game is lost.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
    }

    /**
     * Spawns enemy units based on the current number of enemies and spawn probability.
     */
    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
                addEnemyUnit(newEnemy);
            }
        }
    }

    /**
     * Instantiates the level view for this level.
     *
     * @return A {@link LevelView} object representing the visual elements of the level.
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH); // Same as LevelOne
    }

    /**
     * Adds an enemy unit to the level.
     *
     * @param enemy The {@link ActiveActorDestructible} enemy unit to add.
     */
    @Override
    public void addEnemyUnit(ActiveActorDestructible enemy) {
        super.addEnemyUnit(enemy);
    }

    /**
     * Initializes friendly units, including the player's plane and the destroy planes counter.
     */
    @Override
    public void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
        destroyPlanesCounter = new DestroyPlanesCounter(getUser());
        destroyPlanesCounter.setLayoutX(getScreenWidth() - 200); // Adjust X position
        destroyPlanesCounter.setLayoutY(20); // Adjust Y position
        getRoot().getChildren().add(destroyPlanesCounter);
    }

    /**
     * Cleans up level-specific elements during level transition.
     */
    @Override
    protected void onCleanup() {
        getRoot().getChildren().remove(destroyPlanesCounter);
        System.out.println("LevelInfinity-specific cleanup complete.");
    }
}
