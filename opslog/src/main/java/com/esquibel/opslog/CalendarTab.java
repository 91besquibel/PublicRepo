import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class CalendarTab extends javafx.scene.control.Tab {

    private GridPane calendarGrid;
    private LocalDate currentDate;

    public CalendarTab() {
        super("Calendar");
        createLayout();
    }

    private void createLayout() {
        VBox calendarLayout = new VBox(10);
        calendarGrid = new GridPane();
        updateCalendar(LocalDate.now());

        Button prevMonthButton = new Button("<<");
        prevMonthButton.setOnAction(e -> updateCalendar(currentDate.minusMonths(1)));

        Button nextMonthButton = new Button(">>");
        nextMonthButton.setOnAction(e -> updateCalendar(currentDate.plusMonths(1)));

        VBox navigationBox = new VBox(prevMonthButton, nextMonthButton);
        navigationBox.setSpacing(10);

        calendarLayout.getChildren().addAll(navigationBox, calendarGrid);

        setContent(calendarLayout);
    }

    private void updateCalendar(LocalDate date) {
        currentDate = date;
        calendarGrid.getChildren().clear();

        Button prevMonthButton = new Button("<<");
        prevMonthButton.setOnAction(e -> updateCalendar(currentDate.minusMonths(1)));

        Button nextMonthButton = new Button(">>");
        nextMonthButton.setOnAction(e -> updateCalendar(currentDate.plusMonths(1)));

        VBox navigationBox = new VBox(prevMonthButton, nextMonthButton);
        navigationBox.setSpacing(10);
        calendarGrid.add(navigationBox, 0, 0, 7, 1);

        LocalDate firstDayOfMonth = date.withDayOfMonth(1);
        int dayOfWeek = firstDayOfMonth.getDayOfWeek().getValue(); 

        for (int i = 0; i < 7; i++) {
            Label dayLabel = new Label(firstDayOfMonth.plusDays(i - dayOfWeek + 1).getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.getDefault()));
            dayLabel.setStyle("-fx-font-weight: bold");
            calendarGrid.add(dayLabel, i, 1);
        }

        int row = 2;
        int col = 0;
        for (int i = 1; i <= date.lengthOfMonth(); i++) {
            Label dayLabel = new Label(Integer.toString(i));
            calendarGrid.add(dayLabel, col, row);
            col++;
            if (col == 7) {
                col = 0;
                row++;
            }
        }
    }
}
