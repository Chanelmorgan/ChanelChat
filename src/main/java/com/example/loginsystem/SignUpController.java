package com.example.loginsystem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Button signup_button;

    @FXML
    private Button button_login;

    @FXML
    private TextField tf_username;

    @FXML
    private TextField tf_password;

    @FXML
    private TextField tf_first_name;

    @FXML
    private TextField tf_last_name;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_confirm;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {




        signup_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // to remove white spaces in the username and checking that the passwords match
                if (!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty() && tf_password.getText().trim().equals(tf_confirm.getText().trim())) {
                    DBUtils.signUpUser(event, tf_username.getText(), tf_password.getText(), tf_first_name.getText(), tf_last_name.getText(), tf_email.getText());
                } else if(!tf_password.getText().trim().equals(tf_confirm.getText().trim())){
                    System.out.println("Passwords do not match");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Passwords do not match!");
                    alert.show();
                } else{
                    System.out.println("Please fill in all information!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to sign up!");
                    alert.show();
                }
            }
        });

        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "hello-view.fxml", "Log in!",  null);
            }
        });



    }
}
