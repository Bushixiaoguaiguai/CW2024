package com.example.demo.level.manager;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

/**
 * Manages background music for the game.
 * This class provides functionality to play, stop, and adjust the volume of background music.
 * It follows the Singleton design pattern to ensure a single instance manages the music.
 */
public class BackgroundMusicManager {

    private static BackgroundMusicManager instance;
    private MediaPlayer mediaPlayer;

    /**
     * Private constructor to initialize the background music manager.
     * Loads the background music and sets it to loop indefinitely.
     */
    private BackgroundMusicManager() {
        URL resource = getClass().getResource("/com/example/demo/audios/bgm.mp3");
        if (resource != null) {
            Media media = new Media(resource.toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        } else {
            System.err.println("Background music file not found!");
        }
    }

    /**
     * Returns the singleton instance of the BackgroundMusicManager.
     *
     * @return The singleton instance of BackgroundMusicManager.
     */
    public static BackgroundMusicManager getInstance() {
        if (instance == null) {
            instance = new BackgroundMusicManager();
        }
        return instance;
    }

    /**
     * Starts playing the background music.
     */
    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    /**
     * Stops the background music.
     */
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    /**
     * Adjusts the volume of the background music.
     *
     * @param volume A double value between 0.0 (mute) and 1.0 (maximum volume).
     */
    public void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(Math.max(0.0, Math.min(volume, 1.0)));
        }
    }

    /**
     * Retrieves the current volume of the background music.
     *
     * @return A double value representing the current volume.
     */
    public double getVolume() {
        return mediaPlayer != null ? mediaPlayer.getVolume() : 0.0;
    }
}
