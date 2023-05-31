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
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
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
        GreenBallApp.getStage().setTitle("GreenBallApp > Mis reservas");
        //tableView.getColumns().addAll(fechaReservaColumn, horaColumn, pistaColumn, pagadoColumn);
        printTable();
        //Rellena el tableView
        fechaReservaColumn.setCellValueFactory(new PropertyValueFactory<>("madeForDay"));
        fechaReservaColumn.setReorderable(false);
        horaColumn.setCellValueFactory(new PropertyValueFactory<>("fromTime"));
        horaColumn.setReorderable(false);
        pistaColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pistaColumn.setReorderable(false);
        pagadoColumn.setCellValueFactory(new PropertyValueFactory<>("paide"));
        pagadoColumn.setReorderable(false);
        //Si no hay ningun elemento seleccionado, desabilita el boton cancelar
        cancelButton.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());
    }
    public void printTable(){

        reservas = FXCollections.observableArrayList(club.getUserBookings(GreenBallApp.getMember().getNickName()));

        ObservableList<Reserva> newReservas = FXCollections.observableArrayList();
        for(Booking b : reservas){
            Reserva r = new Reserva(b);
            newReservas.add(r);
        }
        Comparator<Reserva> comparador = (r1, r2) -> r2.getBookingDate().compareTo(r1.getBookingDate());
        Collections.sort(newReservas, comparador);
        tableView.setItems(newReservas);


    }

    @javafx.fxml.FXML
    public void btnEnterOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/menu.fxml"));
        Parent root = loader.load();
        GreenBallApp.setRoot(root);
    }

    @Deprecated
    public void viewBookingOnAction(ActionEvent actionEvent) {
    }
    public myReservesController() throws ClubDAOException, IOException {
        this.club = Club.getInstance();

    }
    //Cuando se pulsa el boton cancelar reserva
    @FXML
    public void cancelOnAction(ActionEvent actionEvent) throws ClubDAOException, IOException {
        Reserva reserve = new Reserva(tableView.getSelectionModel().getSelectedItem());
        LocalDateTime ti = reserve.getBookingDate();
        String cou = reserve.getName();
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar cancelación");
        alerta.setHeaderText("¿Está seguro de que desea eliminar la reserva con fecha " + ti + " en la " + cou + " ?");

        //Compara la fecha y hora de la reserva y en caso de que esta sea en menos de 24 horas no permite cancelar
        Optional<ButtonType> resultado = alerta.showAndWait();
        if (resultado.get() == ButtonType.OK) {
            Reserva b = tableView.getSelectionModel().getSelectedItem();
            LocalDateTime fe = b.getBookingDate();
            LocalTime ho = b.getFromTime();
            LocalDateTime ahora = LocalDateTime.now();
            LocalDateTime fechaHoraReserva = fe.toLocalDate().atTime(ho);
            LocalDateTime hoy = LocalDateTime.now();
            if((hoy.getDayOfYear() - b.getMadeForDay().getDayOfYear()) < -1){
                boolean a = club.removeBooking(b.getBooking());
                System.out.println("Reserva cancelada: " + a);
                printTable();
            }else if((hoy.getDayOfYear() - b.getMadeForDay().getDayOfYear()) < 0 && hoy.getHour() < b.getFromTime().getHour()){
                boolean a = club.removeBooking(b.getBooking());
                System.out.println("Reserva cancelada: " + a);
                printTable();
            }else if(hoy.getMinute() < b.getFromTime().getMinute()){
                boolean a = club.removeBooking(b.getBooking());
                System.out.println("Reserva cancelada: " + a);
                printTable();
            }else if(hoy.getDayOfYear()> b.getMadeForDay().getDayOfYear() || (hoy.getDayOfYear() == b.getMadeForDay().getDayOfYear() && hoy.getHour() >= b.getFromTime().getHour())){
                Alert alerta2 = new Alert(Alert.AlertType.ERROR);
                alerta2.setTitle("Error");
                alerta2.setHeaderText("Cancelar Reserva");
                alerta2.setContentText("No se puede cancelar una reserva con menos de 24h de antelación");

                Optional<ButtonType> resultado1 = alerta2.showAndWait();
                if (resultado.get() == ButtonType.CLOSE) {
                    initialize();
                }
            }else{
                Alert alerta2 = new Alert(Alert.AlertType.ERROR);
                alerta2.setTitle("Error");
                alerta2.setHeaderText("Cancelar Reserva");
                alerta2.setContentText("No se puede cancelar una reserva con menos de 24h de antelación");

                Optional<ButtonType> resultado1 = alerta2.showAndWait();
                if (resultado.get() == ButtonType.CLOSE) {
                    initialize();
                }
            }

        }
    }
}