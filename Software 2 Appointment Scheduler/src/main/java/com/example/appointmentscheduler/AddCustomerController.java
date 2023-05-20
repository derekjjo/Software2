package com.example.appointmentscheduler;

import helper.DatabaseAppointmentQuery;
import helper.DatabaseCustomerQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {
    @FXML public TextField customerID;
    @FXML public TextField addName;
    @FXML public TextField addAddress;
    @FXML public TextField addPostal;
    @FXML public TextField addPhone;
    @FXML public ComboBox<String> addCountry;
    @FXML public ArrayList<String> countryArray;
    @FXML public ArrayList<String> divArray;
    @FXML public ComboBox<String> division;

    public void toMain(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Mainview.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 806);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void getDivisions(){
        divArray = new ArrayList<>();
        division.getItems().clear();
        division.getItems().addAll(divArray);
        try {
            divArray = (DatabaseCustomerQuery.getDivisions(addCountry.getValue()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        division.getItems().addAll(divArray);

    }

    public void addCustomerButton(ActionEvent actionEvent) throws IOException, SQLException {
        String n = null;
        String a = null;
        String post = null;
        String phone = null;
        String country = null;
        int div = 0;
        String c = LoginController.currentUsername;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Select \"OK\" to confirm");
        alert.setTitle("Confirm");
        alert.setContentText("Are you sure you want to add the customer?");

        if (alert.showAndWait().get()== ButtonType.OK) {
            try {
                n = addName.getText().trim();
                a = addAddress.getText().trim();
                post = addPostal.getText().trim();
                phone = addPhone.getText().trim();
                div = DatabaseCustomerQuery.getDivisionIDFromName(division.getValue());
            } catch (NumberFormatException e) {
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setHeaderText("Part input type values do not match.");
                error.setTitle("Input Value Error");
                error.setContentText("Please make sure to use appropriate input values!");
                error.showAndWait();
                return;
            }
            if(DatabaseCustomerQuery.insert(n,a,post,phone,div,c) == 1){
                toMain(actionEvent);
            }
            else{
                Alert unableto = new Alert(Alert.AlertType.ERROR);
                unableto.setHeaderText("Unable to add Customer");
                unableto.setTitle("Confirm");
                unableto.setContentText("Check your values and try again.");
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            countryArray = new ArrayList<>(DatabaseCustomerQuery.getCountries());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        addCountry.getItems().addAll(countryArray);

    }
}
