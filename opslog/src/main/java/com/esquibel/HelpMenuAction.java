public class HelpMenuAction implements MenuAction {
    @Override
    public void execute() {
        // Logic for Setting menu action
        PopupWindow helpPopup = new PopupWindow("Help");
      helpPopup.show();
    }
}
