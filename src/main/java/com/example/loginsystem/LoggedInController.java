package com.example.loginsystem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {


    @FXML
    private Button button_logout;

    @FXML
    private Label label_welcome;




    // allows use to interact with the widgets
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "hello-view.fxml", "Log in!", null);
            }
        });

    }

    public void setUserInformation(String username){
        label_welcome.setText("Welcome " + username + "!");
    }
}
