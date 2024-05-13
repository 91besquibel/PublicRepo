import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TabManagerMenuAction implements MenuAction {

    @Override
    public void execute() {
        // Create table, buttons, and border pane layout
        TableView<TabInfo> tableView = new TableView<>();
        TableColumn<TabInfo, String> creationDateColumn = new TableColumn<>("Creation Date");
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        
        TableColumn<TabInfo, String> creatorColumn = new TableColumn<>("Creator");
        creatorColumn.setCellValueFactory(new PropertyValueFactory<>("creator"));
        
        TableColumn<TabInfo, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        
        TableColumn<TabInfo, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        
        TableColumn<TabInfo, String> filePathColumn = new TableColumn<>("File Path");
        filePathColumn.setCellValueFactory(new PropertyValueFactory<>("filePath"));
        
        TableColumn<TabInfo, String> visibilityColumn = new TableColumn<>("Visibility");
        visibilityColumn.setCellValueFactory(new PropertyValueFactory<>("visibility"));

        tableView.getColumns().addAll(creationDateColumn, creatorColumn, typeColumn, titleColumn, filePathColumn, visibilityColumn);

        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        Button saveButton = new Button("Save");
        Button closeButton = new Button("Close");

        VBox buttonBox = new VBox(10);
        buttonBox.getChildren().addAll(addButton, editButton, deleteButton, saveButton, closeButton);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(tableView);
        borderPane.setRight(buttonBox);

        // Add button functionality
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
                    String settingsFilePath = SettingMenuAction.getFilePath(); // Assuming getFilePath() returns the filepath
                    String filepath = generateFilePath(title, settingsFilePath);
                    TabInfo newTab = new TabInfo(now.format(formatter), creator, type, title, filepath, "Visible");
                    tableView.getItems().add(newTab);
                    createTabFolder(filepath);
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

        // Add other button functionality (to be implemented)

        Stage stage = new Stage();
        stage.setScene(new Scene(borderPane, 800, 600));
        stage.setTitle("Tab Manager");
        stage.show();
    }

    private String generateFilePath(String title, String settingsFilePath) {
        return settingsFilePath + "/" + title;
    }


    private void createTabFolder(String filepath) {
        // Implementation depends on your specific requirements and file system operations
    }
}
