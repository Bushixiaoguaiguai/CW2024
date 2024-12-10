package com.example.demo.level.screens;

import com.example.demo.level.manager.BackGroundMusicManager;
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

public class MainMenu {

    private final Scene scene;

    public MainMenu(double screenWidth, double screenHeight, Runnable startGameCallback, Runnable infinityModeCallback, Runnable quitCallback) {
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

        double bgmOriginalVolume = BackGroundMusicManager.getInstance().getVolume();
        double shootOriginalVolume = SoundEffectManager.getInstance().getShootVolume();
        double explosionOriginalVolume = SoundEffectManager.getInstance().getExplosionVolume();

        Runnable bgmSetVolumeAction = () -> {
            if (bgmMuted[0]) {
                BackGroundMusicManager.getInstance().setVolume(0.0);
            } else {
                BackGroundMusicManager.getInstance().setVolume(bgmOriginalVolume);
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

    private Button createButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 18px; -fx-padding: 10px 20px;");
        button.setOnAction(e -> action.run());
        return button;
    }

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

    public Scene getScene() {
        return scene;
    }
}