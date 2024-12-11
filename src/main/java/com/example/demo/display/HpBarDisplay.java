package com.example.demo.display;

import com.example.demo.actors.enemies.Boss;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 * The HpBarDisplay class represents a graphical health bar for a boss character.
 *
 * <p>This class displays a health bar that visually represents the current health of the boss.
 * The health bar dynamically adjusts its width as the boss's health changes.</p>
 */
public class HpBarDisplay extends StackPane {

    private static final int BAR_HEIGHT = 20;
    private static final int BAR_WIDTH = 400;
    private static final int BACKGROUND_COLOR = 0xD3D3D3; // Light Gray
    private static final int HP_COLOR = 0xFF0000; // Red

    private final Rectangle background;
    private final Rectangle hpBar;

    /**
     * Constructs an HpBarDisplay for the given boss character.
     *
     * @param boss the {@link Boss} whose health will be displayed on the health bar
     */
    public HpBarDisplay(Boss boss) {
        // Background bar setup
        background = new Rectangle(BAR_WIDTH, BAR_HEIGHT);
        background.setFill(javafx.scene.paint.Color.rgb(
                (BACKGROUND_COLOR >> 16) & 0xFF,
                (BACKGROUND_COLOR >> 8) & 0xFF,
                BACKGROUND_COLOR & 0xFF
        ));

        // Health bar setup
        hpBar = new Rectangle(BAR_WIDTH, BAR_HEIGHT);
        hpBar.setFill(javafx.scene.paint.Color.rgb(
                (HP_COLOR >> 16) & 0xFF,
                (HP_COLOR >> 8) & 0xFF,
                HP_COLOR & 0xFF
        ));

        // Add bars to the stack pane
        this.getChildren().addAll(background, hpBar);
        setAlignment(hpBar, Pos.TOP_LEFT);

        // Bind the health bar width to the boss's health property
        bindHpBar(boss);
    }

    /**
     * Binds the health bar's width to the boss's current health.
     *
     * <p>The width of the health bar dynamically changes in proportion to the boss's
     * current health relative to its maximum health.</p>
     *
     * @param boss the {@link Boss} whose health determines the width of the health bar
     */
    private void bindHpBar(Boss boss) {
        hpBar.widthProperty().bind(
                Bindings.createDoubleBinding(
                        () -> (boss.currentHealthProperty().get() / (double) boss.getMaxHealth()) * BAR_WIDTH,
                        boss.currentHealthProperty()
                )
        );
    }

    /**
     * Returns the width of the health bar.
     *
     * @return the fixed width of the health bar
     */
    public static int getBarWidth() {
        return BAR_WIDTH;
    }
}