public class HistoryViewMenuAction implements MenuAction {
    @Override
    public void execute() {
        // Logic for Setting menu action
        PopupWindow settingPopup = new PopupWindow("History View");
        historyViewPopup.show();
    }
}
