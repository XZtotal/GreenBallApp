package GreenBallApp;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Club;
import model.ClubDAOException;
import model.Court;
import model.Member;

import java.io.IOException;


public class GreenBallApp extends Application {
    public static Stage stage;
    private static Club club;
    private static Member member;

    @Override
    public void start(Stage stage) throws Exception {
        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        this.stage=stage;


        FXMLLoader loader= new FXMLLoader(getClass().getResource("interfaces/main.fxml"));
        Parent root = loader.load();
        ;
        //======================================================================
        // 2- creación de la escena con el nodo raiz del grafo de escena
        Scene scene = new Scene(root);
        //======================================================================
        // 3- asiganación de la escena al Stage que recibe el metodo 
        //     - configuracion del stage
        //     - se muestra el stage de manera no modal mediante el metodo show()
        stage.setScene(scene);
        stage.setTitle("GreenBallApp > Inicio");
        stage.setMinWidth(600);
        stage.setMinHeight(600);
        stage.show();

        try {
            stage.getIcons().add(new Image("GreenBallApp/image/bola.png"))  ;
        }catch (Exception e){
            System.out.println("No se ha podido cargar la imagen");
        }

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
    public static void setRoot(Parent root){

        stage.getScene().setRoot(root);
        stage.show();

    }

    public static void initialize() throws ClubDAOException, IOException {
        System.out.println("GreenBallApp.initialize()");
        club = Club.getInstance();




    }
    public static Stage getStage(){
        return stage;
    }

    public static Member getMember() {
        return member;
    }

    public static void setMember(Member member) {
        GreenBallApp.member = member;

    }

    public static Club getClub() {
        return club;
    }




}
