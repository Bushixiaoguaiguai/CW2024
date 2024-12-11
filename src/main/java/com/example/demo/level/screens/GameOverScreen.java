package com.example.demo.level.screens;

import com.example.demo.display.GameOverImage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class GameOverScreen {

    private final Scene scene;

    public GameOverScreen(double screenWidth, double screenHeight, Runnable retryCallback, Runnable mainMenuCallback) {
        // Game Over Image
        GameOverImage gameOverImage = new GameOverImage((screenWidth - 600) / 2, (screenHeight - 500) / 2);

        // Buttons
        Button retryButton = createButton("Retry", retryCallback);
        Button mainMenuButton = createButton("Main Menu", mainMenuCallback);

        // Layout
        VBox layout = new VBox(20, gameOverImage, retryButton, mainMenuButton);
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
