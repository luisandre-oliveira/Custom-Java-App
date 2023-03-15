package com.piti.controllers;

import com.piti.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
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
    private TextArea textArea;

    @FXML
    private CheckBox integerCheck;

    private boolean intCheckFlag;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        String temp = "";

        if(!textArea.getText().isEmpty() && !intCheckFlag) { // if textarea has text and the checkbox is not ticked
            temp = textArea.getText();
        }

        else if(textArea.getText().isEmpty() && !intCheckFlag) {  // if textarea doesn't have text and the checkbox is not ticked
            // will print a random set of chars of a random size
            int leftLimit = 65, rightLimit = 90; //ascii values from capital A to capital Z
            int numChars = (int) (Math.random() * 10) + 1;

            Random random = new Random();

            temp = random.ints(leftLimit, rightLimit + 1)
                    .limit(numChars)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            textArea.setPromptText(temp);
        }

        else if(intCheckFlag) { // if textarea doesn't have text and the checkbox is ticked
            int randInt = (int) (Math.random() * 2000000) + 1;
            temp = String.valueOf(randInt);

            textArea.setPromptText(temp);
        }

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

    @FXML
    private void handleOnlyIntCheckAction(ActionEvent event) {
        if(integerCheck.isSelected()) { //println only here for testing purposes on the functionality of the checkbox
            System.out.println("User only wants integers");
            intCheckFlag = true;
        } else {
            System.out.println("User wants all chars");
            intCheckFlag = false;
        }
        event.consume();
    }

}
