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
    private JPanel spacer3;
    private JPanel spacer2;
    private JComboBox comboBox1;
    private JButton clearbtn;
    private JTextField txttrackingid;
    private JLabel lbltrackingid;

    public adminView() {
        txtID.setEditable(false); // Make ID field non-editable
        txtshipmentid.setEditable(false);
        txttrackingid.setEditable(false); // trackingID should not be editable

        loadTrackTable();
        loadPersonnelTable();
        setNextPersonnelID(); // Set next available ID on startup
        setNextShipmentID(); // Set next available shipment ID on startup
        setNextTrackingID(); // Set next available tracking ID on startup
        clearPersonnelFields();
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
                    Controller.DeliveryPersonnelController controller = new Controller.DeliveryPersonnelController();
                    Model.DeliveryPersonnel p1 = new Model.DeliveryPersonnel(nextID, personnelName, personnelContact, schedule, assignedRoute, deliveryHistory);
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
                    Controller.DeliveryPersonnelController controller = new Controller.DeliveryPersonnelController();
                    Model.DeliveryPersonnel p1 = new Model.DeliveryPersonnel(personnelID, personnelName, personnelContact, schedule, assignedRoute, deliveryHistory);
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
        String[] columnNames = {"ID", "Name", "Contact", "Schedule", "Route", "Deliveries"};
        String[][] data = new String[list.size()][columnNames.length];
        for (int i = 0; i < list.size(); i++) {
            Model.DeliveryPersonnel personnel = list.get(i);
            data[i][0] = String.valueOf(personnel.getPersonnelID());
            data[i][1] = personnel.getPersonnelName();
            data[i][2] = personnel.getPersonnelContact();
            data[i][3] = personnel.getSchedule();
            data[i][4] = personnel.getAssignedRoute();
            data[i][5] = personnel.getDeliveryHistory();
        }
        AllDriversView.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
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
