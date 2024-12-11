package com.example.demo.level;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Factory class for creating game levels.
 *
 * <p>This class dynamically registers and creates levels based on their {@link LevelType}.</p>
 */
public class LevelFactory {

    private final Map<LevelType, Supplier<LevelParent>> levelMap;

    /**
     * Constructs a LevelFactory with the specified screen dimensions.
     *
     * @param screenWidth  The width of the game screen.
     * @param screenHeight The height of the game screen.
     */
    public LevelFactory(double screenWidth, double screenHeight) {
        this.levelMap = new HashMap<>();

        // Register levels here
        registerLevel(LevelType.LEVEL_ONE, () -> new LevelOne(screenHeight, screenWidth));
        registerLevel(LevelType.LEVEL_TWO, () -> new LevelTwo(screenHeight, screenWidth));
        registerLevel(LevelType.LEVEL_INFINITY, () -> new LevelInfinity(screenHeight, screenWidth));
    }

    /**
     * Registers a level with the factory.
     *
     * @param levelType     The type of the level to register.
     * @param levelSupplier A supplier that creates instances of the level.
     */
    private void registerLevel(LevelType levelType, Supplier<LevelParent> levelSupplier) {
        levelMap.put(levelType, levelSupplier);
    }

    /**
     * Creates a level based on the specified {@link LevelType}.
     *
     * @param levelType The type of level to create.
     * @return An instance of the level corresponding to the given {@code levelType}.
     * @throws IllegalArgumentException if the {@code levelType} is not supported.
     */
    public LevelParent createLevel(LevelType levelType) {
        Supplier<LevelParent> levelSupplier = levelMap.get(levelType);
        if (levelSupplier != null) {
            return levelSupplier.get();
        } else {
            throw new IllegalArgumentException("Unsupported LevelType: " + levelType);
        }
    }
}