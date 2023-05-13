package helper;

import com.example.appointmentscheduler.Customer;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DatabaseCustomerQuery {
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
    public static ObservableList<Customer> select() throws SQLException {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("address");
            String postalCode = rs.getString("Postal_Code");
            String phoneNumber = rs.getString("Phone");
            int divisionID = rs.getInt("Division_ID");
            Customer c = new Customer(customerID,customerName,address,postalCode,phoneNumber, divisionID);
            customerList.add(c);
        }
        return customerList;
//        CustomerTable.setItems(customerList);
    }

}
