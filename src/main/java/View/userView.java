package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.List;
import Model.CustomerNotification;
import Model.CustomerNotificationDAO;
import Controller.ScheduleDeliveriesController;
import Model.TrackShipmentProgress;

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
    private JLabel lblwelcome;
    private JLabel lblusernamegoeshere;
    private JLabel lblusername;
    private JTextField customerNameField, packageDetailsField, slotField;
    private JButton scheduleButton, updateStatusButton;
    private JTable deliveryTable;
    private DefaultTableModel tableModel;

    private JComboBox availableDriversDropdown;
    private JTable availableDriversTable;
    private JButton refreshButton;
    private JButton refreshButton1;
    private JCheckBox urgentCheckBox;
    private JButton refreshButton2;

    private int customerId = -1;

    private String username = "";

    private ScheduleDeliveriesController scheduleDeliveriesController;

    public userView(int customerId, String username) {
        this.customerId = customerId;
        this.username = username;
        scheduleDeliveriesController = new ScheduleDeliveriesController();
        if (lblusername != null) {
            lblusername.setText(username);
        }
        if (lblusernamegoeshere != null) {
            lblusernamegoeshere.setText(username);
        }
        loadCustomerNotifications(customerId);


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

        if (btndeletenotification != null) {
            btndeletenotification.addActionListener(e -> deleteSelectedNotification(customerId));
        } else {
            System.err.println("btndeletenotification is null. Check your form bindings.");
        }
        if (btnclearallnotifications != null) {
            btnclearallnotifications.addActionListener(e -> clearAllNotifications(customerId));
        } else {
            System.err.println("btnclearallnotifications is null. Check your form bindings.");
        }
        if (btnclearfields != null) {
            btnclearfields.addActionListener(e -> {
                if (txtreceivername != null) txtreceivername.setText("");
                if (txtaddress != null) txtaddress.setText("");
                if (comboBoxyear != null) comboBoxyear.setSelectedIndex(0);
                if (comboBoxmonth != null) comboBoxmonth.setSelectedIndex(0);
                if (spinnerday != null) spinnerday.setValue(1);
                if (comboBoxtimeslot != null) comboBoxtimeslot.setSelectedIndex(0);
                if (availableDriversDropdown != null) availableDriversDropdown.setSelectedIndex(0);
                if (urgentCheckBox != null) urgentCheckBox.setSelected(false);
                JOptionPane.showMessageDialog(null, "All fields have been cleared.");
            });
        } else {
            System.err.println("btnclearfields is null. Check your form bindings.");
        }
       

        loadAvailableDrivers();

        if (refreshButton != null) {
            refreshButton.addActionListener(e -> {
                loadAvailableDrivers();
            });
        } else {
            System.err.println("refreshButton is null. Check your form bindings for the available drivers refresh button.");
        }
        if (refreshButton1 != null) {
            refreshButton1.addActionListener(e -> {
                loadTrackShipmentProgressTable(customerId);
            });
        }
        if (refreshButton2 != null) {
            refreshButton2.addActionListener(e -> {
                loadCustomerNotifications(customerId);
            });
        }

        btnaddshipment.addActionListener(e -> {
            try {
                String receiverName = txtreceivername.getText();
                String status = "Pending";
                Integer driverID = null;
                int urgent = (urgentCheckBox != null && urgentCheckBox.isSelected()) ? 1 : 0;
                String currentLocation = (txtaddress != null) ? txtaddress.getText() : null;
                if (availableDriversDropdown != null && availableDriversDropdown.getSelectedItem() != null) {
                    String selected = availableDriversDropdown.getSelectedItem().toString();
                    Model.DeliveryPersonnelDAO dao = new Model.DeliveryPersonnelDAO();
                    java.util.List<Model.DeliveryPersonnel> all = dao.getAllPersonnel();
                    for (Model.DeliveryPersonnel p : all) {
                        if (selected.equals(p.getPersonnelName())) {
                            driverID = p.getPersonnelID();
                            break;
                        }
                    }
                }
                int year = Integer.parseInt(comboBoxyear.getSelectedItem().toString());
                String monthName = comboBoxmonth.getSelectedItem().toString();
                int month = java.time.Month.valueOf(monthName.toUpperCase()).getValue(); // 1-based
                int day = Integer.parseInt(spinnerday.getValue().toString());
                String timeSlot = (comboBoxtimeslot != null && comboBoxtimeslot.getSelectedItem() != null) ? comboBoxtimeslot.getSelectedItem().toString() : "12:00:00";
                String timeForDb;
                switch (timeSlot) {
                    case "8AM - 10AM":
                        timeForDb = "08:00:00";
                        break;
                    case "10AM - 12PM":
                        timeForDb = "10:00:00";
                        break;
                    case "12PM - 2PM":
                        timeForDb = "12:00:00";
                        break;
                    case "2PM - 4PM":
                        timeForDb = "14:00:00";
                        break;
                    default:
                        timeForDb = "12:00:00";
                }
                String estimatedDeliveryTime = String.format("%04d-%02d-%02d %s", year, month, day, timeForDb);
                System.out.println("Estimated Delivery Time: " + estimatedDeliveryTime);
                if (receiverName.isEmpty() || driverID == null) {
                    JOptionPane.showMessageDialog(null, "Please fill all required fields and select a driver!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Controller.ShipmentsController controller = new Controller.ShipmentsController();
                controller.addShipment(receiverName, status, driverID, customerId, estimatedDeliveryTime, urgent, currentLocation);
                if (driverID != null) {
                    Model.DriverNotificationDAO driverNotificationDAO = new Model.DriverNotificationDAO();
                    String message = "A new shipment has been assigned to you. Please check your dashboard.";
                    driverNotificationDAO.addNotification(driverID, message);
                }
                JOptionPane.showMessageDialog(null, "Shipment added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                txtreceivername.setText("");
                loadAvailableDrivers();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding shipment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loadTrackShipmentProgressTable(customerId);
        if (backpanel != null && trackshipmenttab != null) {
            backpanel.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    if (backpanel.getSelectedComponent() == trackshipmenttab) {
                        loadTrackShipmentProgressTable(customerId);
                    }
                }
            });
        }

        if (spinnerday != null) {
            spinnerday.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));
        }
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getUsername() {
        return username;
    }

    public userView() {
        scheduleDeliveriesController = new ScheduleDeliveriesController();
    }

    public void loadCustomerNotifications(int customerId) {
        CustomerNotificationDAO dao = new CustomerNotificationDAO();
        List<CustomerNotification> notifications = dao.getNotificationsByUserID(customerId);
        System.out.println("Loaded notifications count: " + notifications.size());

        String[] columnNames = {"Message", "Timestamp"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (CustomerNotification notification : notifications) {
            Object[] row = {notification.getMessage(), notification.getCreatedOn()};
            model.addRow(row);
        }
        if (notificationsdata != null) {
            notificationsdata.setModel(model);
        } else {
            System.err.println("notificationsdata JTable is null. Ensure it is initialized and matches the field name in the .form file.");
        }
    }

    public void loadAllNotificationsTable() {
        CustomerNotificationDAO dao = new CustomerNotificationDAO();
        List<CustomerNotification> notifications = dao.getAllNotifications();

        String[] columnNames = {"Message", "Created On"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
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

        String selectedMessage = (String) notificationsdata.getValueAt(selectedRow, 0);
        String selectedTimestamp = (String) notificationsdata.getValueAt(selectedRow, 1);

        System.out.println("Selected Message: " + selectedMessage);
        System.out.println("Selected Timestamp: " + selectedTimestamp);

        CustomerNotificationDAO dao = new CustomerNotificationDAO();
        int notificationId = dao.getNotificationIdByMessageAndTimestamp(selectedMessage, selectedTimestamp, customerId);

        if (notificationId != -1) {
            dao.deleteNotification(notificationId);

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
            CustomerNotificationDAO dao = new CustomerNotificationDAO();
            dao.clearAllNotificationsForUser(customerId);

            DefaultTableModel model = (DefaultTableModel) notificationsdata.getModel();
            model.setRowCount(0);

            JOptionPane.showMessageDialog(null, "All notifications cleared successfully.");
        }
    }

    public void clearNotificationsTable() {
        DefaultTableModel model = (DefaultTableModel) notificationsdata.getModel();
        if (model != null) {
            model.setRowCount(0);
        }
    }

    private void loadAvailableDrivers() {
        Model.DeliveryPersonnelDAO dao = new Model.DeliveryPersonnelDAO();
        java.util.List<Model.DeliveryPersonnel> all = dao.getAllPersonnel();
        java.util.List<Model.DeliveryPersonnel> available = new java.util.ArrayList<>();
        for (Model.DeliveryPersonnel p : all) {
            if ("Available".equalsIgnoreCase(p.getAvailability())) {
                available.add(p);
            }
        }
        System.out.println("Available drivers found: " + available.size());
        if (availableDriversDropdown != null) {
            availableDriversDropdown.removeAllItems();
            for (Model.DeliveryPersonnel p : available) {
                availableDriversDropdown.addItem(p.getPersonnelName());
            }
        } else {
            System.out.println("availableDriversDropdown is null");
        }
        if (availableDriversTable != null) {
            String[] columns = {"ID", "Name", "Contact", "Schedule", "Route", "Availability"};
            String[][] data = new String[available.size()][columns.length];
            for (int i = 0; i < available.size(); i++) {
                Model.DeliveryPersonnel p = available.get(i);
                data[i][0] = String.valueOf(p.getPersonnelID());
                data[i][1] = p.getPersonnelName();
                data[i][2] = p.getPersonnelContact();
                data[i][3] = p.getSchedule();
                data[i][4] = p.getAssignedRoute();
                data[i][5] = p.getAvailability();
            }
            availableDriversTable.setModel(new javax.swing.table.DefaultTableModel(data, columns));
        } else {
            System.out.println("availableDriversTable is null");
        }
    }

    public void loadTrackShipmentProgressTable(int userId) {
        String[] columnNames = {"Tracking ID", "Shipment ID", "Current Location", "Estimated Delivery Time", "Delay", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        List<TrackShipmentProgress> progressList = scheduleDeliveriesController.getTrackShipmentProgressForUser(userId);
        for (TrackShipmentProgress progress : progressList) {
            Object[] row = {
                progress.getTrackingID(),
                progress.getShipmentID(),
                progress.getCurrentLocation(),
                progress.getEstimatedDeliveryTime(),
                progress.getDelay(),
                progress.getStatus()
            };
            model.addRow(row);
        }
        if (trackshipmentsdata != null) {
            trackshipmentsdata.setModel(model);
        } else {
            System.err.println("trackshipmentsdata JTable is null. Ensure it is initialized and matches the field name in the .form file.");
        }
    }

    public JTabbedPane getMainPanel() {
        return backpanel;
    }

    public static void main(String[] args) {
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
