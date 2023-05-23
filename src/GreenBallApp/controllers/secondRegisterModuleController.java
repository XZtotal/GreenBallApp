package GreenBallApp.controllers;

import GreenBallApp.GreenBallApp;
import GreenBallApp.util.Utils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import jdk.jshell.execution.Util;
import model.Club;
import model.ClubDAOException;

import java.io.File;
import java.io.IOException;

public class secondRegisterModuleController
{
    BooleanProperty errorUsername = new SimpleBooleanProperty(true);
    BooleanProperty errorPassword = new SimpleBooleanProperty(true);
    BooleanProperty errorRepeatPassword =  new SimpleBooleanProperty(true);

    BooleanProperty imageSelected = new SimpleBooleanProperty(false);

    Image defaultImage = new Image("GreenBallApp/image/addphoto3.png");

    boolean firstTry = true;


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
    private VBox vboxUsername;
    @javafx.fxml.FXML
    private VBox vboxPass;
    @javafx.fxml.FXML
    private VBox vboxRepeatPass;
    @javafx.fxml.FXML
    private Label labelUsername;
    @javafx.fxml.FXML
    private Label labelPass;
    @javafx.fxml.FXML
    private Label labelRepeatPass;

    @javafx.fxml.FXML
    public void initialize() {
        linkDeleteImage.visibleProperty().bind(imageSelected);

        errorUsername.addListener((observable, oldValue, newValue) -> {
            System.out.println("errorUsername: " + newValue);
            if (newValue) {
                if(fieldUsername.getText().isEmpty())
                    labelUsername.setText("Escribe un nombre");
                else labelUsername.setText("Nombre ya en uso");
                vboxUsername.setStyle("-fx-background-color: rgb(251, 255, 182) ; -fx-background-radius: 10");
                labelUsername.setOpacity(1);

            } else {
                vboxUsername.setStyle("-fx-background-color: null");
                labelUsername.setOpacity(0);
            }


        });

        errorPassword.addListener((observable, oldValue, newValue) -> {
            System.out.println("errorPassword: " + newValue);
            if (newValue) {
                vboxPass.setStyle("-fx-background-color: rgb(251, 255, 182) ; -fx-background-radius: 10");
                labelPass.setOpacity(1);

            } else {
                vboxPass.setStyle("-fx-background-color: null");
                labelPass.setOpacity(0);
            }


        });


        errorRepeatPassword.addListener((observable, oldValue, newValue) -> {
            System.out.println("errorRepeatPassword: " + newValue);
            if (newValue) {
                vboxRepeatPass.setStyle("-fx-background-color: rgb(251, 255, 182) ; -fx-background-radius: 10");
                labelRepeatPass.setOpacity(1);

            } else {
                vboxRepeatPass.setStyle("-fx-background-color: null");
                labelRepeatPass.setOpacity(0);
            }
        });

        errorUsername.setValue(false);



        errorPassword.setValue(false);
        errorRepeatPassword.setValue(false);
        //eventos de los campos de texto
        fieldUsername.textProperty().addListener((observable, oldValue, newValue) -> {

            getUserName();

        });

        pfieldPass.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!firstTry) {
                getPassword();
                pfieldRepeatPass.clear();
            }else{
                pfieldRepeatPass.clear();
            }
        });

        pfieldRepeatPass.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!firstTry) {
                getPassword();
            }
        });
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

        imageSelected.setValue(true);


        imgUserImage.setImage(image);
        imgUserImage.setFitWidth(135);





        Utils.circularCutout(imgUserImage);


    }

    @javafx.fxml.FXML
    public void linkDeleteImageOnAction(ActionEvent actionEvent) {
        imgUserImage.setImage(defaultImage);
        //quita el clip de la imagen
        imgUserImage.setClip(null);
        imageSelected.setValue(false);
    }

    //getters and setters

    public String getUserName() {
        firstTry = false;
        String username = fieldUsername.getText();
        boolean exists = true;
        try {
            exists = Club.getInstance().existsLogin(username);
        }catch (ClubDAOException | IOException e){
            e.printStackTrace();
        }

        if (username != null && !username.isEmpty() && !exists) {
            errorUsername.setValue(false);
            return username;
        } else {
            errorUsername.setValue(true);
            return null;
        }
    }

    public String getPassword(){
        String password = pfieldPass.getText();
        String repeatPassword = pfieldRepeatPass.getText();
        if (password != null && !password.isEmpty()) {
            errorPassword.setValue(false);
            if (repeatPassword != null && !repeatPassword.isEmpty()) {

                if (password.equals(repeatPassword)) {
                    errorRepeatPassword.setValue(false);
                    return password;
                } else {
                    errorRepeatPassword.setValue(true);
                    return null;
                }
            } else {
                errorRepeatPassword.setValue(true);
                return null;
            }
        } else {
            errorPassword.setValue(true);
            return null;
        }
    }

    public Image getImage(){
        if (imageSelected.getValue()) {
            return imgUserImage.getImage();
        } else {
            return null;
        }
    }



}