package com.example.demo.effect;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class MusicPlayer {

    private MediaPlayer mediaPlayer;

    public MusicPlayer(String musicFilePath) {
        try {
            Media media = new Media(new File(musicFilePath).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            // Set music to loop
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        } catch (Exception e) {
            System.err.println("Failed to load music: " + e.getMessage());
        }
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
     * Pauses the music.
     */
    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    /**
     * Stops the music completely.
     */
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    /**
     * Adjusts the volume of the music.
     * @param volume A double value between 0.0 (mute) and 1.0 (maximum volume).
     */
    public void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(Math.max(0.0, Math.min(volume, 1.0))); // Ensure volume is within [0.0, 1.0]
        }
    }

    /**
     * Checks if the music is currently playing.
     * @return True if the music is playing, false otherwise.
     */
    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }
}
