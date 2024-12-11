package com.example.demo.effect;

import com.example.demo.JavaFXTestBase;
import com.example.demo.actors.enemies.Boss;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HpBarDisplayTest extends JavaFXTestBase {

    private Boss boss;
    private HpBarDisplay hpBarDisplay;

    @BeforeEach
    public void setUp() {
        boss = new Boss() {
            private final SimpleIntegerProperty currentHealth = new SimpleIntegerProperty(100);
            private final int maxHealth = 100;
        };

        hpBarDisplay = new HpBarDisplay(boss);
    }

    @Test
    public void testHpBarInitialWidth() {
        double expectedWidth = HpBarDisplay.getBarWidth();
        Rectangle hpBar = (Rectangle) hpBarDisplay.getChildren().get(1);
        assertEquals(expectedWidth, hpBar.getWidth(), "The initial width of the health bar should match the maximum width.");
    }

    @Test
    public void testHpBarWidthAfterDamage() {
        for (int i=0; i<5; i++) {
            boss.takeDamage(); // Reduce health to zero
        } // Reduce health by half
        double expectedWidth = HpBarDisplay.getBarWidth() / 2.0;
        Rectangle hpBar = (Rectangle) hpBarDisplay.getChildren().get(1);
        assertEquals(expectedWidth, hpBar.getWidth(), "The health bar width should be halved after reducing health by 50%.");
    }

    @Test
    public void testHpBarWidthAtZeroHealth() {
        for (int i=0; i<10; i++) {
            boss.takeDamage(); // Reduce health to zero
        }
        double expectedWidth = 0.0;
        Rectangle hpBar = (Rectangle) hpBarDisplay.getChildren().get(1);
        assertEquals(expectedWidth, hpBar.getWidth(), "The health bar width should be zero when health is zero.");
    }

    @Test
    public void testHpBarColor() {
        Rectangle hpBar = (Rectangle) hpBarDisplay.getChildren().get(1);
        Color expectedColor = Color.rgb(255, 0, 0); // Red
        assertEquals(expectedColor.toString(), hpBar.getFill().toString(), "The health bar should be red.");
    }

    @Test
    public void testBackgroundColor() {
        Rectangle background = (Rectangle) hpBarDisplay.getChildren().get(0);
        Color expectedColor = Color.rgb(211, 211, 211); // Light Gray
        assertEquals(expectedColor.toString(), background.getFill().toString(), "The background bar should be light gray.");
    }
}