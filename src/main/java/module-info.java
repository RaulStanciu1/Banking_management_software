module com.bms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.bms to javafx.fxml;
    exports com.bms;
}