package com.esquibel.opslog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;

public class TabManagerMenuAction implements MenuAction {

    private final TabPane tabPane; // Added reference to TabPane

    public TabManagerMenuAction(TabPane tabPane) { // Constructor to initialize TabPane
        this.tabPane = tabPane;
    }

    public static class TabInfo {
        private String creationDate;
        private String creator;
        private String type;
        private String title;
        private String filePath;
        private String visibility;

        // Constructor
        public TabInfo(String creationDate, String creator, String type, String title, String filePath, String visibility) {
            this.creationDate = creationDate;
            this.creator = creator;
            this.type = type;
            this.title = title;
            this.filePath = filePath;
            this.visibility = visibility;
        }

        // Getters and setters
        public String getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(String creationDate) {
            this.creationDate = creationDate;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getVisibility() {
            return visibility;
        }

        public void setVisibility(String visibility) {
            this.visibility = visibility;
        }
    }

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
                    data.add(newTab);
                    createTabFolder(filepath);
                    if ("Calendar".equals(type)) {
                        CalendarTab calendarTab = new CalendarTab(tabPane, this); // Pass TabPane and TabManagerMenuAction
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
        // Create a new tab based on tabInfo and add it to the tabPane
        tab.setText(title);
        // Assuming tabPane is defined somewhere in your application
        tabPane.getTabs().add(tab);
    }
}


