package com.example.appointmentscheduler;

import java.sql.Time;

public class Appointment {
    private int appointmentID;
    private String title;
    private String location;
    private int contact;
    private String type;
    private String startTime;
    private String endTime;
    private int customerID;
    private int userID;

    public Appointment(int appointmentID, String title, String location, int contact, String type, String startTime, String endTime, int customerID, int userID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerID = customerID;
        this.userID = userID;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public int getContact() {
        return contact;
    }

    public String getType() {
        return type;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getUserID() {
        return userID;
    }
}

