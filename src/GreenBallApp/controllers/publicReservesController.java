package GreenBallApp.controllers;

import GreenBallApp.GreenBallApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Booking;
import model.Club;
import model.ClubDAOException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class publicReservesController
{
    @javafx.fxml.FXML
    private Button btnReturn;
    @javafx.fxml.FXML
    private TableView Table;
    @javafx.fxml.FXML
    private TableColumn <Booking,String> nicknameColumn;
    @javafx.fxml.FXML
    private TableColumn <Booking,String> pistaColumn;
    @javafx.fxml.FXML
    private TableColumn <Booking, LocalDateTime> inicioColumn;
    @javafx.fxml.FXML
    private TableColumn <Booking,LocalDateTime> finColumn;

    public publicReservesController() throws ClubDAOException, IOException {
    }

    @javafx.fxml.FXML
    public void initialize() {
    }

    //Obtener la lista de reservas
    Club club = Club.getInstance();
    ArrayList<Booking> bookings = club.getBookings();

    ObservableList<Booking> bookingList = FXCollections.observableArrayList(bookings);


    @javafx.fxml.FXML
    public void btnReturnOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        GreenBallApp.setScene(scene);
    }
}