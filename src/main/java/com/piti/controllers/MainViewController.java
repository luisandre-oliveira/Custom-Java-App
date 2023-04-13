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

        ports = getAvailableCOMPorts();

        if(ports.size() == 0) {
            if(getListPortsFromApp().size() == 0) {
                // let's create some random COMs for testing purposes
                ports.addAll(createRandomCOM());
                setListPortsOnApp(ports);
            } else {
                ports.addAll(getListPortsFromApp());
                // not necessary to set on app level because it´s better to check again
            }
        }

        final ObservableList<String> portOptions = FXCollections.observableArrayList(ports);
        final ObservableList<String> baudrateOptions = FXCollections.observableArrayList("2400", "9600", "28800", "57600", "115200");

        if(getBaudrateFromApp() != null) {
            comboBoxBR.valueProperty().setValue(getBaudrateFromApp());
        }

        if(getPortFromApp() != null) {
            comboBoxCOM.valueProperty().setValue(getPortFromApp());
        }

        comboBoxBR.getItems().addAll(baudrateOptions);
        comboBoxCOM.getItems().addAll(portOptions);

        DialogPane dialogPane = alert.getDialogPane();
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
            alertNullPort();
        } else if(baudrate == null) {
            alertNullBaudrate();
        } else {
            System.out.println("\nE: COM port = " + port);
            System.out.println("E: Baudrate = " + baudrate);

            setPortOnApp(port);
            setBaudrateOnApp(baudrate);

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
            alertNullPort();
        } else if(baudrate == null) {
            alertNullBaudrate();
        } else {
            System.out.println("\nR: COM port = " + port);
            System.out.println("R: Baudrate = " + baudrate);

            setPortOnApp(port);
            setBaudrateOnApp(baudrate);

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

    public String getCOMPort() { return comboBoxCOM.valueProperty().getValue(); }
    public String getBaudRate() { return comboBoxBR.valueProperty().getValue(); }

    private static void setPortOnApp(String p) { App.port = p; }
    private static void setBaudrateOnApp(String br) { App.baudrate = br; }
    private static void setListPortsOnApp(ArrayList<String> temp) { App.portsApp.addAll(temp); }

    private static String getPortFromApp() { return App.port; }
    private static String getBaudrateFromApp() { return App.baudrate; }
    private static ArrayList<String> getListPortsFromApp() { return App.portsApp; }

}
