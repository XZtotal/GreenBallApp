package GreenBallApp.controllers;

import GreenBallApp.GreenBallApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

import java.io.IOException;


public class registerController
{
    public static final int FIRST_MODULE = 1;
    public static final int SECOND_MODULE = 2;
    public static final int THIRD_MODULE = 3;
    int currentModule = 3;

    firstRegisterModuleController firstRegisterModuleController;
    secondRegisterModuleController secondRegisterModuleController;
    thirdRegisterModuleController thirdRegisterModuleController;

    Parent firstRegisterModuleParent;
    Parent secondRegisterModuleParent;
    Parent thirdRegisterModuleParent;



    String name = "";
    String surname = "";
    String email = "";

    String userName = "";
    String password = "";
    Image profileImage = null;

    String creditCard = "";
    String exprireDate = "";
    String cvv = "";





    @FXML
    private Button btnReturn;
    @FXML
    private BorderPane bpContainer;
    @FXML
    private Hyperlink linkOmit;
    @FXML
    private Button btnLast;
    @FXML
    private Button btnNext;

    @FXML
    public void initialize() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/firstRegisterModule.fxml"));
        this.firstRegisterModuleParent = loader.load();
        this.firstRegisterModuleController = loader.getController();

        loader = new FXMLLoader(getClass().getResource("../interfaces/secondRegisterModule.fxml"));
        this.secondRegisterModuleParent = loader.load();
        this.secondRegisterModuleController = loader.getController();

        loader = new FXMLLoader(getClass().getResource("../interfaces/thirdRegisterModule.fxml"));
        this.thirdRegisterModuleParent = loader.load();
        this.thirdRegisterModuleController = loader.getController();


        bpContainer.setCenter(thirdRegisterModuleParent);

        btnNext.setOnAction(event -> {
            nextPage();
        });
        btnLast.setOnAction(event -> {
            lastPage();
        });


    }

    public void chargeModule(int nModule) throws IOException {
        switch (nModule) {
            case 1:
                bpContainer.setCenter(firstRegisterModuleParent);
                break;

            case 2:
                bpContainer.setCenter(secondRegisterModuleParent);
                break;

            case 3:
                bpContainer.setCenter(thirdRegisterModuleParent);
                break;

        }
    }


    @FXML
    public void btnReturnOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/main.fxml"));
        Parent root = loader.load();

        GreenBallApp.setRoot(root);
    }

    @FXML
    public void linkOmitOnAction(ActionEvent actionEvent) {
    }


    public void lastPage(){
        switch (currentModule) {
            case 1:

                break;
            case 2:
                currentModule--;

                try {
                    chargeModule(currentModule);
                } catch (IOException e) {
                    currentModule++;
                }
                break;

            case 3:
                String var = thirdRegisterModuleController.getCreditNumber();
                String var2 = thirdRegisterModuleController.getExpireDate();
                int var3 = thirdRegisterModuleController.getCVV();
                linkOmit.setVisible(false);
                currentModule--;

                if(! (var == null || var2 == null || var3 < 0) ){

                    creditCard = var;
                    exprireDate = var2;
                    cvv = String.valueOf(var3);
                }
                try {
                    chargeModule(currentModule);
                } catch (IOException e) {
                    currentModule++;
                }

                break;

        }

        if(currentModule == FIRST_MODULE ) btnLast.setDisable(true);
        else btnLast.setDisable(false);


    }

    public void nextPage(){
        String var = "";
        String var2 = "";
        String var3 = "";
        int var4 = 0;
        Image var5 = null;


        switch (currentModule) {
            case 1:
                currentModule++;
                try {
                    chargeModule(currentModule);
                    System.out.println("hola");
                } catch (IOException e) {
                    currentModule--;
                    e.printStackTrace();
                }
                break;


            case 2:

                var = secondRegisterModuleController.getUserName();
                var2 = secondRegisterModuleController.getPassword();
                var5 = secondRegisterModuleController.getImage();

                if(var == null || var2 == null){
                    break;
                }
                userName = var;
                password = var2;
                profileImage = var5;

                linkOmit.setVisited(false);
                linkOmit.setVisible(true);
                currentModule++;
                try {
                    chargeModule(currentModule);
                } catch (IOException e) {
                    currentModule--;
                }





                break;
            case 3:
                var = thirdRegisterModuleController.getCreditNumber();
                var2 = thirdRegisterModuleController.getExpireDate();
                var4 = thirdRegisterModuleController.getCVV();

                if(var == null || var2 == null || var4 < 0 ){
                    break;
                }
                creditCard = var;
                exprireDate = var2;
                cvv = String.valueOf(var4);



        }
        if(currentModule == FIRST_MODULE ) btnLast.setDisable(true);
        else btnLast.setDisable(false);
    }


}