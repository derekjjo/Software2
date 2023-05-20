package com.example.appointmentscheduler;

public class Customer {
        private int customerId;
        private String customerName;
        private String address;
        private String postalCode;
        private String phoneNumber;
        private int divisionID;
        private String createdBY;

    public Customer(int customerId, String customerName, String address, String postalCode, String phoneNumber, int divisionID, String createdBY) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
        this.createdBY = createdBY;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getDivisionID() {
        return divisionID;
    }
}
