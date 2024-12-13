package com.example.demo.display;

import javafx.scene.media.AudioClip;

import java.net.URL;

/**
 * Plays sound effects for the game using audio files.
 */
public class SoundEffectPlayer {

    final AudioClip audioClip;

    /**
     * Initializes the sound display player with a given sound file.
     *
     * @param soundFilePath The relative file path to the sound display (e.g., "/com/example/demo/audios/explosion.mp3").
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
     * Plays the sound display.
     */
    public void play() {
        if (audioClip != null) {
            audioClip.play();
        }
    }

    /**
     * Adjusts the volume of the sound display.
     *
     * @param volume A double value between 0.0 (mute) and 1.0 (maximum volume).
     */
    public void setVolume(double volume) {
        if (audioClip != null) {
            audioClip.setVolume(Math.max(0.0, Math.min(volume, 1.0))); // Clamp volume between 0.0 and 1.0
        }
    }
}
