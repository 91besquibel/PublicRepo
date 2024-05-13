public class SettingMenuAction implements MenuAction {
    @Override
    public void execute() {
        // Logic for Setting menu action
        PopupWindow settingPopup = new PopupWindow("Setting");
        settingPopup.show();
    }
}
