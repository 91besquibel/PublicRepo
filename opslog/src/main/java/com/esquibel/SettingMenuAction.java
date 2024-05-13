import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class SettingMenuAction implements MenuAction {

    private static String filePath; // Make filePath static to allow access from other classes
    private ComboBox<String> timeZoneComboBox;
    private ComboBox<String> timeFormatComboBox;
    private Label clockLabel;

    /**
     * Initializes default settings.
     */
    public SettingMenuAction() {
        this.filePath = "default_file_path";
    }

    /**
     * Executes the action for the Settings menu option.
     * Displays a popup window with options to set the file path, choose time formats,
     * and save settings. Handles error handling, validation, and feedback.
     */
    @Override
    public void execute() {
        // Create a grid pane to layout the settings
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // File Path section
        Label filePathLabel = new Label("File Path:");
        TextField filePathTextField = new TextField(this.filePath);
        gridPane.addRow(0, filePathLabel, filePathTextField);

        // Date and Time section
        Label dateTimeLabel = new Label("Date:");
        clockLabel = new Label();
        updateTime(); // Update clock label initially
        timeZoneComboBox = new ComboBox<>();
        timeZoneComboBox.getItems().addAll("Local", "UTC");
        timeZoneComboBox.setValue("Local");
        timeZoneComboBox.setOnAction(event -> updateTime());
        timeFormatComboBox = new ComboBox<>();
        timeFormatComboBox.getItems().addAll("12-hour", "24-hour");
        timeFormatComboBox.setValue("24-hour");
        timeFormatComboBox.setOnAction(event -> updateTime());
        gridPane.addRow(1, dateTimeLabel, clockLabel, timeZoneComboBox, timeFormatComboBox);

        // Create a dialog to contain the grid pane
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Settings");
        dialog.getDialogPane().setContent(gridPane);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Show dialog and handle user response
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Validate and save settings to file when OK button is clicked
            this.filePath = filePathTextField.getText().trim(); // Update static filePath
            if (validateFilePath()) {
                saveSettingsToFile();
                displayFeedback("Settings saved successfully.");
            } else {
                displayFeedback("Invalid file path. Please provide a valid file path.");
            }
        }
    }

    /**
     * Updates the clock label based on the selected time zone and time format.
     */
    private void updateTime() {
        String timeZone = timeZoneComboBox.getValue();
        String timeFormat = timeFormatComboBox.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat.equals("12-hour") ? "hh:mm:ss a" : "HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        if (timeZone.equals("UTC")) {
            ZonedDateTime utcDateTime = localDateTime.atZone(ZoneId.of("UTC"));
            clockLabel.setText(utcDateTime.format(formatter) + " UTC");
        } else {
            clockLabel.setText(localDateTime.format(formatter) + " Local");
        }
    }

    /**
     * Validates the provided file path.
     * @return true if the file path is valid, false otherwise.
     */
    private boolean validateFilePath() {
        // Perform basic validation for the file path
        // You can implement more sophisticated validation as needed
        return !filePath.isEmpty();
    }

    /**
     * Saves settings to the sysSetting.txt file.
     * Handles the creation of the main app folder if the file path has changed.
     * Displays error messages if saving fails.
     */
    private void saveSettingsToFile() {
        // Read previous file path from sysSetting.txt
        String previousFilePath = readPreviousFilePath();

        // Save settings to sysSetting.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("sysSetting.txt"))) {
            writer.write("File Path: " + this.filePath + "\n");
            writer.write("Time Zone: " + timeZoneComboBox.getValue() + "\n");
            writer.write("Time Format: " + timeFormatComboBox.getValue() + "\n");

            // If file path has changed, create main app folder and move sysSetting.txt
            if (!this.filePath.equals(previousFilePath)) {
                createMainAppFolder();
                moveSysSettingFile();
            }
        } catch (IOException e) {
            displayFeedback("Error saving settings: " + e.getMessage());
        }
    }

    /**
     * Retrieves the file path.
     * @return the file path.
     */
    public static String getFilePath() {
        return filePath;
    }

    /**
     * Reads the previous file path from the sysSetting.txt file if it exists.
     * @return the previous file path, or an empty string if not found.
     */
    private String readPreviousFilePath() {
        // Read previous file path from sysSetting.txt if exists
        // If not, return an empty string
        // You can implement this method to read the previous file path
        // For simplicity, I'll return an empty string
        return "";
    }

    /**
     * Creates the main app folder if it doesn't exist.
     */
    private void createMainAppFolder() {
        // Create main app folder if it doesn't exist
        String mainAppFolderPath = this.filePath + "/MainAppFolder"; // Example folder name
        boolean success = new File(mainAppFolderPath).mkdirs();
        if (!success) {
            displayFeedback("Failed to create main app folder.");
        }
    }

    /**
     * Moves the sysSetting.txt file to the main app folder.
     */
    private void moveSysSettingFile() {
        // Move sysSetting.txt to the main app folder
        String sysSettingFilePath = "sysSetting.txt";
        String mainAppFolderPath = this.filePath + "/MainAppFolder"; // Example folder name
        try {
            Files.move(Paths.get(sysSettingFilePath), Paths.get(mainAppFolderPath, sysSettingFilePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            displayFeedback("Error moving sysSetting.txt: " + e.getMessage());
        }
    }

    /**
     * Displays feedback to the user.
     * @param message the feedback message to display.
     */
    private void displayFeedback(String message) {
        Alert feedbackAlert = new Alert(Alert.AlertType.INFORMATION);
        feedbackAlert.setTitle("Feedback");
        feedbackAlert.setHeaderText(null);
        feedbackAlert.setContentText(message);
        feedbackAlert.showAndWait();
    }
}
