package GreenBallApp.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;

public class configModuleController
{

    @javafx.fxml.FXML
    private Button btnIndentify;
    @javafx.fxml.FXML
    private Button btnSecurity;
    @javafx.fxml.FXML
    private Button btnCreditCard;

    configController configController;

    @javafx.fxml.FXML
    public void initialize() {
        btnIndentify.setOnAction( e -> {
            try {
                configController.chargeModule(GreenBallApp.controllers.configController.FIRST_MODULE);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        btnSecurity.setOnAction( e -> {
            try {
                configController.chargeModule(GreenBallApp.controllers.configController.SECOND_MODULE);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        btnCreditCard.setOnAction( e -> {
            try {
                configController.chargeModule(GreenBallApp.controllers.configController.THIRD_MODULE);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }
    public void setConfigController(configController configController) {
        this.configController = configController;
    }



}