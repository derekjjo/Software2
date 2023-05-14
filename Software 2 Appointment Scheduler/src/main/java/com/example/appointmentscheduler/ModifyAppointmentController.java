package com.example.appointmentscheduler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;



public class ModifyAppointmentController implements Initializable {
    @FXML public TextField modifyName;
    @FXML public TextField modifyID;
    @FXML public TextField description;
    @FXML public TextField location;
    @FXML public TextField type;

    private static Appointment modifyme;
    public static void addToModify(Appointment appointment){
        modifyme = appointment;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        modifyID.setText(Integer.toString(modifyme.getAppointmentID()));
        modifyName.setText(modifyme.getTitle());
        location.setText(modifyme.getLocation());
        modifyPrice.setText(Double.toString(modifyme.getPrice()));
        modifyMax.setText(Integer.toString(modifyme.getMax()));
        modifyMin.setText(Integer.toString(modifyme.getMin()));

    }
}
