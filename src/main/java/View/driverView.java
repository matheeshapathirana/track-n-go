package View;

import javax.swing.*;

public class driverView {

    private JTabbedPane tabbedPane1;

    // Notifications Tab Components
    private JPanel notificationsPanel;          // JPanel for Notifications tab
    private JList<String> notificationsList;    // JList to display notifications
    private JButton refreshNotificationsButton; // Button to refresh notifications
    private JButton deleteNotificationButton;   // Button to delete selected notification
    private JButton clearAllNotificationsButton; // Button to clear all notifications

    private JTable table1;
    private JButton clearAllButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private JPanel trackbackpanel;
    private JLabel lblshipmentid;
    private JTextField txtshipmentid;
    private JLabel lbllocation;
    private JTextField txtlocation;
    private JLabel lbldeliverytime;
    private JTextField txtdeliverytimes;
    private JLabel lbldelays;
    private JTextField txtdelays;
    private JButton btnupdatetrack;
    private JTable tracktable;
    private JComboBox comboBox1;
    private JTextField txttrackingid;
    private JLabel lbltrackingid;
    private JButton btnrefreshtrack;

    public driverView() {
        // Initialize the notifications tab components
        notificationsPanel = new JPanel();
        notificationsPanel.setLayout(new BoxLayout(notificationsPanel, BoxLayout.Y_AXIS)); // Use vertical layout

        // JList for displaying notifications
        notificationsList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(notificationsList); // Add scrollpane to JList
        notificationsPanel.add(scrollPane);

        // Buttons for managing notifications
        refreshNotificationsButton = new JButton("Refresh Notifications");
        deleteNotificationButton = new JButton("Delete Notification");
        clearAllNotificationsButton = new JButton("Clear All Notifications");

        // Add buttons to the panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshNotificationsButton);
        buttonPanel.add(deleteNotificationButton);
        buttonPanel.add(clearAllNotificationsButton);
        notificationsPanel.add(buttonPanel);

        // Add the Notifications tab to the JTabbedPane
        tabbedPane1.addTab("Notifications", notificationsPanel);
    }

    // Getters for Notifications tab components (optional, if needed)
    public JList<String> getNotificationsList() {
        return notificationsList;
    }

    public JButton getRefreshNotificationsButton() {
        return refreshNotificationsButton;
    }

    public JButton getDeleteNotificationButton() {
        return deleteNotificationButton;
    }

    public JButton getClearAllNotificationsButton() {
        return clearAllNotificationsButton;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane1;
    }

    // Main method to run and test the driverView GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            driverView view = new driverView(); // Create an instance of driverView

            // Create a JFrame to display the UI
            JFrame frame = new JFrame("Driver View");
            frame.setContentPane(view.tabbedPane1); // Add the main tabbedPane1 to the frame
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });

    }
}