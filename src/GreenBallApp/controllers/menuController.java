package GreenBallApp.controllers;

import GreenBallApp.GreenBallApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
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
    }
    @FXML
    public void initialize() {
        try {
            welcomeMessage();
            showImage();
        } catch (ClubDAOException | IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void cuentaOnAction(ActionEvent actionEvent) {
    }



    @FXML
    public void reservasOnAction(ActionEvent actionEvent) throws IOException {


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
