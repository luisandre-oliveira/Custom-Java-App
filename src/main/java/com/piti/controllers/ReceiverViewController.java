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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReceiverViewController implements Initializable {

    @FXML
    private TextArea ReceiverTextArea;

    final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ReceiverTextArea.setEditable(false);

        addText("FIRST MESSAGE");
        addText("SECOND MESSAGE");
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

    public void addText(String msg) {
        LocalDateTime ldt = LocalDateTime.now();
        String time = ldt.format(format);

        if(Objects.equals(ReceiverTextArea.getText(), "")) {
            ReceiverTextArea.setText(time + " " + msg);
        } else {
            ReceiverTextArea.appendText("\n" + time + " " + msg);
        }
    }
}