package GreenBallApp.controllers;

import GreenBallApp.GreenBallApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;


public class registerController
{
    int moduleN = 1;

    firstRegisterModuleController firstRegisterModuleController;
    secondRegisterModuleController secondRegisterModuleController;
    thirdRegisterModuleController thirdRegisterModuleController;



    String name = "";
    String surname = "";
    String email = "";

    String userName = "";
    String password = "";

    String creditCard = "";
    String exprireDate = "";
    String cvv = "";





    @FXML
    private Button btnReturn;
    @FXML
    private BorderPane bpContainer;
    @FXML
    private Hyperlink linkOmit;
    @FXML
    private Button btnLast;
    @FXML
    private Button btnNext;

    @FXML
    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/thirdRegisterModule.fxml"));
        Parent root = loader.load();
        loader.get

        bpContainer.setCenter(root);


    }

    public void chargeModule(int moduleN) throws IOException {
        switch (moduleN) {
            case 1:
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/firstRegisterModule.fxml"));
                Parent root = loader.load();

                bpContainer.setCenter(root);
                break;
            case 2:
                loader = new FXMLLoader(getClass().getResource("../interfaces/secondRegisterModule.fxml"));
                root = loader.load();
                bpContainer.setCenter(root);
                break;
            case 3:
                loader = new FXMLLoader(getClass().getResource("../interfaces/thirdRegisterModule.fxml"));
                root = loader.load();
                bpContainer.setCenter(root);
                break;
        }
    }


    @FXML
    public void btnReturnOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        GreenBallApp.setScene(scene);
    }

    @FXML
    public void linkOmitOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void btnLastOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void btnNextOnAction(ActionEvent actionEvent) {
        switch (moduleN) {
            case 1:

            case 2:

            case 3:

        }
    }
}