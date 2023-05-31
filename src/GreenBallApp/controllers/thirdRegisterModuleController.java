package GreenBallApp.controllers;

import GreenBallApp.util.Utils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class thirdRegisterModuleController
{

    boolean errorCreditNumber = false; // Indica si hay un error en el número de tarjeta de crédito.
    boolean errorExpireDate = false;// Indica si hay un error en la fecha de vencimiento.
    boolean errorCVV = false; // Indica si hay un error en el CVV.


    private boolean firstTry = true;// Indica si es el primer intento de validación.

    private boolean configMode = false;

    BooleanProperty cardLoaded = new SimpleBooleanProperty(false); // Propiedad que indica si se ha cargado una tarjeta.


    configController configController;


    @FXML
    private TextField fieldCreditNumber;
    @FXML
    private DatePicker fieldExpireDate;
    @FXML
    private PasswordField pfieldCVV;
    @FXML
    private VBox vboxCreditNumber;
    @FXML
    private VBox vboxExpireDate;
    @FXML
    private VBox vboxCVV;
    @FXML
    private Label labelCardNumber;
    @FXML
    private Label labelExpireDate;
    @FXML
    private Label labelCVV;
    @FXML
    private Label text1;
    @FXML
    private Label text2;
    @FXML
    private Button btnDeleteCard;

    @FXML
    public void initialize() {
        //limite de 3 caracteseres en el campo de texto del CVV
        pfieldCVV.textProperty().addListener((observable, oldValue, newValue) -> {
            if (pfieldCVV.getText().length() > 3) {
                String s = pfieldCVV.getText().substring(0, 3);
                pfieldCVV.setText(s);
            }
            if(!this.isFirstTry()) {
                this.setErrorCVV(pfieldCVV.getText().length() != 3);
                this.showErrors();
            }
        });

        final thirdRegisterModuleController thisController = this;

        //limite de 16 caracteres sin contar los guiones en el campo de texto del numero de tarjeta y poner un "-" cada 4 caracteres. Ademas, no se puede escribir nada que no sea un numero.
        fieldCreditNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            // Elimina todos los caracteres que no son números
            if( !oldValue.equals(newValue) ) {
                String cleanedValue = newValue.replaceAll("[^\\d]", "");

                // Agrega un guión después de cada grupo de 4 caracteres
                StringBuilder formattedValue = new StringBuilder();
                for (int i = 0; i < cleanedValue.length(); i++) {
                    if (i > 0 && i % 4 == 0) {
                        formattedValue.append("-");
                    }
                    formattedValue.append(cleanedValue.charAt(i));
                }

                // Limita el campo de texto a 16 caracteres (sin contar los guiones)
                if (formattedValue.length() > 19) {
                    formattedValue.delete(19, formattedValue.length());
                }


                //Comprueba que el numero de tarjeta sea valido
                if(!thisController.isFirstTry()) {
                    thisController.setErrorCreditNumber(formattedValue.length() != 19);
                    thisController.showErrors();
                }
                // Actualiza el campo de texto.


                    fieldCreditNumber.setText(formattedValue.toString());

                //ERROR en consola: Cuando borró un caracter con un guion alante se borra el guion tambien. Esto produce un error en consola que no hemos sido capaces de solucionar.



            }




        });

        fieldExpireDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(!this.isFirstTry()) {
                this.setErrorExpireDate(newValue == null);
                this.showErrors();
            }
        });


        showErrors();
        firstTry = true;

        fieldExpireDate.setDayCellFactory((DatePicker picker) -> {
            return new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();
                    setDisable(empty || date.compareTo(today) < 0 );
                }
            };
        });

        cardLoaded.addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                btnDeleteCard.setVisible(true);
                text1.setVisible(false);
                text2.setVisible(true);

            } else {
                btnDeleteCard.setVisible(false);
                text1.setVisible(true);
                text2.setVisible(false);

            }
        });

        btnDeleteCard.setOnAction( event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar tarjeta");
            alert.setHeaderText("¿Estás seguro de que quieres eliminar la tarjeta?");
            alert.setContentText("Esta acción no se puede deshacer.");
            DialogPane dialogPane = alert.getDialogPane();
            /*dialogPane.getStylesheets().add(
                    getClass().getResource("/GreenBallApp/assets/css/darkMode.css").toExternalForm());
            dialogPane.getStyleClass().add("darkMode");*/
            if (alert.showAndWait().get() == ButtonType.OK){
                configController.deleteCard();
                cardLoaded.setValue(false);
            }




        });

        text1.setVisible(true);
        text2.setVisible(false);
        btnDeleteCard.setVisible(false);


    }

    public void activateConfigMode(configController cc,String cardNumber) {
        this.configMode = true;
        this.configController = cc;
        if(cardNumber != null && !cardNumber.equals("")) {
            cardLoaded.setValue(true);

        }

    }
    public void clearFields() {
        fieldCreditNumber.setText("");
        fieldExpireDate.setValue(null);
        pfieldCVV.setText("");
    }

    /**
     * Muestra los errores en los campos de entrada.
     */
    public void showErrors() {
        firstTry = false;
        if (errorCreditNumber) {
            vboxCreditNumber.setStyle("-fx-background-color: rgb(251, 255, 182) ; -fx-background-radius: 10");
            labelCardNumber.setOpacity(1);

        }else{
            vboxCreditNumber.setStyle("-fx-background-color: null");
            labelCardNumber.setOpacity(0);
        }

        if (errorExpireDate) {
            vboxExpireDate.setStyle("-fx-background-color: rgb(251, 255, 182) ; -fx-background-radius: 10");
            labelExpireDate.setOpacity(1);

        } else {
            vboxExpireDate.setStyle("-fx-background-color: null");
            labelExpireDate.setOpacity(0);
        }

        if (errorCVV) {
            vboxCVV.setStyle("-fx-background-color: rgb(251, 255, 182); -fx-background-radius: 10");
            labelCVV.setOpacity(1);

        } else {
            vboxCVV.setStyle("-fx-background-color: null");
            labelCVV.setOpacity(0);
        }
    }

    //Getters

    /**
     * Obtiene el número de tarjeta de crédito ingresado.
     *
     * @return El número de tarjeta de crédito. Null si el texto no es válido.
     */
    public String getCreditNumber() {
        if (fieldCreditNumber.getText().replaceAll("-", "") .length() != 16) {
            errorCreditNumber = true;
            showErrors();

            return null;
        }
        errorCreditNumber = false;
        showErrors();

        return fieldCreditNumber.getText().replaceAll("-", "");

    }

    /**
     * Obtiene la fecha de vencimiento ingresada.
     *
     * @return La fecha de vencimiento. Null si el texto no es válido.
     */
    public String getExpireDate() {
        if (fieldExpireDate.getValue() == null) {
            errorExpireDate = true;
            showErrors();

            return null;
        }
        errorExpireDate = false;
        showErrors();

        return fieldExpireDate.getValue().toString();
    }


    /**
     * Obtiene el número CVV de la tarjeta de crédito.
     *
     * @return El número CVV. -1 si el CVV no es válido.
     */
    public int getCVV() {
        if (pfieldCVV.getText().length() != 3 || !Utils.isNumeric(pfieldCVV.getText()) ) {
            errorCVV = true;
            showErrors();

            return -1;
        }
        errorCVV = false;
        showErrors();

        return Integer.parseInt(pfieldCVV.getText());
    }
    /**
     * Verifica si es el primer intento de validación de los campos.
     *
     * @return True si es el primer intento, False de lo contrario.
     */
    public boolean isFirstTry() {
        return firstTry;
    }

    /**
     * Verifica si hay un error en el número de tarjeta de crédito.
     *
     * @return True si hay un error, False de lo contrario.
     */

    public boolean isErrorCreditNumber() {
        return errorCreditNumber;
    }

    public void setErrorCreditNumber(boolean errorCreditNumber) {
        this.errorCreditNumber = errorCreditNumber;
    }

    public boolean isErrorExpireDate() {
        return errorExpireDate;
    }

    public void setErrorExpireDate(boolean errorExpireDate) {
        this.errorExpireDate = errorExpireDate;
    }

    public boolean isErrorCVV() {
        return errorCVV;
    }

    public void setErrorCVV(boolean errorCVV) {
        this.errorCVV = errorCVV;
    }



}