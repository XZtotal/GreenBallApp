package GreenBallApp.controllers;

import GreenBallApp.GreenBallApp;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
    private TableView <Booking> Table;
    @javafx.fxml.FXML
    private TableColumn <Booking,String> nicknameColumn;
    @javafx.fxml.FXML
    private TableColumn <Booking,String> pistaColumn;
    @javafx.fxml.FXML
    private TableColumn <Booking, LocalDateTime> inicioColumn;
    @javafx.fxml.FXML
    private TableColumn <Booking,LocalDateTime> finColumn;
    @FXML
    private TextField Nickname;


     @FXML
     public void initialize() {
         rellenaTabla();

         Nickname.textProperty().addListener((observable, oldValue, newValue) -> {
             //Busque dentro de la columna de nickname el nickname introducido y borre la fila si lo encuentra
           Boolean reservaExiste = false;
              for (int i = 0; i < Table.getItems().size(); i++) {
                   Booking item = (Booking) Table.getItems().get(i);
                   if (item.getMember().getNickName().equals(newValue)) {
                       Table.getItems().remove(i);
                       reservaExiste = true;
                       break;
                   }

              }
              if(reservaExiste){
                  //Esto hay que cambiarlo por el metodo de filtrar
                  System.out.println("Existe");
              }else{
                  Nickname.setStyle("-fx-text-fill: red");
              }




         });
    }




    @javafx.fxml.FXML
    public void btnReturnOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        GreenBallApp.setScene(scene);
    }

    public void rellenaTabla(){
        try{
            Club club = Club.getInstance();
            ArrayList<Booking> bookings = club.getBookings();
            ObservableList<Booking> bookingList = FXCollections.observableArrayList(bookings);
            nicknameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMember().getNickName()));
            pistaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourt().getName()));
            inicioColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getFromTime()));
            finColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getFromTime()));
            Table.setItems(bookingList);
        } catch (Exception e) {
            System.out.println("error");
        }

    }



}