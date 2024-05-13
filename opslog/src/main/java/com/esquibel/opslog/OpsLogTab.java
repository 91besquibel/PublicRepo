import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;

public class OpsLogTab extends javafx.scene.control.Tab {

    public OpsLogTab() {
        super("Ops Log");
        createLayout();
    }

    private void createLayout() {
        VBox opsLogLayout = new VBox(10);
        VBox importantNotesBar = new VBox(10);
        importantNotesBar.setPadding(new Insets(10));
        importantNotesBar.setStyle("-fx-background-color: lightblue;");
        Label importantNotesLabel = new Label("Important Notes");
        importantNotesLabel.setStyle("-fx-font-weight: bold;");
        importantNotesBar.getChildren().add(importantNotesLabel);

        VBox rollingLogBar = new VBox(10);
        rollingLogBar.setPadding(new Insets(10));
        rollingLogBar.setStyle("-fx-background-color: lightgray;");
        Label rollingLogLabel = new Label("Rolling Log");
        rollingLogLabel.setStyle("-fx-font-weight: bold;");
        rollingLogBar.getChildren().add(rollingLogLabel);

        ScrollPane rollingLogScrollPane = new ScrollPane();
        VBox logContent = new VBox(5);
        logContent.setPadding(new Insets(10));
        // Add log entries (can be dynamically populated)
        logContent.getChildren().addAll(
                new Label("Sample Log Entry 1"),
                new Label("Sample Log Entry 2")
        );
        rollingLogScrollPane.setContent(logContent);
        rollingLogScrollPane.setFitToWidth(true);
        rollingLogScrollPane.setPrefHeight(200);

        Button addLogButton = new Button("Add");
        Button editLogButton = new Button("Edit");
        Button deleteLogButton = new Button("Delete");

        VBox logButtons = new VBox(10);
        logButtons.setPadding(new Insets(10));
        logButtons.getChildren().addAll(addLogButton, editLogButton, deleteLogButton);

        rollingLogBar.getChildren().addAll(rollingLogScrollPane, logButtons);

        opsLogLayout.getChildren().addAll(importantNotesBar, rollingLogBar);

        setContent(opsLogLayout);
    }
}

