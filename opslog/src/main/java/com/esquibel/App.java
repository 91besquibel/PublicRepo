import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
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

        // Create minimize, exit, and fullscreen buttons
        Button minimizeButton = new Button("Minimize");
        Button exitButton = new Button("Exit");
        Button fullscreenButton = new Button("Fullscreen");

        // Create tab bar
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); // Disable tab closing

        // Create scene
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setRight(new ToolBar(minimizeButton, exitButton, fullscreenButton));
        root.setCenter(tabPane);

        // Set event handlers for menu items
        menuSetting.setOnAction(event -> executeMenuAction(new SettingMenuAction()));
        menuTabManager.setOnAction(event -> executeMenuAction(new TabManagerMenuAction()));
        menuHistoryView.setOnAction(event -> executeMenuAction(new HistoryViewMenuAction()));
        menuHelp.setOnAction(event -> executeMenuAction(new HelpMenuAction()));

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
