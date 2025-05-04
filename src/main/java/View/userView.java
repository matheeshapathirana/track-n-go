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
    private JLabel lblwelcomeuser;
    private JLabel lblusernamegoeshere;

    // Constructor to initialize userView with customerId and username
    public userView(int customerId, String username) {
        if (lblusernamegoeshere != null) {
            lblusernamegoeshere.setText(username);
        }
        loadCustomerNotifications(customerId);
        loadAllNotificationsTable(customerId);
    }

    // Add this method to load notifications for a customer
    public void loadCustomerNotifications(int customerId) {
        CustomerNotificationDAO dao = new CustomerNotificationDAO();
        List<CustomerNotification> notifications = dao.getNotificationsByUserID(customerId);
        String[] columnNames = {"Message", "Timestamp"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (CustomerNotification notification : notifications) {
            Object[] row = {notification.getMessage(), notification.getCreatedOn()};
            model.addRow(row);
        }
        notificationsdata.setModel(model);
    }

    // Load all notifications into the notificationsdata table (filtered by user)
    public void loadAllNotificationsTable(int userId) {
        CustomerNotificationDAO dao = new CustomerNotificationDAO();
        java.util.List<CustomerNotification> notifications = dao.getNotificationsByUserID(userId);
        String[] columnNames = {"ID", "Recipient ID", "Message", "Timestamp"};
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(columnNames, 0);
        for (CustomerNotification notification : notifications) {
            Object[] row = {
                notification.getNotificationId(),
                notification.getRecipientId(),
                notification.getMessage(),
                notification.getCreatedOn()
            };
            model.addRow(row);
        }
        notificationsdata.setModel(model);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("User View");
        int customerId = 1; // Replace with actual customer ID from login/session
        String username = "username"; // Replace with actual username from login/session
        userView view = new userView(customerId, username);
        frame.setContentPane(view.backpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
