public class TabManagerMenuAction implements MenuAction {
    @Override
    public void execute() {
        // Logic for Setting menu action
        PopupWindow tabManagerPopup = new PopupWindow("Tab Manager");
        tabManagerPopup.show();
    }
}
