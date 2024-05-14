package com.esquibel.opslog;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class App extends Application {

    private TabPane tabPane;

    @Override
    public void start(Stage primaryStage) {
        // Create background canvas
        Canvas backgroundCanvas = new Canvas(800, 600);
        backgroundCanvas.setStyle("-fx-background-color: lightgrey;");

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

        // Create rounded rectangle for button box
        Rectangle buttonBoxBackground = new Rectangle(0, 0, 200, 600);
        buttonBoxBackground.setArcWidth(20);
        buttonBoxBackground.setArcHeight(20);
        buttonBoxBackground.setFill(Color.WHITE);

        // Stack button box and its background
        StackPane buttonStackPane = new StackPane();
        buttonStackPane.getChildren().addAll(buttonBoxBackground, buttonBox);

        // Create tab pane and add a welcome tab
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); // Disable tab closing
        VBox welcomeContent = new VBox(new Label("Welcome to the App!"));
        tabPane.getTabs().add(new Tab("Welcome", welcomeContent));

        // Create rounded rectangle for tab pane
        Rectangle tabPaneBackground = new Rectangle(0, 0, 600, 600);
        tabPaneBackground.setArcWidth(20);
        tabPaneBackground.setArcHeight(20);
        tabPaneBackground.setFill(Color.WHITE);

        // Stack tab pane and its background
        StackPane tabStackPane = new StackPane();
        tabStackPane.getChildren().addAll(tabPaneBackground, tabPane);

        // Create HBox to hold button stack and tab stack
        HBox hbox = new HBox(buttonStackPane, tabStackPane);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(10));

        // Create border pane to arrange components
        BorderPane root = new BorderPane();
        root.setCenter(backgroundCanvas); // Add background canvas
        root.setLeft(hbox); // Add HBox with button stack and tab stack

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("My JavaFX App");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
