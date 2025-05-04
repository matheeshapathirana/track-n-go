package Model;

import java.sql.Timestamp;

public class DriverNotification {
    private int notificationID;
    private int personnelID;
    private String message;
    private Timestamp sentOn;

    // Constructor for new notifications
    public DriverNotification(int notificationID, int personnelID, String message, Timestamp sentOn) {
        this.notificationID = notificationID;
        this.personnelID = personnelID;
        this.message = message;
        this.sentOn = sentOn;
    }

    // Getters and Setters
    public int getNotificationID() { return notificationID; }
    public void setNotificationID(int notificationID) { this.notificationID = notificationID; }

    public int getPersonnelID() { return personnelID; }
    public void setPersonnelID(int personnelID) { this.personnelID = personnelID; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Timestamp getSentOn() { return sentOn; }
    public void setSentOn(Timestamp sentOn) { this.sentOn = sentOn; }
}
