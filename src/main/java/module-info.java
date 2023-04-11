module javafxui {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;

    opens com.piti to javafx.fxml;
    opens com.piti.controllers to javafx.fxml;

    exports com.piti;
}