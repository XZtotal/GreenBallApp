package GreenBallApp.controllers;

import GreenBallApp.GreenBallApp;
import com.sun.javafx.stage.EmbeddedWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class myReservesController
{

    @javafx.fxml.FXML
    private Button btnReturn;
    @javafx.fxml.FXML
    private TableView<Booking> tableView;
    @javafx.fxml.FXML
    private TableColumn<Booking,LocalDateTime> fechaColumn;

    @javafx.fxml.FXML
    private TableColumn<Booking,LocalDate> fechaReservaColumn;
    @javafx.fxml.FXML
    private TableColumn<Booking,LocalTime> horaColumn;
    @javafx.fxml.FXML
    private TableColumn<Booking,Court> pistaColumn;
    @javafx.fxml.FXML
    private TableColumn<Booking,Member> miembroColumn;
    @javafx.fxml.FXML
    private TableColumn<Booking,Boolean> pagadoColumn;
    private ObservableList<Booking> reservas;



    @javafx.fxml.FXML
    public void initialize() throws ClubDAOException, IOException {
        GreenBallApp.getStage().setTitle("GreenBallApp > Menú > Mis reservas");
        tableView.getColumns().addAll(fechaColumn, fechaReservaColumn, horaColumn, pistaColumn, miembroColumn, pagadoColumn);
        reservas = FXCollections.observableArrayList(Club.getInstance().getBookings());
        tableView.setItems(reservas);
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        fechaReservaColumn.setCellValueFactory(new PropertyValueFactory<>("madeForDay"));
        horaColumn.setCellValueFactory(new PropertyValueFactory<>("fromTime"));
        pistaColumn.setCellValueFactory(new PropertyValueFactory<>("court"));
        miembroColumn.setCellValueFactory(new PropertyValueFactory<>("member"));
        pagadoColumn.setCellValueFactory(new PropertyValueFactory<>("paid"));
    }

    @Deprecated
    public void registerOnAction(ActionEvent actionEvent) {
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
}