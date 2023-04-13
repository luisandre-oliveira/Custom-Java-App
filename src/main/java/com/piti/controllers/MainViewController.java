package com.piti.controllers;

import com.fazecast.jSerialComm.SerialPort;
import com.piti.App;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

    ArrayList<String> ports = new ArrayList<>();

    final Alert alert = new Alert(Alert.AlertType.ERROR);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/application.css")).toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");

        ports = getAvailableCOMPorts();

        final ObservableList<String> portOptions = FXCollections.observableArrayList(ports);
        final ObservableList<String> baudrateOptions = FXCollections.observableArrayList("2400", "9600", "28800", "57600", "115200");

        if (getBaudrateFromApp() != null) {
            comboBoxBR.valueProperty().setValue(getBaudrateFromApp());
        }

        if (getPortFromApp() != null) {
            comboBoxCOM.valueProperty().setValue(getPortFromApp());
        }

        comboBoxBR.getItems().addAll(baudrateOptions);
        comboBoxCOM.getItems().addAll(portOptions);
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

    @FXML
    private void handleMinimizeButtonClicked(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void handleRefreshCOMPorts() {
        ports = getAvailableCOMPorts();
        System.out.println(ports);
        //setListPortsOnApp(ports);
        comboBoxCOM.setItems(FXCollections.observableArrayList(ports));
    }

    @FXML
    private void handleEmitterViewAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/EmitterView.fxml")));
        Scene newScene = new Scene(root);
        newScene.setFill(Color.TRANSPARENT);

        String tempBaudrate = getBaudrateFromCB();
        String tempPort = getCOMPortNameFromCB();

        if (tempPort == null) {
            alertNullPort();
        } else if (tempBaudrate == null) {
            alertNullBaudrate();
        } else {
            System.out.println("\nE: COM port = " + tempPort);
            System.out.println("E: Baudrate = " + tempBaudrate);

            setPortOnApp(tempPort);
            setBaudrateOnApp(tempBaudrate);

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

        String tempBaudrate = getBaudrateFromCB();
        String tempPort = getCOMPortNameFromCB();

        if (tempPort == null) {
            alertNullPort();
        } else if (tempBaudrate == null) {
            alertNullBaudrate();
        } else {
            System.out.println("\nR: COM port = " + tempPort);
            System.out.println("R: Baudrate = " + tempBaudrate);

            setPortOnApp(tempPort);
            setBaudrateOnApp(tempBaudrate);

            Stage newStage = App.getStage();
            newStage.setScene(newScene);
        }
        event.consume();
    }

    private void alertNullPort() {
        alert.setTitle("ERROR: COM PORT");
        alert.setContentText("No COM port has been chosen");
        alert.setContentText("Choose a COM port to proceed...");
        alert.show();
    }

    private void alertNullBaudrate() {
        alert.setTitle("ERROR: BAUDRATE ERROR");
        alert.setContentText("No baud rate has been chosen");
        alert.setContentText("Choose a baud rate to proceed...");
        alert.show();
    }

    private ArrayList<String> createRandomCOM() {
        ArrayList<String> COMs = new ArrayList<>();

        int leftLimit = 1, rightLimit = 5, maxNumPorts = 3;
        int numPorts = (int) (Math.random() + maxNumPorts);

        Random random = new Random();

        for (int i = 0; i < numPorts; i++) {
            int intTemp = random.nextInt(rightLimit - leftLimit + 1) + leftLimit;
            String temp = "COM" + intTemp;
            if (!COMs.contains(temp)) {
                COMs.add(temp);
            }
        }
        Collections.sort(COMs);
        return COMs;
    }

    private ArrayList<String> getAvailableCOMPorts() {
        SerialPort[] ports = SerialPort.getCommPorts();
        ArrayList<String> temp = new ArrayList<>();
        String name;

        for (SerialPort port : ports) {
            name = port.getSystemPortName() + ": " + port;
            temp.add(name);
        }

        return temp;
    }

    public String getCOMPortNameFromCB() { return comboBoxCOM.valueProperty().getValue(); }

    public String getBaudrateFromCB() { return comboBoxBR.valueProperty().getValue(); }

    private static void setPortOnApp(String p) { App.port = p; }

    private static void setBaudrateOnApp(String br) { App.baudrate = br; }

    public static String getPortFromApp() { return App.port; }

    public static String getBaudrateFromApp() { return App.baudrate; }

}
