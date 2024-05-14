package com.esquibel.opslog;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private TabPane tabPane;

    @Override
    public void start(Stage primaryStage) {
        // Create buttons
        Button settingButton = new Button("Setting");
        Button tabManagerButton = new Button("Tab Manager");
        Button historyViewButton = new Button("History View");
        Button helpButton = new Button("Help");

        // Find the widest button
        double maxWidth = Math.max(Math.max(settingButton.prefWidth(-1), tabManagerButton.prefWidth(-1)),
                                   Math.max(historyViewButton.prefWidth(-1), helpButton.prefWidth(-1)));

        // Set all buttons to the same width
        settingButton.setPrefWidth(maxWidth);
        tabManagerButton.setPrefWidth(maxWidth);
        historyViewButton.setPrefWidth(maxWidth);
        helpButton.setPrefWidth(maxWidth);

        // Center text on buttons
        settingButton.setAlignment(Pos.CENTER);
        tabManagerButton.setAlignment(Pos.CENTER);
        historyViewButton.setAlignment(Pos.CENTER);
        helpButton.setAlignment(Pos.CENTER);

        // Create VBox for buttons
        VBox buttonBox = new VBox(10, settingButton, tabManagerButton, historyViewButton, helpButton);
        buttonBox.setAlignment(Pos.CENTER_LEFT); // Align buttons to the left side
        buttonBox.setPadding(new Insets(10)); // Add padding around the buttons

        // Create tab pane and add a welcome tab
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); // Disable tab closing
        VBox welcomeContent = new VBox(new Label("Welcome to the App!"));
        tabPane.getTabs().add(new Tab("Welcome", welcomeContent));

        // Create border pane to arrange components
        BorderPane root = new BorderPane();
        root.setLeft(buttonBox);
        root.setCenter(tabPane);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("My JavaFX App");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

