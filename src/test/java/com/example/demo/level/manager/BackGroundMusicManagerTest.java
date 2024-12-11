package com.example.demo.level.manager;

import com.example.demo.JavaFXTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BackGroundMusicManagerTest extends JavaFXTestBase {

    private BackGroundMusicManager bgmManager;

    @BeforeEach
    public void setUp() {
        bgmManager = BackGroundMusicManager.getInstance();
    }

    @Test
    public void testSingletonBehavior() {
        BackGroundMusicManager instance1 = BackGroundMusicManager.getInstance();
        BackGroundMusicManager instance2 = BackGroundMusicManager.getInstance();
        assertSame(instance1, instance2, "BackGroundMusicManager should follow the singleton pattern");
    }

    @Test
    public void testPlayMusic() {
        assertDoesNotThrow(() -> bgmManager.play(), "Calling play() should not throw an exception");
    }

    @Test
    public void testStopMusic() {
        assertDoesNotThrow(() -> bgmManager.stop(), "Calling stop() should not throw an exception");
    }

    @Test
    public void testSetVolume() {
        bgmManager.setVolume(0.5);
        assertEquals(0.5, bgmManager.getVolume(), 0.01, "Volume should be set to 0.5");

        bgmManager.setVolume(1.0);
        assertEquals(1.0, bgmManager.getVolume(), 0.01, "Volume should be set to 1.0");

        bgmManager.setVolume(-1.0);
        assertEquals(0.0, bgmManager.getVolume(), 0.01, "Volume should be clamped to 0.0");
    }

    @Test
    public void testVolumeClamping() {
        bgmManager.setVolume(2.0);
        assertEquals(1.0, bgmManager.getVolume(), 0.01, "Volume should be clamped to 1.0 when set above the max");

        bgmManager.setVolume(-0.5);
        assertEquals(0.0, bgmManager.getVolume(), 0.01, "Volume should be clamped to 0.0 when set below the min");
    }
}