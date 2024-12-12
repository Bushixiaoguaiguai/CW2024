package com.example.demo.level.screens;

import com.example.demo.display.WinImage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * Represents the victory screen displayed when the player wins the game.
 * <p>
 * The WinScreen provides an image indicating victory and options for the player to:
 * <ul>
 *     <li>Retry the game in Infinity Mode.</li>
 *     <li>Return to the main menu.</li>
 * </ul>
 */
public class WinScreen {

    private final Scene scene;

    /**
     * Constructs a new {@code WinScreen} with the specified screen dimensions and button actions.
     *
     * @param screenWidth         the width of the screen.
     * @param screenHeight        the height of the screen.
     * @param infinityModeCallback a {@link Runnable} action invoked when the "Try Infinity Mode" button is clicked.
     * @param mainMenuCallback    a {@link Runnable} action invoked when the "Main Menu" button is clicked.
     */
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

    /**
     * Creates a styled button with the specified text and action.
     *
     * @param text   the label of the button.
     * @param action the {@link Runnable} action invoked when the button is clicked.
     * @return the created {@link Button}.
     */
    private Button createButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 18px; -fx-padding: 10px 20px;");
        button.setOnAction(e -> action.run());
        return button;
    }

    /**
     * Retrieves the {@link Scene} for the WinScreen.
     *
     * @return the {@link Scene} containing the WinScreen layout.
     */
    public Scene getScene() {
        return scene;
    }
}
