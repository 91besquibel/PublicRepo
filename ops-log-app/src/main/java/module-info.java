module com.esquibel {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.esquibel to javafx.fxml;
    exports com.esquibel;
}
