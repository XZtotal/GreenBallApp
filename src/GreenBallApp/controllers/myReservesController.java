package GreenBallApp.controllers;

import GreenBallApp.GreenBallApp;
import GreenBallApp.extras.Reserva;
import com.sun.javafx.stage.EmbeddedWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.*;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class myReservesController {

    @javafx.fxml.FXML
    private Button btnReturn;
    @javafx.fxml.FXML
    private TableView<Reserva> tableView;

    @javafx.fxml.FXML
    private TableColumn<Reserva, LocalDate> fechaReservaColumn;
    @javafx.fxml.FXML
    private TableColumn<Reserva, LocalTime> horaColumn;
    @javafx.fxml.FXML
    private TableColumn<Reserva, Court> pistaColumn;
    @javafx.fxml.FXML
    private TableColumn<Reserva, Boolean> pagadoColumn;
    private ObservableList<Booking> reservas;
    @FXML
    private Button cancelButton;
    private Club club;


    @FXML
    public void initialize() throws ClubDAOException, IOException {
        GreenBallApp.getStage().setTitle("GreenBallApp > Menú > Mis reservas");
        tableView.getColumns().addAll(fechaReservaColumn, horaColumn, pistaColumn, pagadoColumn);
        reservas = FXCollections.observableArrayList(club.getUserBookings(GreenBallApp.getMember().getNickName()));
        ObservableList<Reserva> newReservas = FXCollections.observableArrayList();
        for(Booking b : reservas){
            Reserva r = new Reserva(b);
            newReservas.add(r);
        }
        tableView.setItems(newReservas);
        fechaReservaColumn.setCellValueFactory(new PropertyValueFactory<>("madeForDay"));
        horaColumn.setCellValueFactory(new PropertyValueFactory<>("fromTime"));
        pistaColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pagadoColumn.setCellValueFactory(new PropertyValueFactory<>("paid"));
    }

    @javafx.fxml.FXML
    public void btnEnterOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/menu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        GreenBallApp.setScene(scene);

    }

    @Deprecated
    public void viewBookingOnAction(ActionEvent actionEvent) {
    }
    public myReservesController() throws ClubDAOException, IOException {
        this.club = Club.getInstance();

    }

    @FXML
    public void cancelOnAction(ActionEvent actionEvent) throws ClubDAOException, IOException {
        Reserva reserve = new Reserva(tableView.getSelectionModel().getSelectedItem());
        LocalDateTime ti = reserve.getBookingDate();
        String cou = reserve.getName();
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar cancelación");
        alerta.setHeaderText("¿Está seguro de que desea eliminar la reserva con fecha " + ti + " en la " + cou + " ?");

        Optional<ButtonType> resultado = alerta.showAndWait();
        if (resultado.get() == ButtonType.OK) {
            Booking b = tableView.getSelectionModel().getSelectedItem();
            boolean a = club.removeBooking(b);
            initialize();
        }
    }
}