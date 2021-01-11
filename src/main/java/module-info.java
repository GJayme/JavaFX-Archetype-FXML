module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example.view to javafx.fxml;
    opens org.example.controller to javafx.fxml;

    exports org.example.view;
    exports org.example.controller;
}