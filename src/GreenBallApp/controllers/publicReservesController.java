package GreenBallApp.controllers;

import GreenBallApp.GreenBallApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class publicReservesController
{
    LocalDate currentDate;
    @javafx.fxml.FXML
    private Button btnReturn;

    int numRow = 13;
    @FXML
    private DatePicker date;
    @FXML
    private GridPane board;
    @FXML
    private Button btnPreDay;
    @FXML
    private Button btnNextDay;

    @FXML
     public void initialize() throws ClubDAOException, IOException {
        GreenBallApp.getStage().setTitle("GreenBallApp > Reservas públicas");
        currentDate = LocalDate.now();
        date.setValue(currentDate);
        printTable();
        //Actualiza la tabla cuando se cambia la fecha
        date.setOnAction(event -> {
            //si la fecha es hoy o un dia anteror, derabilita el boton anterior
            checkPreButton();
            try {
                printTable();
            } catch (ClubDAOException | IOException e) {
                e.printStackTrace();
            }



        });
        //Desactiva las fechas anteriores a la actual
        date.setDayCellFactory((DatePicker picker) -> {
            return new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();
                    setDisable(empty || date.compareTo(today) < 0 );
                }
            };
        });

        btnNextDay.setOnAction(event -> {
            date.setValue(date.getValue().plusDays(1));
            checkPreButton();
        });
        //si es el dia actual, no lo cambia
        btnPreDay.setOnAction(event -> {
            if(!date.getValue().equals(LocalDate.now())) date.setValue(date.getValue().minusDays(1));
            checkPreButton();
        });
        checkPreButton();





    }


    private void checkPreButton(){
        if (date.getValue().isBefore(LocalDate.now()) || date.getValue().isEqual(LocalDate.now())) {
            //si es una fecha pasada o actual, desabilita el boton de anterior
            btnPreDay.setDisable(true);

        } else {
            //si es una fecha futura, habilita el boton de anterior
            btnPreDay.setDisable(false);
        }
        try {
            printTable();
        } catch (ClubDAOException | IOException e) {
            e.printStackTrace();
        }
    }

     //Método para volver al menú principal
    @javafx.fxml.FXML
    public void btnReturnOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/main.fxml"));
        Parent root = loader.load();
        GreenBallApp.setRoot(root);
    }

    //Método para imprmir la tabla en el gridpane
    public void printTable() throws ClubDAOException, IOException {
        LocalDate date = this.date.getValue();
        if(date != null) currentDate = date;


        for (int i = 0; i < 6; i++) {
            //Bucle que itera sobre las pistas y lo añade a una lsita con el nombre de la pista y las reservas de ese día
            String courtName = "Pista " + (i + 1);
            ArrayList<Booking> courtBookings = new ArrayList<>(Club.getInstance().getCourtBookings(courtName, currentDate));

            //Bucle que itera sobre las filas y nos servirá para añadir las labels de si esta reservado o no
            for (int j = 9; j <= 21; j++) {

                //Creamos un objeto LocalTime con la hora que queremos comprobar y las hbox y labels que vamos a introducir en el gridpane
                LocalTime time = LocalTime.of(j, 0);

                HBox hbox = new HBox();
                hbox.setAlignment(javafx.geometry.Pos.CENTER);
                Label label = new Label();
                label.setFont(new javafx.scene.text.Font(14.0));
                hbox.getChildren().add(label);

                //Comprobamos si la hora esta reservada si esta ponemos el nombre del usuario que la ha reservado y si no pondra una etiqueta de libre
                if (courtBookings.size() > 0) {
                    for (Booking booking : courtBookings) {
                        if (booking.getFromTime().equals(time)) {
                            label.setText(String.valueOf(booking.getMember().getNickName()));
                            label.setStyle("-fx-text-fill: #005c5e ; -fx-font-weight: bold ; -fx-font-size: 14");
                            hbox.setStyle("-fx-background-color: WHITE");
                            courtBookings.remove(booking);
                            break;
                        } else {
                            label.setText("Libre");
                            label.setStyle("-fx-text-fill: #1ab47c");
                        }
                    }
                }else{
                    label.setText("Libre");
                    label.setStyle("-fx-text-fill: #1ab47c");
                }
                //colorea el cebreado de la tabla
                if (j % 2 == 0) hbox.setStyle("-fx-background-color: #f4f4f4 ; -fx-background-radius: 15");
                else hbox.setStyle("-fx-background-color: white ");

                board.add(hbox, i + 1, j - 8);


            }


        }




    }








}