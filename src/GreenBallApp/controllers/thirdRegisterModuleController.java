package GreenBallApp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class thirdRegisterModuleController
{
    boolean errorCreditNumber = false;
    boolean errorExpireDate = false;
    boolean errorCVV = false;


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
        });
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
                // Actualiza el campo de texto

                fieldCreditNumber.setText(formattedValue.toString());
            }


        });


    }
    public void showErrors() {
        if (errorCreditNumber) {
            vboxCreditNumber.setStyle("-fx-background-color: rgb(251, 255, 182)");
            labelCardNumber.setOpacity(1);

        }else{
            vboxCreditNumber.setStyle("-fx-background-color: null");
            labelCardNumber.setOpacity(0);
        }

        if (errorExpireDate) {
            vboxExpireDate.setStyle("-fx-background-color: rgb(251, 255, 182)");
            labelExpireDate.setOpacity(1);

        } else {
            vboxExpireDate.setStyle("-fx-background-color: null");
            labelExpireDate.setOpacity(0);
        }

        if (errorCVV) {
            vboxCVV.setStyle("-fx-background-color: rgb(251, 255, 182)");
            labelCVV.setOpacity(1);

        } else {
            vboxCVV.setStyle("-fx-background-color: null");
            labelCVV.setOpacity(0);
        }
    }

    //getters
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





}