package com.example.demo;

import javafx.application.Platform;

public class JavaFXTestBase {
    static {
        Platform.startup(() -> {});
    }
}
