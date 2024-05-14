package com.esquibel.opslog;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Orientation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarTab extends javafx.scene.control.Tab {

    private GridPane calendarGrid;
    private ListView<String> upcomingEventsListView;
    private TabPane tabPane; // Reference to TabPane
    private TabManagerMenuAction tabManager; // Reference to TabManagerMenuAction

    private List<Event> events; // List to store events

    public CalendarTab(TabPane tabPane, TabManagerMenuAction tabManager) {
        super("Calendar");
        this.tabPane = tabPane; // Initialize TabPane
        this.tabManager = tabManager; // Initialize TabManagerMenuAction
        events = new ArrayList<>();
        createLayout();
    }

    private void createLayout() {
        BorderPane root = new BorderPane();

        // Create the Upcoming Events Pane
        VBox upcomingEventsPane = createUpcomingEventsPane();
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.HORIZONTAL);
        splitPane.getItems().addAll(upcomingEventsPane, createCalendarPane());
        splitPane.setDividerPositions(0.3); // Set initial divider position

        root.setCenter(splitPane);
        setContent(root);
    }

    private VBox createUpcomingEventsPane() {
        VBox upcomingEventsPane = new VBox();
        upcomingEventsPane.setPadding(new Insets(10));

        Label upcomingEventsLabel = new Label("Upcoming Events");
        upcomingEventsListView = new ListView<>();
        upcomingEventsListView.setPrefHeight(200); // Set initial height

        // Populate upcoming events list
        for (Event event : events) {
            upcomingEventsListView.getItems().add(event.toString());
        }

        HBox buttonBox = new HBox();
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> new Event(tabManager, tabPane).createEventPopup(this, tabPane)); // Pass tabManager and tabPane to Event
        Button deleteButton = new Button("Delete");
        Button editButton = new Button("Edit");
        buttonBox.getChildren().addAll(addButton, deleteButton, editButton);
        buttonBox.setSpacing(10);

        upcomingEventsPane.getChildren().addAll(upcomingEventsLabel, upcomingEventsListView, buttonBox);
        VBox.setVgrow(upcomingEventsListView, Priority.ALWAYS);

        return upcomingEventsPane;
    }

    private GridPane createCalendarPane() {
        GridPane calendarPane = new GridPane();

        // Create buttons to switch between months
        Button prevMonthButton = new Button("<< Prev Month");
        Button nextMonthButton = new Button("Next Month >>");
        HBox buttonBox = new HBox(prevMonthButton, nextMonthButton);
        GridPane.setColumnSpan(buttonBox, 7);
        GridPane.setConstraints(buttonBox, 0, 0);

        // Populate calendar grid with dummy data for demonstration
        for (int i = 1; i <= 31; i++) {
            Label dateLabel = new Label(String.valueOf(i));
            calendarPane.add(dateLabel, (i - 1) % 7, (i - 1) / 7 + 1);
        }

        calendarPane.getChildren().addAll(buttonBox);
        return calendarPane;
    }

    public void addEventToList(LocalDate date, String title, String description) {
        // Add event to the list
        Event event = new Event(tabManager, tabPane); // Pass tabManager and tabPane to Event constructor
        events.add(event);

        // Update the ListView
        upcomingEventsListView.getItems().add(event.toString());
    }
}

