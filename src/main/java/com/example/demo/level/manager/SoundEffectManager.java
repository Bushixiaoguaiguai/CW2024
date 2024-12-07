package com.example.demo.level.manager;

import com.example.demo.effect.SoundEffectPlayer;

public class SoundEffectManager {

    private final SoundEffectPlayer explosionSound;
    private final SoundEffectPlayer shootSound;

    public SoundEffectManager() {
        explosionSound = new SoundEffectPlayer("D:\\year2\\CW2024\\src\\main\\resources\\com\\example\\demo\\audios\\explosion.mp3");
        shootSound = new SoundEffectPlayer("D:\\year2\\CW2024\\src\\main\\resources\\com\\example\\demo\\audios\\shoot.mp3");
    }

    public void playExplosionSound() {
        explosionSound.play();
    }

    public void playShootSound() {
        shootSound.play();
    }
}
