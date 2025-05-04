package Controller;

import Model.DriverNotification;
import Model.DriverNotificationsDAO;
import View.driverView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DriverViewController {
    private driverView view;
    private DriverNotificationsDAO notificationsDAO;
    private int loggedInDriverId;

    public DriverViewController(driverView view, int loggedInDriverId) {
        this.view = view;
        this.notificationsDAO = new DriverNotificationsDAO(); // DAO for database access
        this.loggedInDriverId = loggedInDriverId;

        // Initialize button listeners and load initial notifications
        initializeListeners();
        loadNotifications();
    }

    private void initializeListeners() {
        // Refresh button: Reload notifications from the database
        view.getRefreshNotificationsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadNotifications();
            }
        });

        // Clear All button: Clear notifications from the UI
        view.getClearAllNotificationsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAllNotifications();
            }
        });

        // Delete selected notification button: Delete the selected notification
        view.getDeleteNotificationButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedNotification();
            }
        });
    }

    // Fetch notifications from the database and display them in the view
    private void loadNotifications() {
        // Get notifications for the logged-in driver
        List<DriverNotification> notifications = notificationsDAO.getNotifications(loggedInDriverId);

        // Create a DefaultListModel to populate the JList
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (DriverNotification notification : notifications) {
            // Display messages, but could include additional formatting
            listModel.addElement(notification.getNotificationID() + ": " + notification.getMessage());
        }

        // Set the model to the JList in the view
        view.getNotificationsList().setModel(listModel);
    }

    // Clear the notifications list in the UI
    private void clearAllNotifications() {
        // Set the list model to an empty one
        view.getNotificationsList().setModel(new DefaultListModel<>());
    }

    // Delete the selected notification and refresh the list
    private void deleteSelectedNotification() {
        // Get the selected notification string (contains ID and message)
        String selectedNotificationText = view.getNotificationsList().getSelectedValue();
        if (selectedNotificationText != null) {
            // Extract the notificationID from the selected text
            int notificationId = Integer.parseInt(selectedNotificationText.split(":")[0].trim());

            // Confirm with the user before deleting
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to delete this notification?",
                    "Delete Notification",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                // Use DAO to delete the notification by ID
                boolean success = notificationsDAO.deleteNotification(notificationId);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Notification deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error deleting the notification.");
                }

                // Reload notifications in the view
                loadNotifications();
            }
        } else {
            // No notification was selected
            JOptionPane.showMessageDialog(null, "No notification selected!");
        }
    }
}