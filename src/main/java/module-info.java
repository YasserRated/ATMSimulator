module com.atmproject.atmsimulator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.atmproject.atmsimulator to javafx.fxml;
    exports com.atmproject.atmsimulator;
}