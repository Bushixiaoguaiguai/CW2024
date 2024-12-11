package com.example.demo.level.manager;

import com.example.demo.JavaFXTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SoundEffectManagerTest extends JavaFXTestBase {

    private SoundEffectManager soundEffectManager;

    @BeforeEach
    void setUp() {
        soundEffectManager = SoundEffectManager.getInstance();
    }

    @Test
    void testSingletonBehavior() {
        SoundEffectManager instance1 = SoundEffectManager.getInstance();
        SoundEffectManager instance2 = SoundEffectManager.getInstance();
        assertSame(instance1, instance2, "SoundEffectManager should be a singleton");
    }

    @Test
    void testSetExplosionVolume() {
        double volume = 0.5;
        soundEffectManager.setExplosionVolume(volume);

        // Verify the volume was set correctly
        assertEquals(volume, soundEffectManager.getExplosionVolume(), "Explosion volume should be set correctly");
    }

    @Test
    void testSetShootVolume() {
        double volume = 0.7;
        soundEffectManager.setShootVolume(volume);

        // Verify the volume was set correctly
        assertEquals(volume, soundEffectManager.getShootVolume(), "Shoot volume should be set correctly");
    }

    @Test
    void testPlayExplosionSound() {
        assertDoesNotThrow(() -> soundEffectManager.playExplosionSound(), "Playing explosion sound should not throw exceptions");
    }

    @Test
    void testPlayShootSound() {
        assertDoesNotThrow(() -> soundEffectManager.playShootSound(), "Playing shoot sound should not throw exceptions");
    }
}
