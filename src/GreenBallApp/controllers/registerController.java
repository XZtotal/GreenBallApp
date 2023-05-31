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
import model.ClubDAOException;
import model.Member;

import java.io.IOException;


public class registerController
{
    public static final int FIRST_MODULE = 1;
    public static final int SECOND_MODULE = 2;
    public static final int THIRD_MODULE = 3;
    int currentModule = 1;

    firstRegisterModuleController firstRegisterModuleController;
    secondRegisterModuleController secondRegisterModuleController;
    thirdRegisterModuleController thirdRegisterModuleController;

    Parent firstRegisterModuleParent;
    Parent secondRegisterModuleParent;
    Parent thirdRegisterModuleParent;



    String name = "";
    String surname = "";
    String phone = "";

    String userName = "";
    String password = "";
    Image profileImage = null;

    String creditCard = "";
    String exprireDate = "";
    int cvv = 0;





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
    private Label label1;
    @FXML
    private Label label3;
    @FXML
    private Label label5;
    @FXML
    private Label label2;
    @FXML
    private Label label4;

    @FXML
    public void initialize() throws IOException {

        GreenBallApp.getStage().setTitle("GreenBallApp > Registro");
        GreenBallApp.getStage().setMinWidth(700);
        GreenBallApp.getStage().setMinHeight(700);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/firstRegisterModule.fxml"));
        this.firstRegisterModuleParent = loader.load();
        this.firstRegisterModuleController = loader.getController();



        loader = new FXMLLoader(getClass().getResource("../interfaces/secondRegisterModule.fxml"));
        this.secondRegisterModuleParent = loader.load();
        this.secondRegisterModuleController = loader.getController();

        loader = new FXMLLoader(getClass().getResource("../interfaces/thirdRegisterModule.fxml"));
        this.thirdRegisterModuleParent = loader.load();
        this.thirdRegisterModuleController = loader.getController();


        bpContainer.setCenter(firstRegisterModuleParent);

        btnNext.setOnAction(event -> {
            nextPage();
        });
        btnLast.setOnAction(event -> {
            lastPage();
        });

        label1.setDisable(false);
        label2.setDisable(true);
        label3.setDisable(true);
        label4.setDisable(true);
        label5.setDisable(true);
        linkOmit.setVisible(false);
        selectActualState();


    }
    /**
     * Carga el módulo especificado en el contenedor del formulario.
     *
     * @param nModule El número del módulo que se va a cargar.
     * @throws IOException Si ocurre una excepción de E/S al cargar el módulo.
     */
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

    /**
     * Maneja la acción cuando se hace clic en el botón "Return" (Volver).
     * Navega de vuelta a la pantalla principal de la aplicación, en este caso el login.
     *
     */
    @FXML
    public void btnReturnOnAction(ActionEvent actionEvent) throws IOException {
        returnToMain();
    }
    private void returnToMain() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/main.fxml"));
        Parent root = loader.load();

        GreenBallApp.setRoot(root);
    }
    private void goToMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/menu.fxml"));
        Parent root = loader.load();

        GreenBallApp.setRoot(root);
    }

    @FXML
    public void linkOmitOnAction(ActionEvent actionEvent) {
        if(currentModule == THIRD_MODULE){
            creditCard = "";
            exprireDate = "";
            cvv = 0;
            registerMember();
        }
    }

    /**
     * Maneja la acción cuando se hace clic en el botón "Previous" (Anterior).
     * Navega al módulo anterior.
     */
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

                if(! (var == null || var2 == null || var3 <= 0) ){

                    creditCard = var;
                    exprireDate = var2;
                    cvv = var3;
                }
                try {
                    chargeModule(currentModule);
                } catch (IOException e) {
                    currentModule++;
                }

                break;

        }
        selectActualState();

        if(currentModule == FIRST_MODULE ) btnLast.setDisable(true);
        else btnLast.setDisable(false);


    }

    /**
     * Registra al miembro y navega al menú principal si el registro es exitoso.
     * Crea un nuevo objeto Member y lo registra en la instancia de GreenBallApp.
     */
    private void registerMember(){
        Member member = null;
        try {
            if(profileImage == null) profileImage = new Image("GreenBallApp/image/cimg2.png");
            member = GreenBallApp.getClub().registerMember(name,surname,phone,userName,password,creditCard,cvv,profileImage);
        } catch (ClubDAOException ignored) {

        }
        if(member == null){
            try {
                returnToMain();
            } catch (IOException ignored) {}
        }else{
            GreenBallApp.setMember(member);
            try {
                goToMenu();
            } catch (IOException ignored) {}
        }

    }
    /**
     * Maneja la acción cuando se hace clic en el botón "Next" (Siguiente).
     * Navega al siguiente módulo y recopila los datos del módulo actual.
     */
    public void nextPage(){
        String var = "";
        String var2 = "";
        String var3 = "";
        int var4 = 0;
        Image var5 = null;


        switch (currentModule) {
            case 1:
                var = firstRegisterModuleController.getName();
                var2 = firstRegisterModuleController.getSurname();
                var3 = firstRegisterModuleController.getPhone();

                if(var == null || var2 == null || var3 == null){
                    break;
                }
                name = var;
                surname = var2;
                phone = var3;



                currentModule++;
                try {
                    chargeModule(currentModule);
                    label2.setDisable(false);
                    label3.setDisable(false);
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
                    label4.setDisable(false);
                    label5.setDisable(false);
                } catch (IOException e) {
                    currentModule--;
                }

                break;
            case 3:
                var = thirdRegisterModuleController.getCreditNumber();
                var2 = thirdRegisterModuleController.getExpireDate();
                var4 = thirdRegisterModuleController.getCVV();

                if(var == null || var2 == null || var4 <= 0 ){
                    break;
                }
                creditCard = var;
                exprireDate = var2;
                cvv = var4;

                registerMember();



        }
        selectActualState();
        if(currentModule == FIRST_MODULE ) btnLast.setDisable(true);
        else btnLast.setDisable(false);
    }

    /**
     * Actualiza el estado visual del formulario para resaltar el módulo actual.
     * Aplica estilos CSS a las etiquetas correspondientes.
     */

    private void selectActualState(){
        if(currentModule == FIRST_MODULE ) {
            //poner familia css "activo"
            label3.getStyleClass().remove("actualEnabledStateLabel");
            label5.getStyleClass().remove("actualEnabledStateLabel");
            if (!label1.getStyleClass().contains("actualEnabledStateLabel"))
                label1.getStyleClass().add("actualEnabledStateLabel");


        } else if ( currentModule == SECOND_MODULE ) {
            //poner familia css "activo"
            label1.getStyleClass().remove("actualEnabledStateLabel");
            label5.getStyleClass().remove("actualEnabledStateLabel");
            if (!label3.getStyleClass().contains("actualEnabledStateLabel"))
                label3.getStyleClass().add("actualEnabledStateLabel");

        } else if ( currentModule == THIRD_MODULE ) {
            //poner familia css "activo"
            label1.getStyleClass().remove("actualEnabledStateLabel");
            label3.getStyleClass().remove("actualEnabledStateLabel");
            if (!label5.getStyleClass().contains("actualEnabledStateLabel"))
                label5.getStyleClass().add("actualEnabledStateLabel");

        }
        label1.applyCss();
        label3.applyCss();
        label5.applyCss();

        //imprime las clases css que tiene el label3





    }


}