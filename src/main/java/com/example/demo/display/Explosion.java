package com.example.demo.display;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * The Explosion class represents an animated explosion display using a sprite sheet.
 *
 * <p>This class handles the setup, animation, and cleanup of an explosion display in a JavaFX scene.
 * It animates frames of a sprite sheet over a specified duration and removes itself from the scene once finished.</p>
 */
public class Explosion {

    private final ImageView explosionImageView;
    private final Timeline explosionTimeline;
    private final Parent root;

    private boolean isFinished;
    private boolean isPlaying;

    /**
     * Constructs an Explosion object to display an animated explosion display.
     *
     * @param texturePath        the path to the sprite sheet image for the explosion animation
     * @param x                  the x-coordinate of the explosion's position in the scene
     * @param y                  the y-coordinate of the explosion's position in the scene
     * @param width              the width of the explosion animation
     * @param height             the height of the explosion animation
     * @param totalAnimationTime the total duration of the explosion animation in seconds
     * @param root               the parent node ({@link Parent}) to which the explosion will be added
     */
    public Explosion(String texturePath, double x, double y, double width, double height, double totalAnimationTime, Parent root) {
        this.root = root;
        this.isFinished = false;
        this.isPlaying = false;

        // Load the explosion sprite sheet
        Image explosionSpriteSheet = new Image(getClass().getResource(texturePath).toExternalForm());
        explosionImageView = new ImageView(explosionSpriteSheet);
        explosionImageView.setPreserveRatio(true);
        explosionImageView.setFitWidth(width);
        explosionImageView.setFitHeight(height);
        explosionImageView.setTranslateX(x);
        explosionImageView.setTranslateY(y);

        // Calculate frame dimensions based on the sprite sheet
        int rows = 4; // Number of rows in the sprite sheet
        int cols = 4; // Number of columns in the sprite sheet
        double frameWidth = explosionSpriteSheet.getWidth() / cols;
        double frameHeight = explosionSpriteSheet.getHeight() / rows;

        // Initialize the timeline for the explosion animation
        explosionTimeline = new Timeline();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int frameIndex = row * cols + col;
                double frameTimeValue = frameIndex * (totalAnimationTime / (rows * cols));

                int finalRow = row;
                int finalCol = col;

                Duration frameTime = Duration.seconds(frameTimeValue);
                KeyFrame frame = new KeyFrame(frameTime, e -> {
                    explosionImageView.setViewport(new Rectangle2D(
                            finalCol * frameWidth, finalRow * frameHeight, frameWidth, frameHeight));
                });
                explosionTimeline.getKeyFrames().add(frame);
            }
        }

        explosionTimeline.setCycleCount(1);
        explosionTimeline.setAutoReverse(false);
        explosionTimeline.setOnFinished(e -> cleanup());
    }

    /**
     * Starts the explosion animation and adds it to the scene.
     *
     * <p>If the explosion is not already playing, this method will add it to the root group
     * and begin the animation.</p>
     */
    public void start() {
        if (!isPlaying) {
            if (root instanceof Group group) {
                group.getChildren().add(explosionImageView);
            }
            explosionTimeline.play();
            isPlaying = true;
        }
    }

    /**
     * Stops the explosion animation and removes it from the scene.
     *
     * <p>This method stops the animation immediately and performs cleanup to remove
     * the explosion from the scene graph.</p>
     */
    public void stop() {
        explosionTimeline.stop();
        cleanup();
    }

    /**
     * Checks if the explosion animation has finished.
     *
     * @return true if the explosion animation is complete, false otherwise
     */
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * Cleans up the explosion by removing it from the scene and marking it as finished.
     *
     * <p>This method is called automatically when the explosion animation ends,
     * or manually if the explosion is stopped prematurely.</p>
     */
    private void cleanup() {
        if (root instanceof Group group) {
            group.getChildren().remove(explosionImageView);
        }
        isFinished = true;
        isPlaying = false;
    }
}