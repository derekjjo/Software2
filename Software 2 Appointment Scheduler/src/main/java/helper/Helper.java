package helper;

import javafx.scene.control.Alert;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public abstract class Helper {
    public static String currentUTCDateTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZoneId utc = ZoneId.of("Etc/Greenwich");
        ZonedDateTime zonedDateTime = ZonedDateTime.now(utc);
        return dtf.format(zonedDateTime);
    }
    public static String convertLocalToUTC(String time){
        ZoneId userZone = ZoneId.systemDefault();
        DateTimeFormatter utcDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC"));
        DateTimeFormatter localDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(userZone);
        LocalDateTime timeObject = LocalDateTime.parse(time,localDTF);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(timeObject,userZone);
        String toInstant = utcDTF.format(zonedDateTime.toInstant());
        return toInstant;
    }
    public static String convertUTCtoLocal(String time){
        ZoneId userZone = ZoneId.systemDefault();
        DateTimeFormatter localDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(userZone);
        DateTimeFormatter utcDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC"));
        ZonedDateTime utcDNT = ZonedDateTime.parse(time,utcDTF);
        Instant instant = utcDNT.toInstant();
        LocalDateTime timeObject = LocalDateTime.ofInstant(instant,userZone);
        String toLocal = localDTF.format(timeObject);
        return toLocal;
    }
    public static boolean isInBusinessHours(String time){
        ZoneId userZone = ZoneId.systemDefault();
        DateTimeFormatter utcDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC"));
        DateTimeFormatter localDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(userZone);
        DateTimeFormatter etcDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("US/Eastern"));
        LocalDateTime timeObject = LocalDateTime.parse(time,localDTF);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(timeObject,userZone);
        String toInstant = utcDTF.format(zonedDateTime.toInstant());
        ZonedDateTime utcDNT = ZonedDateTime.parse(toInstant,utcDTF);
        Instant instant = utcDNT.toInstant();
        LocalDateTime estTimeObject = LocalDateTime.ofInstant(instant,ZoneId.of("US/Eastern"));
        String toEST = etcDTF.format(estTimeObject);
        String hourOnly = toEST.substring(11,13);
        String minOnly = toEST.substring(14,16);
        String totalTime = hourOnly+minOnly;
        int totalTimeInt = Integer.parseInt(totalTime);
        if (totalTimeInt >= 800 && totalTimeInt <= 2200) {
            return true;
        }
        return false;
    }


    public static Alert alertUser(String alertTitle, String alertMessage){
        Alert error = new Alert(Alert.AlertType.WARNING);
        error.setHeaderText(alertTitle);
        error.setTitle("Alert");
        error.setContentText(alertMessage);
        return error;
    }

}
