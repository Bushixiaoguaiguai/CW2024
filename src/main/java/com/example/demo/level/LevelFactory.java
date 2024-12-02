package com.example.demo.level;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class LevelFactory {

    private final double screenWidth;
    private final double screenHeight;
    private final Map<LevelType, Supplier<LevelParent>> levelMap;

    public LevelFactory(double screenWidth, double screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.levelMap = new HashMap<>();

        // Register levels here
        registerLevel(LevelType.LEVEL_ONE, () -> new LevelOne(screenHeight, screenWidth));
        registerLevel(LevelType.LEVEL_TWO, () -> new LevelTwo(screenHeight, screenWidth));
    }

    // Register levels dynamically
    private void registerLevel(LevelType levelType, Supplier<LevelParent> levelSupplier) {
        levelMap.put(levelType, levelSupplier);
    }

    // Create level based on LevelType
    public LevelParent createLevel(LevelType levelType) {
        Supplier<LevelParent> levelSupplier = levelMap.get(levelType);
        if (levelSupplier != null) {
            return levelSupplier.get();
        } else {
            throw new IllegalArgumentException("Unsupported LevelType: " + levelType);
        }
    }
}
