package com.example.loginsystem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateGropController implements Initializable {

    @FXML
    private Button button_back;

    @FXML
    private Button button_create_chat;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        button_create_chat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // creates the chat and takes you to that page
            }
        });

        // get the users name from the database ??
        button_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "logged-in.fxml", "Welcome!", null);
            }
        });


    }
}
