/**
 *Class for the main page controller corresponding to MainMenu.fxml
 *
 */

package com.example.appointmentscheduler;
import helper.DatabaseAppointmentQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainviewController implements Initializable {

    @FXML public TableView appointmentTable;
    @FXML public TableColumn appointmentID;
    @FXML public TableColumn title;
    @FXML public TableColumn location;
    @FXML public TableColumn contact;
    @FXML public TableColumn type;
    @FXML public TableColumn startTime;
    @FXML public TableColumn endTime;
    @FXML public TableColumn customerID;
    @FXML public TableColumn userID;

//    private int appointmentID;
//    private String title;
//    private String location;
//    private int contact;
//    private String type;
//    private String startTime;
//    private String endTime;
//    private int customerID;
//    private int userID;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Appointment> list = FXCollections.observableArrayList();
        try {
            list = DatabaseAppointmentQuery.select();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        appointmentTable.setItems(list);


        appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userID.setCellValueFactory(new PropertyValueFactory<>("userID"));

    }
}