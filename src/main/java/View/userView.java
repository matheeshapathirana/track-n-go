package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.List;
import Model.CustomerNotification;
import Model.CustomerNotificationDAO;

public class userView {
    private JTabbedPane backpanel;
    private JLabel lblreceivername;
    private JLabel lbldate;
    private JTextField txtreceivername;
    private JButton btnaddshipment;
    private JButton btnclearfields;
    private JTextPane txtaddress;
    private JComboBox comboBoxyear;
    private JComboBox comboBoxmonth;
    private JSpinner spinnerday;
    private JComboBox comboBoxtimeslot;
    private JTable notificationsdata;
    private JButton btndeletenotification;
    private JButton btnclearallnotifications;
    private JTable trackshipmentsdata;
    private JPanel newshipmenttab;
    private JPanel trackshipmenttab;
    private JPanel notificationstab;
    private JLabel lblcreatenewshipment;
    private JLabel lbladdress;
    private JLabel lbltimeslot;
    private JTable table1;
    private JComboBox comboBox1;
    private JLabel lblwelcome;
    private JLabel lblusernamegoeshere;
    private JLabel lblusername;

    // Store the logged-in customerId as a field
    private int customerId = -1;

    private String username = "";

    // Updated constructor to accept username
    public userView(int customerId, String username) {
        this.customerId = customerId;
        this.username = username;
        if (lblusername != null) {
            lblusername.setText(username);
        }
        if (lblusernamegoeshere != null) {
            lblusernamegoeshere.setText(username);
        }
        loadCustomerNotifications(customerId); // Only load notifications for this user

        // Add ChangeListener to refresh notifications when Notifications tab is selected
        if (backpanel != null && notificationstab != null) {
            backpanel.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    int selectedIndex = backpanel.getSelectedIndex();
                    if (selectedIndex != -1 && backpanel.getComponentAt(selectedIndex) == notificationstab) {
                        loadCustomerNotifications(customerId);
                    }
                }
            });
        }

        btndeletenotification.addActionListener(e -> deleteSelectedNotification(customerId));
        btnclearallnotifications.addActionListener(e -> clearAllNotifications(customerId));
        btnclearfields.addActionListener(e -> {
            clearNotificationsTable();
            JOptionPane.showMessageDialog(null, "All notifications have been cleared from the display.");
        });
    }

    // Optionally, provide a method to get the logged-in user ID
    public int getCustomerId() {
        return customerId;
    }

    // Optionally, provide a method to get the logged-in username
    public String getUsername() {
        return username;
    }

    // Default constructor for compatibility (optional)
    public userView() {
        // You may want to set a default or prompt for customerId here
        // For now, do nothing
    }

    // Add this method to load notifications for a customer
    public void loadCustomerNotifications(int customerId) {
        CustomerNotificationDAO dao = new CustomerNotificationDAO();
        List<CustomerNotification> notifications = dao.getNotificationsByUserID(customerId);

        String[] columnNames = {"Message", "Timestamp"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        for (CustomerNotification notification : notifications) {
            Object[] row = {notification.getMessage(), notification.getCreatedOn()};
            model.addRow(row);
        }
        if (notificationsdata != null) {
            notificationsdata.setModel(model);
        } else {
            // This means the notificationsdata JTable is not initialized. Check your GUI designer or initialization code.
            System.err.println("notificationsdata JTable is null. Ensure it is initialized and matches the field name in the .form file.");
        }
    }

    // Load all notifications into the notificationsdata table (admin/global view)
    public void loadAllNotificationsTable() {
        CustomerNotificationDAO dao = new CustomerNotificationDAO();
        List<CustomerNotification> notifications = dao.getAllNotifications();

        String[] columnNames = {"Message", "Created On"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        for (CustomerNotification notification : notifications) {
            Object[] row = {notification.getMessage(), notification.getCreatedOn()};
            model.addRow(row);
        }

        notificationsdata.setModel(model);
    }

    public void deleteSelectedNotification(int customerId) {
        int selectedRow = notificationsdata.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a notification to delete.");
            return;
        }

        // Retrieve the message and timestamp from the selected row
        String selectedMessage = (String) notificationsdata.getValueAt(selectedRow, 0);
        String selectedTimestamp = (String) notificationsdata.getValueAt(selectedRow, 1);

        // Debug: Log selected data
        System.out.println("Selected Message: " + selectedMessage);
        System.out.println("Selected Timestamp: " + selectedTimestamp);

        // Retrieve the notification ID using the DAO
        CustomerNotificationDAO dao = new CustomerNotificationDAO();
        int notificationId = dao.getNotificationIdByMessageAndTimestamp(selectedMessage, selectedTimestamp, customerId);

        if (notificationId != -1) {
            // Delete the notification from the database
            dao.deleteNotification(notificationId);

            // Remove the row from the JTable
            DefaultTableModel model = (DefaultTableModel) notificationsdata.getModel();
            model.removeRow(selectedRow);

            JOptionPane.showMessageDialog(null, "Notification deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to find the notification to delete.");
        }
    }


    public void clearAllNotifications(int customerId) {
        int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear all notifications?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            // Clear notifications from the database
            CustomerNotificationDAO dao = new CustomerNotificationDAO();
            dao.clearAllNotificationsForUser(customerId);

            // Clear the JTable
            DefaultTableModel model = (DefaultTableModel) notificationsdata.getModel();
            model.setRowCount(0);

            JOptionPane.showMessageDialog(null, "All notifications cleared successfully.");
        }
    }

    public void clearNotificationsTable() {
        DefaultTableModel model = (DefaultTableModel) notificationsdata.getModel();
        if (model != null) {
            model.setRowCount(0); // Clear all rows from the table
        }
    }

    // Public getter for the main panel
    public JTabbedPane getMainPanel() {
        return backpanel;
    }

    public static void main(String[] args) {
        // Check if user is logged in (simulate by checking args or environment)
        // In real app, this should be session-based or passed from loginView
        int customerId = -1;
        String username = null;
        if (args.length >= 2) {
            try {
                customerId = Integer.parseInt(args[0]);
                username = args[1];
            } catch (Exception e) {
                customerId = -1;
                username = null;
            }
        }
        if (customerId == -1 || username == null || username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Not logged in. Please login first.", "Error", JOptionPane.ERROR_MESSAGE);
            // Redirect to loginView
            loginView.main(new String[]{});
            return;
        }
        JFrame frame = new JFrame("User View");
        userView view = new userView(customerId, username);
        frame.setContentPane(view.backpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
