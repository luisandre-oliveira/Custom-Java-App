package com.edencoding.controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private CheckBox integerCheck;

    private boolean intCheckFlag;

    @FXML
    private TableView historyTable;

    private final ObservableList<String> data = FXCollections.observableArrayList();

    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        int counter = 0;
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

        counter++;
        System.out.println("Message: " + temp);

        /*
        historyTable.setEditable(true);

        TableColumn numberColumn = new TableColumn("N");
        numberColumn.setMinWidth(25);
        numberColumn.setCellFactory(new PropertyValueFactory<Integer,String>("counter"));

        TableColumn messageColumn = new TableColumn("Messages");
        messageColumn.setMinWidth(300);
        messageColumn.setCellFactory(new PropertyValueFactory<Integer,String>("temp"));

        historyTable.setItems(data);
        historyTable.getColumns().addAll(counter,temp);

        data.add(temp);
        */

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
