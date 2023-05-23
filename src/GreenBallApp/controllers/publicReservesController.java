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
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class publicReservesController
{
    LocalDate currentDate;
    @javafx.fxml.FXML
    private Button btnReturn;
    @FXML
    private GridPane tabla;
    @FXML
    private RowConstraints col1;
    @FXML
    private RowConstraints col2;
    @FXML
    private RowConstraints col3;
    @FXML
    private RowConstraints col4;
    @FXML
    private RowConstraints col5;
    @FXML
    private RowConstraints col6;
    @FXML
    private RowConstraints col7;

    int numRow = 13;
    @FXML
    private DatePicker fecha;

    @FXML
     public void initialize() throws ClubDAOException, IOException {
        currentDate = LocalDate.now();
        fecha.setValue(currentDate);
        printTable();

        fecha.setOnAction(event -> {
            try {
                printTable();
            } catch (ClubDAOException | IOException e) {
                e.printStackTrace();
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

    public void printTable() throws ClubDAOException, IOException {
        LocalDate date = fecha.getValue();
        if(date != null) currentDate = date;

        for (int i = 0; i < 6; i++) {

            String courtName = "Pista " + (i + 1);
            ArrayList<Booking> courtBookings = new ArrayList<>(Club.getInstance().getCourtBookings(courtName, currentDate));

            for (int j = 9; j <= 21; j++) {

                LocalTime time = LocalTime.of(j, 0);

                HBox hbox = new HBox();
                hbox.setAlignment(javafx.geometry.Pos.CENTER);
                Label label = new Label();
                label.setFont(new javafx.scene.text.Font(12.0));
                hbox.getChildren().add(label);

                if (courtBookings.size() > 0) {
                    for (Booking booking : courtBookings) {
                        if (booking.getFromTime().equals(time)) {
                            label.setText(String.valueOf(booking.getMember().getNickName()));
                            label.setStyle("-fx-text-fill: #005c5e ; -fx-font-weight: bold");
                            hbox.setStyle("-fx-background-color: WHITE");
                        } else {
                            label.setText("Libre");
                            label.setStyle("-fx-text-fill: #1ab47c");
                        }
                    }
                }
                //colorea el cebreado de la tabla
                if (j % 2 == 0) hbox.setStyle("-fx-background-color: #f4f4f4 ; -fx-background-radius: 15");
                else hbox.setStyle("-fx-background-color: white ");

                tabla.add(hbox, i + 1, j - 8);


            }


        }




    }








}