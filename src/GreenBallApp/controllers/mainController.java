package GreenBallApp.controllers;

import GreenBallApp.GreenBallApp;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
        btnBook.setOnAction(e -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/publicReserves.fxml"));
            try {
                Parent root = loader.load();
                GreenBallApp.setRoot(root);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        fieldPassword.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                btnEnterOnAction(new ActionEvent());
            }
        });



    }


    @FXML
    public void btnEnterOnAction(ActionEvent actionEvent)  {
       String nickname = fieldUsername.getText();
        String password = fieldPassword.getText();

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


    public void aparecerError(){
        Error.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(600), Error);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

    public boolean checkMemberCredentials(String nickname, String password) throws ClubDAOException, IOException {
        Club club = Club.getInstance();
        try {
            Member exist = club.getMemberByCredentials(nickname, password);
            if (exist == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error");
            return false;
        }
    }



    @Deprecated
    public void viewBookingOnAction(ActionEvent actionEvent) throws IOException {


    }

    @FXML
    public void registerOnAction(ActionEvent actionEvent) throws IOException, InterruptedException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/register.fxml"));
        Parent root = loader.load();
        GreenBallApp.setRoot(root);

    }
}