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
    private TableView<Booking> tableView = new TableView<>();
    @javafx.fxml.FXML
    private TableColumn<Booking,LocalDateTime> fechaColumn = new TableColumn<>("Fecha");

    @javafx.fxml.FXML
    private TableColumn<Booking,LocalDate> fechaReservaColumn = new TableColumn<>("Fecha Reserva");
    @javafx.fxml.FXML
    private TableColumn<Booking,LocalTime> horaColumn = new TableColumn<>("Hora");
    @javafx.fxml.FXML
    private TableColumn<Booking,Court> pistaColumn = new TableColumn<>("Pista");
    @javafx.fxml.FXML
    private TableColumn<Booking,Member> miembroColumn = new TableColumn<>("Miembro");
    @javafx.fxml.FXML
    private TableColumn<Booking,Boolean> pagadoColumn = new TableColumn<>("Pagado");
    Scene scene = new Scene(tableView, 800, 600);
    private EmbeddedWindow primaryStage;


    @javafx.fxml.FXML
    public void initialize() throws ClubDAOException, IOException {
        fechaColumn.setCellValueFactory(new PropertyValueFactory("bookingDate"));
        fechaReservaColumn.setCellValueFactory(new PropertyValueFactory<>("madeForDay"));
        horaColumn.setCellValueFactory(new PropertyValueFactory<>("fromTime"));
        pistaColumn.setCellValueFactory(new PropertyValueFactory<>("court"));
        miembroColumn.setCellValueFactory(new PropertyValueFactory<>("member"));
        pagadoColumn.setCellValueFactory(new PropertyValueFactory<>("paid"));
        tableView.getColumns().addAll(fechaColumn, fechaReservaColumn, horaColumn, pistaColumn, miembroColumn, pagadoColumn);
        ObservableList<Booking> reservas = FXCollections.observableArrayList(Club.getInstance().getBookings());
        tableView.setItems(reservas);
        Scene scene = new Scene(tableView, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
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