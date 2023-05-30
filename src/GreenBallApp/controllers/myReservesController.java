package GreenBallApp.controllers;

import GreenBallApp.GreenBallApp;
import GreenBallApp.extras.Reserva;
import com.sun.javafx.stage.EmbeddedWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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


    @FXML
    public void initialize() throws ClubDAOException, IOException {
        GreenBallApp.getStage().setTitle("GreenBallApp > Mis reservas");
        tableView.getColumns().addAll(fechaReservaColumn, horaColumn, pistaColumn, pagadoColumn);
        reservas = FXCollections.observableArrayList(Club.getInstance().getUserBookings(GreenBallApp.getMember().getNickName()));
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
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        // Establecer un prefWidth muy grande en las columnas
        fechaReservaColumn.setPrefWidth(250);
        horaColumn.setPrefWidth(220);
        pistaColumn.setPrefWidth(200);
        pagadoColumn.setPrefWidth(135);

        horaColumn.setStyle("-fx-background-color: rgb(0, 100, 109) ");
        fechaReservaColumn.setStyle("-fx-background-color: rgb(0, 100, 109)");
        pistaColumn.setStyle("-fx-background-color: rgb(0, 100, 109)");
        pagadoColumn.setStyle("-fx-background-color: rgb(0, 100, 109)");

        // Redondear las esquinas
        tableView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
        // Cambiar el tipo de letra
        tableView.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 30px;");
        tableView.setStyle("--fx-background-color: EMPTY;");
        // Añadir bordes gruesos y redondeados entre filas
        tableView.setStyle("-fx-cell-size: 100;");

        tableView.setRowFactory(tv -> new TableRow<Reserva>() {
            @Override
            public void updateItem(Reserva item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setStyle("");
                } else {
                    setStyle("-fx-background-color: #fff; -fx-border-color: #bbb; -fx-border-width: 2; -fx-border-radius: 7; -fx-border-height: 10;");
                }
            }
        });
    }

    @Deprecated
    public void registerOnAction(ActionEvent actionEvent) {
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
            Club.getInstance().removeBooking(b);
            reservas.remove(b);
            initialize();
        }
    }
}