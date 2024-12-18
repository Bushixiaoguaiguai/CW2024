module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;


    opens com.example.demo.controller to javafx.fxml;
    exports com.example.demo.controller;
    opens com.example.demo.level to javafx.fxml;
//    opens com.example.demo.actors to javafx.fxml;
    opens com.example.demo.display to javafx.fxml;
    opens com.example.demo.level.screens to javafx.fxml;
    opens com.example.demo.actors.shared to javafx.fxml;
    opens com.example.demo.actors.friends to javafx.fxml;
    opens com.example.demo.actors.enemies to javafx.fxml;
    opens com.example.demo.level.manager to javafx.fxml;
}