module com.example.detyra3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.detyra3 to javafx.fxml;
    exports com.example.detyra3;
}