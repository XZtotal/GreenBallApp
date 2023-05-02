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
    private HBox hboxcenter;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    public void initialize() {
    }

    @FXML
    public void btnEnterOnAction(ActionEvent actionEvent) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), hboxcenter);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0.0);

        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);
        fadeTransition.play();
    }


    @Deprecated
    public void registerOnAction(ActionEvent actionEvent) {
    }

    @Deprecated
    public void viewBookingOnAction(ActionEvent actionEvent) {

    }
}