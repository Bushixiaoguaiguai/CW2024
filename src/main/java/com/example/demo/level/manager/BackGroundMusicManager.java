package com.example.demo.level.manager;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class BackGroundMusicManager {

    private static BackGroundMusicManager instance;
    private MediaPlayer mediaPlayer;

    private BackGroundMusicManager() {
        // Load the background music
        URL resource = getClass().getResource("/com/example/demo/audios/bgm.mp3");
        if (resource != null) {
            Media media = new Media(resource.toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the music
        } else {
            System.err.println("Background music file not found!");
        }
    }

    public static BackGroundMusicManager getInstance() {
        if (instance == null) {
            instance = new BackGroundMusicManager();
        }
        return instance;
    }

    /**
     * Starts playing the music.
     */
    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    /**
     * Stops the music.
     */
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    /**
     * Adjusts the volume of the music.
     *
     * @param volume A double value between 0.0 (mute) and 1.0 (maximum volume).
     */
    public void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(Math.max(0.0, Math.min(volume, 1.0))); // Ensure volume is within [0.0, 1.0]
        }
    }

    public double getVolume(){
        return mediaPlayer.getVolume();
    }
}
