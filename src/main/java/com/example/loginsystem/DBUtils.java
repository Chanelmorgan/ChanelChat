package com.example.loginsystem;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *  A utility class in have is a class that contains just static methods
 *
 *  It cannot be instantiated.
 *
 *  It contains a bunch of related methods.
 *
 *  It is also known as a helper class.
 *
 *
 */
public class DBUtils {

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String favChannel){
        Parent root = null;

        // validation check
        if(username != null && favChannel != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(username, favChannel);
            }catch(IOException e){
                e.printStackTrace();
            }
        } else {
            // the user is not passing any information just switching between log in and sign up
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        // A stage is essentially the window of the GUI. The scene is what is displayed in this window.
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();


    }
}
