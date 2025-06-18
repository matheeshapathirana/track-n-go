package Model;

public class CustomerNotification {
    private int notificationId;
    private String recipientType;
    private int recipientId;
    private String message;
    private String createdOn;

    public CustomerNotification(int notificationId, String recipientType, int recipientId, String message, String createdOn) {
        this.notificationId = notificationId;
        this.recipientType = recipientType;
        this.recipientId = recipientId;
        this.message = message;
        this.createdOn = createdOn;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getCreatedOn() { return createdOn; }
    public void setCreatedOn(String createdOn) { this.createdOn = createdOn; }
}
