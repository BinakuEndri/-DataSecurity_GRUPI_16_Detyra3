module com.example.detyra3 {
    requires javafx.controls;
    requires javafx.fxml;

    opens Controller;
    opens com.example.detyra3 to javafx.fxml;
    exports Controller;
    exports com.example.detyra3;
}