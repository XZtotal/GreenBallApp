package GreenBallApp.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class firstRegisterModuleController
{
    BooleanProperty errorName = new SimpleBooleanProperty(true);
    BooleanProperty errorSurname = new SimpleBooleanProperty(true);
    BooleanProperty errorPhone = new SimpleBooleanProperty(true);
    boolean firstTry = true;

    boolean configMode = false;



    @javafx.fxml.FXML
    private VBox vboxName;
    @javafx.fxml.FXML
    private TextField fieldName;
    @javafx.fxml.FXML
    private Label labelName;
    @javafx.fxml.FXML
    private TextField fieldSurname;
    @javafx.fxml.FXML
    private Label labelSurname;
    @javafx.fxml.FXML
    private VBox vboxPhone;
    @javafx.fxml.FXML
    private TextField fieldPhone;
    @javafx.fxml.FXML
    private Label labelPhone;
    @javafx.fxml.FXML
    private VBox vboxSurname;
    @javafx.fxml.FXML
    private VBox vboxtext1;
    @javafx.fxml.FXML
    private VBox vboxtext2;

    @javafx.fxml.FXML
    public void initialize() {


        fieldPhone.textProperty().addListener((observable, oldValue, newValue) -> {
            String phone = newValue.replace(" ", "");
            if(!firstTry) {
                if (phone.matches("\\+[1-9][0-9]{0,2}[0-9]{9}|[0-9]{9}")) {
                    errorPhone.setValue(false);
                } else {
                    errorPhone.setValue(true);
                }
            }

        });

        vboxtext1.setVisible(true);
        vboxtext2.setVisible(false);



        errorPhone.addListener((observable, oldValue, newValue) -> {

            if (newValue) {
                vboxPhone.setStyle("-fx-background-color: rgb(251, 255, 182) ; -fx-background-radius: 10");
                labelPhone.setOpacity(1);
            } else {
                vboxPhone.setStyle("-fx-background-color: null");
                labelPhone.setOpacity(0);
            }

        });

        errorName.addListener((observable, oldValue, newValue) -> {

            if (newValue) {
                vboxName.setStyle("-fx-background-color: rgb(251, 255, 182) ; -fx-background-radius: 10");
                labelName.setOpacity(1);
            } else {
                vboxName.setStyle("-fx-background-color: null");
                labelName.setOpacity(0);
            }

        });
        errorSurname.addListener((observable, oldValue, newValue) -> {

            if (newValue) {
                vboxSurname.setStyle("-fx-background-color: rgb(251, 255, 182) ; -fx-background-radius: 10");
                labelSurname.setOpacity(1);
            } else {
                vboxSurname.setStyle("-fx-background-color: null");
                labelSurname.setOpacity(0);
            }

        });


        fieldName.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!firstTry) {
                if (newValue != null && !newValue.isEmpty()) {
                    errorName.setValue(false);
                } else {
                    errorName.setValue(true);
                }
            }
        });
        fieldSurname.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!firstTry) {
                if (newValue != null && !newValue.isEmpty()) {
                    errorSurname.setValue(false);
                } else {
                    errorSurname.setValue(true);
                }
            }
        });


        errorName.setValue(false);
        errorSurname.setValue(false);
        errorPhone.setValue(false);


    }

    /**
     * The activeConfigMode function sets the configMode variable to true.

     */
    public void activateConfigMode(String name, String surname, String phone) {
        this.configMode = true;
        fieldName.setText(name);
        fieldSurname.setText(surname);
        fieldPhone.setText(phone);

        vboxtext1.setVisible(false);
        vboxtext2.setVisible(true);


    }

    //getters and setters
    public String getName() {
        firstTry = false;
        String name = fieldName.getText();
        if (name != null && !name.isEmpty()) {
            errorName.setValue(false);
            return name;
        } else {
            errorName.setValue(true);
            return null;
        }
    }
    public String getSurname() {
        firstTry = false;
        String surname = fieldSurname.getText();
        if (surname != null && !surname.isEmpty()) {
            errorSurname.setValue(false);
            return surname;
        } else {
            errorSurname.setValue(true);
            return null;
        }
    }
    public String getPhone() {
        firstTry = false;
        String phone = fieldPhone.getText().replace(" ", "");
        //suponemos que son numeros españoles e internacinales con +%prefijo%
        if (phone != null && !phone.isEmpty() && phone.matches("\\+[1-9][0-9]{0,2}[0-9]{9}|[0-9]{9}")) {
            errorPhone.setValue(false);
            return phone;
        } else {
            errorPhone.setValue(true);
            return null;
        }
    }

}