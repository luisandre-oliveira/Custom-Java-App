package com.piti.controllers;

import com.piti.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class EmitterViewController implements Initializable {

    @FXML
    private TextArea EmitterTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EmitterTextArea.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                EmitterTextArea.setText(EmitterTextArea.getText().replace("\n",""));
                handleSubmitButtonAction(new ActionEvent());
            }
        });
    }

    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        String temp = "";

        if(!EmitterTextArea.getText().isEmpty()) { // if TextArea has text
            temp = EmitterTextArea.getText();
        } else if(EmitterTextArea.getText().isEmpty()  || EmitterTextArea.getText().equals("")) {  // if TextArea doesn't have text
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

        int minValue = 10, maxValue = 40; // will print a random set of chars of a random size between this two values
        int leftLimit = 65, rightLimit = 90; //ascii values from capital A to capital Z
        int numChars = (int) (Math.random() * maxValue) + minValue;

        Random random = new Random();

        temp = random.ints(leftLimit, rightLimit + 1)
                .limit(numChars)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return temp;
    }
}
