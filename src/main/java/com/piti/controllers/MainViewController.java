package com.piti.controllers;

import com.piti.App;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainViewController implements Initializable {

    @FXML
    ComboBox<String> comboBoxBR = new ComboBox<>();

    @FXML
    ComboBox<String> comboBoxCOM = new ComboBox<>();

    Alert alert = new Alert(Alert.AlertType.ERROR);

    DialogPane dialogPane = alert.getDialogPane();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final ObservableList<String> baudrateOptions = FXCollections.observableArrayList("2400", "9600", "57600", "115200");
        comboBoxBR.getItems().addAll(baudrateOptions);

        ArrayList<String> ports = createRandomCOM();
        final ObservableList<String> portOptions = FXCollections.observableArrayList(ports);
        comboBoxCOM.getItems().addAll(portOptions);

        dialogPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/application.css")).toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
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
        newScene.setFill(Color.TRANSPARENT);

        String baudrate = getBaudRate();
        String port = getCOMPort();

        if (port == null) {
            alert.setTitle("ERROR: COM PORT");
            alert.setHeaderText("No COM port has been chosen!!");
            alert.setContentText("Choose a COM port to proceed...");
            alert.show();
        } else if(baudrate == null) {
            alert.setTitle("ERROR: BAUD RATE");
            alert.setHeaderText("No baud rate has been chosen!!");
            alert.setContentText("Choose a baud rate to proceed...");
            alert.show();
        } else {
            System.out.println("COM port = " + port);
            System.out.println("BaudRate = " + baudrate);

            Stage newStage = App.getStage();
            newStage.setScene(newScene);
        }

        event.consume();
    }

    @FXML
    private void handleReceiverViewAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/ReceiverView.fxml")));
        Scene newScene = new Scene(root);
        newScene.setFill(Color.TRANSPARENT);

        String baudrate = getBaudRate();
        String port = getCOMPort();

        if (port == null) {
            alert.setContentText("No COM port has been chosen");
            alert.show();
        } else if(baudrate == null) {
            alert.setContentText("No baud rate has been chosen");
            alert.show();
        } else {
            System.out.println("COM port= " + port);
            System.out.println("BaudRate= " + baudrate);

            Stage newStage = App.getStage();
            newStage.setScene(newScene);
        }

        event.consume();
    }

    private ArrayList<String> createRandomCOM() {
        ArrayList<String> COMs = new ArrayList<>();

        int leftLimit = 1, rightLimit = 5, maxNumPorts = 3;
        int numPorts = (int) (Math.random() + maxNumPorts);

        Random random = new Random();

        for(int i = 0; i < numPorts; i++) {
            int intTemp = random.nextInt(rightLimit - leftLimit + 1) + leftLimit;
            String temp = "COM" + intTemp;
            if(!COMs.contains(temp)) {
                COMs.add(temp);
            }
        }

        Collections.sort(COMs);
        return COMs;
    }

    public String getCOMPort() {
        return comboBoxCOM.valueProperty().getValue();
    }

    public String getBaudRate() {
        return comboBoxBR.valueProperty().getValue();
    }

}
