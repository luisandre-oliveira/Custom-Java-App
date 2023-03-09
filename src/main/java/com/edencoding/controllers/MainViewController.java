package com.edencoding.controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @FXML
    private TextArea textArea;

    @FXML
    private Button submitButton;

    @FXML
    private CheckBox integerCheck;

    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        if(!textArea.getText().isEmpty() && integerCheck.isSelected()) { // if textarea has text and the checkbox is ticked
            int randInt = (int) (Math.random() * 2000000) + 1;
            System.out.println("Message: " + randInt);
            textArea.setPromptText(String.valueOf(randInt));
        }

        else if(textArea.getText().isEmpty() && integerCheck.isSelected()) { // if textarea doesn't have text and the checkbox is ticked
            System.out.println("Message: " + textArea.getText());
            System.out.println("Does the user only want integers -> " + integerCheck.isSelected());
        }

        else if(textArea.getText().isEmpty() && !integerCheck.isSelected()) {  // if textarea doesn't have text and the checkbox is not ticked
            // will print a random set of chars of a random size
            int leftLimit = 65, rightLimit = 90; //ascii values from capital A to capital Z
            int numChars = (int) (Math.random() * 10) + 1;

            Random random = new Random();

            String temp = random.ints(leftLimit, rightLimit + 1)
                    .limit(numChars)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            System.out.println(temp);
            textArea.setPromptText(temp);
        }
        event.consume();
    }

    @FXML
    private void handleExitButtonClicked(ActionEvent event) {
        Platform.exit();
        event.consume();
    }

    @FXML
    private void handleGitButtonClicked(ActionEvent event) {
        new Application() {
            @Override
            public void start(Stage stage) { }
        }.getHostServices().showDocument("https://github.com/joseluisgomes/PITI/");
        event.consume();
    }

    @FXML
    private void handleOnlyIntCheckAction(ActionEvent event) {
        if(integerCheck.isSelected()) //println only here for testing purposes on the functionality of the checkbox
            System.out.println("User only wants integers");
        else
            System.out.println("User wants all chars");
        event.consume();
    }
}
