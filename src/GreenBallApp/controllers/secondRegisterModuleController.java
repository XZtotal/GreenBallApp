package GreenBallApp.controllers;

import GreenBallApp.GreenBallApp;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

public class secondRegisterModuleController
{
    boolean errorUsername = false;
    boolean errorPassword = false;
    boolean errorRepeatPassword = false;


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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg"));
        File file = fileChooser.showOpenDialog(GreenBallApp.getStage());
        //cojemos la imagen la recortamos de manera circular

        Image image = new Image(file.toURI().toString());


    }

    @javafx.fxml.FXML
    public void linkDeleteImageOnAction(ActionEvent actionEvent) {
    }
}