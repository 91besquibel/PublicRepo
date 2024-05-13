import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;

public class TabManagerMenuAction implements MenuAction {

    @Override
    public void execute() {
        BorderPane borderPane = new BorderPane();
        TableView<TabInfo> tableView = new TableView<>();
        TableColumn<TabInfo, String> creationDateCol = new TableColumn<>("Creation Date");
        creationDateCol.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        TableColumn<TabInfo, String> creatorCol = new TableColumn<>("Creator");
        creatorCol.setCellValueFactory(new PropertyValueFactory<>("creator"));
        TableColumn<TabInfo, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<TabInfo, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<TabInfo, String> filePathCol = new TableColumn<>("File Path");
        filePathCol.setCellValueFactory(new PropertyValueFactory<>("filePath"));
        TableColumn<TabInfo, String> visibilityCol = new TableColumn<>("Visibility");
        visibilityCol.setCellValueFactory(new PropertyValueFactory<>("visibility"));
        tableView.getColumns().addAll(creationDateCol, creatorCol, typeCol, titleCol, filePathCol, visibilityCol);
        ObservableList<TabInfo> data = FXCollections.observableArrayList();
        tableView.setItems(data);

        VBox buttonVBox = new VBox();
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            Stage popupStage = new Stage();
            popupStage.setTitle("Add New Tab");

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            Label creationDateLabel = new Label("Creation Date:");
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            Label creationDateValueLabel = new Label(now.format(formatter));
            Label creatorLabel = new Label("Creator:");
            TextField creatorTextField = new TextField();
            Label typeLabel = new Label("Type:");
            ComboBox<String> typeComboBox = new ComboBox<>();
            typeComboBox.getItems().addAll("Calendar", "OpsLog");
            Label titleLabel = new Label("Title:");
            TextField titleTextField = new TextField();

            gridPane.addRow(0, creationDateLabel, creationDateValueLabel);
            gridPane.addRow(1, creatorLabel, creatorTextField);
            gridPane.addRow(2, typeLabel, typeComboBox);
            gridPane.addRow(3, titleLabel, titleTextField);

            Button createButton = new Button("Create");
            Button cancelButton = new Button("Cancel");
            createButton.setOnAction(e -> {
                String creator = creatorTextField.getText().trim();
                String type = typeComboBox.getValue();
                String title = titleTextField.getText().trim();
                if (!creator.isEmpty() && type != null && !title.isEmpty()) {
                    // Call generateFilePath with the filepath obtained from the SettingMenuAction class
                    String settingsFilePath = SettingMenuAction.getFilePath(); // Retrieve file path
                    String filepath = generateFilePath(title, settingsFilePath);
                    TabInfo newTab = new TabInfo(now.format(formatter), creator, type, title, filepath, "Visible");
                    tableView.getItems().add(newTab);
                    createTabFolder(filepath);
                    if ("Calendar".equals(type)) {
                        CalendarTab calendarTab = new CalendarTab();
                        addTabToPane(calendarTab, title);
                    } else if ("OpsLog".equals(type)) {
                        OpsLogTab opsLogTab = new OpsLogTab();
                        addTabToPane(opsLogTab, title);
                    }
                    popupStage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill in all fields.");
                    alert.showAndWait();
                }
            });
            cancelButton.setOnAction(e -> popupStage.close());

            gridPane.addRow(4, createButton, cancelButton);

            popupStage.setScene(new Scene(gridPane));
            popupStage.show();
        });
        buttonVBox.getChildren().addAll(addButton);

        borderPane.setCenter(tableView);
        borderPane.setRight(buttonVBox);

        // Create scene and stage to display the Tab Manager
        Scene scene = new Scene(borderPane, 800, 600); // Adjust width and height as needed
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Tab Manager");
        stage.show();
    }

    private String generateFilePath(String title, String settingsFilePath) {
        return settingsFilePath + "/" + title;
    }

    private void createTabFolder(String filepath) {
        // Create folder using the filepath
        File folder = new File(filepath);
        if (!folder.exists()) {
            boolean success = folder.mkdirs();
            if (!success) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to create folder for tab.");
                alert.showAndWait();
            }
        }
    }

    private void addTabToPane(Tab tab, String title) {
        // Add tab to your TabPane or TabView instance in your UI
        // For example:
        tab.setText(title); // Set tab title
        tabPane.getTabs().add(tab); // Assuming tabPane is your TabPane or TabView instance
    }
}
