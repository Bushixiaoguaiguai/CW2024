package com.example.demo.display;

import com.example.demo.actors.friends.UserPlane;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * A UI component that displays the number of planes destroyed by the user.
 * Updates dynamically based on the user's kill count.
 */
public class DestroyPlanesCounter extends StackPane {

    /**
     * Creates a new DestroyPlanesCounter and binds it to the user's kill count.
     *
     * @param userPlane The {@link UserPlane} whose kill count is displayed.
     */
    public DestroyPlanesCounter(UserPlane userPlane) {
        // Initialize the label
        Label counterLabel = new Label("Planes Destroyed: 0");
        counterLabel.setStyle("-fx-font-size: 20; -fx-text-fill: white;");

        // Bind label text to the numberOfKills from UserPlane
        counterLabel.textProperty().bind(
                Bindings.createStringBinding(
                        () -> "Planes Destroyed: " + userPlane.getNumberOfKills(),
                        userPlane.numberOfKillsProperty()
                )
        );

        // Add the label to the StackPane
        this.getChildren().add(counterLabel);
    }
}