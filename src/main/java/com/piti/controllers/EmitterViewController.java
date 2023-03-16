package com.piti.controllers;

import com.piti.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class EmitterViewController implements Initializable {

    @FXML
    private TextArea EmitterTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        String temp = "";

        if(!EmitterTextArea.getText().isEmpty()) { // if textarea has text
            temp = EmitterTextArea.getText();
        }

        else if(EmitterTextArea.getText().isEmpty()) {  // if textarea doesn't have text
            temp = createRandomMessage();
            EmitterTextArea.setPromptText(temp);
        }

        EmitterTextArea.setText("");
        System.out.println("Message: " + temp);

        event.consume();
    }

    @FXML
    private void handleReturnButtonClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/MainView.fxml")));

        Scene newScene = new Scene(root);
        newScene.setFill(Color.TRANSPARENT);

        Stage newStage = App.getStage();
        newStage.setScene(newScene);
        event.consume();
    }

    private String createRandomMessage() {
        String temp;

        // will print a random set of chars of a random size
        int leftLimit = 65, rightLimit = 90; //ascii values from capital A to capital Z
        int numChars = (int) (Math.random() * 10) + 1;

        Random random = new Random();

        temp = random.ints(leftLimit, rightLimit + 1)
                .limit(numChars)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return temp;
    }

}
