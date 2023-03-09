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
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @FXML
    private TextArea text;

    @FXML
    private Button submitButton;

    @FXML
    private CheckBox integerCheck;

    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        if(!text.getText().isEmpty()) {
            System.out.println(text.getText());
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
            public void start(Stage stage) {
            }
        }.getHostServices().showDocument("https://github.com/joseluisgomes/PITI/");
        event.consume();
    }
}
