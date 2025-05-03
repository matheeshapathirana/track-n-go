package Model;

public class CustomerNotification {
    private int notificationId;
    private int customerId;
    private String message;
    private String timestamp;

    public CustomerNotification() {}

    public CustomerNotification(int notificationId, int customerId, String message, String timestamp) {
        this.notificationId = notificationId;
        this.customerId = customerId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getNotificationId() { return notificationId; }
    public void setNotificationId(int notificationId) { this.notificationId = notificationId; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
