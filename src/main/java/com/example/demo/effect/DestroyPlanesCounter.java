package com.example.demo.effect;

import com.example.demo.actors.friends.UserPlane;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class DestroyPlanesCounter extends StackPane {

    private final Label counterLabel;

    public DestroyPlanesCounter(UserPlane userPlane) {
        // Initialize the label
        counterLabel = new Label("Planes Destroyed: 0");
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
