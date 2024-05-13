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

    private String filePath;
    private ComboBox<String> timeZoneComboBox;
    private ComboBox<String> timeFormatComboBox;
    private Label clockLabel;

    public SettingMenuAction() {
        // Initialize default settings
        this.filePath = "default_file_path";
    }

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
            // Save settings to file when OK button is clicked
            this.filePath = filePathTextField.getText();
            saveSettingsToFile();
        }
    }

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

    private void saveSettingsToFile() {
        // Read previous file path from sysSetting.txt
        String previousFilePath = readPreviousFilePath();

        // Save settings to sysSetting.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("sysSetting.txt"))) {
            writer.write("File Path: " + this.filePath + "\n");
            writer.write("Time Zone: " + timeZoneComboBox.getValue() + "\n");
            writer.write("Time Format: " + timeFormatComboBox.getValue() + "\n");
            System.out.println("Settings saved successfully.");

            // If file path has changed, create main app folder and move sysSetting.txt
            if (!this.filePath.equals(previousFilePath)) {
                createMainAppFolder();
                moveSysSettingFile();
            }
        } catch (IOException e) {
            System.err.println("Error saving settings: " + e.getMessage());
        }
    }

   private String readPreviousFilePath() {
    // Read previous file path from sysSetting.txt if exists
    String previousFilePath = "";
    try (BufferedReader reader = new BufferedReader(new FileReader("sysSetting.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("File Path:")) {
                previousFilePath = line.substring(line.indexOf(":") + 1).trim();
                break;
            }
        }
    } catch (IOException e) {
        System.err.println("Error reading previous file path: " + e.getMessage());
    }
    return previousFilePath;
    }

    private void createMainAppFolder() {
        // Create main app folder if it doesn't exist
        // You can specify the name of the main app folder here
        String mainAppFolderPath = this.filePath + "/MainAppFolder"; // Example folder name
        boolean success = new File(mainAppFolderPath).mkdirs();
        if (success) {
            System.out.println("Main app folder created: " + mainAppFolderPath);
        } else {
            System.err.println("Failed to create main app folder.");
        }
    }

    private void moveSysSettingFile() {
        // Move sysSetting.txt to the main app folder
        String sysSettingFilePath = "sysSetting.txt";
        String mainAppFolderPath = this.filePath + "/MainAppFolder"; // Example folder name
        try {
            Files.move(Paths.get(sysSettingFilePath), Paths.get(mainAppFolderPath, sysSettingFilePath), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("sysSetting.txt moved to: " + mainAppFolderPath);
        } catch (IOException e) {
            System.err.println("Error moving sysSetting.txt: " + e.getMessage());
        }
    }
}
