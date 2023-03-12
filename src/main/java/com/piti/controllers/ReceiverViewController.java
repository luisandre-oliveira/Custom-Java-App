package com.piti.controllers;

import com.piti.App;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class ReceiverViewController {

    @FXML
    private void handleExitButtonClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/MainView.fxml")));
        Scene newScene = new Scene(root);
        App.getStage().setScene(newScene);
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
}
