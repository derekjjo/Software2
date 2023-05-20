package com.example.appointmentscheduler;

import helper.DatabaseAppointmentQuery;
import helper.Helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class ModifyAppointmentController implements Initializable {
    @FXML
    public TextField addTitle;
    @FXML
    public TextField appointmentID;
    @FXML public TextField addDescription;
    @FXML public TextField addLocation;
    @FXML public TextField addType;
    @FXML public ComboBox<String> contactID;
    @FXML public ArrayList<String> contactIDArray;
    @FXML public DatePicker datePicker;
    @FXML public Spinner<String> startHour;
    @FXML public Spinner<String> endHour;
    @FXML public ComboBox<Integer> customerID;
    @FXML public ArrayList<Integer> customerIDArray;
    @FXML public ComboBox<Integer> userID;
    @FXML public ComboBox<String> startMinute;
    @FXML public ComboBox<String> endMinute;
    @FXML public ArrayList<Integer> userIDArray;


    private static Appointment modifyme;
    public static void addToModify(Appointment appointment){
        modifyme = appointment;
    }

    public void toMain(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Mainview.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 806);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void modifyAppointmentButton(ActionEvent actionEvent) throws IOException, SQLException {
        String t = null;
        String d = null;
        String l = null;
        String type = null;
        int cust = 0;
        int cont = 0;
        int u = 0;
        int id = modifyme.getAppointmentID();
        String startd = null;
        String endD = null;
        String c = LoginController.currentUsername;
        boolean endBool = false;
        boolean startBool = false;
        boolean overlapBool = false;
        int startHourInt = 0;
        int endHourInt = 1;
        int endMinInt = 0;
        int startMinInt = 0;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Select \"OK\" to confirm");
        alert.setTitle("Confirm");
        alert.setContentText("Are you sure you want to add the appointment?");

        if (alert.showAndWait().get()== ButtonType.OK) {
            try {
                t = addTitle.getText().trim();
                d = addDescription.getText().trim();
                l = addLocation.getText().trim();
                type = addType.getText().trim();
                cont = DatabaseAppointmentQuery.getContactIDFromName(contactID.getValue());
                cust = customerID.getValue();
                u = userID.getValue();
            } catch (NumberFormatException e) {
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setHeaderText("Appointment input type values do not match.");
                error.setTitle("Input Value Error");
                error.setContentText("Please make sure to use appropriate input values!");
                error.showAndWait();
                return;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String s = null;
            String end = null;
            try {
                startHourInt = Integer.parseInt(startHour.getValue());
                System.out.println(startHourInt);
                endHourInt = Integer.parseInt(endHour.getValue());
                System.out.println(endHourInt);
                endMinInt = Integer.parseInt(endMinute.getValue());
                System.out.println(startMinInt);
                startMinInt = Integer.parseInt(startMinute.getValue());
                System.out.println(endMinInt);
                LocalDate myDate = datePicker.getValue();
                String formattedDate = myDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                startd = formattedDate + " " + startHour.getValue() + ":" + startMinute.getValue() + ":00";
                endD = formattedDate + " " + endHour.getValue() + ":" + endMinute.getValue() + ":00";

                s = Helper.convertLocalToUTC(startd);
                end = Helper.convertLocalToUTC(endD);endBool = Helper.isInBusinessHours(endD);
                startBool = Helper.isInBusinessHours(startd);
                overlapBool = DatabaseAppointmentQuery.checkForOverlappingModifyAppointments(cust,s,end,modifyme.getAppointmentID());
                if (startHourInt > endHourInt || (startHourInt == endHourInt && startMinInt >= endMinInt)){
                    Alert ttime = Helper.alertUser("Appointment Start Must be Before Appointment END", "Update Appointment so that it starts before it ends...");
                    ttime.showAndWait();
                    return;
                }
            }
            catch (NumberFormatException e){}

            if (endBool == false || startBool == false){
                Alert a = Helper.alertUser("Appointment Not In Business Hours", "Update Appointment so that it lies between 0800 and 2200 EST");
                a.showAndWait();
                return;
            }
            if (overlapBool == true){
                Alert b = Helper.alertUser("Customer has an overlapping appointment already scheduled! ", "Update Appointment so that it does not overlap with existing appointments");
                b.showAndWait();
                return;
            }
            if ((endBool) && (startBool) && (!overlapBool))  {
                if (DatabaseAppointmentQuery.insert(t,d,l,type,s,end,cust,u,cont,c) ==1){
                    toMain(actionEvent);

                }
                else {
                    Alert x = Helper.alertUser("Something went wrong!", "Check your values and try again");
                    x.showAndWait();

                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactIDArray = new ArrayList<>(DatabaseAppointmentQuery.getContactIDs());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            customerIDArray = new ArrayList<>(DatabaseAppointmentQuery.getCustomerIDs());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            userIDArray = new ArrayList<>(DatabaseAppointmentQuery.getUserIDs());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        contactID.getItems().addAll(contactIDArray);
        customerID.getItems().addAll(customerIDArray);
        userID.getItems().addAll(userIDArray);

        appointmentID.setText(Integer.toString(modifyme.getAppointmentID()));
        addTitle.setText(modifyme.getTitle());
        addLocation.setText(modifyme.getLocation());
        addDescription.setText(modifyme.getDescription());
        addType.setText(modifyme.getType());
        try {
            contactID.setValue(DatabaseAppointmentQuery.getNameFromContactID(modifyme.getContact()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        customerID.setValue(modifyme.getCustomerID());
        userID.setValue(modifyme.getUserID());

        startMinute.getItems().addAll(
                "00","15","30","45"
        );
        endMinute.getItems().addAll(
                "00","15","30","45"
        );
        String dateSQLString = modifyme.getStartTime();
        String endDateSQLString = modifyme.getEndTime();
        String dateOnly = dateSQLString.substring(0,10);
        String startHourOnly = dateSQLString.substring(11,13);
        String endHourOnly = endDateSQLString.substring(11,13);
        String startMinOnly = dateSQLString.substring(14,16);
        String endMinOnly = endDateSQLString.substring(14,16);
        startMinute.setValue(startMinOnly);
        endMinute.setValue(endMinOnly);
        //SpinnerValueFactory<Integer> valueFactoryHour = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23);
        ObservableList<String> numbers = FXCollections.observableArrayList();
        numbers.addAll("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23");
        SpinnerValueFactory<String> valueFactoryHour = new SpinnerValueFactory.ListSpinnerValueFactory<>(numbers);
        valueFactoryHour.setValue(startHourOnly);
        SpinnerValueFactory<String> valueFactoryEndHour = new SpinnerValueFactory.ListSpinnerValueFactory<>(numbers);
        valueFactoryEndHour.setValue(endHourOnly);
        startHour.setValueFactory(valueFactoryHour);
        endHour.setValueFactory(valueFactoryEndHour);

        LocalDate localDate = LocalDate.parse(dateOnly,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        datePicker.setValue(localDate);


    }
}
