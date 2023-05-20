/**
 *Class for the main page controller corresponding to MainMenu.fxml
 *
 */

package com.example.appointmentscheduler;
import helper.DatabaseAppointmentQuery;
import helper.DatabaseCustomerQuery;
import helper.JDBC;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
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

    @FXML private TableView appointmentTable;
    @FXML private TableView customerTable;
    @FXML public AnchorPane scenePane;
    @FXML private TableColumn appointmentID;
    @FXML private TableColumn title;
    @FXML private TableColumn location;
    @FXML private TableColumn contact;
    @FXML private TableColumn type;
    @FXML private TableColumn startTime;
    @FXML private TableColumn endTime;
    @FXML private TableColumn customerID;
    @FXML private TableColumn userID;
    @FXML private TableColumn customerIDcustomer;
    @FXML private TableColumn customerName;
    @FXML private TableColumn address;
    @FXML private TableColumn phone;
    @FXML private TableColumn divisionID;

    @FXML private ToggleGroup AppointmentsBY;
    @FXML public RadioButton viewWeekRB;
    @FXML public RadioButton viewMonthRB;
    @FXML public RadioButton viewAllRB;
    public Appointment modifyMeAppointment;
    public Customer modifyMeCustomer;

    public void addAppointmentButtonPushed(ActionEvent event) throws IOException {
        Parent addAppointmentParent = FXMLLoader.load(getClass().getResource("AddAppointment.fxml"));
        Scene addAppointmentScene = new Scene(addAppointmentParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(addAppointmentScene);
        window.show();
    }
    public void addCustomerButtonPushed(ActionEvent event) throws IOException {
        Parent addAppointmentParent = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
        Scene addAppointmentScene = new Scene(addAppointmentParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(addAppointmentScene);
        window.show();
    }
    public void viewReportsButton(ActionEvent event) throws IOException {
        Parent addAppointmentParent = FXMLLoader.load(getClass().getResource("Reports.fxml"));
        Scene addAppointmentScene = new Scene(addAppointmentParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(addAppointmentScene);
        window.show();
    }
    public void modifyAppointmentButtonPushed(ActionEvent event) throws IOException {
        modifyMeAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();

        ModifyAppointmentController.addToModify(modifyMeAppointment);

        Parent addPartParent = FXMLLoader.load(getClass().getResource("ModifyAppointment.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        // This line gets the stage info
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(addPartScene);
        window.show();
    }
    public void modifyCustomerButtonPushed(ActionEvent event) throws IOException {
        modifyMeCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        ModifyCustomerController.addToModify(modifyMeCustomer);

        Parent addPartParent = FXMLLoader.load(getClass().getResource("ModifyCustomer.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        // This line gets the stage info
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(addPartScene);
        window.show();
    }

    public void appointmentMonthRBPressed(){
        ObservableList<Appointment> lists;
        try {
            lists = DatabaseAppointmentQuery.selectMonth();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        appointmentTable.setItems(lists);
    }
    public void appointmentWeekRBPressed(){
        ObservableList<Appointment> lists;
        try {
            lists = DatabaseAppointmentQuery.selectWeek();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        appointmentTable.setItems(lists);
    }
    public void appointmentAllRBPressed(){
        ObservableList<Appointment> lists;
        try {
            lists = DatabaseAppointmentQuery.select();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        appointmentTable.setItems(lists);
    }
    public void deleteAppointmentButton() throws SQLException {
        ObservableList<Appointment> selectedRow;
        //gives us selected rows
        selectedRow = appointmentTable.getSelectionModel().getSelectedItems();
        int appID = selectedRow.get(0).getAppointmentID();
        String type = selectedRow.get(0).getType();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Select \"OK\" to confirm");
        alert.setTitle("Confirm");
        alert.setContentText("Are you sure you want to DELETE the selected appointment? Appointment ID: " + appID + " Type: "+type);
        if (alert.showAndWait().get()==ButtonType.OK){
            for (Appointment appointment: selectedRow){
                DatabaseAppointmentQuery.delete(appointment.getAppointmentID());
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                alert1.setHeaderText("Appointment Deleted");
                alert1.setTitle("Confirm");
                alert1.setContentText("Appointment ID: " + appID + " Type: "+type + " has been successfully deleted.");
                alert1.showAndWait();
            }
            ObservableList<Appointment> list;
            try {
                list = DatabaseAppointmentQuery.select();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            appointmentTable.setItems(list);
        }
    }
    public void deleteCustomerButton() throws SQLException {
        ObservableList<Customer> selectedRow;
        //gives us selected rows
        selectedRow = customerTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Select \"OK\" to confirm");
        alert.setTitle("Confirm");
        alert.setContentText("Are you sure you want to DELETE the selected Customer? This action will also delete all appointments associated with that customer!");
        if (alert.showAndWait().get()==ButtonType.OK){
            for (Customer customer: selectedRow){
                DatabaseCustomerQuery.delete(customer.getCustomerId());
            }
            ObservableList<Customer> list;
            try {
                list = DatabaseCustomerQuery.select();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ObservableList<Appointment> listApp;
            try {
                listApp = DatabaseAppointmentQuery.select();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            appointmentTable.setItems(listApp);
            customerTable.setItems(list);
        }
    }

    Stage stage;
    public void logout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Select \"OK\" to confirm");
        alert.setTitle("Confirm");
        alert.setContentText("Are you sure you want to Exit?");
        if (alert.showAndWait().get()==ButtonType.OK){
            JDBC.closeConnection();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppointmentsBY = new ToggleGroup();
        this.viewAllRB.setToggleGroup(AppointmentsBY);
        this.viewMonthRB.setToggleGroup(AppointmentsBY);
        this.viewWeekRB.setToggleGroup(AppointmentsBY);


        ObservableList<Appointment> list;
        try {
            list = DatabaseAppointmentQuery.select();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        appointmentTable.setItems(list);

        ObservableList<Customer> customerList;
        try {
            customerList = DatabaseCustomerQuery.select();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        customerTable.setItems(customerList);


        appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userID.setCellValueFactory(new PropertyValueFactory<>("userID"));

        customerIDcustomer.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        divisionID.setCellValueFactory(new PropertyValueFactory<>("divisionID"));



    }
}