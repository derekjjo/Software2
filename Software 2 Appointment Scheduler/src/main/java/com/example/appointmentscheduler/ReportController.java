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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    @FXML private TableView appointmentTable;
    @FXML private TableView appTypeTable;
    @FXML private TableView createdTable;
    @FXML public AnchorPane scenePane;
    @FXML private TableColumn appointmentID;
    @FXML private TableColumn title;
    @FXML private TableColumn description;
    @FXML private TableColumn type;
    @FXML private TableColumn startTime;
    @FXML private TableColumn endTime;
    @FXML private TableColumn customerID;

    @FXML private TableColumn Count;
    @FXML private TableColumn Type;
    @FXML private TableColumn Month;
    @FXML private TableColumn countCreated;
    @FXML private TableColumn monthCreated;

    @FXML public ArrayList<Integer> contactIDArray;
    @FXML public ComboBox<Integer> contactIDNumber;
    @FXML public ComboBox<Integer> monthCombo;

    Stage stage;

    public void toMain(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Mainview.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 806);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
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
    public void changeContactID(){
        ObservableList<Appointment> lists;
        int a = contactIDNumber.getValue();
        try {
            lists = DatabaseAppointmentQuery.selectContactID(a);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        appointmentTable.setItems(lists);
    }
    public void changeMonth(){
        ObservableList<typeMonthAppt> lists;
        int a = monthCombo.getValue();
        try {
            lists = DatabaseAppointmentQuery.selectTypeMonth(a);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        appTypeTable.setItems(lists);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactIDArray = new ArrayList<>(DatabaseAppointmentQuery.getContactIDNumbers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        monthCombo.getItems().addAll(
                1,2,3,4,5,6,7,8,9,10,11,12
        );
        monthCombo.setValue(1);

        contactIDNumber.getItems().addAll(contactIDArray);
        contactIDNumber.setValue(contactIDArray.get(0));
        ObservableList<Appointment> list;
        try {
            list = DatabaseAppointmentQuery.selectContactID(contactIDNumber.getValue());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        appointmentTable.setItems(list);

        ObservableList<typeMonthAppt> customerList;
        try {
            customerList = DatabaseAppointmentQuery.selectTypeMonth(monthCombo.getValue());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        appTypeTable.setItems(customerList);

        ObservableList<createdReport> createdList;
        try {
            createdList = DatabaseAppointmentQuery.selectCreated();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        createdTable.setItems(createdList);


        appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));


        Count.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        Month.setCellValueFactory(new PropertyValueFactory<>("month"));

        countCreated.setCellValueFactory(new PropertyValueFactory<>("count"));
        monthCreated.setCellValueFactory(new PropertyValueFactory<>("month"));

    }
}