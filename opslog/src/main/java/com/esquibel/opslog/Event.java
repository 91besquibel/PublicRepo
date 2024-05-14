package com.esquibel.opslog;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event implements Serializable {

    private transient TabManagerMenuAction tabManager;
    private TabPane tabPane; // Field to hold reference to TabPane

    public Event(TabManagerMenuAction tabManager, TabPane tabPane) {
        this.tabManager = tabManager;
        this.tabPane = tabPane; // Initialize TabPane
    }

    public void createEventPopup(CalendarTab currentTab, TabPane tabPane) {
        String eventFilePath = SettingMenuAction.getFilePath();
        if (eventFilePath == null) {
            return;
        }

        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Add Event");
        dialog.setHeaderText("Fill in the details for the event");

        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Date Picker
        DatePicker datePicker = new DatePicker();
        datePicker.setConverter(new StringConverter<LocalDate>() {
            String pattern = "MM/dd/yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                datePicker.setPromptText(pattern.toLowerCase());
            }

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        grid.add(new Label("Date:"), 0, 0);
        grid.add(datePicker, 1, 0);

        // Initials Text Field
        TextField initialsField = new TextField();
        initialsField.setPromptText("Enter initials");
        grid.add(new Label("Initials:"), 0, 1);
        grid.add(initialsField, 1, 1);

        // Title Text Field
        TextField titleField = new TextField();
        titleField.setPromptText("Enter title");
        grid.add(new Label("Title:"), 0, 2);
        grid.add(titleField, 1, 2);

        // Description Text Area
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Enter description");
        grid.add(new Label("Description:"), 0, 3);
        grid.add(descriptionArea, 1, 3);

        // Add to Calendar CheckBox
        CheckBox addToCalendarCheckBox = new CheckBox("Add to Calendar");
        grid.add(addToCalendarCheckBox, 0, 4, 2, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                LocalDate date = datePicker.getValue();
                String initials = initialsField.getText();
                String title = titleField.getText();
                String description = descriptionArea.getText();
                boolean addToCalendar = addToCalendarCheckBox.isSelected();

                String filepath = generateFilePath(title, eventFilePath);
                saveEventForCurrentDay(filepath, date, initials, title, description, addToCalendar);

                // Add event to the CalendarTab if checkbox is checked
                if (addToCalendar) {
                    if (currentTab instanceof CalendarTab) {
                        ((CalendarTab) currentTab).addEventToList(date, title, description);
                    }
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private String generateFilePath(String title, String eventFilePath) {
        return eventFilePath + "/" + title + ".txt";
    }

    private void saveEventForCurrentDay(String filepath, LocalDate date, String initials, String title, String description, boolean addToCalendar) {
        try (FileWriter writer = new FileWriter(filepath, true)) {
            // Write event details...
        } catch (IOException e) {
            e.printStackTrace();
            // Handle file writing error
        }
    }
}

