package com.esquibel.opslog;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create menu bar
        MenuBar menuBar = new MenuBar();
        Menu menuSetting = new Menu("Setting");
        Menu menuTabManager = new Menu("Tab Manager");
        Menu menuHistoryView = new Menu("History View");
        Menu menuHelp = new Menu("Help");
        menuBar.getMenus().addAll(menuSetting, menuTabManager, menuHistoryView, menuHelp);

        // Create tab pane and add a welcome tab
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); // Disable tab closing
        Tab welcomeTab = new Tab("Welcome");
        VBox welcomeContent = new VBox(new Label("Welcome to the App!"));
        welcomeTab.setContent(welcomeContent);
        tabPane.getTabs().add(welcomeTab);

        // Set event handlers for menu items
        menuSetting.setOnAction(event -> executeMenuAction(new SettingMenuAction()));
        menuTabManager.setOnAction(event -> executeMenuAction(new TabManagerMenuAction(tabPane)));
        menuHistoryView.setOnAction(event -> executeMenuAction(new HistoryViewMenuAction()));
        menuHelp.setOnAction(event -> executeMenuAction(new HelpMenuAction()));

        // Create border pane to arrange components
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(tabPane);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("My JavaFX App");
        primaryStage.show();
    }

    private void executeMenuAction(MenuAction action) {
        action.execute();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

