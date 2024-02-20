package com.example.loginsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("server-view.fxml"));
        stage.setTitle("Server");
        stage.setScene(new Scene(root, 700, 550));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}