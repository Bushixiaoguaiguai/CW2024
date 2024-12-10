package com.example.demo.level.manager;

import com.example.demo.effect.SoundEffectPlayer;

public class SoundEffectManager {

    private static SoundEffectManager instance;

    private final SoundEffectPlayer explosionSound;
    private final SoundEffectPlayer shootSound;

    private double explosionVolume;
    private double shootVolume;

    private SoundEffectManager() {
        explosionSound = new SoundEffectPlayer("/com/example/demo/audios/explosion.mp3");
        shootSound = new SoundEffectPlayer("/com/example/demo/audios/shoot.mp3");
    }

    public static SoundEffectManager getInstance() {
        if (instance == null) {
            instance = new SoundEffectManager();
        }
        return instance;
    }

    public void playExplosionSound() {
        explosionSound.play();
    }

    public void playShootSound() {
        shootSound.play();
    }

    public void setExplosionVolume(double volume) {
        explosionSound.setVolume(volume);
        explosionVolume = volume;
    }

    public void setShootVolume(double volume) {
        shootSound.setVolume(volume);
        shootVolume = volume;
    }

    public double getExplosionVolume(){
        return explosionVolume;
    }

    public double getShootVolume(){
        return shootVolume;
    }
}
