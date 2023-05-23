package GreenBallApp.controllers;

import GreenBallApp.util.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class thirdRegisterModuleController
{

    boolean errorCreditNumber = false;
    boolean errorExpireDate = false;
    boolean errorCVV = false;


    public boolean firstTry = true;


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
                System.out.println("antes de settext");

                //Comprueba que el numero de tarjeta sea valido
                if(!thisController.isFirstTry()) {
                    thisController.setErrorCreditNumber(formattedValue.length() != 19);
                    thisController.showErrors();
                }
                // Actualiza el campo de texto
                fieldCreditNumber.setText(formattedValue.toString());
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


    }
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
     * The getCreditNumber function returns the value of the fieldCreditNumber variable.
     * @return The value of the CreditCardNumber. Null if the text is not valid.
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
     * The getExpireDate function returns the value of the fieldExpireDate variable.
     * @return The value of the ExpireDate. Null if the text is not valid.
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
     * The getCVV function returns the CVV number of the credit card.

     * @return CVV number. - 1 if the cvv is not valid.
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

    public boolean isFirstTry() {
        return firstTry;
    }



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