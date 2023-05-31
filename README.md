# Software2
Software 2 repo

SCHEDULING SOFTWARE: This application is a appointment and customer tracking software for a global company that does business in timezones all over the world. The software is designed the difficulties of scheduling international appointments in mind with input validation relating to timezones and international business hours built in.
The software communicates with a SQL database to Create/read/update/delete appointments and customers.

DEREK JOHNSON | Derekjjo@gmail.com | Version 1.0 | 5/21/2023

IntelliJ IDEA 2023.1 (Community Edition) | Java version "17.0.6" | javafx.version=20

USAGE: use this application can be used to organize Customers (int customerId, String customerName, String address, String postalCode, String phoneNumber, int divisionID, String createdBY) and appointments (int appointmentID, String title, String description, String location, int contact, String type, String startTime, String endTime, int customerID, int userID).
The user can add appointments that are assigned to specific customers. The appointments are checked against the database to make sure the appointments lie within business hours and have no overlapping appointments.
There is also a REPORTS page that runs 3 live reports against the database to inform users about employee scheduling and busiest times of the year.

REPORT:
ADDITIONAL REPORT checks the database for when appointments are created by month so that companies can better understand when people are making appointments and if there is additional need for staffing around those times.


mysql-Connector/Java 8.0.33
