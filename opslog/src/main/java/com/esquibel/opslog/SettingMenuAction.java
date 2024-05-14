package com.esquibel.opslog;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class SettingMenuAction implements MenuAction {

    private static String filePath;
    private ComboBox<String> timeZoneComboBox;
    private ComboBox<String> timeFormatComboBox;
    private Label clockLabel;

    public SettingMenuAction() {
        filePath = "default_file_path";
    }

    @Override
    public void execute() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label filePathLabel = new Label("File Path:");
        TextField filePathTextField = new TextField(filePath);
        gridPane.addRow(0, filePathLabel, filePathTextField);

        Label dateTimeLabel = new Label("Date:");
        clockLabel = new Label();
        updateTime();
        timeZoneComboBox = new ComboBox<>();
        timeZoneComboBox.getItems().addAll("Local", "UTC");
        timeZoneComboBox.setValue("Local");
        timeZoneComboBox.setOnAction(event -> updateTime());
        timeFormatComboBox = new ComboBox<>();
        timeFormatComboBox.getItems().addAll("12-hour", "24-hour");
        timeFormatComboBox.setValue("24-hour");
        timeFormatComboBox.setOnAction(event -> updateTime());
        gridPane.addRow(1, dateTimeLabel, clockLabel, timeZoneComboBox, timeFormatComboBox);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Settings");
        dialog.getDialogPane().setContent(gridPane);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            filePath = filePathTextField.getText().trim();
            if (validateFilePath()) {
                saveSettingsToFile();
                displayFeedback("Settings saved successfully.");
            } else {
                displayFeedback("Invalid file path. Please provide a valid file path.");
            }
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

    private boolean validateFilePath() {
        return !filePath.isEmpty();
    }

    private void saveSettingsToFile() {
        String previousFilePath = readPreviousFilePath();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("sysSetting.txt"))) {
            writer.write("File Path: " + filePath + "\n");
            writer.write("Time Zone: " + timeZoneComboBox.getValue() + "\n");
            writer.write("Time Format: " + timeFormatComboBox.getValue() + "\n");
            if (!filePath.equals(previousFilePath)) {
                createMainAppFolder();
                moveSysSettingFile();
            }
        } catch (IOException e) {
            displayFeedback("Error saving settings: " + e.getMessage());
        }
    }

    public static String getFilePath() {
        return filePath;
    }

    private String readPreviousFilePath() {
        return "";
    }

    private void createMainAppFolder() {
        String mainAppFolderPath = filePath + "/MainAppFolder";
        boolean success = new File(mainAppFolderPath).mkdirs();
        if (!success) {
            displayFeedback("Failed to create main app folder.");
        }
    }

    private void moveSysSettingFile() {
        String sysSettingFilePath = "sysSetting.txt";
        String mainAppFolderPath = filePath + "/MainAppFolder";
        try {
            Files.move(Paths.get(sysSettingFilePath), Paths.get(mainAppFolderPath, sysSettingFilePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            displayFeedback("Error moving sysSetting.txt: " + e.getMessage());
        }
    }

    private void displayFeedback(String message) {
        Alert feedbackAlert = new Alert(Alert.AlertType.INFORMATION);
        feedbackAlert.setTitle("Feedback");
        feedbackAlert.setHeaderText(null);
        feedbackAlert.setContentText(message);
        feedbackAlert.showAndWait();
    }
}
