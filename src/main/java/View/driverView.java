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
    private JCheckBox availableCheckBox;
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
        if (refreshButton != null) {
            refreshButton.addActionListener(e -> loadAssignedShipmentsTable(this.driverId));
        }
        loadDriverNotifications(driverId);
        if (txtdriver != null) {
            txtdriver.setText(driverName);
        }
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(tabbedPane1);
            if (frame != null) {
                frame.setSize(800, 500);
                frame.setLocationRelativeTo(null);
            }
        });
        if (spinnerday != null) {
            spinnerday.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));
        }
        try {
            Model.DeliveryPersonnelDAO dao = new Model.DeliveryPersonnelDAO();
            java.util.List<Model.DeliveryPersonnel> personnelList = dao.getAllPersonnel();
            for (Model.DeliveryPersonnel p : personnelList) {
                if (p.getPersonnelID() == this.driverId) {
                    if (availableCheckBox != null) {
                        availableCheckBox.setSelected("Available".equalsIgnoreCase(p.getAvailability()));
                    }
                    break;
                }
            }
        } catch (Exception ex) {
        }
        if (availableCheckBox != null) {
            availableCheckBox.addActionListener(e -> {
                String newAvailability = availableCheckBox.isSelected() ? "Available" : "Unavailable";
                try {
                    Model.DeliveryPersonnel p = new Model.DeliveryPersonnel();
                    p.setPersonnelID(this.driverId);
                    p.setAvailability(newAvailability);
                    Controller.DeliveryPersonnelController controller = new Controller.DeliveryPersonnelController();
                    Model.DeliveryPersonnelDAO dao = new Model.DeliveryPersonnelDAO();
                    java.util.List<Model.DeliveryPersonnel> personnelList = dao.getAllPersonnel();
                    for (Model.DeliveryPersonnel dp : personnelList) {
                        if (dp.getPersonnelID() == this.driverId) {
                            p.setPersonnelName(dp.getPersonnelName());
                            p.setPersonnelContact(dp.getPersonnelContact());
                            p.setSchedule(dp.getSchedule());
                            p.setAssignedRoute(dp.getAssignedRoute());
                            p.setDeliveryHistory(dp.getDeliveryHistory());
                            break;
                        }
                    }
                    controller.updateDeliveryPersonnel(p);
                    JOptionPane.showMessageDialog(null, "Availability updated to: " + newAvailability);
                } catch (Exception ex2) {
                    JOptionPane.showMessageDialog(null, "Error updating availability: " + ex2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
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
            tracktable.getSelectionModel().addListSelectionListener(e -> {
                int selectedRow = tracktable.getSelectedRow();
                if (selectedRow >= 0) {
                    txtshipmentid.setText(String.valueOf(tracktable.getValueAt(selectedRow, 0)));
                    txtlocation.setText(String.valueOf(tracktable.getValueAt(selectedRow, 6)));
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

    public JTabbedPane getMainPanel() {
        return tabbedPane1;
    }

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Please login as a driver to access this view.", "Access Denied", JOptionPane.WARNING_MESSAGE);
        loginView.main(new String[]{});
    }

    {
        if (btnupdatetrack != null) {
            btnupdatetrack.addActionListener(e -> {
                try {
                    int shipmentId = Integer.parseInt(txtshipmentid.getText());
                    String location = txtlocation.getText();
                    int year = Integer.parseInt(comboBoxyear.getSelectedItem().toString());
                    String monthName = comboBoxmonth.getSelectedItem().toString();
                    int month = java.time.Month.valueOf(monthName.toUpperCase()).getValue();
                    int day = (int) spinnerday.getValue();
                    String deliveryTime = String.format("%04d-%02d-%02d 00:00:00", year, month, day);
                    String delay = txtdelays.getText();
                    int urgent = urgentCheckBox.isSelected() ? 1 : 0;
                    String status = comboBox1.getSelectedItem() != null ? comboBox1.getSelectedItem().toString() : "";
                    Controller.ShipmentsController controller = new Controller.ShipmentsController();
                    controller.updateShipmentStatusAndFields(shipmentId, location, deliveryTime, delay, urgent, status);
                    loadAssignedShipmentsTable(driverId);
                    JOptionPane.showMessageDialog(null, "Shipment updated successfully.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error updating shipment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
        if (refreshButton != null) {
            refreshButton.addActionListener(e -> loadDriverNotifications(this.driverId));
        }
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
        if (btnrefreshtrack != null) {
            btnrefreshtrack.addActionListener(e -> loadAssignedShipmentsTable(this.driverId));
        }
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
