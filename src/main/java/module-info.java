module com.example.loginsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.xml.crypto;


    opens com.example.loginsystem to javafx.fxml;
    exports com.example.loginsystem;
}