module com.example.project360 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project360 to javafx.fxml;
    exports com.example.project360;
}