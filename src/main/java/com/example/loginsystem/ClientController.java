package com.example.loginsystem;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    @FXML
    private AnchorPane apMain;

    @FXML
    private Button buttonSend;

    @FXML
    private TextField tfMessage;

    @FXML
    private ScrollPane spMain;

    @FXML
    private VBox vboxMessages;

    @FXML
    private Button button_exit;

    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            client = new Client(new Socket("localhost", 1234));
            System.out.println("Connected to server.");
        }catch(IOException e){
            e.printStackTrace();
        }

        // This makes the scroll bar the new length when there is more messages
        vboxMessages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                spMain.setVvalue((Double) newValue);
            }
        });

        client.receiveMessageFromServer(vboxMessages);

        // Adding action to the send button
        buttonSend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handleSendMessage();
            }
        });

        tfMessage.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleSendMessage();
            }
        });


        button_exit.setOnAction(new EventHandler<ActionEvent>() {
            String username = SessionManager.getUsername();
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "logged-in.fxml", "Welcome!", username);
            }
        });


    }

    private void handleSendMessage() {
        String messageToSend = tfMessage.getText();
        if (!messageToSend.isEmpty()) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5, 5, 5, 10));
            Text text = new Text(messageToSend);
            TextFlow textFlow = new TextFlow(text);

            // the styling
            textFlow.setStyle("-fx-color: rgb(239, 242, 255); " +
                    "-fx-background-color: rgb(15, 125, 242);" +
                    " -fx-background-radius: 20px;");

            textFlow.setPadding(new Insets(5, 10, 5, 10));
            text.setFill(Color.color(0.934, 0.945, 0.996));

            // add this to the hBox
            hBox.getChildren().add(textFlow);
            vboxMessages.getChildren().add(hBox);

            client.sendMessageToServer(messageToSend);
            tfMessage.clear();
        }
    }

    public static void addLabel(String messageFromServer, VBox vBox){
        HBox hBox = new HBox();
        // on the left this time because we are receiving the message
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(messageFromServer);
        text.setFont(Font.font(25)); // Set the font size to 18 (adjust as needed)
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle( "-fx-background-color: rgb(233, 233, 235);" +
                " -fx-background-radius: 20px;");
        textFlow.setPadding(new Insets(5, 10, 5,10));

        hBox.getChildren().add(textFlow);


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });



    }





}