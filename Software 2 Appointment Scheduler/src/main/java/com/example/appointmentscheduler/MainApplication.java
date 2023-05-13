package com.example.appointmentscheduler;

import helper.DatabaseCustomerQuery;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Mainview.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
//        int rowsAffected = DatabaseCustomerQuery.insert("John bon", "134 dog lane","12234","script",1);
//
//        if(rowsAffected > 0) {
//            System.out.println("SUCCESS BIOTCH");
//        }
//        else{
//            System.out.println("sorry bro");
//        }
//
//        int rows = DatabaseCustomerQuery.delete(5);
//        if (rows == 1){
//            System.out.println("dude");
//        }
//        JDBC.closeConnection();


        launch();


    }
}