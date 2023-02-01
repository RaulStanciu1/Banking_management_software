module com.bms {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.bms to javafx.fxml;
    exports com.bms;
}