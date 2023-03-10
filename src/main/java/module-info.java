module javafxui {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.piti to javafx.fxml;
    opens com.piti.controllers to javafx.fxml;

    exports com.piti;
}