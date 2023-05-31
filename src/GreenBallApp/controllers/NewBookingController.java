package GreenBallApp.controllers;

import GreenBallApp.GreenBallApp;
import GreenBallApp.extras.Reserva;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NewBookingController
{
    LocalDate currentDate;


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
    private DatePicker date;
    @FXML
    private Button btnReturn;

    @FXML
     public void initialize() throws ClubDAOException, IOException {
        GreenBallApp.getStage().setTitle("GreenBallApp > Menú > Reservar");
        currentDate = LocalDate.now();
        date.setValue(currentDate);
        printTable();

        date.setOnAction(event -> {
            try {
                printTable();
            } catch (ClubDAOException | IOException e) {
                e.printStackTrace();
            }
        });

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


    }







    @FXML


    public void printTable() throws ClubDAOException, IOException {
        LocalDate date = this.date.getValue();
        LocalDateTime date1 = LocalDateTime.now();
        Member mem = GreenBallApp.getMember();
        List<Court> courts = Club.getInstance().getCourts();
        if(date != null) currentDate = date;

        for (int i = 0; i < 6; i++) {

            String courtName = "Pista " + (i + 1);
            Court court = courts.get(i);
            ArrayList<Booking> courtBookings = new ArrayList<>(Club.getInstance().getCourtBookings(courtName, currentDate));
            ArrayList<Booking> courtBookings1 = new ArrayList<>(Club.getInstance().getCourtBookings(courtName, currentDate));
            for (int j = 9; j <= 21; j++) {

                LocalTime time = LocalTime.of(j, 0);
                LocalTime time1 = LocalTime.of(j-1,0);
                LocalTime time2 = LocalTime.of(j-2,0);
                LocalTime time3 = LocalTime.of(j+1,0);
                LocalTime time4 = LocalTime.of(j+2,0);


                HBox hbox = new HBox();
                hbox.setAlignment(javafx.geometry.Pos.CENTER);
                Button boton = new Button("LIBRE");

                hbox.getChildren().add(boton);

                if (courtBookings.size() > 0 ) {
                    for (Booking booking : courtBookings) {
                        if (booking.getFromTime().equals(time)) {
                            boton.setText(String.valueOf(booking.getMember().getNickName()));
                            boton.setDisable(true);
                            boton.setBackground(Background.EMPTY);
                            boton.setTextFill(Color.RED);
                            hbox.setStyle("-fx-background-color: WHITE");
                            courtBookings.remove(booking);
                            break;
                        } else {
                            boton.setBackground(Background.EMPTY);
                            boton.setTextFill(Color.GREEN);
                            boton.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                            boton.setOnMouseEntered(event -> {
                                boton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
                            });
                            boton.setOnMouseExited(event -> {
                                boton.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                            });
                            boton.setOnAction(event -> {
                                try{
                                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                                    alerta.setTitle("Confirmar acción");
                                    alerta.setHeaderText("¿Está seguro de que desea realizar esta acción?");

                                    Optional<ButtonType> resultado = alerta.showAndWait();
                                    if (resultado.get() == ButtonType.OK){
                                        int aux = time.getHour();
                                        boolean aux1 = true;
                                        int contadorA = 0;
                                        int contadorB = 0;
                                        int contadorC = 0;
                                        switch (aux){
                                            case 9:
                                                for(Booking b : courtBookings1){
                                                    Reserva re = new Reserva(b);
                                                    String name = re.getUserName();
                                                    if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time3)){
                                                        contadorA++;
                                                    }
                                                    if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time4)){
                                                        contadorB++;
                                                    }
                                                }
                                                if(contadorA == 1 && contadorB == 1){
                                                    aux1 = false;
                                                }
                                                break;
                                            case 10:
                                                for(Booking b : courtBookings1){
                                                    Reserva re = new Reserva(b);
                                                    String name = re.getUserName();
                                                    if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time1)){
                                                        contadorA++;
                                                    }
                                                    if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time4)){
                                                        contadorB++;
                                                    }
                                                    if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time3)){
                                                        contadorA++;
                                                        contadorB++;
                                                    }
                                                }
                                                if(contadorA == 2 || contadorB == 2){
                                                    aux1 = false;
                                                }
                                                break;
                                            case 20:
                                                for(Booking b : courtBookings1){
                                                    Reserva re = new Reserva(b);
                                                    String name = re.getUserName();
                                                    if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time3)){
                                                        contadorA++;
                                                    }
                                                    if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time2)){
                                                        contadorB++;
                                                    }
                                                    if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time1)){
                                                        contadorA++;
                                                        contadorB++;
                                                    }
                                                }
                                                if (contadorA == 2 || contadorB == 2){
                                                    aux1 = false;
                                                }
                                                break;
                                            case 21:
                                                for(Booking b : courtBookings1){
                                                    Reserva re = new Reserva(b);
                                                    String name = re.getUserName();
                                                    if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time1)){
                                                        contadorA++;
                                                    }
                                                    if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time2)){
                                                        contadorB++;
                                                    }
                                                }
                                                if(contadorA == 1 && contadorB == 1){
                                                    aux1 = false;
                                                }
                                                break;
                                            default:
                                                for(Booking b : courtBookings1){
                                                    Reserva re = new Reserva(b);
                                                    String name = re.getUserName();
                                                    if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time1)){
                                                        contadorA++;
                                                        contadorB++;
                                                    }
                                                    if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time2)){
                                                        contadorB++;
                                                    }
                                                    if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time3)){
                                                        contadorA++;
                                                        contadorC++;
                                                    }
                                                    if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time4)){
                                                        contadorC++;
                                                    }
                                                }
                                                if (contadorA == 2 || contadorB == 2 || contadorC == 2){
                                                    aux1 = false;
                                                }
                                        }
                                        if(aux1){
                                            Booking b = Club.getInstance().registerBooking(date1, date, time, true, court, mem);
                                            courtBookings.add(b);
                                            printTable();
                                        }else{
                                            Alert alerta2 = new Alert(Alert.AlertType.ERROR);
                                            alerta2.setTitle("Error");
                                            alerta2.setHeaderText("Numero de reservas maximas por dia alcanzado");
                                            alerta2.setContentText("Si desea realizar otra reserva, cancele una de las ya existentes");

                                            Optional<ButtonType> resultado1 = alerta2.showAndWait();
                                            if (resultado.get() == ButtonType.CLOSE) {
                                                initialize();
                                            }
                                        }
                                    }
                                }catch (ClubDAOException e){
                                    e.printStackTrace();
                                }
                                catch (IOException ex){
                                    ex.printStackTrace();
                                }
                            });
                        }
                    }
                }else{
                    boton.setBackground(Background.EMPTY);
                    boton.setTextFill(Color.GREEN);
                    boton.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                    boton.setOnMouseEntered(event -> {
                        boton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
                    });
                    boton.setOnMouseExited(event -> {
                        boton.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                    });
                    boton.setOnAction(event -> {
                        try{
                            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                            alerta.setTitle("Confirmar acción");
                            alerta.setHeaderText("¿Está seguro de que desea realizar esta acción?");

                            Optional<ButtonType> resultado = alerta.showAndWait();
                            if (resultado.get() == ButtonType.OK){
                                int aux = time.getHour();
                                boolean aux1 = true;
                                int contadorA = 0;
                                int contadorB = 0;
                                int contadorC = 0;
                                switch (aux){
                                    case 9:
                                        for(Booking b : courtBookings1){
                                            Reserva re = new Reserva(b);
                                            String name = re.getUserName();
                                            if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time3)){
                                                contadorA++;
                                            }
                                            if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time4)){
                                                contadorB++;
                                            }
                                        }
                                        if(contadorA == 1 && contadorB == 1){
                                            aux1 = false;
                                        }
                                        break;
                                    case 10:
                                        for(Booking b : courtBookings1){
                                            Reserva re = new Reserva(b);
                                            String name = re.getUserName();
                                            if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time1)){
                                                contadorA++;
                                            }
                                            if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time4)){
                                                contadorB++;
                                            }
                                            if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time3)){
                                                contadorA++;
                                                contadorB++;
                                            }
                                        }
                                        if(contadorA == 2 || contadorB == 2){
                                            aux1 = false;
                                        }
                                        break;
                                    case 20:
                                        for(Booking b : courtBookings1){
                                            Reserva re = new Reserva(b);
                                            String name = re.getUserName();
                                            if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time3)){
                                                contadorA++;
                                            }
                                            if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time2)){
                                                contadorB++;
                                            }
                                            if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time1)){
                                                contadorA++;
                                                contadorB++;
                                            }
                                        }
                                        if (contadorA == 2 || contadorB == 2){
                                            aux1 = false;
                                        }
                                        break;
                                    case 21:
                                        for(Booking b : courtBookings1){
                                            Reserva re = new Reserva(b);
                                            String name = re.getUserName();
                                            if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time1)){
                                                contadorA++;
                                            }
                                            if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time2)){
                                                contadorB++;
                                            }
                                        }
                                        if(contadorA == 1 && contadorB == 1){
                                            aux1 = false;
                                        }
                                        break;
                                    default:
                                        for(Booking b : courtBookings1){
                                            Reserva re = new Reserva(b);
                                            String name = re.getUserName();
                                            if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time1)){
                                                contadorA++;
                                                contadorB++;
                                            }
                                            if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time2)){
                                                contadorB++;
                                            }
                                            if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time3)){
                                                contadorA++;
                                                contadorC++;
                                            }
                                            if(name.equals(GreenBallApp.getMember().getName()) && re.getFromTime().equals(time4)){
                                                contadorC++;
                                            }
                                        }
                                        if (contadorA == 2 || contadorB == 2 || contadorC == 2){
                                            aux1 = false;
                                        }
                                }
                                if(aux1){
                                    Booking b = Club.getInstance().registerBooking(date1, date, time, true, court, mem);
                                    courtBookings.add(b);
                                    printTable();
                                }else{
                                    Alert alerta2 = new Alert(Alert.AlertType.ERROR);
                                    alerta2.setTitle("Error");
                                    alerta2.setHeaderText("Numero de reservas maximas por dia alcanzado");
                                    alerta2.setContentText("Si desea realizar otra reserva, cancele una de las ya existentes");

                                    Optional<ButtonType> resultado1 = alerta2.showAndWait();
                                    if (resultado.get() == ButtonType.CLOSE) {
                                        initialize();
                                    }
                                }
                            }
                        }catch (ClubDAOException e){
                            e.printStackTrace();
                        }
                        catch (IOException ex){
                            ex.printStackTrace();
                        }
                    });

                }
                //colorea el cebreado de la tabla
                if (j % 2 == 0) hbox.setStyle("-fx-background-color: #f4f4f4 ; -fx-background-radius: 15");
                else hbox.setStyle("-fx-background-color: white ");

                tabla.add(hbox, i + 1, j - 8);


            }


        }




    }


    @FXML
    public void btnReturnOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/menu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        GreenBallApp.setScene(scene);
    }
}