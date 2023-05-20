package helper;

import com.example.appointmentscheduler.Appointment;
import com.example.appointmentscheduler.Customer;
import com.example.appointmentscheduler.createdReport;
import com.example.appointmentscheduler.typeMonthAppt;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class DatabaseAppointmentQuery {
    public static int insert(String title, String description,String location,String type, String start, String end,int customerID,int userID,int contactID,String updatedBy) throws SQLException {
        String sql = "INSERT INTO APPOINTMENTS (Title,DESCRIPTION,LOCATION,TYPE,START,END,CUSTOMER_ID,USER_ID,CONTACT_ID,LAST_UPDATE,LAST_UPDATED_BY,CREATED_BY,CREATE_DATE) VALUES (?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP(),?,?,CURRENT_TIMESTAMP())";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,title);
        ps.setString(2,description);
        ps.setString(3,location);
        ps.setString(4,type);
        ps.setString(5,start);
        ps.setString(6,end);
        ps.setInt(7,customerID);
        ps.setInt(8,userID);
        ps.setInt(9,contactID);
        ps.setString(10,updatedBy);
        ps.setString(11,updatedBy);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    //"UPDATE CUSTOMERS SET CUSTOMER_NAME = ?,ADDRESS = ?,POSTAL_CODE = ? ,LAST_UPDATED_BY = ? ,DIVISION_ID = ?, Phone = ?, LAST_UPDATE = CURRENT_TIMESTAMP() WHERE CUSTOMER_ID = ?";
    public static int update(String title, String description,String location,String type, String start, String end,int customerID,int userID,int contactID, int appointID,String updatedBy) throws SQLException {
        String sql = "UPDATE APPOINTMENTS SET Title = ?,DESCRIPTION = ?,LOCATION = ? ,TYPE = ? ,START = ?, END = ?,CUSTOMER_ID = ?, USER_ID = ?, CONTACT_ID = ?, Last_Update = CURRENT_TIMESTAMP(), LAST_UPDATED_BY = ? WHERE APPOINTMENT_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,title);
        ps.setString(2,description);
        ps.setString(3,location);
        ps.setString(4,type);
        ps.setString(5,start);
        ps.setString(6,end);
        ps.setInt(7,customerID);
        ps.setInt(8,userID);
        ps.setInt(9,contactID);
        ps.setInt(11,appointID);
        ps.setString(10,updatedBy);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    public static int delete(int appointmentID) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE APPOINTMENT_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,appointmentID);
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
            String description = rs.getString("Description");
            String address = rs.getString("location");
            int contact = rs.getInt("Contact_ID");
            String type = rs.getString("Type");
            String startTime = rs.getString("Start");
            String end = rs.getString("End");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");

            String s = Helper.convertUTCtoLocal(startTime);
            String e = Helper.convertUTCtoLocal(end);

            Appointment a = new Appointment(appointmentID,title,description,address,contact,type, s,e,customerID,userID);
            appointmentList.add(a);
        }
        return appointmentList;
    }
    public static ObservableList<typeMonthAppt> selectTypeMonth(int month) throws SQLException {
        ObservableList<typeMonthAppt> appointmentList = FXCollections.observableArrayList();
        String sql = "SELECT COUNT(Appointment_ID), Type, MONTH(START) as Month FROM APPOINTMENTS WHERE MONTH(START) = ? GROUP BY MONTH, Type";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,month);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int appointmentID = rs.getInt("COUNT(Appointment_ID)");
            String type = rs.getString("Type");
            int customerID = rs.getInt("Month");

            typeMonthAppt a = new typeMonthAppt(appointmentID,type,customerID);
            appointmentList.add(a);
        }
        return appointmentList;
    }
    public static ObservableList<createdReport> selectCreated() throws SQLException {
        ObservableList<createdReport> createdList = FXCollections.observableArrayList();
        String sql = "SELECT COUNT(Appointment_ID) as COUNT, MONTH(CREATE_DATE) as Month FROM APPOINTMENTS GROUP BY MONTH(CREATE_DATE)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int count = rs.getInt("COUNT");
            int month = rs.getInt("Month");
            createdReport a = new createdReport(count, month);
            createdList.add(a);
        }
        return createdList;
    }
    public static ObservableList<Appointment> selectContactID(int contactID) throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS WHERE CONTACT_ID = ? ORDER BY START";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,contactID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String address = rs.getString("location");
            int contact = rs.getInt("Contact_ID");
            String type = rs.getString("Type");
            String startTime = rs.getString("Start");
            String end = rs.getString("End");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");

            String s = Helper.convertUTCtoLocal(startTime);
            String e = Helper.convertUTCtoLocal(end);

            Appointment a = new Appointment(appointmentID,title,description,address,contact,type, s,e,customerID,userID);
            appointmentList.add(a);
        }
        return appointmentList;
    }

    public static ObservableList<Appointment> selectMonth() throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS WHERE MONTH(START) = MONTH(NOW()) AND YEAR(START) = YEAR(NOW())";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String address = rs.getString("location");
            int contact = rs.getInt("Contact_ID");
            String description = rs.getString("Description");
            String type = rs.getString("Type");
            String startTime = rs.getString("Start");
            String end = rs.getString("End");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            String s = Helper.convertUTCtoLocal(startTime);
            String e = Helper.convertUTCtoLocal(end);

            Appointment a = new Appointment(appointmentID,title,description,address,contact,type, s,e,customerID,userID);
            appointmentList.add(a);
        }
        return appointmentList;
    }
    public static ObservableList<Appointment> selectWeek() throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS WHERE WEEK(START) = WEEK(NOW()) AND YEAR(START) = YEAR(NOW())";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String address = rs.getString("location");
            int contact = rs.getInt("Contact_ID");
            String description = rs.getString("Description");
            String type = rs.getString("Type");
            String startTime = rs.getString("Start");
            String end = rs.getString("End");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            String s = Helper.convertUTCtoLocal(startTime);
            String e = Helper.convertUTCtoLocal(end);

            Appointment a = new Appointment(appointmentID,title,description,address,contact,type, s,e,customerID,userID);
            appointmentList.add(a);
        }
        return appointmentList;
    }
    public static Appointment getUserAppointment(String username) throws SQLException {
        Appointment a = null;
        String sql = "SELECT * FROM APPOINTMENTS INNER JOIN USERS ON APPOINTMENTS.USER_ID = USERS.USER_ID WHERE Users.User_Name = ? AND START BETWEEN UTC_TIMESTAMP() AND UTC_TIMESTAMP() + INTERVAL 15 MINUTE";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,username);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String address = rs.getString("location");
            int contact = rs.getInt("Contact_ID");
            String description = rs.getString("Description");
            String type = rs.getString("Type");
            String startTime = rs.getString("Start");
            String end = rs.getString("End");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            String s = Helper.convertUTCtoLocal(startTime);
            String e = Helper.convertUTCtoLocal(end);
            System.out.println(s);
            a = new Appointment(appointmentID,title,description,address,contact,type, s,e,customerID,userID);
        }
        return a;
    }
    public static boolean checkForOverlappingAppointments(int customerID, String utcStartT, String utcEndT) throws SQLException {
        boolean overlapping = false;
        String sql = "SELECT * FROM APPOINTMENTS WHERE Customer_ID = ? AND (START BETWEEN ? AND ? OR END BETWEEN ? AND ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,customerID);
        ps.setString(2,utcStartT);
        ps.setString(3,utcEndT);
        ps.setString(4,utcStartT);
        ps.setString(5,utcEndT);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            System.out.println(overlapping);
            overlapping = true;
            String startt = rs.getString("START");
            String endt = rs.getString("END");
            System.out.println(startt);
            System.out.println(endt);
        }
        String sql2 = "SELECT * FROM APPOINTMENTS WHERE Customer_ID = ? AND (? BETWEEN START AND END OR ? BETWEEN START AND END)";
        PreparedStatement ps2 = JDBC.connection.prepareStatement(sql2);
        ps2.setInt(1,customerID);
        ps2.setString(2,utcStartT);
        ps2.setString(3,utcEndT);
        ResultSet rs2 = ps.executeQuery();
        while(rs2.next()){
            System.out.println(overlapping);
            overlapping = true;
            String startt = rs2.getString("START");
            String endt = rs2.getString("END");
            System.out.println(startt);
            System.out.println(endt);
        }
        System.out.println(overlapping);
        return overlapping;
    }
    public static boolean checkForOverlappingModifyAppointments(int customerID, String utcStartT, String utcEndT, int appointmentID) throws SQLException {
        boolean overlapping = false;
        String sql = "SELECT * FROM APPOINTMENTS WHERE Customer_ID = ? AND appointment_ID != ? AND (START BETWEEN ? AND ? OR END BETWEEN ? AND ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,customerID);
        ps.setString(3,utcStartT);
        ps.setString(4,utcEndT);
        ps.setString(5,utcStartT);
        ps.setString(6,utcEndT);
        ps.setInt(2,appointmentID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            System.out.println(overlapping);
            overlapping = true;
        }
        String sql2 = "SELECT * FROM APPOINTMENTS WHERE Customer_ID = ? AND appointment_ID != ? AND (? BETWEEN START AND END OR ? BETWEEN START AND END)";
        PreparedStatement ps2 = JDBC.connection.prepareStatement(sql2);
        ps2.setInt(1,customerID);
        ps.setInt(2,appointmentID);
        ps2.setString(3,utcStartT);
        ps2.setString(4,utcEndT);
        ResultSet rs2 = ps.executeQuery();
        while(rs2.next()){
            System.out.println(overlapping);
            overlapping = true;
        }
        System.out.println(overlapping);
        return overlapping;
    }

    //working on returning array of contact IDs for modify appointment combobox
    public static ArrayList<String> getContactIDs() throws SQLException {
        ArrayList<String> contactIds = new ArrayList<>();
        String sql = "SELECT Contact_Name FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String contact = rs.getString("Contact_Name");
            contactIds.add(contact);
        }
        return contactIds;
    }
    public static ArrayList<Integer> getContactIDNumbers() throws SQLException {
        ArrayList<Integer> contactIds = new ArrayList<>();
        String sql = "SELECT DISTINCT Contact_ID FROM Appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int contact = rs.getInt("Contact_ID");
            contactIds.add(contact);
        }
        return contactIds;
    }
    public static ArrayList<Integer> getCustomerIDs() throws SQLException {
        ArrayList<Integer> customerIDs = new ArrayList<>();
        String sql = "SELECT DISTINCT Customer_ID FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int customer = rs.getInt("Customer_ID");
            customerIDs.add(customer);
        }
        return customerIDs;
    }
    public static ArrayList<Integer> getUserIDs() throws SQLException {
        ArrayList<Integer> userIds = new ArrayList<>();
        String sql = "SELECT DISTINCT User_ID FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int user = rs.getInt("User_ID");
            userIds.add(user);
        }
        return userIds;
    }
    public static int getContactIDFromName(String name) throws SQLException {
        int contactID;
        String sql = "Select CONTACT_ID FROM CONTACTS WHERE CONTACT_NAME = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);;
        ps.setString(1,name);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            contactID = rs.getInt("CONTACT_ID");
            return contactID;
        }
        return 0;
    }

    public static String getNameFromContactID(int contactID) throws SQLException {
        String contactName;
        String sql = "Select CONTACT_NAME FROM CONTACTS WHERE CONTACT_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);;
        ps.setInt(1,contactID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            contactName = rs.getString("CONTACT_Name");
            return contactName;
        }
        return null;
    }


}
