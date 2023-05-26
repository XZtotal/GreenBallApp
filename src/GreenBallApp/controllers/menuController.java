package GreenBallApp.controllers;

import GreenBallApp.GreenBallApp;
import GreenBallApp.util.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import model.*;

import java.io.File;
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
    @FXML
    private ImageView MiFoto;
    @FXML
    private Label labelWelcome;
    @FXML
    private Label labelNickName;
    @FXML
    private Button imagen;
    @FXML
    private ImageView cambioFoto;

    @javafx.fxml.FXML
    public void btnReturnOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar sesión");
        alert.setHeaderText("¿Seguro que desea cerrar sesión?");
        alert.setContentText("Estas a punto de cerrar sesión");
        //poner una imagen en el alert en GreenBallApp/image/iconoCerrarSesionsvg.png
        ImageView imageView = new ImageView(new Image("GreenBallApp/image/imgCerrarSesion.png"));
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);

        //ponerle margin al imageView


        alert.setGraphic(imageView);
        alert.showAndWait();
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        // Si el usuario pulsa OK, se cierra la sesión y se vuelve a la pantalla de inicio
        if (alert.getResult().equals(ButtonType.OK)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/main.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                GreenBallApp.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void initialize() {
        GreenBallApp.getStage().setTitle("GreenBallApp > Menú");
        GreenBallApp.getStage().setMinHeight(500);
        GreenBallApp.getStage().setMinWidth(600);
        try {
            welcomeMessage();
            showNickaname();
            showImage();
            imagen.hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    cambioFoto.setImage(new Image("GreenBallApp/image/cimg2.png"));
                    MiFoto.setEffect(new GaussianBlur(5));
                } else {
                    cambioFoto.setImage(null);
                    MiFoto.setEffect(null);
                }
            });
        } catch (ClubDAOException | IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void cuentaOnAction(ActionEvent actionEvent) {
        
    }



    @FXML
    public void reservasOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/myReserves.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        GreenBallApp.setScene(scene);

    }

    @FXML
    public void reservarOnAction(ActionEvent actionEvent) throws ClubDAOException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/NewBooking.fxml"));
        Parent root = loader.load();

        GreenBallApp.setRoot(root);
    }
    // Escribe un metodo que escriba en el label welcome un mensaje de bienvenida a cada usuario

    public void welcomeMessage() throws ClubDAOException, IOException {
       Club club = Club.getInstance();
       Member currentMember = GreenBallApp.getMember();
       String nombre = currentMember.getName();
       labelWelcome.setText("Bienvenido " + nombre);
    }

    // Escribe un metodo que muestre la imagen del usuario

    public void showImage() throws ClubDAOException, IOException {
        Club club = Club.getInstance();
        Member currentMember = GreenBallApp.getMember();
        Image foto = currentMember.getImage();
        Utils.circularCutout(MiFoto);
        MiFoto.setImage(foto);

    }
    
    public void showNickaname() throws ClubDAOException, IOException {
        Club club = Club.getInstance();
        Member currentMember = GreenBallApp.getMember();
        String nickname = currentMember.getNickName();
        labelNickName.setText("Nombre de usuario: " + nickname);

    }

    @FXML
    public void imagenOnAction(ActionEvent actionEvent) {


    }




}
