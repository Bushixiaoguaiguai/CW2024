package com.example.demo.level;

public class LevelFactory {

    private final double screenWidth;
    private final double screenHeight;

    public LevelFactory(double screenWidth, double screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public LevelParent createLevel(LevelType levelType) {
        return switch (levelType) {
            case LEVEL_ONE -> new LevelOne(screenHeight, screenWidth);
            case LEVEL_TWO -> new LevelTwo(screenHeight, screenWidth);
        };
    }
}
