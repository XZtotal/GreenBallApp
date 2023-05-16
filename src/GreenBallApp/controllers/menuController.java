package GreenBallApp.controllers;

import GreenBallApp.GreenBallApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import model.Club;
import model.ClubDAOException;
import model.Member;

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
    private Label Bienvenida;

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
        /*try {
            welcomeMessage();
            showImage();
        } catch (ClubDAOException | IOException e) {
            e.printStackTrace();
        }

         */
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
    public void reservarOnAction(ActionEvent actionEvent) {
    }
    // Escribe un metodo que escriba en el label welcome un mensaje de bienvenida a cada usuario

    public void welcomeMessage() throws ClubDAOException, IOException {
       Club club = Club.getInstance();
       Member currentMember = GreenBallApp.member;
       String nombre = currentMember.getName();
       Bienvenida.setText("Bienvenido " + nombre);
    }

    // Escribe un metodo que muestre la imagen del usuario

    public void showImage() throws ClubDAOException, IOException {
        Club club = Club.getInstance();
        Member currentMember = GreenBallApp.member;
        Image foto = currentMember.getImage();
        MiFoto.setImage(foto);

    }

}
