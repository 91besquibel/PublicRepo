import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Class representing an event.
 */
public class Event {

    private String date;
    private String time;
    private String initials;
    private String title;
    private String description;
    private boolean addToCalendar;

    /**
     * Constructor for Event.
     * @param date The date of the event.
     * @param time The time of the event.
     * @param initials The initials of the person associated with the event.
     * @param title The title of the event.
     * @param description The description of the event.
     */
    public Event(String date, String time, String initials, String title, String description) {
        this.date = date;
        this.time = time;
        this.initials = initials;
        this.title = title;
        this.description = description;
        this.addToCalendar = false;
    }

    /**
     * Creates a popup dialog for adding an event.
     */
    public void createEventPopup() {
        // Create the popup dialog
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Add Event");
        dialog.setHeaderText("Fill in the details for the event");

        // Set the button types
        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        // Create the grid layout for the popup
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Add controls to the grid
        TextField initialsField = new TextField();
        initialsField.setPromptText("Enter initials");
        TextField titleField = new TextField();
        titleField.setPromptText("Enter title");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Enter description");
        CheckBox linkToCalendarCheckBox = new CheckBox("Link to Calendar");
        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();
        ComboBox<String> startTimeComboBox = new ComboBox<>();
        ComboBox<String> endTimeComboBox = new ComboBox<>();
        // Populate the time dropdowns with dummy values for demonstration
        startTimeComboBox.getItems().addAll("09:00", "10:00", "11:00");
        endTimeComboBox.getItems().addAll("10:00", "11:00", "12:00");

        grid.add(new Label("Initials:"), 0, 0);
        grid.add(initialsField, 1, 0);
        grid.add(new Label("Title:"), 0, 1);
        grid.add(titleField, 1, 1);
        grid.add(new Label("Description:"), 0, 2);
        grid.add(descriptionArea, 1, 2);
        grid.add(linkToCalendarCheckBox, 0, 3, 2, 1);
        grid.add(new Label("Calendar Range:"), 0, 4);
        grid.add(startDatePicker, 1, 4);
        grid.add(startTimeComboBox, 2, 4);
        grid.add(new Label("to"), 3, 4);
        grid.add(endDatePicker, 4, 4);
        grid.add(endTimeComboBox, 5, 4);

        // Enable/disable the create button based on the text field inputs
        Button createButton = (Button) dialog.getDialogPane().lookupButton(createButtonType);
        createButton.setDisable(true);

        initialsField.textProperty().addListener((observable, oldValue, newValue) ->
                createButton.setDisable(newValue.trim().isEmpty() || titleField.getText().trim().isEmpty() || descriptionArea.getText().trim().isEmpty()));

        titleField.textProperty().addListener((observable, oldValue, newValue) ->
                createButton.setDisable(newValue.trim().isEmpty() || initialsField.getText().trim().isEmpty() || descriptionArea.getText().trim().isEmpty()));

        descriptionArea.textProperty().addListener((observable, oldValue, newValue) ->
                createButton.setDisable(newValue.trim().isEmpty() || initialsField.getText().trim().isEmpty() || titleField.getText().trim().isEmpty()));

        // Set the dialog content
        dialog.getDialogPane().setContent(grid);

        // Request focus on the initials field by default
        initialsField.requestFocus();

        // Convert the result to an event when the create button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                return null; // Handle event creation here
            }
            return null;
        });

        // Show the dialog
        dialog.showAndWait();
    }
}

