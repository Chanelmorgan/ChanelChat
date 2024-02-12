package com.example.loginsystem;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.sql.*;

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

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String favChannel) {
        Parent root = null;

        // validation check
        if (username != null && favChannel != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(username, favChannel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // the user is not passing any information just switching between log in and sign up
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // A stage is essentially the window of the GUI. The scene is what is displayed in this window.
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();


    }

    public static void signUpUser(ActionEvent event, String username, String password, String favChannel) {
        // To interact with the database
        Connection connection = null;
        PreparedStatement psInsertStatement = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxLogin", "root", "Twins2012");
            // Need to check that the user is not already in the user
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(1, username);
            // execute the qury
            resultSet = psCheckUserExists.executeQuery();

            // used to check if the results set is empty
            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username");
                alert.show();
            } else {
                psInsertStatement = connection.prepareStatement("INSERT INTO users (username, password, favChannel VALUES (?, ?, ?)");
                psInsertStatement.setString(1, username);
                psInsertStatement.setString(2, password);
                psInsertStatement.setString(3, favChannel);
                psInsertStatement.executeUpdate();

                // change the scene once the user log in/ creates an account
                changeScene(event, "Logged-in.fxml", "Welcome!", username, favChannel);
            }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            // finally, is always executed - closing all the resources to make sure there is no memory leaks
            if(resultSet != null) {
                try {
                    resultSet.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(psCheckUserExists !=null){
                try {
                    psCheckUserExists.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsertStatement !=null) {
                try {
                    psInsertStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void logInUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxLogin", "root", "Twins2012");
            preparedStatement = connection.prepareStatement("SELECT password, favChannel FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst()){
                System.out.println("User not found in the database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            } else {
                // compare the passwords
                while(resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedChannel = resultSet.getString("favChannel");
                    if(retrievedPassword.equals(password)){
                        // change the scence
                        changeScene(event, "logged-in.fxml", "Welcome!", username, retrievedChannel);

                    } else {
                        System.out.println("passwords did not match");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Provided credentials are incorrect!");
                        alert.show();
                    }

                }
            }

        } catch(SQLException e){
            e.printStackTrace();
        }finally {
            // finally, is always executed - closing all the resources to make sure there is no memory leaks
            if(resultSet != null) {
                try {
                    resultSet.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(preparedStatement !=null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
