package GreenBallApp.controllers;

import GreenBallApp.GreenBallApp;
import GreenBallApp.util.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import model.*;

import java.io.File;
import java.io.IOException;


public class menuController {
    @javafx.fxml.FXML
    private Button btnReturn;
    @FXML
    private Hyperlink linkMyAccount;
    @FXML
    private Button btnMyReserves;
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
    @FXML
    private BorderPane root;

    private final BoxBlur blur = new BoxBlur(10, 10, 3);

    @FXML
    public void initialize() {
        GreenBallApp.getStage().setTitle("GreenBallApp > Menú");
        GreenBallApp.getStage().setMinHeight(600);
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
    @javafx.fxml.FXML
    public void btnReturnOnAction(ActionEvent actionEvent) {

        root.setEffect(blur);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar sesión");
        alert.setHeaderText("¿Seguro que desea cerrar sesión?");
        alert.setContentText("Estas a punto de cerrar sesión");
        //poner una imagen en el alert en GreenBallApp/image/iconoCerrarSesionsvg.png
        ImageView imageView = new ImageView(new Image("GreenBallApp/image/imgCerrarSesion.png"));
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);




        alert.setGraphic(imageView);
        alert.showAndWait();
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        // Si el usuario pulsa OK, se cierra la sesión y se vuelve a la pantalla de inicio
        if (alert.getResult().equals(ButtonType.OK)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/main.fxml"));
                Parent root = loader.load();
                GreenBallApp.setRoot(root);
                GreenBallApp.setMember(null);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            root.setEffect(null);
        }


    }

    @FXML
    public void cuentaOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/config.fxml"));
        Parent root = loader.load();

        GreenBallApp.setRoot(root);

    }



    @FXML
    public void reservasOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/myReserves.fxml"));
        Parent root = loader.load();
        GreenBallApp.setRoot(root);

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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(GreenBallApp.getStage());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            MiFoto.setImage(image);
            Utils.circularCutout(MiFoto);
            Member currentMember = GreenBallApp.getMember();
            currentMember.setImage(image);
        }


    }





}
