package View;

import javax.swing.*;

public class driverView {
    private JTabbedPane tabbedPane1;
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
    private JLabel lbldelays;
    private JButton btnupdatetrack;
    private JTable tracktable;
    private JComboBox comboBox1;
    private JButton btnrefreshtrack;
    private JSpinner spinnerday;
    private JComboBox comboBoxmonth;
    private JComboBox comboBoxyear;
    private JCheckBox urgentCheckBox;
    private JTextField txtdelays;
    private JLabel lblwelcome;
    private JLabel txtdriver;
    private JButton btnclearfields;

    private int driverId = -1;
    private String driverName = "";
    private Controller.DriverShipmentsController driverShipmentsController;

    public driverView(int driverId, String driverName) {
        txtshipmentid.setEditable(false);
        this.driverId = driverId;
        this.driverName = driverName;
        this.driverShipmentsController = new Controller.DriverShipmentsController();
        loadAssignedShipmentsTable(driverId);
        // Add refresh button action
        if (refreshButton != null) {
            refreshButton.addActionListener(e -> loadAssignedShipmentsTable(this.driverId));
        }
        // Load driver notifications on startup
        loadDriverNotifications(driverId);
        // Optionally, set driver name somewhere in the UI
        // Example: setTitle("Driver View - " + driverName);
        // Show the current logged in driver name
        if (txtdriver != null) {
            txtdriver.setText(driverName);
        }
        // Increase the window size for better visibility
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(tabbedPane1);
            if (frame != null) {
                frame.setSize(800, 500); // Set to a larger size
                frame.setLocationRelativeTo(null); // Center the window
            }
        });
        // Set spinnerday min/max to 1 and 31
        if (spinnerday != null) {
            spinnerday.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));
        }
    }

    private void loadAssignedShipmentsTable(int driverId) {
        String[] columnNames = {"Shipment ID", "Receiver Name", "Status", "Created On", "User ID", "Urgent", "Current Location", "Estimated Delivery Time", "Delay"};
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        java.util.List<Model.Shipments> shipments = driverShipmentsController.getShipmentsForDriver(driverId);
        for (Model.Shipments shipment : shipments) {
            Object[] row = {
                shipment.getShipmentID(),
                shipment.getReceiverName(),
                shipment.getShipmentStatus(),
                shipment.getCreatedOn(),
                shipment.getUserid(),
                shipment.getUrgent(),
                shipment.getCurrentLocation(),
                shipment.getEstimatedDeliveryTime(),
                shipment.getDelay()
            };
            model.addRow(row);
        }
        if (tracktable != null) {
            tracktable.setModel(model);
            tracktable.getTableHeader().setVisible(true);
            // Add row selection listener to fill fields
            tracktable.getSelectionModel().addListSelectionListener(e -> {
                int selectedRow = tracktable.getSelectedRow();
                if (selectedRow >= 0) {
                    txtshipmentid.setText(String.valueOf(tracktable.getValueAt(selectedRow, 0)));
                    txtlocation.setText(String.valueOf(tracktable.getValueAt(selectedRow, 6)));
                    // Parse estimated delivery time to set comboBoxyear, comboBoxmonth, spinnerday
                    String estDelivery = String.valueOf(tracktable.getValueAt(selectedRow, 7));
                    if (estDelivery != null && !estDelivery.isEmpty() && !estDelivery.equals("null")) {
                        try {
                            String[] dateParts = estDelivery.split(" ")[0].split("-");
                            if (dateParts.length == 3) {
                                comboBoxyear.setSelectedItem(dateParts[0]);
                                int monthInt = Integer.parseInt(dateParts[1]);
                                String monthName = java.time.Month.of(monthInt).name();
                                comboBoxmonth.setSelectedItem(monthName.substring(0,1) + monthName.substring(1).toLowerCase());
                                spinnerday.setValue(Integer.parseInt(dateParts[2]));
                            }
                        } catch (Exception ex) {
                            // Ignore parse errors
                        }
                    }
                    txtdelays.setText(String.valueOf(tracktable.getValueAt(selectedRow, 8)));
                    urgentCheckBox.setSelected("1".equals(String.valueOf(tracktable.getValueAt(selectedRow, 5))));
                }
            });
        }
    }

    private void loadDriverNotifications(int driverId) {
        Model.DriverNotificationDAO dao = new Model.DriverNotificationDAO();
        java.util.List<Model.DriverNotification> notifications = dao.getNotificationsByDriverId(driverId);
        String[] columnNames = {"Message", "Sent On"};
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Model.DriverNotification notification : notifications) {
            Object[] row = {notification.getMessage(), notification.getSentOn()};
            model.addRow(row);
        }
        if (table1 != null) {
            table1.setModel(model);
        }
    }

    // Optionally, provide a getter for the main panel
    public JTabbedPane getMainPanel() {
        return tabbedPane1;
    }

    public static void main(String[] args) {
        // Prevent direct access: show warning and redirect to login
        JOptionPane.showMessageDialog(null, "Please login as a driver to access this view.", "Access Denied", JOptionPane.WARNING_MESSAGE);
        loginView.main(new String[]{});
    }

    // Add update button logic
    {
        if (btnupdatetrack != null) {
            btnupdatetrack.addActionListener(e -> {
                try {
                    int shipmentId = Integer.parseInt(txtshipmentid.getText());
                    String location = txtlocation.getText();
                    // Build deliveryTime from comboBoxyear, comboBoxmonth, spinnerday
                    int year = Integer.parseInt(comboBoxyear.getSelectedItem().toString());
                    String monthName = comboBoxmonth.getSelectedItem().toString();
                    int month = java.time.Month.valueOf(monthName.toUpperCase()).getValue();
                    int day = (int) spinnerday.getValue();
                    String deliveryTime = String.format("%04d-%02d-%02d 00:00:00", year, month, day);
                    String delay = txtdelays.getText();
                    int urgent = urgentCheckBox.isSelected() ? 1 : 0;
                    Controller.ShipmentsController controller = new Controller.ShipmentsController();
                    controller.updateShipmentFields(shipmentId, location, deliveryTime, delay, urgent);
                    loadAssignedShipmentsTable(driverId);
                    JOptionPane.showMessageDialog(null, "Shipment updated successfully.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error updating shipment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
        // Add refresh button action for notifications
        if (refreshButton != null) {
            refreshButton.addActionListener(e -> loadDriverNotifications(this.driverId));
        }
        // Add clear all button action for notifications
        if (clearAllButton != null) {
            clearAllButton.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear all notifications?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    Model.DriverNotificationDAO dao = new Model.DriverNotificationDAO();
                    dao.clearAllNotificationsForDriver(this.driverId);
                    loadDriverNotifications(this.driverId);
                    JOptionPane.showMessageDialog(null, "All notifications cleared successfully.");
                }
            });
        }
        // Add delete button action for notifications
        if (deleteButton != null) {
            deleteButton.addActionListener(e -> {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a notification to delete.");
                    return;
                }
                String message = String.valueOf(table1.getValueAt(selectedRow, 0));
                String sentOn = String.valueOf(table1.getValueAt(selectedRow, 1));
                Model.DriverNotificationDAO dao = new Model.DriverNotificationDAO();
                int notificationId = dao.getNotificationIdByMessageAndTimestamp(this.driverId, message, sentOn);
                if (notificationId != -1) {
                    dao.deleteNotification(notificationId);
                    loadDriverNotifications(this.driverId);
                    JOptionPane.showMessageDialog(null, "Notification deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to find the notification to delete.");
                }
            });
        }
        // Add refresh button for tracktable
        if (btnrefreshtrack != null) {
            btnrefreshtrack.addActionListener(e -> loadAssignedShipmentsTable(this.driverId));
        }
        // Add clear fields button action for tracktable fields
        if (btnclearfields != null) {
            btnclearfields.addActionListener(e -> {
                txtshipmentid.setText("");
                txtlocation.setText("");
                if (comboBoxyear.getItemCount() > 0) comboBoxyear.setSelectedIndex(0);
                if (comboBoxmonth.getItemCount() > 0) comboBoxmonth.setSelectedIndex(0);
                spinnerday.setValue(1);
                txtdelays.setText("");
                urgentCheckBox.setSelected(false);
                // Optionally, clear table selection
                if (tracktable != null) tracktable.clearSelection();
            });
        }
    }
}
