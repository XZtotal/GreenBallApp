package GreenBallApp.controllers;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class mainController
{
    @FXML
    private Button btnEnter;
    @FXML
    private Hyperlink linkRegister;
    @FXML
    private HBox hboxCenter;
    @FXML
    private TextField fieldUsername;
    @FXML
    private PasswordField fieldPassword;
    @FXML
    private Button btnBook;

    @FXML
    public void initialize() {
    }

    @FXML
    public void btnEnterOnAction(ActionEvent actionEvent) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), hboxCenter);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0.0);

        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);
        fadeTransition.play();
    }







    @FXML
    public void viewBookingOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void registerOnAction(ActionEvent actionEvent) {
    }
}