package com.example.demo.level.screens;

import com.example.demo.level.manager.BackgroundMusicManager;
import com.example.demo.level.manager.SoundEffectManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Represents the main menu screen of the game.
 * <p>
 * The MainMenuScreen provides:
 * <ul>
 *     <li>A background image for the main menu.</li>
 *     <li>Buttons to start the game, enter infinity mode, or quit the application.</li>
 *     <li>Mute/unmute buttons for background music (BGM), shooting sound effects, and explosion sound effects.</li>
 * </ul>
 */
public class MainMenuScreen {

    private final Scene scene;

    /**
     * Constructs a {@code MainMenuScreen} with the specified dimensions and button actions.
     *
     * @param screenWidth        the width of the main menu screen.
     * @param screenHeight       the height of the main menu screen.
     * @param startGameCallback  a {@link Runnable} action triggered when the "Start Game" button is clicked.
     * @param infinityModeCallback a {@link Runnable} action triggered when the "Infinity Mode" button is clicked.
     * @param quitCallback       a {@link Runnable} action triggered when the "Quit" button is clicked.
     */
    public MainMenuScreen(double screenWidth, double screenHeight, Runnable startGameCallback, Runnable infinityModeCallback, Runnable quitCallback) {
        // Background Image
        ImageView background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/com/example/demo/images/main.png")).toExternalForm()));
        background.setFitWidth(screenWidth);
        background.setFitHeight(screenHeight);
        background.setPreserveRatio(false);

        // Buttons
        Button startButton = createButton("Start Game", startGameCallback);
        Button infinityButton = createButton("Infinity Mode", infinityModeCallback);
        Button quitButton = createButton("Quit", quitCallback);

        // Mute/Unmute Buttons
        final boolean[] bgmMuted = {false};
        final boolean[] shootMuted = {false};
        final boolean[] explosionMuted = {false};

        double bgmOriginalVolume = BackgroundMusicManager.getInstance().getVolume();
        double shootOriginalVolume = SoundEffectManager.getInstance().getShootVolume();
        double explosionOriginalVolume = SoundEffectManager.getInstance().getExplosionVolume();

        Runnable bgmSetVolumeAction = () -> {
            if (bgmMuted[0]) {
                BackgroundMusicManager.getInstance().setVolume(0.0);
            } else {
                BackgroundMusicManager.getInstance().setVolume(bgmOriginalVolume);
            }
        };

        Runnable shootSetVolumeAction = () -> {
            if (shootMuted[0]) {
                SoundEffectManager.getInstance().setShootVolume(0.0);
            } else {
                SoundEffectManager.getInstance().setShootVolume(shootOriginalVolume);
            }
        };

        Runnable explosionSetVolumeAction = () -> {
            if (explosionMuted[0]) {
                SoundEffectManager.getInstance().setExplosionVolume(0.0);
            } else {
                SoundEffectManager.getInstance().setExplosionVolume(explosionOriginalVolume);
            }
        };

        Button bgmButton = createMuteButton("BGM", bgmMuted, bgmSetVolumeAction);
        Button shootButton = createMuteButton("Shoot", shootMuted, shootSetVolumeAction);
        Button explosionButton = createMuteButton("Explosion", explosionMuted, explosionSetVolumeAction);

        HBox muteContainer = new HBox(10, bgmButton, shootButton, explosionButton);
        muteContainer.setAlignment(Pos.CENTER);

        // Layout
        VBox buttonContainer = new VBox(20, startButton, infinityButton, quitButton, muteContainer);
        buttonContainer.setAlignment(Pos.CENTER);

        StackPane root = new StackPane();
        root.getChildren().addAll(background, buttonContainer);
        root.setAlignment(Pos.CENTER);

        // Scene
        scene = new Scene(root, screenWidth, screenHeight);
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
     * Creates a styled mute/unmute button with the specified text, state, and volume adjustment action.
     *
     * @param text           the label of the mute/unmute button (e.g., "BGM", "Shoot").
     * @param isMuted        a boolean array indicating the current mute state.
     * @param setVolumeAction the {@link Runnable} action to adjust the volume.
     * @return the created {@link Button}.
     */
    private Button createMuteButton(String text, final boolean[] isMuted, final Runnable setVolumeAction) {
        Button button = new Button(isMuted[0] ? "Unmute " + text : "Mute " + text);
        button.setStyle("-fx-font-size: 12px; -fx-padding: 5px 10px;");
        button.setOnAction(e -> {
            isMuted[0] = !isMuted[0];
            button.setText(isMuted[0] ? "Unmute " + text : "Mute " + text);
            setVolumeAction.run();
        });
        return button;
    }

    /**
     * Retrieves the {@link Scene} for the MainMenuScreen.
     *
     * @return the {@link Scene} containing the MainMenuScreen layout.
     */
    public Scene getScene() {
        return scene;
    }
}
