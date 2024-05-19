# Opslog Notes

## Application

### Start Up Screen
- **Description:** Application will open up to a small box that will ask if the user wants to create "New" or "Open" a file path.
- **Structure:** Small box with 3 buttons "New", "Open", "Close".
  - **New Button:** Brings up the file path screen. Goes to the file path screen and creates a new file tree.
  - **Open Button:** Brings up the file path screen.

### File Path Screen
- **Description:** This will ask the user for the file path where the current/new file system will be located for the application. The file path can be input manually or found by using a file navigation button. There is an apply button and a cancel button to finish the interaction with this screen.
- **Structure:** A medium-sized box that has a fillable text box labeled File Path, a button to the right labeled "Find", two buttons below the text box labeled "Apply" and "Cancel".
  - **Find Button:** This button when selected will open the file explorer and allow the user to navigate to a location in the directory. Once the user has finished their selection, the file path text box will be filled.
  - **Apply Button:** This button will store the file path close the file path screen. It will tell the application to begin one of two options. If "New" was selected at the start-up screen, it will create a new file tree and setting file based on the file tree structure below. Once the new file tree has been created, the main screen will load. If "Open" was selected at the start-up, it will scan through the file path checking the folders and loading all the relevant data.

### File Tree Structure
- **Description:** The first folder created will be named "OpsLog" this is the main folder in which all other folders are created. Inside the main folder will be a series of folders created and labeled "Help", "Tabs", "Event", and "Log". There will also be a settings.txt file created, this is the file that the application will use to load and store settings for the application.
  - **OpsLog Folder:** Main application folder stores all saved data.
  - **Settings.txt:** This file stores the user settings.
  - **Tab Folder:** Location of all user/premade tabs. There will be the following premade tabs created settings, tab manager. The tab folder will have text files with the following naming convention TabName.txt. Inside the .txt files will be the information needed to load the tab in the main application. Visibility, type, title, and contents.
  - **Event Folder:** Location of user-created event formats.
  - **Log Folder:** Location of all logs. This will have its own file tree inside. There will be a yearly folder with folders for all the months and a daily folder. Every time an event log is created, it will be stored in the current daily folder with the naming convention yyyy/mm/dd_HH:MM_Log.txt. This naming convention created when the log is created is what will determine where a log is stored.
  - **Help Folder:** Location of all the guides.

## Main Screen
- **Description:** Will consist of three boxes navigator, Interaction, and Tab Pane. 
- **Structure:** On the left side of the screen will be two vertical boxes in a single column.
The top box will be the navigator box which will allow the user to navigate/controll tabs on the main screen. 
  - **Navigator Box:** Has the following buttons: Settings, Tab, History, Help.
    - **Settings Button:** This will open the settings Tab.
    - **Tab Button:** Will open the tab manager tab.
    - **History Button:** Will open the history viewer tab.
    - **Help Button:** Will open the help tab.
  - **Interaction Box:** Allows users to interact with the view screen: Add, Edit, Delete, Open, Close. These buttons will be grayed out if the current tab does not have that capability.
    - **Add Button:**
    - **Edit Button:** 
    - **Delete Button:**
    - **Open Button:**
    - **Close Button:**
  - **Tab Pane Box:** This is a view Screen that allows users to view each tab. Views differ based on tab types.

## Tab Types
- **Log:** User created based on a premade format. Users can add and edit events. Is used to display the daily rolling log. Each one of these when created will generate a new log file tree. 
- **Calendar:** User created based on a premade format. Users can add, edit, and delete events from the calendar.
- **Note:** User created based on a premade format. Users can edit this document. 
- **Checklist:** User created based on a premade format. Users can edit and interact with this tab.
- **History:** System Created can be interacted with but not deleted. Users can interact with this. This tab displays a range of log based on a user input range.
- **Setting:** System Created can be edited but not deleted. Users can edit this tab.
- **Tab Manager:** System created can be edited but not deleted. Users can add, edit, delete, open, and close other tabs. This screen displays all current tabs located in the file path. This controls what tabs are visible on the tab pane and allows the user to create/delete/edit tabs.

## Background Tasks
- **Description:** The application will have various background and automated tasks that will constantly run. 
  - **Log Tab:**: A log tab will create a new daily folder at a designated time stored in the tabname.txt. A log tab will constantly be scanning the current daily folder for new events. If a new event is found, it will display it on the log tab. Whenever a new event is added to the log, it will automatically pull the date and time from the computer and add it to the log. Whenever a new event is created, it will automatically save the file to the current daily folder using the yyyy/mm/dd_HH:MM:SS_Log.txt format.
    

## Resources
- **Open Source Calendars:**
  - [Calendar-JavaFX](https://github.com/Ben-Avrahami/Calendar-JavaFX)
  - [CalendarFX](https://dlsc.com/products/calendarfx/)
  - [JFXCalendar](https://github.com/JKostikiadis/JFXCalendar/tree/master/src/model)
- **JavaFX Application Structure and Adding Controllers to FXML:** [YouTube Tutorial](https://www.youtube.com/watch?v=WOtcjvtOeZw)
- **JavaFX CSS Tutorial #3 TabPane CSS** https://www.youtube.com/watch?v=MyByMfzNRNA
