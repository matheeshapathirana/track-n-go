package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Model.TrackShipmentProgressDAO;
import Model.TrackShipmentProgress;
import javax.swing.table.DefaultTableModel;
import java.util.List;

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
    private JTextField txtSchedule;
    private JTextField txtRoute;
    private JButton addDriverButton;
    private JButton updateDriverButton;
    private JButton deleteDriverButton;
    private JTextField txtID;
    private JTextArea txtAreaHistory;
    private JTable AllDriversView;
    private JLabel alllDriverslbl;
    private JComboBox comboBox1;
    private JButton clearbtn;
    private JTextField txttrackingid;
    private JLabel lbltrackingid;
    private JComboBox comboBox2;
    private JTextField txtassigneddriverid;
    private JLabel lblshipmentsshipmentid;
    private JLabel lblassigneddriverid;
    private JLabel lblsendername;
    private JLabel lblreceivername;
    private JLabel lblshipmentstatus;
    private JTextField txtshipmentshipmentid;
    private JTextField txtsendername;
    private JTextField txtreceivername;
    private JButton btnaddshipment;
    private JButton btnupdateshipment;
    private JButton btndeleteshipment;
    private JButton btnclearfields;
    private JTable shipmentdatatable;
    private JLabel availabilitylbl;
    private JComboBox comboboxavailability;
    private JLabel lblemail;
    private JLabel lblusername;
    private JLabel lblpassword;
    private JLabel lblrole;
    private JTextField txtemail;
    private JTextField txtusername;
    private JTextField txtpassword;
    private JComboBox comboboxrole;
    private JButton btnadduser;
    private JButton btnupdateuser;
    private JButton btndeleteuser;
    private JButton btnclearuserfields;
    private JTable userdata;
    private JComboBox comboBoxyear;
    private JComboBox comboBoxmonth;
    private JButton btngenerate;
    private JPanel reportsbackpanel;
    private JLabel lblgeneratemonthlyreports;
    private JLabel lblyear;
    private JLabel lblmonth;
    private JLabel lbltotaldeliveries;
    private JLabel lbltotaldeliveriesnumber;
    private JLabel lbldelayeddeliveries;
    private JLabel lbldelayeddeliveriesnumber;
    private JLabel lblaveragecustomerrating;
    private JLabel lblaveragecustomerratingnumber;
    private JLabel lbltotalshipments;
    private JLabel lbltotalshipmentsnumber;

    public adminView() {
        txtID.setEditable(false); // Make ID field non-editable
        txtshipmentid.setEditable(false);
        txttrackingid.setEditable(false); // trackingID should not be editable

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

        btnupdatetrack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txttrackingid.getText().isEmpty() || txtshipmentid.getText().isEmpty() || txtlocation.getText().isEmpty() || txtdeliverytimes.getText().isEmpty() || txtdelays.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String trackingID = txttrackingid.getText();
                    String shipmentID = txtshipmentid.getText();
                    String location = txtlocation.getText();
                    String deliveryTime = txtdeliverytimes.getText();
                    String delay = txtdelays.getText();
                    String status = comboBox1.getSelectedItem() != null ? comboBox1.getSelectedItem().toString() : "";
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
                txttrackingid.setText(tracktable.getValueAt(selectedRow, 0).toString());
                txtshipmentid.setText(tracktable.getValueAt(selectedRow, 1).toString());
                txtlocation.setText(tracktable.getValueAt(selectedRow, 2).toString());
                txtdeliverytimes.setText(tracktable.getValueAt(selectedRow, 3).toString());
                txtdelays.setText(tracktable.getValueAt(selectedRow, 4).toString());
                // status is not editable, but you can add a field if needed
            }
        });

        // Delivery Personnel Table: populate fields when a row is selected
        AllDriversView.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = AllDriversView.getSelectedRow();
            if (selectedRow >= 0) {
                txtID.setText(AllDriversView.getValueAt(selectedRow, 0).toString());
                txtName.setText(AllDriversView.getValueAt(selectedRow, 1).toString());
                txtContact.setText(AllDriversView.getValueAt(selectedRow, 2).toString());
                txtSchedule.setText(AllDriversView.getValueAt(selectedRow, 3).toString());
                txtRoute.setText(AllDriversView.getValueAt(selectedRow, 4).toString());
                txtAreaHistory.setText(AllDriversView.getValueAt(selectedRow, 5).toString());
                comboboxavailability.setSelectedItem(AllDriversView.getValueAt(selectedRow, 6).toString());
            }
        });

        // Add Personnel
        addDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtName.getText().isEmpty() || txtContact.getText().isEmpty() || txtSchedule.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill required fields!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Get next available ID
                    int nextID = getNextPersonnelID();
                    String personnelName = txtName.getText();
                    String personnelContact = txtContact.getText();
                    String schedule = txtSchedule.getText();
                    String assignedRoute = txtRoute.getText();
                    String deliveryHistory = txtAreaHistory.getText();
                    String availability = comboboxavailability.getSelectedItem() != null ? comboboxavailability.getSelectedItem().toString() : "";
                    Controller.DeliveryPersonnelController controller = new Controller.DeliveryPersonnelController();
                    Model.DeliveryPersonnel p1 = new Model.DeliveryPersonnel(nextID, personnelName, personnelContact, schedule, assignedRoute, deliveryHistory, availability);
                    controller.addDeliveryPersonnel(p1);
                    JOptionPane.showMessageDialog(null, "Driver added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearPersonnelFields();
                    loadPersonnelTable();
                }
            }
        });

        // Update Personnel
        updateDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int personnelID = Integer.parseInt(txtID.getText());
                    String personnelName = txtName.getText();
                    String personnelContact = txtContact.getText();
                    String schedule = txtSchedule.getText();
                    String assignedRoute = txtRoute.getText();
                    String deliveryHistory = txtAreaHistory.getText();
                    String availability = comboboxavailability.getSelectedItem() != null ? comboboxavailability.getSelectedItem().toString() : "";
                    Controller.DeliveryPersonnelController controller = new Controller.DeliveryPersonnelController();
                    Model.DeliveryPersonnel p1 = new Model.DeliveryPersonnel(personnelID, personnelName, personnelContact, schedule, assignedRoute, deliveryHistory, availability);
                    controller.updateDeliveryPersonnel(p1);
                    JOptionPane.showMessageDialog(null, "Driver updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearPersonnelFields();
                    loadPersonnelTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please select a valid driver to proceed", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Delete Personnel
        deleteDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int personnelID = Integer.parseInt(txtID.getText());
                    // Check if driver is assigned to any shipment
                    boolean isAssigned = false;
                    try {
                        java.sql.Connection conn = Utility.DBConnection.getConnection();
                        String sql = "SELECT COUNT(*) FROM Shipments WHERE assignedDriverID = ?";
                        java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setInt(1, personnelID);
                        java.sql.ResultSet rs = stmt.executeQuery();
                        if (rs.next() && rs.getInt(1) > 0) {
                            isAssigned = true;
                        }
                        rs.close();
                        stmt.close();
                        conn.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error checking driver assignment!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (isAssigned) {
                        JOptionPane.showMessageDialog(null, "Driver already assigned to a shipment!", "Warning", JOptionPane.WARNING_MESSAGE);
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

    // Load all delivery personnel data into the table
    private void loadPersonnelTable() {
        Model.DeliveryPersonnelDAO dao = new Model.DeliveryPersonnelDAO();
        java.util.List<Model.DeliveryPersonnel> list = dao.getAllPersonnel();
        String[] columnNames = {"ID", "Name", "Contact", "Schedule", "Route", "Deliveries", "Availability"};
        String[][] data = new String[list.size()][columnNames.length];
        for (int i = 0; i < list.size(); i++) {
            Model.DeliveryPersonnel personnel = list.get(i);
            populatePersonnelRow(data, i, personnel);
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

    // Clear personnel fields and set next available ID
    private void clearPersonnelFields() {
        txtID.setText(String.valueOf(getNextPersonnelID()));
        txtName.setText("");
        txtContact.setText("");
        txtSchedule.setText("");
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
    private void setNextTrackingID() {
        txttrackingid.setText(String.valueOf(getNextTrackingID()));
    }

    // Clear user fields
    private void clearUserFields() {
        txtemail.setText("");
        txtusername.setText("");
        txtpassword.setText("");
        comboboxrole.setSelectedIndex(0);
    }

    public static void populatePersonnelRow(String[][] data, int i, Model.DeliveryPersonnel personnel) {
        data[i][0] = String.valueOf(personnel.getPersonnelID());
        data[i][1] = personnel.getPersonnelName();
        data[i][2] = personnel.getPersonnelContact();
        data[i][3] = personnel.getSchedule();
        data[i][4] = personnel.getAssignedRoute();
        data[i][5] = personnel.getDeliveryHistory();
        data[i][6] = personnel.getAvailability();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Admin View");
        adminView view = new adminView();
        frame.setContentPane(view.tabbedPane1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
