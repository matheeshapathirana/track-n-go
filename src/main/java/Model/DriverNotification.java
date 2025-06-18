package Model;

import java.sql.Timestamp;

public class DriverNotification {
    private int notificationID;
    private int personnelID;
    private String message;
    private Timestamp sentOn;

    public DriverNotification(int notificationID, int personnelID, String message, Timestamp sentOn) {
        this.notificationID = notificationID;
        this.personnelID = personnelID;
        this.message = message;
        this.sentOn = sentOn;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getSentOn() {
        return sentOn;
    }
}
