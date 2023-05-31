package GreenBallApp.controllers;

import GreenBallApp.GreenBallApp;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import model.Club;
import model.ClubDAOException;
import model.Member;


import java.io.IOException;

import static java.lang.Thread.sleep;

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
    private Label Error;



    @FXML
    public void initialize() {
        GreenBallApp.getStage().setTitle("GreenBallApp > Inicio");
        GreenBallApp.getStage().setMinHeight(600);
        GreenBallApp.getStage().setMinHeight(600);
        GreenBallApp.getStage().getIcons().clear();
        GreenBallApp.getStage().getIcons().add(new Image("GreenBallApp/image/bola.png"));
        //poner imagen superior de aplicacion

        //Al pulsar el boton entra a ver las reservas existentes sin tener que hacer login
        btnBook.setOnAction(e -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/publicReserves.fxml"));
            try {
                Parent root = loader.load();
                GreenBallApp.setRoot(root);
            } catch (IOException ex) {
                throw new RuntimeException(ex);

            }

        });
        //Cambia el foco del campo de texto de usuario al de contraseña al pulsar enter
        fieldUsername.setOnKeyPressed(e -> {
            if(!fieldUsername.getText().equals("") && e.getCode() == KeyCode.ENTER) {
               //fieldPassword.requestFocus();
                fieldPassword.requestFocus();
            }
        });
        //Al pulsar enter en el campo de contraseña se ejecuta el metodo btnEnterOnAction
        fieldPassword.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                btnEnterOnAction(new ActionEvent());
            }
        });



    }

    //Comprueba las credenciales del usuario y si son correctas carga el menu principal y si no muestra mensaje de error
    @FXML
    public void btnEnterOnAction(ActionEvent actionEvent)  {
        String nickname = fieldUsername.getText().trim();
        String password = fieldPassword.getText();
        if(nickname.equalsIgnoreCase("basketball")) {
            eastereggs();
            return;
        }

        try {

            Member member = Club.getInstance().getMemberByCredentials(nickname, password);


            if (member != null) {
                GreenBallApp.setMember(member);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/menu.fxml"));
                Parent root = loader.load();
                GreenBallApp.setRoot(root);
            } else {
                if (!Error.isVisible()) {
                    aparecerError();
                } else {
                    remarcarError();
                }

            }

        }catch (Exception e){

            if (!Error.isVisible()) {
                aparecerError();
            } else {
                remarcarError();
            }

        }

    }

    //Remarca el mensaje de error
    public void remarcarError(){
        FadeTransition ft = new FadeTransition(Duration.millis(200), Error);
        ft.setFromValue(1.0);
        ft.setToValue(0.7);
        ft.play();
        FadeTransition fd = new FadeTransition(Duration.millis(400), Error);
        fd.setFromValue(0.7);
        fd.setToValue(1.0);
        fd.play();


    }
    public void eastereggs(){
        GreenBallApp.getStage().setTitle("OrangeBallApp > Inicio");

        GreenBallApp.getStage().getIcons().clear();
        GreenBallApp.getStage().getIcons().add(new Image("GreenBallApp/image/easteregg.jpg"));
        //coger el componente padre y cambiarle el color de fondo

        GreenBallApp.getStage().getScene().getRoot().setStyle("-fx-background-image: url('GreenBallApp/image/easteregg.jpg'); ");
    }

    //Muestra el mensaje de error
    public void aparecerError(){
        Error.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(600), Error);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }





    @Deprecated


    //Carga la ventana de registro
    @FXML
    public void registerOnAction(ActionEvent actionEvent) throws IOException, InterruptedException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/register.fxml"));
        Parent root = loader.load();
        GreenBallApp.setRoot(root);

    }
}