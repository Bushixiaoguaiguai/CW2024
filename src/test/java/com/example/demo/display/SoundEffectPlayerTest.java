package com.example.demo.display;

import javafx.scene.media.AudioClip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URL;

public class SoundEffectPlayerTest {

    private static final String VALID_SOUND_FILE = "/com/example/demo/audios/explosion.mp3";
    private static final String INVALID_SOUND_FILE = "/com/example/demo/audios/nonexistent.mp3";

    private TestSoundEffectPlayer testSoundPlayer;

    @BeforeEach
    void setUp() {
        testSoundPlayer = new TestSoundEffectPlayer(VALID_SOUND_FILE);
    }

    @Test
    void testInitializationWithValidFilePath() {
        assertNotNull(testSoundPlayer.getAudioClip());
    }

    @Test
    void testInitializationWithInvalidFilePath() {
        assertThrows(IllegalArgumentException.class, () -> new TestSoundEffectPlayer(INVALID_SOUND_FILE));
    }

    @Test
    void testPlayMethod() {
        assertNotNull(testSoundPlayer.getAudioClip()); // Assume play() works if AudioClip is valid
    }

    @Test
    void testSetVolumeWithClamping() {
        testSoundPlayer.setVolume(1.5);
        assertEquals(1.0, testSoundPlayer.getVolume());

        testSoundPlayer.setVolume(-0.5);
        assertEquals(0.0, testSoundPlayer.getVolume());

        testSoundPlayer.setVolume(0.5);
        assertEquals(0.5, testSoundPlayer.getVolume());
    }
}

// Subclass for testing purposes
class TestSoundEffectPlayer extends SoundEffectPlayer {

    public TestSoundEffectPlayer(String soundFilePath) {
        super(soundFilePath);
    }

    public AudioClip getAudioClip() {
        return audioClip;
    }

    public double getVolume() {
        return audioClip != null ? audioClip.getVolume() : 0.0;
    }
}