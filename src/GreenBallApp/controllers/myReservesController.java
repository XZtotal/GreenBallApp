package GreenBallApp.controllers;

import GreenBallApp.GreenBallApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class myReservesController
{

    @javafx.fxml.FXML
    private Button btnReturn;
    @javafx.fxml.FXML
    private TableColumn NickName;
    @javafx.fxml.FXML
    private TableColumn Pista;
    @javafx.fxml.FXML
    private TableColumn HoraInicio;
    @javafx.fxml.FXML
    private TableColumn HoraFin;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @Deprecated
    public void registerOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void btnEnterOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/menu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        GreenBallApp.setScene(scene);

    }

    @Deprecated
    public void viewBookingOnAction(ActionEvent actionEvent) {
    }
}