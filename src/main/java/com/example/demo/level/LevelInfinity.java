package com.example.demo.level;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.EnemyPlane;
import com.example.demo.effect.DestroyPlanesCounter;

public class LevelInfinity extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int TOTAL_ENEMIES = 8;
    private static final double ENEMY_SPAWN_PROBABILITY = .4;
    private DestroyPlanesCounter destroyPlanesCounter;

    public LevelInfinity(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
    }

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

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH); // Same as LevelOne
    }

    @Override
    public void addEnemyUnit(ActiveActorDestructible enemy) {
        super.addEnemyUnit(enemy);
    }

    @Override
    public void initializeFriendlyUnits(){
        getRoot().getChildren().add(getUser());
        destroyPlanesCounter = new DestroyPlanesCounter(getUser());
        destroyPlanesCounter.setLayoutX(getScreenWidth() - 200); // Adjust X position
        destroyPlanesCounter.setLayoutY(20); // Adjust Y position
        getRoot().getChildren().add(destroyPlanesCounter);
    }

    @Override
    protected void onCleanup(){
        getRoot().getChildren().remove(destroyPlanesCounter);
        System.out.println("LevelInfinity-specific cleanup complete.");
    }
}
