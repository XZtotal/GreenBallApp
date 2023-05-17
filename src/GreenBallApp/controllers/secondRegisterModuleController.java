package GreenBallApp.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class secondRegisterModuleController
{
    @javafx.fxml.FXML
    private TextField fieldUsername;
    @javafx.fxml.FXML
    private PasswordField pfieldPass;
    @javafx.fxml.FXML
    private PasswordField pfieldRepeatPass;
    @javafx.fxml.FXML
    private ImageView imgUserImage;
    @javafx.fxml.FXML
    private Button btnAddImage;
    @javafx.fxml.FXML
    private Hyperlink linkDeleteImage;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void btnAddImageOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void linkDeleteImageOnAction(ActionEvent actionEvent) {
    }
}