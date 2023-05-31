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
    private Label labelWelcome;
    @FXML
    private Label labelNickName;
    @FXML
    private BorderPane root;

    private final BoxBlur blur = new BoxBlur(10, 10, 3);
    @FXML
    private Button book;
    @FXML
    private Button image;
    @FXML
    private ImageView MyPhoto;
    @FXML
    private ImageView changePhoto;

    @FXML
    public void initialize() {
        GreenBallApp.getStage().setTitle("GreenBallApp > Menú");
        GreenBallApp.getStage().setMinHeight(600);
        GreenBallApp.getStage().setMinWidth(600);
        try {
            //Se muestra el nombre de usuario el nickname y la imagen de perfil
            welcomeMessage();
            showNickaname();
            showImage();
            //Si se pasa por encima de la imagen de perfil se pone borroso y se muestra un icono de que se permite cambiar la foto
            image.hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    changePhoto.setImage(new Image("GreenBallApp/image/cimg2.png"));
                    MyPhoto.setEffect(new GaussianBlur(5));
                } else {
                    changePhoto.setImage(null);
                    MyPhoto.setEffect(null);
                }
            });

        } catch (ClubDAOException | IOException e) {
            e.printStackTrace();
        }


    }

    //Metodo para cerrar sesión y volver a la pantalla de inicio
    @javafx.fxml.FXML
    public void btnReturnOnAction(ActionEvent actionEvent) {
        //Cuando se pulsa el boton se pone borroso el fondo y sale el mensaje de confirmacion
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
            //Si se cancela deja quita el efecto borroso
            root.setEffect(null);
        }


    }

    //Metodo para ir al menu de  configuracion
    @FXML
    public void cuentaOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/config.fxml"));
        Parent root = loader.load();

        GreenBallApp.setRoot(root);

    }


    //Metodo para ir al menu de mis reservas
    @FXML
    public void reservasOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/myReserves.fxml"));
        Parent root = loader.load();
        GreenBallApp.setRoot(root);

    }

    //Metodo para ir al menu de reservar pista
    @FXML
    public void reservarOnAction(ActionEvent actionEvent) throws ClubDAOException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/NewBooking.fxml"));
        Parent root = loader.load();

        GreenBallApp.setRoot(root);
    }

    // Metodo que cambia el label de bienvenida para cada usuario
    public void welcomeMessage() throws ClubDAOException, IOException {
       Club club = Club.getInstance();
       Member currentMember = GreenBallApp.getMember();
       String nombre = currentMember.getName();
       labelWelcome.setText("Bienvenido " + nombre);
    }

    // Metodo que muestra la imagen del usuario en el menu

    public void showImage() throws ClubDAOException, IOException {
        Club club = Club.getInstance();
        Member currentMember = GreenBallApp.getMember();
        Image foto = currentMember.getImage();
        Utils.circularCutout(MyPhoto);
        MyPhoto.setImage(foto);

    }
    //Metodo que muestra el nickname del usuario que ha accedido al menu
    public void showNickaname() throws ClubDAOException, IOException {
        Club club = Club.getInstance();
        Member currentMember = GreenBallApp.getMember();
        String nickname = currentMember.getNickName();
        labelNickName.setText("Nombre de usuario: " + nickname);

    }

    //Metodo para cambiar la imagen de perfil
    @FXML
    public void imagenOnAction(ActionEvent actionEvent) {
        //Se abre un filechooser para seleccionar la imagen
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(GreenBallApp.getStage());
        //Se establece la imagen tanto en el menu como en el miembro
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            MyPhoto.setImage(image);
            Utils.circularCutout(MyPhoto);
            Member currentMember = GreenBallApp.getMember();
            currentMember.setImage(image);
        }


    }





}
