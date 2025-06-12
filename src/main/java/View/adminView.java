package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Model.TrackShipmentProgressDAO;
import Model.TrackShipmentProgress;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class adminView {
    private JTabbedPane tabbedPane1;
    private JTextField txtshipmentid;
    private JTextField txtlocation;
    private JTextField txtdeliverytimes;
    private JTextField txtdelays;
    private JButton btnupdatetrack;
    private JTable tracktable;
    private JLabel lblshipmentid;
    private JLabel lbllocation;
    private JLabel lbldeliverytime;
    private JLabel lbldelays;
    private JPanel trackbackpanel;
    private JPanel PersonnelManagerPanel;
    private JLabel IDlbl;
    private JLabel Namelbl;
    private JLabel contactlbl;
    private JLabel schedulelbl;
    private JLabel AssignedRoutelbl;
    private JTextField txtName;
    private JTextField txtContact;
    private JTextField txtRoute;
    private JButton addDriverButton;
    private JButton updateDriverButton;
    private JButton deleteDriverButton;
    private JTextField txtID;
    private JTextArea txtAreaHistory;
    private JTable AllDriversView;
    private JLabel alllDriverslbl;
    private JComboBox<String> comboBox1;
    private JButton clearbtn;
    private JTextField txttrackingid;
    private JLabel lbltrackingid;
    private JComboBox<String> comboBox2;
    private JTextField txtassigneddriverid;
    private JLabel lblshipmentsshipmentid;
    private JLabel lblassigneddriverid;
    private JLabel lblsenderid;
    private JLabel lblreceivername;
    private JLabel lblshipmentstatus;
    private JTextField txtshipmentshipmentid;
    private JTextField txtsenderid;
    private JTextField txtreceivername;
    private JButton btnaddshipment;
    private JButton btnupdateshipment;
    private JButton btndeleteshipment;
    private JButton btnclearfields;
    private JTable shipmentdatatable;
    private JLabel availabilitylbl;
    private JComboBox<String> comboboxavailability;
    private JLabel lblemail;
    private JLabel lblusername;
    private JLabel lblpassword;
    private JLabel lblrole;
    private JTextField txtemail;
    private JTextField txtusername;
    private JTextField txtpassword;
    private JComboBox<String> comboboxrole;
    private JButton btnadduser;
    private JButton btnupdateuser;
    private JButton btndeleteuser;
    private JButton btnclearuserfields;
    private JTable userdata;
    private JComboBox<String> comboBoxYear;
    private JComboBox<String> comboBoxMonth;
    private JPanel reportsbackpanel;
    private JLabel lblgeneratemonthlyreports;
    private JLabel lblyear;
    private JLabel lblmonth;
    private JLabel lbltotaldeliveries;
    private JLabel lbldelayeddeliveries;
    private JLabel lbltotalshipments;
    private JButton btnrefreshshipments;
    private JButton btnrefreshusers;
    private JButton btnrefreshdrivers;
    private JButton btnrefreshtrack;
    private Integer loggedInUserId;
    private JComboBox<String> comboUserDrivers;
    private java.util.Map<String, Integer> driverMap = new java.util.HashMap<>();

    //pt2:load drivers to combo box (personnel part)
    private void loadDriverUsers() {
        if (comboUserDrivers == null) {
            System.err.println("comboUserDrivers is null. Check your form bindings or initialization.");
            return;
        }
        try (Connection conn = Utility.DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT userID, username FROM Users WHERE role = 'driver'");
             ResultSet rs = stmt.executeQuery()) {
            comboUserDrivers.removeAllItems();
            driverMap.clear();
            while (rs.next()) {
                int id = rs.getInt("userID");
                String name = rs.getString("username");
                comboUserDrivers.addItem(name);
                driverMap.put(name, id);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading drivers: " + e.getMessage());
        }
        // Update txtID field when a driver is selected
        comboUserDrivers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDriver = (String) comboUserDrivers.getSelectedItem();
                if (selectedDriver != null && driverMap.containsKey(selectedDriver)) {
                    txtID.setText(String.valueOf(driverMap.get(selectedDriver)));
                } else {
                    txtID.setText("");
                }
            }
        });
    }

    public adminView(String loggedInUserEmail) {
        // Remove manual initialization of GUI components!
        // The GUI designer (adminView.form) already initializes these fields.
        // If you get NullPointerException, ensure the form is loaded properly.

        //call the method to fill the combobox when selected
        loadDriverUsers();

        // Get the logged-in user's ID from the database
        Model.UsersDAO usersDAO = new Model.UsersDAO();
        this.loggedInUserId = usersDAO.getUserIdByEmail(loggedInUserEmail);

        txtID.setEditable(false); // Make ID field non-editable
        txtshipmentid.setEditable(false);
        txttrackingid.setEditable(false); // trackingID should not be editable
        txtemail.setEditable(false);


        // Initialize availability combo box options
        comboboxavailability.addItem("Available");
        comboboxavailability.addItem("Unavailable");

        loadTrackTable();
        loadPersonnelTable();
        loadUserTable();
        setNextPersonnelID(); // Set next available ID on startup
        setNextShipmentID(); // Set next available shipment ID on startup
        setNextTrackingID(); // Set next available tracking ID on startup
        clearPersonnelFields();
        clearUserFields();
        //action to clear all fields
        clearbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPersonnelFields();
            }
        });

        // Refresh tracking table
        btnrefreshtrack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTrackTable();
            }
        });
        btnrefreshusers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadUserTable();
            }
        });

        btnupdatetrack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txttrackingid.getText().isEmpty() || txtshipmentid.getText().isEmpty() || txtlocation.getText().isEmpty() || txtdelays.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String trackingID = txttrackingid.getText();
                    String shipmentID = txtshipmentid.getText();
                    String location = txtlocation.getText();
                    String delay = txtdelays.getText();
                    String status = comboBox1.getSelectedItem() != null ? comboBox1.getSelectedItem().toString() : "";
                    // Build delivery date string from spinnerday, comboBoxmonth, comboBoxyear
                    int day = (int) spinnerday.getValue();
                    String month = comboBoxmonth.getSelectedItem() != null ? comboBoxmonth.getSelectedItem().toString() : "";
                    String year = comboBoxyear.getSelectedItem() != null ? comboBoxyear.getSelectedItem().toString() : "";
                    String deliveryTime = "";
                    if (!month.isEmpty() && !year.isEmpty()) {
                        // Convert month name to month number (1-12)
                        int monthNum = java.time.Month.valueOf(month.toUpperCase()).getValue();
                        deliveryTime = String.format("%s-%02d-%02d 00:00:00", year, monthNum, day);
                    }
                    Controller.TrackShipmentProgressController controller = new Controller.TrackShipmentProgressController();
                    controller.updateShipmentProgress(trackingID, shipmentID, location, deliveryTime, delay, status);
                    loadTrackTable(); // Refresh table after update
                }
            }
        });

        // Populate text fields when a row is selected
        tracktable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = tracktable.getSelectedRow();
            if (selectedRow >= 0) {
                Object val0 = tracktable.getValueAt(selectedRow, 0);
                Object val1 = tracktable.getValueAt(selectedRow, 1);
                Object val2 = tracktable.getValueAt(selectedRow, 2);
                Object val3 = tracktable.getValueAt(selectedRow, 3);
                Object val4 = tracktable.getValueAt(selectedRow, 4);
                txttrackingid.setText(val0 != null ? val0.toString() : "");
                txtshipmentid.setText(val1 != null ? val1.toString() : "");
                txtlocation.setText(val2 != null ? val2.toString() : "");
                // Parse delivery time string and set spinnerday, comboBoxmonth, comboBoxyear
                if (val3 != null && !val3.toString().isEmpty()) {
                    try {
                        String[] parts = val3.toString().split("[ -:]");
                        if (parts.length >= 3) {
                            comboBoxyear.setSelectedItem(parts[0]);
                            int monthNum = Integer.parseInt(parts[1]);
                            String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
                            if (monthNum >= 1 && monthNum <= 12) {
                                comboBoxmonth.setSelectedItem(months[monthNum - 1]);
                            }
                            spinnerday.setValue(Integer.parseInt(parts[2]));
                        }
                    } catch (Exception ex) {
                        // Ignore parse errors
                    }
                }
                txtdelays.setText(val4 != null ? val4.toString() : "");
                // status is not editable, but you can add a field if needed
            }
        });

        // Delivery Personnel Table: populate fields when a row is selected
        AllDriversView.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = AllDriversView.getSelectedRow();
            if (selectedRow >= 0) {
                String username = AllDriversView.getValueAt(selectedRow, 1).toString();
                comboUserDrivers.setSelectedItem(username); // select by name
                if (driverMap.containsKey(username)) {
                    txtID.setText(String.valueOf(driverMap.get(username))); // set corresponding userID
                } else {
                    txtID.setText(""); // fallback if somehow not found
                }
                txtContact.setText(AllDriversView.getValueAt(selectedRow, 2).toString());
                comboBoxtimeslot.setSelectedItem(AllDriversView.getValueAt(selectedRow, 3).toString());
                txtRoute.setText(AllDriversView.getValueAt(selectedRow, 4).toString());
                txtAreaHistory.setText(AllDriversView.getValueAt(selectedRow, 5).toString());
                comboboxavailability.setSelectedItem(AllDriversView.getValueAt(selectedRow, 6).toString());
            }
        });


        // Add Personnel: pt2
        addDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtContact.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill required fields!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String selectedDriver = (String) comboUserDrivers.getSelectedItem();
                    if (selectedDriver == null || !driverMap.containsKey(selectedDriver)) {
                        JOptionPane.showMessageDialog(null, "Please select a valid driver!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int personnelID = driverMap.get(selectedDriver); // foreign key
                    String personnelContact = txtContact.getText();
                    String assignedRoute = txtRoute.getText();
                    String schedule = comboBoxtimeslot.getSelectedItem().toString();
                    String deliveryHistory = txtAreaHistory.getText();
                    String availability = comboboxavailability.getSelectedItem() != null ? comboboxavailability.getSelectedItem().toString() : "";

                    Controller.DeliveryPersonnelController controller = new Controller.DeliveryPersonnelController();
                    Model.DeliveryPersonnel p1 = new Model.DeliveryPersonnel(personnelID, selectedDriver, personnelContact, schedule, assignedRoute, deliveryHistory, availability);
                    controller.addDeliveryPersonnel(p1);

                    JOptionPane.showMessageDialog(null, "Driver added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearPersonnelFields();
                    loadPersonnelTable();
                }
            }
        });


        // Update Personnel: pt2
        updateDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int personnelID = Integer.parseInt(txtID.getText()); // This is userID from Users table
                    String selectedDriver = (String) comboUserDrivers.getSelectedItem(); //select driver name
                    if (selectedDriver == null || !driverMap.containsKey(selectedDriver)) {
                        JOptionPane.showMessageDialog(null, "Please select a valid driver from the list!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // double-check selected personnelID matches selected driver
                    if (driverMap.get(selectedDriver) != personnelID) {
                        JOptionPane.showMessageDialog(null, "Selected ID does not match chosen driver!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String personnelContact = txtContact.getText();
                    String assignedRoute = txtRoute.getText();
                    String schedule = comboBoxtimeslot.getSelectedItem().toString();
                    String deliveryHistory = txtAreaHistory.getText();
                    String availability = comboboxavailability.getSelectedItem() != null ? comboboxavailability.getSelectedItem().toString() : "";

                    Controller.DeliveryPersonnelController controller = new Controller.DeliveryPersonnelController();
                    Model.DeliveryPersonnel p1 = new Model.DeliveryPersonnel(personnelID, selectedDriver, personnelContact, schedule, assignedRoute, deliveryHistory, availability);
                    controller.updateDeliveryPersonnel(p1);

                    JOptionPane.showMessageDialog(null, "Driver updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearPersonnelFields();
                    loadPersonnelTable();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please select a valid driver to proceed", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Delete Personnel/ pt:2
        deleteDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int personnelID = Integer.parseInt(txtID.getText());

                    String selectedDriver = (String) comboUserDrivers.getSelectedItem();
                    if (selectedDriver == null || !driverMap.containsKey(selectedDriver)) {
                        JOptionPane.showMessageDialog(null, "Please select a valid driver!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // Double-check if combo selection matches personnelID (safety)
                    if (driverMap.get(selectedDriver) != personnelID) {
                        JOptionPane.showMessageDialog(null, "Mismatch between selected driver and ID!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // Check if driver is assigned to any shipment
                    boolean isAssigned = false;
                    try (Connection conn = Utility.DBConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Shipments WHERE assignedDriverID = ?")) {
                        stmt.setInt(1, personnelID);
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next() && rs.getInt(1) > 0) {
                            isAssigned = true;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error checking driver assignment!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (isAssigned) {
                        JOptionPane.showMessageDialog(null, "Driver is currently assigned to a shipment!", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    Controller.DeliveryPersonnelController controller = new Controller.DeliveryPersonnelController();
                    Model.DeliveryPersonnel p1 = new Model.DeliveryPersonnel();
                    p1.setPersonnelID(personnelID);
                    controller.deleteDeliveryPersonnel(p1);

                    JOptionPane.showMessageDialog(null, "Driver deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearPersonnelFields();
                    loadPersonnelTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please select a valid driver to proceed", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // User Table: populate fields when a row is selected
        userdata.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = userdata.getSelectedRow();
            if (selectedRow >= 0) {
                txtemail.setText(userdata.getValueAt(selectedRow, 0).toString());
                txtusername.setText(userdata.getValueAt(selectedRow, 1).toString());
                txtpassword.setText(userdata.getValueAt(selectedRow, 2).toString());
                comboboxrole.setSelectedItem(userdata.getValueAt(selectedRow, 3).toString());
            }
        });
        //Refresh the delivery personnel table/ pt:2
        btnrefreshdrivers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadPersonnelTable();
                JOptionPane.showMessageDialog(null, "Driver table list refreshed");
            }
        });

        // Add User
        btnadduser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtemail.getText().isEmpty() || txtusername.getText().isEmpty() || txtpassword.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all user fields!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String email = txtemail.getText();
                    String username = txtusername.getText();
                    String password = txtpassword.getText();
                    String role = comboboxrole.getSelectedItem() != null ? comboboxrole.getSelectedItem().toString() : "user";
                    Controller.UsersController controller = new Controller.UsersController();
                    boolean success = controller.addUser(email, username, password, role);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        loadUserTable();
                        clearUserFields();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add user!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Update User
        btnupdateuser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtemail.getText().isEmpty() || txtusername.getText().isEmpty() || txtpassword.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all user fields!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String email = txtemail.getText();
                    String username = txtusername.getText();
                    String password = txtpassword.getText();
                    String role = comboboxrole.getSelectedItem() != null ? comboboxrole.getSelectedItem().toString() : "user";
                    Controller.UsersController controller = new Controller.UsersController();
                    boolean success = controller.updateUser(email, username, password, role);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "User updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        loadUserTable();
                        clearUserFields();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to update user!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Delete User
        btndeleteuser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtemail.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please select a user to delete!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String email = txtemail.getText();
                    Controller.UsersController controller = new Controller.UsersController();
                    boolean success = controller.deleteUser(email);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "User deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        loadUserTable();
                        clearUserFields();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to delete user!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Clear User Fields
        btnclearuserfields.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearUserFields();
            }
        });

        // Load shipment data
        loadShipmentTable();

        // Shipment table row selection
        shipmentdatatable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = shipmentdatatable.getSelectedRow();

            if (selectedRow >= 0) {
                txtshipmentshipmentid.setText(shipmentdatatable.getValueAt(selectedRow, 0).toString());
                txtsenderid.setText(shipmentdatatable.getValueAt(selectedRow, 1).toString());
                txtreceivername.setText(shipmentdatatable.getValueAt(selectedRow, 2).toString());
                comboBox2.setSelectedItem(shipmentdatatable.getValueAt(selectedRow, 3).toString());
                txtassigneddriverid.setText(shipmentdatatable.getValueAt(selectedRow, 4).toString());
            }
        });

        // Add shipment button
        btnaddshipment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtreceivername.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill required fields!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String receiverName = txtreceivername.getText();
                    String status = comboBox2.getSelectedItem().toString();
                    Integer driverID = txtassigneddriverid.getText().isEmpty() ? null : Integer.parseInt(txtassigneddriverid.getText());
                    // Get estimated delivery date from comboBoxyear, comboBoxmonth, spinnerday
                    int year = Integer.parseInt(comboBoxyear.getSelectedItem().toString());
                    String monthName = comboBoxmonth.getSelectedItem().toString();
                    int month = java.time.Month.valueOf(monthName.toUpperCase()).getValue(); // 1-based
                    int day = Integer.parseInt(spinnerday.getValue().toString());
                    String timeSlot = (comboBoxtimeslot != null && comboBoxtimeslot.getSelectedItem() != null) ? comboBoxtimeslot.getSelectedItem().toString() : "12:00:00";
                    String estimatedDeliveryTime = String.format("%04d-%02d-%02d %s", year, month, day, timeSlot);
                    Controller.ShipmentsController controller = new Controller.ShipmentsController();
                    controller.addShipment(receiverName, status, driverID, loggedInUserId, estimatedDeliveryTime);
                    JOptionPane.showMessageDialog(null, "Shipment added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearShipmentFields();
                    loadShipmentTable();
                }
            }
        });

        // Update shipment button
        btnupdateshipment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtshipmentshipmentid.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please select a shipment to update!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        int shipmentID = Integer.parseInt(txtshipmentshipmentid.getText());
                        String receiverName = txtreceivername.getText();
                        String status = comboBox2.getSelectedItem().toString();
                        Integer driverID = txtassigneddriverid.getText().isEmpty() ? null : Integer.parseInt(txtassigneddriverid.getText());

                        Controller.ShipmentsController controller = new Controller.ShipmentsController();
                        controller.updateShipment(shipmentID, receiverName, status, driverID, loggedInUserId);
                        JOptionPane.showMessageDialog(null, "Shipment updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        clearShipmentFields();
                        loadShipmentTable();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid shipment ID!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Delete shipment button
        btndeleteshipment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtshipmentshipmentid.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please select a shipment to delete!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        int shipmentID = Integer.parseInt(txtshipmentshipmentid.getText());
                        Controller.ShipmentsController controller = new Controller.ShipmentsController();
                        controller.deleteShipment(shipmentID);
                        JOptionPane.showMessageDialog(null, "Shipment deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        clearShipmentFields();
                        loadShipmentTable();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid shipment ID!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Clear fields button
        btnclearfields.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearShipmentFields();
            }
        });



        // Initialize Monthly Report Controller (connects button and dropdowns)
        new Controller.MonthlyReportController(this);

        // Populate year and month dropdowns for reports (if not already populated)
        if (comboBoxYear.getItemCount() == 0) {
            int currentYear = java.time.Year.now().getValue();
            for (int y = currentYear - 5; y <= currentYear + 1; y++) {
                comboBoxYear.addItem(String.valueOf(y));
            }
        }
        if (comboBoxMonth.getItemCount() == 0) {
            String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
            for (String m : months) comboBoxMonth.addItem(m);
        }

        if (btnrefreshshipments != null) {
            btnrefreshshipments.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    loadShipmentTable();
                    JOptionPane.showMessageDialog(null, "Shipment table refreshed");
                }
            });
        }

        // Set spinnerday min/max to 1 and 31
        if (spinnerday != null) {
            spinnerday.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));
        }
    }

    // Load all shipment tracking data into the table
    private void loadTrackTable() {
        TrackShipmentProgressDAO dao = new TrackShipmentProgressDAO();
        List<TrackShipmentProgress> list = dao.getAllShipmentProgress();
        String[] columnNames = {"Tracking ID", "Shipment ID", "Current Location", "Estimated Delivery Time", "Delay", "Status"};
        String[][] data = new String[list.size()][columnNames.length];
        for (int i = 0; i < list.size(); i++) {
            TrackShipmentProgress progress = list.get(i);
            data[i][0] = String.valueOf(progress.getTrackingID());
            data[i][1] = String.valueOf(progress.getShipmentID());
            data[i][2] = progress.getCurrentLocation();
            data[i][3] = progress.getEstimatedDeliveryTime();
            data[i][4] = String.valueOf(progress.getDelay());
            data[i][5] = progress.getStatus();
        }
        tracktable.setModel(new DefaultTableModel(data, columnNames));
    }

    // Load all delivery personnel data into the table / pt:2
    private void loadPersonnelTable() {
        Model.DeliveryPersonnelDAO dao = new Model.DeliveryPersonnelDAO();
        java.util.List<Model.DeliveryPersonnel> list = dao.getAllPersonnel();
        String[] columnNames = {"ID", "Name", "Contact", "Schedule", "Route", "Deliveries", "Availability"};
        String[][] data = new String[list.size()][columnNames.length];
        for (int i = 0; i < list.size(); i++) {
            Model.DeliveryPersonnel personnel = list.get(i);
            data[i][0] = String.valueOf(personnel.getPersonnelID());
            data[i][1] = personnel.getPersonnelName();
            data[i][2] = personnel.getPersonnelContact();
            data[i][3] = personnel.getSchedule();
            data[i][4] = personnel.getAssignedRoute();
            data[i][5] = personnel.getDeliveryHistory();
            data[i][6] = personnel.getAvailability();
        }
        AllDriversView.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    // Load all user data into the table
    private void loadUserTable() {
        Controller.UsersController controller = new Controller.UsersController();
        java.util.List<Model.Users> list = controller.getAllUsers();
        String[] columnNames = {"Email", "Username", "Password", "Role"};
        String[][] data = new String[list.size()][columnNames.length];
        for (int i = 0; i < list.size(); i++) {
            Model.Users user = list.get(i);
            data[i][0] = user.getEmail();
            data[i][1] = user.getUsername();
            data[i][2] = user.getPassword();
            data[i][3] = controller.getUserRole(user.getEmail());
        }
        userdata.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        loadDriverUsers(); // to repopulate the combo box
    }

    // Helper to get next available personnel ID (auto-increment)
    private int getNextPersonnelID() {
        Model.DeliveryPersonnelDAO dao = new Model.DeliveryPersonnelDAO();
        java.util.List<Model.DeliveryPersonnel> list = dao.getAllPersonnel();
        int maxID = 0;
        for (Model.DeliveryPersonnel p : list) {
            if (p.getPersonnelID() > maxID) {
                maxID = p.getPersonnelID();
            }
        }
        return maxID + 1;
    }

    // Set txtID to next available ID
    private void setNextPersonnelID() {
        txtID.setText(String.valueOf(getNextPersonnelID()));
    }

    // Clear personnel fields and set next available ID/ pt:2
    private void clearPersonnelFields() {
        txtID.setText("");
        comboUserDrivers.setSelectedIndex(-1);
        txtContact.setText("");
        comboBoxtimeslot.setSelectedIndex(0);
        txtRoute.setText("");
        txtAreaHistory.setText("");
        comboboxavailability.setSelectedItem("Available");
    }

    // Helper to get next available shipment ID (auto-increment)
    private int getNextShipmentID() {
        TrackShipmentProgressDAO dao = new TrackShipmentProgressDAO();
        List<TrackShipmentProgress> list = dao.getAllShipmentProgress();
        int maxID = 0;
        for (TrackShipmentProgress t : list) {
            try {
                int id = Integer.parseInt(String.valueOf(t.getShipmentID()));
                if (id > maxID) {
                    maxID = id;
                }
            } catch (NumberFormatException ex) {
                // Ignore invalid IDs
            }
        }
        return maxID + 1;
    }

    // Set txtshipmentid to next available shipment ID
    private void setNextShipmentID() {
        txtshipmentid.setText(String.valueOf(getNextShipmentID()));
    }

    // Helper to get next available tracking ID (auto-increment)
    private int getNextTrackingID() {
        TrackShipmentProgressDAO dao = new TrackShipmentProgressDAO();
        List<TrackShipmentProgress> list = dao.getAllShipmentProgress();
        int maxID = 0;
        for (TrackShipmentProgress t : list) {
            try {
                int id = Integer.parseInt(String.valueOf(t.getTrackingID()));
                if (id > maxID) {
                    maxID = id;
                }
            } catch (NumberFormatException ex) {
                // Ignore invalid IDs
            }
        }
        return maxID + 1;
    }

    // Set txttrackingid to next available tracking ID
    public void setNextTrackingID() {
        txttrackingid.setText(String.valueOf(getNextTrackingID()));
    }

    // Clear user fields
    private void clearUserFields() {
        txtemail.setText("");
        txtusername.setText("");
        txtpassword.setText("");
        comboboxrole.setSelectedIndex(0);
    }

    // populating rows in  table/ pt:2
    public static void populatePersonnelRow(String[][] data, int i, Model.DeliveryPersonnel personnel) {
        data[i][0] = String.valueOf(personnel.getPersonnelID());
        data[i][1] = personnel.getPersonnelName(); // show username instead of raw ID
        data[i][2] = personnel.getPersonnelContact();
        data[i][3] = personnel.getSchedule();
        data[i][4] = personnel.getAssignedRoute();
        data[i][5] = personnel.getDeliveryHistory();
        data[i][6] = personnel.getAvailability();
    }

    // Add shipments methods
    private void loadShipmentTable() {
        Controller.ShipmentsController controller = new Controller.ShipmentsController();
        List<Model.Shipments> list = controller.getAllShipments();

        String[] columnNames = {"Shipment ID", "Sender ID", "Receiver Name", "Status", "Driver ID"};
        String[][] data = new String[list.size()][columnNames.length];

        for (int i = 0; i < list.size(); i++) {
            Model.Shipments shipment = list.get(i);
            data[i][0] = String.valueOf(shipment.getShipmentID());
            data[i][1] = shipment.getUserid() != null ? String.valueOf(shipment.getUserid()) : "";
            data[i][2] = shipment.getReceiverName();
            data[i][3] = shipment.getShipmentStatus();
            data[i][4] = shipment.getAssignedDriverID() != null ? String.valueOf(shipment.getAssignedDriverID()) : "";
        }
        shipmentdatatable.setModel(new DefaultTableModel(data, columnNames));
    }

    private void clearShipmentFields() {
        txtshipmentshipmentid.setText("");
        if (txtsenderid != null) {
            txtsenderid.setText(String.valueOf(loggedInUserId));
        }
        txtreceivername.setText("");
        txtassigneddriverid.setText("");
        comboBox2.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        // Start scheduled polling for shipment progress changes
        new Model.TrackShipmentProgressPoller().startPolling();
        // Example: pass a dummy logged-in user email (should be set from login/session)
        JFrame frame = new JFrame("Admin View");
        adminView view = new adminView("admin@admin.com"); // Replace with actual logged-in user email
        frame.setContentPane(view.tabbedPane1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Declare components matching your GUI
    public JButton btnGenerate;
    public JLabel lblTotalDeliveriesNumber;
    public JLabel lblDelayedDeliveriesNumber;
    public JLabel lblTotalShipmentsNumber;
    private JComboBox comboBoxtimeslot;
    private JSpinner spinnerday;
    private JComboBox comboBoxmonth;
    private JComboBox comboBoxyear;


    // Add getters for components
    public JComboBox<String> getComboBoxMonth() { return comboBoxMonth; }
    public JComboBox<String> getComboBoxYear() { return comboBoxYear; }
    public JButton getBtnGenerate() { return btnGenerate; }
    public JLabel getLblTotalDeliveriesNumber() { return lblTotalDeliveriesNumber; }
    public JLabel getLblDelayedDeliveriesNumber() { return lblDelayedDeliveriesNumber; }
    public JLabel getLblTotalShipmentsNumber() { return lblTotalShipmentsNumber; }

}



