module com.example.loginsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.loginsystem to javafx.fxml;
    exports com.example.loginsystem;
}