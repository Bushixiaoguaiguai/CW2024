package com.example.demo.level.screens;

import com.example.demo.display.GameOverImage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * Represents the game over screen displayed when the player loses the game.
 * <p>
 * The GameOverScreen provides:
 * <ul>
 *     <li>A "Game Over" image to indicate the end of the game.</li>
 *     <li>A "Retry" button to restart the current level.</li>
 *     <li>A "Main Menu" button to navigate back to the main menu.</li>
 * </ul>
 */
public class GameOverScreen {

    private final Scene scene;

    /**
     * Constructs a {@code GameOverScreen} with the specified dimensions and button actions.
     *
     * @param screenWidth      the width of the game over screen.
     * @param screenHeight     the height of the game over screen.
     * @param retryCallback    a {@link Runnable} action triggered when the "Retry" button is clicked.
     * @param mainMenuCallback a {@link Runnable} action triggered when the "Main Menu" button is clicked.
     */
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

    /**
     * Creates a styled button with the specified text and action.
     *
     * @param text   the label of the button.
     * @param action the {@link Runnable} action triggered when the button is clicked.
     * @return the created {@link Button}.
     */
    private Button createButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 18px; -fx-padding: 10px 20px;");
        button.setOnAction(e -> action.run());
        return button;
    }

    /**
     * Retrieves the {@link Scene} for the GameOverScreen.
     *
     * @return the {@link Scene} containing the GameOverScreen layout.
     */
    public Scene getScene() {
        return scene;
    }
}
