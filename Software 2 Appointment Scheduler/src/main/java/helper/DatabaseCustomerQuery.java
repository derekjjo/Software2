package helper;

import com.example.appointmentscheduler.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//
//        String n = null;
//                String a = null;
//                int post = 0;
//                String phone = null;
//                String country = null;
//                String div = null;

public abstract class DatabaseCustomerQuery {
    public static int insert(String customerName, String address,String postalCode,String phone, int divisionID, String createdBY) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS (CUSTOMER_NAME,ADDRESS,POSTAL_CODE,PHONE,DIVISION_ID,CREATE_DATE,LAST_UPDATE,Created_BY,Last_Updated_By) VALUES (?,?,?,?,?,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),?,CURRENT_TIMESTAMP())";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,customerName);
        ps.setString(2,address);
        ps.setString(3,postalCode);
        ps.setString(4,phone);
        ps.setInt(5,divisionID);
        ps.setString(6, createdBY);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    public static int update(int customerID , String customerName, String address, String postalCode, String updatedBY, int divisionID, String phone) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET CUSTOMER_NAME = ?,ADDRESS = ?,POSTAL_CODE = ? ,LAST_UPDATED_BY = ? ,DIVISION_ID = ?, Phone = ?, LAST_UPDATE = CURRENT_TIMESTAMP() WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,customerName);
        ps.setString(2,address);
        ps.setString(3,postalCode);
        ps.setString(4,updatedBY);
        ps.setInt(5,divisionID);
        ps.setInt(7,customerID);
        ps.setString(6,phone);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    public static int delete(int customerID) throws SQLException {
        String sql1 = "DELETE FROM APPOINTMENTS WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql1);
        ps.setInt(1,customerID);
        ps.executeUpdate();

        String sql2 = "DELETE FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
        PreparedStatement ps2 = JDBC.connection.prepareStatement(sql2);
        ps2.setInt(1,customerID);
        int rowsAffected = ps2.executeUpdate();
        return rowsAffected;
    }
    public static ArrayList<String> getCountries() throws SQLException {
        ArrayList<String> countries = new ArrayList<>();
        String sql = "SELECT country FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String country = rs.getString("Country");
            countries.add(country);
        }
        return countries;
    }
    public static ArrayList<String> getDivisions(String country) throws SQLException {
        int countryID = DatabaseCustomerQuery.getIDFromCountryName(country);
        ArrayList<String> divisions = new ArrayList<>();
        String sql = "SELECT Division FROM first_level_divisions WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,countryID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String div = rs.getString("Division");
            divisions.add(div);
        }
        return divisions;
    }
    public static String getCountryFromDivID(int divisionID) throws SQLException {
        int countryID = 0;
        String sql = "Select Country_ID FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);;
        ps.setInt(1,divisionID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            countryID = rs.getInt("COUNTRY_ID");
        }
        String country =getNameFromCountryID(countryID);
        return country;
    }
    public static int getIDFromCountryName(String name) throws SQLException {
        int countryID;
        String sql = "Select Country_ID FROM COUNTRIES WHERE COUNTRY = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);;
        ps.setString(1,name);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            countryID = rs.getInt("COUNTRY_ID");
            return countryID;
        }
        return 0;
    }
    public static String getNameFromCountryID(int countryID) throws SQLException {
        String country = null;
        String sql = "Select Country FROM COUNTRIES WHERE COUNTRY_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);;
        ps.setInt(1,countryID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            country = rs.getString("COUNTRY");
            return country;
        }
        return null;
    }

    public static int getDivisionIDFromName(String name) throws SQLException {
        int divID;
        String sql = "Select Division_ID FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);;
        ps.setString(1,name);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            divID = rs.getInt("Division_ID");
            return divID;
        }
        return 0;
    }
    public static String getDivNameFromID(int divisionID) throws SQLException {
        String division = null;
        String sql = "Select DIVISION FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);;
        ps.setInt(1,divisionID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            division = rs.getString("Division");
            return division;
        }
        return null;
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
            String createdBY = rs.getString("Created_BY");
            Customer c = new Customer(customerID,customerName,address,postalCode,phoneNumber, divisionID,createdBY);
            customerList.add(c);
        }
        return customerList;

    }


}
