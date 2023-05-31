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
    private Button btnPreDay;
    @FXML
    private Button btnNextDay;

    @FXML
     public void initialize() throws ClubDAOException, IOException {
        GreenBallApp.getStage().setTitle("GreenBallApp > Reservar");
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





    @FXML

    //Este metodo dibuja la tabla celda a celda comprobando si existe una reserva ya a la hora
    public void printTable() throws ClubDAOException, IOException {
        LocalDate date = this.date.getValue();
        LocalDateTime date1 = LocalDateTime.now();
        Member mem = GreenBallApp.getMember();
        List<Court> courts = Club.getInstance().getCourts();
        if(date != null) currentDate = date;
        //Recorre todas las columnas
        for (int i = 0; i < 6; i++) {

            String courtName = "Pista " + (i + 1);
            Court court = courts.get(i);
            ArrayList<Booking> courtBookings = new ArrayList<>(Club.getInstance().getCourtBookings(courtName, currentDate));
            ArrayList<Booking> courtBookings1 = new ArrayList<>(Club.getInstance().getCourtBookings(courtName, currentDate));
            //Para cada columna se recorre todas las celdas y se rellenan con botones
            for (int j = 9; j <= 21; j++) {

                LocalTime time = LocalTime.of(j, 0);
                LocalTime time1 = LocalTime.of(j-1,0);
                LocalTime time2 = LocalTime.of(j-2,0);
                LocalTime time3 = LocalTime.of(j+1,0);
                LocalTime time4 = LocalTime.of(j+2,0);


                HBox hbox = new HBox();
                hbox.setAlignment(javafx.geometry.Pos.CENTER);
                Button boton = new Button("LIBRE");

                hbox.getStylesheets().add("GreenBallApp/css/booking.css");

                hbox.getChildren().add(boton);

                if (courtBookings.size() > 0 ) {
                    //Comprueba si existe alguna reserva en el momento y pista seleccionados
                    for (int in = 0; in < courtBookings.size(); in++) {
                        Booking booking = courtBookings.get(in);
                        //Si existe se pone el nombre del usuario
                        if (booking.getFromTime().equals(time)) {
                            boton.getStyleClass().add("ocupadobtn");
                            boton.setText(String.valueOf(booking.getMember().getNickName()));
                            boton.setDisable(true);

                            hbox.setStyle("-fx-background-color: WHITE");
                            courtBookings.remove(booking);
                            boton.applyCss();
                            break;
                        //Si no existe se activa el boton y permite reservar
                        } else if(in == courtBookings.size()-1) {
                            boton.getStyleClass().add("librebtn");
                            boton.applyCss();
                            boton.setOnAction(event -> {
                                try{
                                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                                    alerta.setTitle("Confirmar acción");
                                    //Si no ha introducido los datos bancarios le salta un mensaje de advertencia
                                    if(GreenBallApp.getMember().getCreditCard() == null || GreenBallApp.getMember().getCreditCard().equals("")){
                                        alerta.setHeaderText("¿Está seguro de que desea realizar la reserva?. Como no ha introducido los datos de pago lo debera abonarlo en las instalaciones");
                                    }else{
                                        alerta.setHeaderText("¿Está seguro de que desea realizar la reserva?");
                                    }
                                    Optional<ButtonType> resultado = alerta.showAndWait();
                                    //Se comprueba si se puede realizar una reserva en esa hora y se almacena el resultado en aux1
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
                                        //Comprueba si aparte de poder reservar la hora a la que se intenta reservar no haya pasado
                                        if((aux1 && time.getHour() > LocalDateTime.now().getHour() && LocalDateTime.now().getDayOfYear() == date.getDayOfYear()) || (aux1 && LocalDateTime.now().getDayOfYear() != date.getDayOfYear())){
                                            if(GreenBallApp.getMember().getCreditCard().equals("")){
                                                Booking b = Club.getInstance().registerBooking(date1, date, time, false, court, mem);
                                                courtBookings.add(b);
                                                printTable();

                                            }
                                            Booking b = Club.getInstance().registerBooking(date1, date, time, true, court, mem);
                                            courtBookings.add(b);
                                            printTable();
                                        }else if(time.getHour() <= LocalDateTime.now().getHour()){
                                            Alert alerta2 = new Alert(Alert.AlertType.ERROR);
                                            alerta2.setTitle("Error");
                                            alerta2.setHeaderText("Error Reserva");
                                            alerta2.setContentText("No puede reservar una hora que ya ha pasado!");

                                            Optional<ButtonType> resultado1 = alerta2.showAndWait();
                                            if (resultado.get() == ButtonType.CLOSE) {
                                                initialize();
                                            }
                                        }else{
                                            Alert alerta2 = new Alert(Alert.AlertType.ERROR);
                                            alerta2.setTitle("Error");
                                            alerta2.setHeaderText("Número máximo de reservas consecutivas  alcanzado");
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
                    boton.getStyleClass().add("librebtn");
                    boton.applyCss();
                    boton.setOnAction(event -> {
                        try{
                            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                            alerta.setTitle("Confirmar acción");
                            if(GreenBallApp.getMember().getCreditCard() == null || GreenBallApp.getMember().getCreditCard().equals("")){
                                alerta.setHeaderText("¿Está seguro de que desea realizar la reserva?. Como no ha introducido los datos de pago lo debera abonarlo en las instalaciones");
                            }else{
                                alerta.setHeaderText("¿Está seguro de que desea realizar la reserva?");
                            }
                            Optional<ButtonType> resultado = alerta.showAndWait();
                            //Comprueba si se puede hacer una reserva en las columnas donde no hay ninguna reserva
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
                                if((aux1 && time.getHour() > LocalDateTime.now().getHour() && LocalDateTime.now().getDayOfYear() == date.getDayOfYear()) || (aux1 && LocalDateTime.now().getDayOfYear() != date.getDayOfYear())){
                                    Booking b;
                                    if(GreenBallApp.getMember().getCreditCard().equals("")){
                                        b = GreenBallApp.getClub().registerBooking(date1, date, time, false, court, mem);

                                    }else{
                                        b = GreenBallApp.getClub().registerBooking(date1, date, time, true, court, mem);
                                    }
                                    courtBookings.add(b);
                                    printTable();
                                }else if(time.getHour() <= LocalDateTime.now().getHour()){
                                    Alert alerta2 = new Alert(Alert.AlertType.ERROR);
                                    alerta2.setTitle("Error");
                                    alerta2.setHeaderText("Error Reserva");
                                    alerta2.setContentText("No puede reservar una hora que ya ha pasado!");

                                    Optional<ButtonType> resultado1 = alerta2.showAndWait();
                                    if (resultado.get() == ButtonType.CLOSE) {
                                        initialize();
                                    }
                                }else{
                                    Alert alerta2 = new Alert(Alert.AlertType.ERROR);
                                    alerta2.setTitle("Error");
                                    alerta2.setHeaderText("Número máximo de reservas consecutivas  alcanzado");
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
    //boton de ir para atras
    public void btnReturnOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/menu.fxml"));
        Parent root = loader.load();
        GreenBallApp.setRoot(root);
    }
}