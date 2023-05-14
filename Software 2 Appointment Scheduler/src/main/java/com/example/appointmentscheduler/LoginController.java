
package com.example.appointmentscheduler;
import helper.DatabaseAppointmentQuery;
import helper.DatabaseUserAuth;
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
import java.util.EventObject;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    @FXML public TextField usernameTF;
    @FXML public TextField passwordTF;
    @FXML public Label header;
    @FXML public Label usernameLabel;
    @FXML public Label passwordLabel;
    @FXML public Label languageLabel;
    @FXML public ComboBox languageBox;
    @FXML public Button loginButton;
    @FXML public Button resetButton;
    public void toMain(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Mainview.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 806);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void login(ActionEvent event) throws SQLException, IOException {
        String username = usernameTF.getText().trim();
        String password = passwordTF.getText().trim();
        System.out.println(username);
        System.out.println(password);

        if (DatabaseUserAuth.login(username,password) == 1){
            System.out.println("login successful");
            toMain(event);

        }
        else {
            System.out.println("nope");
        }

    }

//    public void languageSelect(){
//        if (languageBox.getSelected
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    languageBox.getItems().addAll(
            "English",
            "French"
    );

    }
}