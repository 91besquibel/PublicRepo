public class TabManagerMenuAction implements MenuAction {
    @Override
    public void execute() {
        // Logic for Setting menu action
        PopupWindow settingPopup = new PopupWindow("Tab Manager");
        settingPopup.show();
    }
}
