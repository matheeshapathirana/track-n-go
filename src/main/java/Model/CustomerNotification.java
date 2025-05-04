package Model;

public class CustomerNotification {
    private int notificationId;
    private int recipientId;
    private String message;
    private String createdOn;

    public CustomerNotification() {}

    public CustomerNotification(int notificationId, int recipientId, String message, String createdOn) {
        this.notificationId = notificationId;
        this.recipientId = recipientId;
        this.message = message;
        this.createdOn = createdOn;
    }

    public int getNotificationId() { return notificationId; }
    public void setNotificationId(int notificationId) { this.notificationId = notificationId; }

    public int getRecipientId() { return recipientId; }
    public void setRecipientId(int recipientId) { this.recipientId = recipientId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getCreatedOn() { return createdOn; }
    public void setCreatedOn(String createdOn) { this.createdOn = createdOn; }
}
