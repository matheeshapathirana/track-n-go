import Model.DriverNotification;
import Model.DriverNotificationsDAO;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DatabasePolling {

    private static final int POLLING_INTERVAL = 5000; // Polling interval in milliseconds
    private static final DriverNotificationsDAO notificationsDAO = new DriverNotificationsDAO();

    public static void main(String[] args) {
        // Create a scheduler to run the pollDatabase method repeatedly
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Schedule the polling every 5 seconds
        scheduler.scheduleAtFixedRate(DatabasePolling::pollDatabase, 0, POLLING_INTERVAL, TimeUnit.MILLISECONDS);
    }

    private static void pollDatabase() {
        // Replace with the personnelID of a specific driver
        int sampleDriverID = 1;

        try {
            // Fetch notifications for a particular driver
            List<DriverNotification> notifications = notificationsDAO.getNotifications(sampleDriverID);

            for (DriverNotification notification : notifications) {
                // Process and display each notification
                handleNotification(notification);

                // Optionally, delete the processed notification
                notificationsDAO.deleteNotification(notification.getNotificationID());
            }

        } catch (Exception e) {
            System.err.println("Error while polling: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void handleNotification(DriverNotification notification) {
        // Print the details of the notification, or wire this logic to a broader notification mechanism
        System.out.println("Notification ID: " + notification.getNotificationID());
        System.out.println("Driver ID: " + notification.getPersonnelID());
        System.out.println("Message: " + notification.getMessage());
        System.out.println("Sent On: " + notification.getSentOn());
    }
}
