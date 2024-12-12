package com.example.demo.level.screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * The {@code PauseScreen} class represents the pause menu screen displayed during gameplay.
 * It provides options for the player to resume the game, return to the main menu, or quit the game.
 */
public class PauseScreen {

    private final Scene scene;

    /**
     * Constructs a new {@code PauseScreen}.
     *
     * @param screenWidth       the width of the screen.
     * @param screenHeight      the height of the screen.
     * @param resumeCallback    the callback action to resume the game.
     * @param mainMenuCallback  the callback action to return to the main menu.
     * @param quitCallback      the callback action to quit the game.
     */
    public PauseScreen(double screenWidth, double screenHeight, Runnable resumeCallback, Runnable mainMenuCallback, Runnable quitCallback) {
        // Buttons
        Button resumeButton = createButton("Resume", resumeCallback);
        Button mainMenuButton = createButton("Main Menu", mainMenuCallback);
        Button quitButton = createButton("Quit", quitCallback);

        // Layout
        VBox layout = new VBox(20, resumeButton, mainMenuButton, quitButton);
        layout.setAlignment(Pos.CENTER);

        // Scene
        scene = new Scene(layout, screenWidth, screenHeight);
    }

    /**
     * Creates a styled button with the specified text and action.
     *
     * @param text   the text to display on the button.
     * @param action the action to execute when the button is clicked.
     * @return a styled {@link Button} with the specified text and action.
     */
    private Button createButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 18px; -fx-padding: 10px 20px;");
        button.setOnAction(e -> action.run());
        return button;
    }

    /**
     * Returns the {@link Scene} associated with this pause menu.
     *
     * @return the pause menu {@link Scene}.
     */
    public Scene getScene() {
        return scene;
    }
}
