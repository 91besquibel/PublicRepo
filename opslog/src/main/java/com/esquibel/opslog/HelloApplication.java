package com.esquibel.opslog;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create a button node
        Button button = new Button("Click me!");

        // Create a label to display the message
        Label messageLabel = new Label();

        // Create a layout container (VBox) and add the button and label to it
        VBox root = new VBox(10); // 10 is the spacing between children
        root.setPadding(new Insets(20)); // Set padding around the VBox
        root.getChildren().addAll(button, messageLabel);

        // Create a scene with the layout container as its root node
        Scene scene = new Scene(root, 300, 200);

        // Set the scene for the stage
        primaryStage.setScene(scene);

        // Set the title of the stage
        primaryStage.setTitle("JavaFX Button Click Example");

        // Add an event handler to the button
        button.setOnAction(e -> {
            // Display a message in the label when the button is clicked
            messageLabel.setText("Button clicked!");
        });

        // Show the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}