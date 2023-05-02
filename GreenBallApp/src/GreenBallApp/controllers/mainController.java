package GreenBallApp.controllers;

import GreenBallApp.GreenBallApp;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    }


    @FXML
    public void btnEnterOnAction(ActionEvent actionEvent) throws IOException, ClubDAOException {
        String nickname = fieldUsername.getText();
        String password = fieldPassword.getText();
        Club club = Club.getInstance();
        Member exist = club.getMemberByCredentials(nickname, password);
        if(exist == null){
            Error.setVisible(true);

        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/register.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            GreenBallApp.setScene(scene);

        }


    }







    @FXML
    public void viewBookingOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void registerOnAction(ActionEvent actionEvent) throws IOException, InterruptedException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/register.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        GreenBallApp.setScene(scene);


    }
}