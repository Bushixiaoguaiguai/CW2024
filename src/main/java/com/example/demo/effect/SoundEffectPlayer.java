package com.example.demo.effect;

import javafx.scene.media.AudioClip;

import java.net.URL;

public class SoundEffectPlayer {

    private final AudioClip audioClip;

    /**
     * Initializes the sound effect player with a given sound file.
     * @param soundFilePath The relative file path to the sound effect (e.g., "/com/example/demo/audios/explosion.mp3").
     */
    public SoundEffectPlayer(String soundFilePath) {
        URL resource = getClass().getResource(soundFilePath);
        if (resource != null) {
            audioClip = new AudioClip(resource.toString());
        } else {
            throw new IllegalArgumentException("Sound file not found: " + soundFilePath);
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
     * Adjusts the volume of the sound effect.
     * @param volume A double value between 0.0 (mute) and 1.0 (maximum volume).
     */
    public void setVolume(double volume) {
        if (audioClip != null) {
            audioClip.setVolume(Math.max(0.0, Math.min(volume, 1.0))); // Clamp volume between 0.0 and 1.0
        }
    }
}
