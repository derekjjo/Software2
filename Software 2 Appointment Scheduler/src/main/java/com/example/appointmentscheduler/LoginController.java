
package com.example.appointmentscheduler;
import helper.DatabaseAppointmentQuery;
import helper.DatabaseUserAuth;
import helper.Helper;
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EventObject;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.time.ZoneOffset.UTC;

public class LoginController implements Initializable {


    @FXML public TextField usernameTF;
    @FXML public TextField passwordTF;
    @FXML public Label header;
    @FXML public Label usernameLabel;
    @FXML public Label passwordLabel;
    @FXML public Label languageLabel;
    @FXML public Label timeZoneLabel;
    @FXML public Label timetextLabel;
    @FXML public ComboBox languageBox;
    @FXML public Button loginButton;
    @FXML public Button resetButton;

    public static String currentUsername = null;
    public Boolean isInFrench = false;
    public void toMain(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Mainview.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 806);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void reset(){
        usernameTF.setText("");
        passwordTF.setText("");
    }
    public void login(ActionEvent event) throws SQLException, IOException {
        String username = usernameTF.getText().trim();
        String password = passwordTF.getText().trim();

        if (DatabaseUserAuth.login(username,password) == 1){
            try {
                FileWriter myWriter = new FileWriter("login_activity.txt",true);
                myWriter.write("\n Username: " + username + " Successful login at " + Helper.currentUTCDateTime()+ " UTC");
                myWriter.close();
            } catch (IOException e) {}
            currentUsername = username;
            Appointment a = DatabaseAppointmentQuery.getUserAppointment(username);
            Alert p;
            if (a == null){
                if  (!isInFrench){ p = Helper.alertUser("No Appointments in next 15 minutes ", "You have no appointments scheduled in the next 15 minutes");}
                else{ p = Helper.alertUser("Aucun rendez-vous dans les 15 prochaines minutes", "Aucun rendez-vous dans les 15 prochaines minutes");}
            }
            else {
                String startTime = a.getStartTime().substring(11,16);
                if  (!isInFrench){ p = Helper.alertUser("You have an appointment scheduled in the next 15 minutes!", "Appointment ID: " + a.getAppointmentID() + " Starts at : " + startTime);}
                else{ p = Helper.alertUser("Vous avez un rendez-vous dans les 15 prochaines minutes!", "Identifiant de rendez-vous: " + a.getAppointmentID() + " Commence : " + startTime);}
            }
            p.showAndWait();
            toMain(event);
        }
        else {
            try {
                FileWriter myWriter = new FileWriter("login_activity.txt",true);
                myWriter.write("\n Username: " + username + " Unsuccessful login at " + Helper.currentUTCDateTime()+ " UTC");
                myWriter.close();
            } catch (IOException e) {
            }
            Alert m = Helper.alertUser("Username AND/OR Password Incorrect", "Please Try to input your Username and Password again- but more carefully. ");
            m.showAndWait();
        }
    }
    public void languageBoxAction(){
        if (languageBox.getValue().equals("English") || languageBox.getValue().equals("Anglais")){
            isInFrench = false;
            header.setText("Appointment Manager");
            usernameLabel.setText("Username");
            passwordLabel.setText("Password");
            loginButton.setText("Log in");
            resetButton.setText("Reset");
            languageLabel.setText("Language: ");
            timetextLabel.setText("TimeZone: ");
            languageBox.setValue("English");
            languageBox.getItems().clear();
            languageBox.getItems().addAll(
                    "English",
                    "French"
            );

        }
        if (languageBox.getValue().equals("French") || languageBox.getValue().equals("Francais")){
            isInFrench = true;
            header.setText("Gestionnaire De Rendez-vous");
            usernameLabel.setText("Nom d'utilisateur");
            passwordLabel.setText("Mot de passe");
            loginButton.setText("Connexion");
            resetButton.setText("Reinitialiser");
            languageLabel.setText("Langue: ");
            timetextLabel.setText("Fuseau Horaire: ");
            languageBox.setValue("Francais");
            languageBox.getItems().clear();
            languageBox.getItems().addAll(
                    "Anglais",
                    "Francais"
            );
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId userZone = ZoneId.systemDefault();
        timeZoneLabel.setText(String.valueOf(userZone));
        languageBox.getItems().addAll(
                "English",
                "French"
        );

        if(Locale.getDefault().getLanguage().equals("fr")) {
            isInFrench = true;
            header.setText("Gestionnaire De Rendez-vous");
            usernameLabel.setText("Nom d'utilisateur");
            passwordLabel.setText("Mot de passe");
            loginButton.setText("Connexion");
            resetButton.setText("Reinitialiser");
            languageLabel.setText("Langue: ");
            timetextLabel.setText("Fuseau Horaire: ");
            languageBox.setValue("Francais");
            languageBox.getItems().clear();
            languageBox.getItems().addAll(
                    "Anglais",
                    "Francais"
            );
        }



    }
}