import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The MainApp class represents the main entry point of the application.
 * It initializes the user interface and manages the overall application structure.
 */
public class MainApp extends Application {

    /**
     * The start method initializes the primary stage and sets up the user interface.
     *
     * @param primaryStage The primary stage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        // Create menu bar with menu items
        MenuBar menuBar = createMenuBar();

        // Create a tab pane to hold draggable tabs
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);

        // Initialize the DraggableTabManager to enable draggable tabs
        DraggableTabManager draggableTabManager = new DraggableTabManager(tabPane);

        // Create a border pane for the main layout
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(tabPane);

        // Create a scene with the main layout
        Scene scene = new Scene(root, 800, 600);

        // Set the scene to the primary stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Draggable Tabs Example");
        primaryStage.show();
    }

    /**
     * Create a menu bar with menu items.
     *
     * @return The created menu bar.
     */
    private MenuBar createMenuBar() {
        Menu menuSettings = new Menu("Settings");
        Menu menuTabManager = new Menu("Tab Manager");
        Menu menuViewHistory = new Menu("View History");
        Menu menuHelp = new Menu("Help");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menuSettings, menuTabManager, menuViewHistory, menuHelp);

        return menuBar;
    }

    /**
     * The main method launches the JavaFX application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        launch(args);
    }
}
