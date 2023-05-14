package helper;

import com.example.appointmentscheduler.Appointment;
import com.example.appointmentscheduler.Customer;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DatabaseAppointmentQuery {
    public static int insert(String customerName, String address,String postalCode,String createdBy, int divisionID) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS (CUSTOMER_NAME,ADDRESS,POSTAL_CODE,CREATED_BY,DIVISION_ID) VALUES (?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,customerName);
        ps.setString(2,address);
        ps.setString(3,postalCode);
        ps.setString(4,createdBy);
        ps.setInt(5,divisionID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    public static int update(int customerID ,String customerName, String address,String postalCode,String createdBy, int divisionID) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET CUSTOMER_NAME= ?,ADDRESS = ?,POSTAL_CODE = ? ,CREATED_BY = ? ,DIVISION_ID = ? WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,customerName);
        ps.setString(2,address);
        ps.setString(3,postalCode);
        ps.setString(4,createdBy);
        ps.setInt(5,divisionID);
        ps.setInt(6,customerID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    public static int delete(int customerID) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,customerID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    //(int customerId, String customerName, String address, String postalCode, String phoneNumber, int divisionID)
    public static ObservableList<Appointment> select() throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String address = rs.getString("location");
            int contact = rs.getInt("Contact_ID");
            String type = rs.getString("Type");
            String startTime = rs.getString("Start");
            String end = rs.getString("End");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");

            Appointment a = new Appointment(appointmentID,title,address,contact,type, startTime,end,customerID,userID);
            appointmentList.add(a);
        }
        return appointmentList;

    }

}
