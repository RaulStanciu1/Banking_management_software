module com.bms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.bms.banking to javafx.fxml;
    opens com.bms.admin to javafx.fxml;
    opens com.bms to javafx.fxml;
    opens com.bms.history.models to javafx.fxml;
    opens com.bms.history to javafx.fxml;
    exports com.bms;
    exports com.bms.banking;
    exports com.bms.admin;
    exports com.bms.data;
    exports com.bms.history;
    exports com.bms.history.models;
}