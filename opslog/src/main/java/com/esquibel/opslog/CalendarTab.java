import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CalendarTab extends javafx.scene.control.Tab {

    private GridPane calendarGrid;
    private ListView<String> upcomingEventsListView;

    public CalendarTab() {
        super("Calendar");
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

        // Populate upcoming events list (dummy data for demonstration)
        List<String> upcomingEvents = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            String event = "Event " + i + ": " + LocalDate.now().plusDays(i).format(DateTimeFormatter.ofPattern("MMM dd, yyyy")) +
                    " - " + LocalDate.now().plusDays(i+1).format(DateTimeFormatter.ofPattern("MMM dd, yyyy")) +
                    " | Event Title " + i + " | Event Description " + i;
            upcomingEvents.add(event);
        }
        upcomingEventsListView.getItems().addAll(upcomingEvents);

        HBox buttonBox = new HBox();
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> new Event(null, null, null, null, null).createEventPopup());
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
}
