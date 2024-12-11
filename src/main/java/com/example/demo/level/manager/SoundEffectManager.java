package com.example.demo.level.manager;

import com.example.demo.display.SoundEffectPlayer;

/**
 * Manages sound effects for the game, including explosions and shooting sounds.
 * This class uses a singleton pattern to ensure a single instance handles sound effects.
 */
public class SoundEffectManager {

    private static SoundEffectManager instance;

    private final SoundEffectPlayer explosionSound;
    private final SoundEffectPlayer shootSound;

    private double explosionVolume;
    private double shootVolume;

    /**
     * Private constructor to initialize sound effects.
     */
    private SoundEffectManager() {
        explosionSound = new SoundEffectPlayer("/com/example/demo/audios/explosion.mp3");
        shootSound = new SoundEffectPlayer("/com/example/demo/audios/shoot.mp3");
    }

    /**
     * Returns the singleton instance of the SoundEffectManager.
     *
     * @return The singleton instance of SoundEffectManager.
     */
    public static SoundEffectManager getInstance() {
        if (instance == null) {
            instance = new SoundEffectManager();
        }
        return instance;
    }

    /**
     * Plays the explosion sound effect.
     */
    public void playExplosionSound() {
        explosionSound.play();
    }

    /**
     * Plays the shooting sound effect.
     */
    public void playShootSound() {
        shootSound.play();
    }

    /**
     * Sets the volume for the explosion sound effect.
     *
     * @param volume A double value between 0.0 (mute) and 1.0 (maximum volume).
     */
    public void setExplosionVolume(double volume) {
        explosionSound.setVolume(volume);
        explosionVolume = volume;
    }

    /**
     * Sets the volume for the shooting sound effect.
     *
     * @param volume A double value between 0.0 (mute) and 1.0 (maximum volume).
     */
    public void setShootVolume(double volume) {
        shootSound.setVolume(volume);
        shootVolume = volume;
    }

    /**
     * Gets the current volume of the explosion sound effect.
     *
     * @return A double value representing the explosion sound volume.
     */
    public double getExplosionVolume() {
        return explosionVolume;
    }

    /**
     * Gets the current volume of the shooting sound effect.
     *
     * @return A double value representing the shooting sound volume.
     */
    public double getShootVolume() {
        return shootVolume;
    }
}
