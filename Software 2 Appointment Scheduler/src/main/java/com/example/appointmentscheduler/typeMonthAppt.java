package com.example.appointmentscheduler;

import helper.DatabaseAppointmentQuery;

import java.sql.SQLException;
import java.sql.Time;

public class typeMonthAppt {

    private int customerID;
    private int month;
    private String type;

    public typeMonthAppt(int customerID, String type, int month) {
        this.customerID = customerID;
        this.type = type;
        this.month = month;
    }
    public String getType() {
        return type;
    }

    public int getMonth() {
        return month;
    }

    public int getCustomerID() {
        return customerID;
    }

}







