package com.example.demo.effect;

import javafx.scene.media.AudioClip;

import java.io.File;

public class SoundEffectPlayer {

    private final AudioClip audioClip;

    /**
     * Initializes the sound effect player with a given sound file.
     * @param soundFilePath The file path to the sound effect (e.g., "src/main/resources/sounds/explosion.mp3").
     */
    public SoundEffectPlayer(String soundFilePath) {
        try {
            File soundFile = new File(soundFilePath);
            audioClip = new AudioClip(soundFile.toURI().toString());
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to load sound file: " + soundFilePath, e);
        }
    }

    /**
     * Plays the sound effect.
     */
    public void play() {
        if (audioClip != null) {
            audioClip.play();
        }
    }

    /**
     * Plays the sound effect with a specified volume.
     * @param volume A double value between 0.0 (mute) and 1.0 (maximum volume).
     */
    public void play(double volume) {
        if (audioClip != null) {
            audioClip.setVolume(Math.max(0.0, Math.min(volume, 1.0))); // Ensure volume is within [0.0, 1.0]
            audioClip.play();
        }
    }
}
