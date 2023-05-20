package com.example.appointmentscheduler;

public class createdReport {

    private int count;
    private int month;

    public createdReport(int count, int month) {
        this.count = count;

        this.month = month;
    }
    public int getMonth() {
        return month;
    }

    public int getCount() {
        return count;
    }
}
