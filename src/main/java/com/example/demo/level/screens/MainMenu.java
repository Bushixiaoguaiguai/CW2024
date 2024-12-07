package com.example.demo.level.screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainMenu {

    private final Scene scene;

    public MainMenu(double screenWidth, double screenHeight, Runnable startGameCallback, Runnable infinityModeCallback, Runnable quitCallback) {
        // Buttons
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> startGameCallback.run());

        Button infinityButton = new Button("InfinityMode");
        infinityButton.setOnAction(e -> infinityModeCallback.run());

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> confirmQuit(quitCallback));

        // Layout
        VBox layout = new VBox(20, startButton, infinityButton, quitButton);
        layout.setAlignment(Pos.CENTER);

        // Scene
        scene = new Scene(layout, screenWidth, screenHeight);
    }

    private void confirmQuit(Runnable quitCallback) {
        Alert confirmation = new Alert(AlertType.CONFIRMATION, "Are you sure you want to quit?");
        confirmation.setHeaderText(null);
        confirmation.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) { // Check against ButtonType.OK
                quitCallback.run(); // Exit the application
            }
        });
    }


    public Scene getScene() {
        return scene;
    }
}
