package GreenBallApp.controllers;

import GreenBallApp.GreenBallApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javax.xml.crypto.Data;
import java.io.IOException;

public class configController
{
    public static final int CONFIG_MODULE = 0;
    public static final int FIRST_MODULE = 1;
    public static final int SECOND_MODULE = 2;
    public static final int THIRD_MODULE = 3;
    private int currentModule = 0;

    configModuleController configModuleController;

    firstRegisterModuleController firstRegisterModuleController;
    secondRegisterModuleController secondRegisterModuleController;
    thirdRegisterModuleController thirdRegisterModuleController;

    Parent configModuleParent;
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

    boolean dataChanged = false;

    @javafx.fxml.FXML
    private Label labelTop;
    @javafx.fxml.FXML
    private BorderPane bpContainer;
    @javafx.fxml.FXML
    private HBox hboxDown;
    @javafx.fxml.FXML
    private Button btnLast;
    @javafx.fxml.FXML
    private Button btnSave;
    @javafx.fxml.FXML
    private Label labelDown;
    @javafx.fxml.FXML
    private Button btnReturn;

    @javafx.fxml.FXML
    public void initialize() throws IOException {
        GreenBallApp.getStage().setTitle("GreenBallApp > Mi cuenta");
        GreenBallApp.getStage().setMinWidth(600);
        GreenBallApp.getStage().setMinHeight(700);
        loadData();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/firstRegisterModule.fxml"));
        this.firstRegisterModuleParent = loader.load();
        this.firstRegisterModuleController = loader.getController();
        firstRegisterModuleController.activateConfigMode(name,surname,phone);

        loader = new FXMLLoader(getClass().getResource("../interfaces/secondRegisterModule.fxml"));
        this.secondRegisterModuleParent = loader.load();
        this.secondRegisterModuleController = loader.getController();
        secondRegisterModuleController.activateConfigMode(userName,password);

        loader = new FXMLLoader(getClass().getResource("../interfaces/thirdRegisterModule.fxml"));
        this.thirdRegisterModuleParent = loader.load();
        this.thirdRegisterModuleController = loader.getController();
        thirdRegisterModuleController.activateConfigMode(this,creditCard);

        loader = new FXMLLoader(getClass().getResource("../interfaces/configModule.fxml"));
        this.configModuleParent = loader.load();
        this.configModuleController = loader.getController();
        configModuleController.setConfigController(this);


        bpContainer.setCenter(configModuleParent);

        btnSave.setOnAction(event -> {
            if (!saveData()) return;
            try {
                back();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        btnLast.setOnAction(event -> {
            try {
                back();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        hboxDown.setVisible(false);
        labelDown.setVisible(true);
        bpContainer.getStyleClass().removeAll("generalContainer");
        bpContainer.getStyleClass().add("generalContainer2");





    }
    /**
     * Maneja la acción cuando se hace clic en el botón "Return" (Volver).
     * Navega de vuelta a la pantalla principal de la aplicación.
     */
    @javafx.fxml.FXML
    public void btnReturnOnAction(ActionEvent actionEvent) {
        try {
            MenuBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga el módulo especificado en el contenedor del formulario.
     *
     * @param nModule El número del módulo que se va a cargar.
     * @throws IOException Si ocurre una excepción de E/S al cargar el módulo.
     */

    public void chargeModule(int nModule) throws IOException {
        switch (nModule) {
            case CONFIG_MODULE:
                bpContainer.setCenter(configModuleParent);
                currentModule = nModule;
                bpContainer.getStyleClass().removeAll("generalContainer");
                bpContainer.getStyleClass().add("generalContainer2");

                break;
            case FIRST_MODULE:
                bpContainer.setCenter(firstRegisterModuleParent);
                firstRegisterModuleController.activateConfigMode(name,surname,phone);
                currentModule = nModule;
                labelTop.setText("Identificación");
                labelDown.setVisible(false);
                hboxDown.setVisible(true);

                bpContainer.getStyleClass().removeAll("generalContainer2");
                bpContainer.getStyleClass().add("generalContainer");

                break;

            case SECOND_MODULE:
                bpContainer.setCenter(secondRegisterModuleParent);
                secondRegisterModuleController.activateConfigMode(userName,password);
                currentModule = nModule;
                labelTop.setText("Seguridad");
                labelDown.setVisible(false);
                hboxDown.setVisible(true);
                bpContainer.getStyleClass().removeAll("generalContainer2");
                bpContainer.getStyleClass().add("generalContainer");
                break;

            case THIRD_MODULE:
                thirdRegisterModuleController.clearFields();
                bpContainer.setCenter(thirdRegisterModuleParent);
                currentModule = nModule;
                labelTop.setText("Datos de Pago");
                labelDown.setVisible(false);
                hboxDown.setVisible(true);
                bpContainer.getStyleClass().removeAll("generalContainer2");
                bpContainer.getStyleClass().add("generalContainer");

                break;

        }
    }


    private void back() throws IOException {

        chargeModule(CONFIG_MODULE);
        hboxDown.setVisible(false);
        labelDown.setVisible(true);
        labelTop.setText("Opciones");


    }

    /**
     * Elimina la tarjeta de crédito del miembro actual.
     * Borra los datos de la tarjeta de crédito del miembro actualmente registrado en GreenBallApp.
     */
    public void deleteCard() {
        creditCard = "";
        exprireDate = "";
        cvv = 0;
        pushData();
    }

    /**
     * Navega de vuelta al menú principal.
     * Carga la interfaz de usuario del menú principal y la establece como la raíz de la aplicación GreenBallApp.
     *
     * @throws IOException Si ocurre una excepción de E/S al cargar la interfaz de usuario del menú principal.
     */
    private void MenuBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/menu.fxml"));
        Parent root = loader.load();

        GreenBallApp.setRoot(root);

    }
    /**
     * Carga los datos del miembro en las variables locales.
     * Obtiene los datos del miembro actualmente registrado en GreenBallApp y los almacena en las variables locales correspondientes.
     */
    public void loadData() {
        name = GreenBallApp.getMember().getName();
        surname = GreenBallApp.getMember().getSurname();
        phone = GreenBallApp.getMember().getTelephone();
        userName = GreenBallApp.getMember().getNickName();
        password = GreenBallApp.getMember().getPassword();
        profileImage = GreenBallApp.getMember().getImage();
        creditCard = GreenBallApp.getMember().getCreditCard();
        cvv = GreenBallApp.getMember().getSvc();

    }

    /**
     * Guarda los datos modificados en el miembro actual.
     * Actualiza los datos del miembro actualmente registrado en GreenBallApp con los valores modificados en el formulario.
     *
     * @return true si los datos se guardaron correctamente, false en caso contrario.
     */

    private boolean saveData() {
        boolean result = false;
        switch(currentModule){
            case FIRST_MODULE -> {
                String var0 = firstRegisterModuleController.getName();
                String var1 = firstRegisterModuleController.getSurname();
                String var2 = firstRegisterModuleController.getPhone();
                if(var0 != null && var1 != null && var2 != null){
                    name = var0;
                    surname = var1;
                    phone = var2;

                    dataChanged = true;
                    result = true;
                }
            }
            case SECOND_MODULE -> {

                String var1 = secondRegisterModuleController.getPassword();
                if ( var1 != null) {

                    password = var1;

                    dataChanged = true;
                    result = true;
                }

            }
            case THIRD_MODULE -> {
                String var0 = thirdRegisterModuleController.getCreditNumber();
                String var1 = thirdRegisterModuleController.getExpireDate();
                int var2 = thirdRegisterModuleController.getCVV();
                if (var0 != null && var1 != null && var2 != 0) {
                    creditCard = var0;
                    exprireDate = var1;
                    cvv = var2;

                    dataChanged = true;
                    thirdRegisterModuleController.activateConfigMode(this,creditCard);
                    result = true;
                }
            }


            }
        if(dataChanged){
            pushData();

        }
        return result;
    }
    /**
     * Actualiza los datos del miembro actual en GreenBallApp.
     * Actualiza los datos del miembro actualmente registrado en GreenBallApp con los valores almacenados en las variables locales.
     */

    public void pushData(){
        GreenBallApp.getMember().setName(name);
        GreenBallApp.getMember().setSurname(surname);
        GreenBallApp.getMember().setTelephone(phone);
        GreenBallApp.getMember().setNickName(userName);
        GreenBallApp.getMember().setPassword(password);
        GreenBallApp.getMember().setCreditCard(creditCard);
        GreenBallApp.getMember().setSvc(cvv);
        GreenBallApp.getMember().setImage(profileImage);
    }

}