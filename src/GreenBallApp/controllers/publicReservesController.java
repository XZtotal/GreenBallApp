package GreenBallApp.controllers;

import GreenBallApp.GreenBallApp;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class publicReservesController
{
    @javafx.fxml.FXML
    private Button btnReturn;


    @FXML
     public void initialize() {


    }




    @javafx.fxml.FXML
    public void btnReturnOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        GreenBallApp.setScene(scene);
    }








}