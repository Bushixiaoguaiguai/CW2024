package com.example.demo.level.screens;

import com.example.demo.effect.WinImage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class WinScreen {

    private final Scene scene;

    public WinScreen(double screenWidth, double screenHeight, Runnable infinityModeCallback, Runnable mainMenuCallback) {
        // Victory Image
        WinImage winImage = new WinImage((screenWidth - 600) / 2, (screenHeight - 500) / 2);

        // Buttons
        Button retryButton = new Button("Try Infinity Mode");
        retryButton.setOnAction(e -> infinityModeCallback.run());

        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setOnAction(e -> mainMenuCallback.run());

        // Layout
        VBox layout = new VBox(20, winImage, retryButton, mainMenuButton);
        layout.setAlignment(Pos.CENTER);

        // Scene
        scene = new Scene(layout, screenWidth, screenHeight);
    }

    public Scene getScene() {
        return scene;
    }
}
