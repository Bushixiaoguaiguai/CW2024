package com.example.demo.level.manager;

import com.example.demo.JavaFXTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BackGroundMusicManagerTest extends JavaFXTestBase {

    private BackgroundMusicManager bgmManager;

    @BeforeEach
    public void setUp() {
        bgmManager = BackgroundMusicManager.getInstance();
    }

    @Test
    public void testSingletonBehavior() {
        BackgroundMusicManager instance1 = BackgroundMusicManager.getInstance();
        BackgroundMusicManager instance2 = BackgroundMusicManager.getInstance();
        assertSame(instance1, instance2, "BackgroundMusicManager should follow the singleton pattern");
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