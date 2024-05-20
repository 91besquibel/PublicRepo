package com.esquibel.opslog;

import javafx.application.Application;
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

        // Set event handlers for buttons
        settingButton.setOnAction(event -> executeMenuAction(new SettingMenuAction()));
        tabManagerButton.setOnAction(event -> executeMenuAction(new TabManagerMenuAction(tabPane)));
        historyViewButton.setOnAction(event -> executeMenuAction(new HistoryViewMenuAction()));
        helpButton.setOnAction(event -> executeMenuAction(new HelpMenuAction()));

        // Create tab pane and add a welcome tab
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); // Disable tab closing
        VBox welcomeContent = new VBox(new Label("Welcome to the App!"));
        tabPane.getTabs().add(new Tab("Welcome", welcomeContent));

        // Create border pane to arrange components
        BorderPane root = new BorderPane();
        VBox buttonBox = new VBox(settingButton, tabManagerButton, historyViewButton, helpButton);
        root.setLeft(buttonBox);
        root.setCenter(tabPane);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("My JavaFX App");
        primaryStage.show();
    }

    private void executeMenuAction(MenuAction action) {
        System.out.println("Menu item clicked: " + action);
        action.execute();
    }

    public static void main(String[] args) {
        launch(args);
    }
}