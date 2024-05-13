module com.esquibel.opslog {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.esquibel.opslog to javafx.fxml;
    exports com.esquibel.opslog;
}