# Opslog Notes

## Application

### Start Up Screen
- **Description:** Application will open up to a small box that will ask if the user wants to create "New" or "Open" a file path.
- **Structure:** Small box with 3 buttons "New", "Open", "close".
  - **New Button:** Brings up the file path screen. Goes to the file path screen and creates a new file tree.
  - **Open Button:** Brings up the file path screen.

### File Path Screen
- **Description:** This will ask the user for the filepath where the current/new file system will be located for the application. The file path can be input manually or found by using a file navigation button. There is an apply button and a cancel button to finish the interaction with this screen.
- **Structure:** A medium-sized box that has a fillable text box labeled File Path, A button to the right labeled "Find", Two buttons below the text box labeled "Apply" and "Cancel".
  - **Find Button:** This button when selected will open the file explorer and allow the user to navigate to a location in the directory. Once the user has finished their selection, the file path text box will be filled.
  - **Apply Button:** This button will store the file path close the file path screen. It will tell the application to begin one of two options. If "New" was selected at the start-up screen, it will create a new file tree and setting file based on the file tree structure below. Once the new file tree has been created, the main screen will load. If "Open" was selected at the start-up, it will scan through the file path checking the folders and loading all the relevant data.

### File Tree Structure
- **Description:** The first folder created will be named "OpsLog" this is the main folder in which all other folders are created. Inside the main folder will be a series of folders created and labeled "Help", "Tabs", "Event", and "Log". There will also be a settings.txt file created, this is the file that the application will use to load and store settings for the application.
  - **OpsLog Folder:** Main application folder stores all saved data.
  - **Settings.txt:** This file stores the user settings.
  - **Tab Folder:** Location of all user created tabs. Will have text files with the following naming convention TabName.txt. Inside the .txt files will be the information needed to load the tab in the main application. Visibility, type, title, and contents.
  - **Event Folder:** Location of user-created event formats.
  - **Log Folder:** Location of all logs. This will have its own file tree inside. There will be a yearly folder with folders for all the months and a daily folder. Every time an event log is created, it will be stored in the current daily folder with the naming convention yyyy/mm/dd_HH:MM_Log.txt. This naming convention created when the log is created is what will determine where a log is stored.
  - **Help Folder:** Location of all the guides.

## Main Screen
- **Description:** Will consist of three boxes navigator, Interaction, and Tab Pane. 
- **Structure:** On the Left side of the screen will be two vertical boxes in a single column. The top box will be the navigator box which will allow the user to navigate/controll tabs on the main screen.
  - **Navigator Box:** has the following buttons Settings, Tab, History, Help.
    -**Settings Button:**
    -** Button:**
    -**Settings Button:**
    -**Settings Button:**
  - **Interaction Box:** Allows users to interact with the view screen Add, Edit, Delete, (load), Close.
  - **Tab Pane Box:** This is a view Screen that allows users to view each tab.

## Resources
- **Open Source Calendars:**
  - [Calendar-JavaFX](https://github.com/Ben-Avrahami/Calendar-JavaFX)
  - [CalendarFX](https://dlsc.com/products/calendarfx/)
  - [JFXCalendar](https://github.com/JKostikiadis/JFXCalendar/tree/master/src/model)
- **JavaFX Application Structure and Adding Controllers to FXML:** [YouTube Tutorial](https://www.youtube.com/watch?v=WOtcjvtOeZw)
