package GreenBallApp;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Club;
import model.ClubDAOException;
import model.Court;

import java.io.IOException;


public class GreenBallApp extends Application {
    public static Stage stage;
    public static Club club;
    @Override
    public void start(Stage stage) throws Exception {
        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        this.stage=stage;

        FXMLLoader loader= new  FXMLLoader(getClass().getResource("interfaces/main.fxml"));
        Parent root = loader.load();
        System.out.println("root: "+root);
        //======================================================================
        // 2- creación de la escena con el nodo raiz del grafo de escena
        Scene scene = new Scene(root);
        //======================================================================
        // 3- asiganación de la escena al Stage que recibe el metodo 
        //     - configuracion del stage
        //     - se muestra el stage de manera no modal mediante el metodo show()
        stage.setScene(scene);
        stage.setTitle("GreenBallApp > Inicio");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClubDAOException, IOException {
        initialize();
        launch(args);


        
    }
    public static void setScene(Scene scene){

        stage.setScene(scene);
        stage.show();

    }
    public static void initialize() throws ClubDAOException, IOException {
        System.out.println("GreenBallApp.initialize()");
        club = Club.getInstance();

        for (Court i : club.getCourts()) {
            System.out.println(i);
        }
        System.out.printf(String.valueOf(club.getCourt("11")));
    }



    
}