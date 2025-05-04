package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

    // Constructor to initialize userView with customerId
    public userView(int customerId) {
        // Initialize UI components (if using a GUI builder, this may be auto-generated)
        loadCustomerNotifications(customerId);
        loadAllNotificationsTable();

        btndeletenotification.addActionListener(e -> deleteSelectedNotification(customerId));
        btnclearallnotifications.addActionListener(e -> clearAllNotifications(customerId));
        btnclearfields.addActionListener(e -> {
            clearNotificationsTable();
            JOptionPane.showMessageDialog(null, "All notifications have been cleared from the display.");
        });
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
        notificationsdata.setModel(model);
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

    public static void main(String[] args) {
        JFrame frame = new JFrame("User View");
        int customerId = 1; // Replace with actual customer ID from login/session
        userView view = new userView(customerId);
        frame.setContentPane(view.backpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
