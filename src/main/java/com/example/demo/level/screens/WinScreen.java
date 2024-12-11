package com.example.demo.level.screens;

import com.example.demo.display.WinImage;
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
        Button retryButton = createButton("Try Infinity Mode", infinityModeCallback);
        Button mainMenuButton = createButton("Main Menu", mainMenuCallback);

        // Layout
        VBox layout = new VBox(20, winImage, retryButton, mainMenuButton);
        layout.setAlignment(Pos.CENTER);

        // Scene
        scene = new Scene(layout, screenWidth, screenHeight);
    }

    private Button createButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 18px; -fx-padding: 10px 20px;");
        button.setOnAction(e -> action.run());
        return button;
    }

    public Scene getScene() {
        return scene;
    }
}
