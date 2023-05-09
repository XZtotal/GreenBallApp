package GreenBallApp.controllers;

import GreenBallApp.GreenBallApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;

import java.io.IOException;


public class menuController {
    @javafx.fxml.FXML
    private Button btnReturn;
    @FXML
    private Hyperlink cuenta;
    @FXML
    private Button reservas;
    @FXML
    private Button reservar;

    @javafx.fxml.FXML
    public void btnReturnOnAction(ActionEvent actionEvent) {
    }
    @FXML
    public void initialize() {
    }

    @FXML
    public void cuentaOnAction(ActionEvent actionEvent) {
    }



    @FXML
    public void reservasOnAction(ActionEvent actionEvent) throws IOException {


    }

    @FXML
    public void reservarOnAction(ActionEvent actionEvent) {
    }
    // Escribe un metodo que escriba en el label welcome un mensaje de bienvenida a cada usuario


}
