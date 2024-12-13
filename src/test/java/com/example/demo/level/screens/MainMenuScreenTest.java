package com.example.demo.level.screens;

import com.example.demo.JavaFXTestBase;
import com.example.demo.level.manager.BackgroundMusicManager;
import com.example.demo.level.manager.SoundEffectManager;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainMenuScreenTest extends JavaFXTestBase {

    private static final double SCREEN_WIDTH = 800.0;
    private static final double SCREEN_HEIGHT = 600.0;

    private Runnable startGameCallback;
    private Runnable infinityModeCallback;
    private Runnable quitCallback;

    private MainMenuScreen mainMenuScreen;

    @BeforeEach
    void setUp() {
        startGameCallback = () -> {};
        infinityModeCallback = () -> {};
        quitCallback = () -> {};

        mainMenuScreen = new MainMenuScreen(SCREEN_WIDTH, SCREEN_HEIGHT, startGameCallback, infinityModeCallback, quitCallback);
    }

    @Test
    void testSceneCreation() {
        Scene scene = mainMenuScreen.getScene();
        assertNotNull(scene);
        assertEquals(SCREEN_WIDTH, scene.getWidth());
        assertEquals(SCREEN_HEIGHT, scene.getHeight());
    }

    @Test
    void testButtonLabels() {
        Scene scene = mainMenuScreen.getScene();
        StackPane root = (StackPane) scene.getRoot();
        VBox buttonContainer = (VBox) root.getChildren().get(1);

        Button startButton = (Button) buttonContainer.getChildren().get(0);
        assertEquals("Start Game", startButton.getText());

        Button infinityButton = (Button) buttonContainer.getChildren().get(1);
        assertEquals("Infinity Mode", infinityButton.getText());

        Button quitButton = (Button) buttonContainer.getChildren().get(2);
        assertEquals("Quit", quitButton.getText());
    }

    @Test
    void testMuteButtonLabels() {
        Scene scene = mainMenuScreen.getScene();
        StackPane root = (StackPane) scene.getRoot();
        VBox buttonContainer = (VBox) root.getChildren().get(1);
        HBox muteContainer = (HBox) buttonContainer.getChildren().get(3);

        Button bgmButton = (Button) muteContainer.getChildren().get(0);
        assertEquals("Mute BGM", bgmButton.getText());

        Button shootButton = (Button) muteContainer.getChildren().get(1);
        assertEquals("Mute Shoot", shootButton.getText());

        Button explosionButton = (Button) muteContainer.getChildren().get(2);
        assertEquals("Mute Explosion", explosionButton.getText());
    }

    @Test
    void testStartButtonAction() {
        Scene scene = mainMenuScreen.getScene();
        StackPane root = (StackPane) scene.getRoot();
        VBox buttonContainer = (VBox) root.getChildren().get(1);
        Button startButton = (Button) buttonContainer.getChildren().get(0);

        startButton.fire();
        // Verify that startGameCallback is called (noop in this case)
    }

    @Test
    void testInfinityModeButtonAction() {
        Scene scene = mainMenuScreen.getScene();
        StackPane root = (StackPane) scene.getRoot();
        VBox buttonContainer = (VBox) root.getChildren().get(1);
        Button infinityButton = (Button) buttonContainer.getChildren().get(1);

        infinityButton.fire();
        // Verify that infinityModeCallback is called (noop in this case)
    }

    @Test
    void testQuitButtonAction() {
        Scene scene = mainMenuScreen.getScene();
        StackPane root = (StackPane) scene.getRoot();
        VBox buttonContainer = (VBox) root.getChildren().get(1);
        Button quitButton = (Button) buttonContainer.getChildren().get(2);

        quitButton.fire();
        // Verify that quitCallback is called (noop in this case)
    }

    @Test
    void testBgmMuteButton() {
        BackgroundMusicManager bgmManager = BackgroundMusicManager.getInstance();
        double originalVolume = bgmManager.getVolume();

        Scene scene = mainMenuScreen.getScene();
        StackPane root = (StackPane) scene.getRoot();
        VBox buttonContainer = (VBox) root.getChildren().get(1);
        HBox muteContainer = (HBox) buttonContainer.getChildren().get(3);
        Button bgmButton = (Button) muteContainer.getChildren().get(0);

        bgmButton.fire();
        assertEquals(0.0, bgmManager.getVolume());

        bgmButton.fire();
        assertEquals(originalVolume, bgmManager.getVolume());
    }

    @Test
    void testShootMuteButton() {
        SoundEffectManager shootManager = SoundEffectManager.getInstance();
        double originalVolume = shootManager.getShootVolume();

        Scene scene = mainMenuScreen.getScene();
        StackPane root = (StackPane) scene.getRoot();
        VBox buttonContainer = (VBox) root.getChildren().get(1);
        HBox muteContainer = (HBox) buttonContainer.getChildren().get(3);
        Button shootButton = (Button) muteContainer.getChildren().get(1);

        shootButton.fire();
        assertEquals(0.0, shootManager.getShootVolume());

        shootButton.fire();
        assertEquals(originalVolume, shootManager.getShootVolume());
    }

    @Test
    void testExplosionMuteButton() {
        SoundEffectManager explosionManager = SoundEffectManager.getInstance();
        double originalVolume = explosionManager.getExplosionVolume();

        Scene scene = mainMenuScreen.getScene();
        StackPane root = (StackPane) scene.getRoot();
        VBox buttonContainer = (VBox) root.getChildren().get(1);
        HBox muteContainer = (HBox) buttonContainer.getChildren().get(3);
        Button explosionButton = (Button) muteContainer.getChildren().get(2);

        explosionButton.fire();
        assertEquals(0.0, explosionManager.getExplosionVolume());

        explosionButton.fire();
        assertEquals(originalVolume, explosionManager.getExplosionVolume());
    }
}