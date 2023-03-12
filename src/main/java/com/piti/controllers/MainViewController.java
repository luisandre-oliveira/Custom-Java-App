package com.piti.controllers;

import com.piti.App;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
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
    private void handleEmitterViewAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/EmitterView.fxml")));
        Scene newScene = new Scene(root);
        App.getStage().setScene(newScene);
        event.consume();
    }

    @FXML
    private void handleReceiverViewAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/ReceiverView.fxml")));
        Scene newScene = new Scene(root);
        App.getStage().setScene(newScene);
        event.consume();
    }

}
