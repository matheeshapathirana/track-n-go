package View;

import javax.swing.*;
import Model.DeliveryPersonnel;
import Model.DeliveryPersonnelDAO;
import Controller.DeliveryPersonnelController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeliveryPersonnelView extends JFrame{
    private JLabel Namelbl;
    private JLabel IDlbl;
    private JLabel contactlbl;
    private JLabel schedulelbl;
    private JLabel AssignedRoutelbl;
    private JTextField txtID;
    private JTextField txtName;
    private JTextField txtContact;
    private JTextField txtSchedule;
    private JTextField txtRoute;
    private JButton addDriverButton;
    private JButton updateDriverButton;
    private JButton deleteDriverButton;
    private JTextArea txtAreaHistory;
    private JTable AllDriversView;
    private JPanel PersonnelManagerPanel;
    private JLabel alllDriverslbl;
    private JLabel mainlbl;
    private JPanel spacer1;  //these spacer1,2,3 was used to keep space in between
    private JPanel spacer2;
    private JPanel spacer3;
    private JButton clearbtn;
    private JLabel availabilitylbl;
    private JComboBox comboBox1;

    private void clearFields() {
        txtID.setText("");
        txtName.setText("");
        txtContact.setText("");
        txtSchedule.setText("");
        txtRoute.setText("");
        txtAreaHistory.setText("");
        comboBox1.setSelectedIndex(0);
    }
    public DeliveryPersonnelView() {
        //to prevent user input bcs userID is autoincrement
        txtID.setEditable(false);
        //combo box
        comboBox1.setModel(new DefaultComboBoxModel<>(new String[]{"Available", "Not Available"}));
        //Load data when view starts. a pvt method is written below
        loadPersonnelTable();
        //this method is used to get the values in the table to fields
        AllDriversView.getSelectionModel().addListSelectionListener(e ->{
            int selectedRow = AllDriversView.getSelectedRow();
            if(selectedRow >= 0)
            {
                txtID.setText(AllDriversView.getValueAt(selectedRow, 0).toString());
                txtName.setText(AllDriversView.getValueAt(selectedRow, 1).toString());
                txtContact.setText(AllDriversView.getValueAt(selectedRow, 2).toString());
                txtSchedule.setText(AllDriversView.getValueAt(selectedRow, 3).toString());
                txtRoute.setText(AllDriversView.getValueAt(selectedRow, 4).toString());
                txtAreaHistory.setText(AllDriversView.getValueAt(selectedRow, 5).toString());
                comboBox1.setSelectedItem(AllDriversView.getValueAt(selectedRow, 6).toString());
            }
        });
        //action to clear all fields
        clearbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        //action for add drivers button
        addDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtName.getText().isEmpty() || txtContact.getText().isEmpty() || txtSchedule.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    String personnelName = txtName.getText();
                    String personnelContact = txtContact.getText();
                    String schedule = txtSchedule.getText();
                    String assignedRoute = txtRoute.getText();
                    String deliveryHistory = txtAreaHistory.getText();
                    String availability = comboBox1.getSelectedItem().toString();

                    DeliveryPersonnelController controller = new DeliveryPersonnelController();
                    DeliveryPersonnel p1 = new DeliveryPersonnel(0, personnelName, personnelContact, schedule, assignedRoute, deliveryHistory, availability);

                    controller.addDeliveryPersonnel(p1);
                    JOptionPane.showMessageDialog(null, "Personnel added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                    loadPersonnelTable();
                    pack();

                }
            }
        });

        //action to update the drivers button
        updateDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int personnelID = Integer.parseInt(txtID.getText());
                    String personnelName = txtName.getText();
                    String personnelContact = txtContact.getText();
                    String schedule = txtSchedule.getText();
                    String assignedRoute = txtRoute.getText();
                    String deliveryHistory = txtAreaHistory.getText();
                    String availability = comboBox1.getSelectedItem().toString();

                    DeliveryPersonnelController controller = new DeliveryPersonnelController();
                    DeliveryPersonnel p1 = new DeliveryPersonnel(personnelID, personnelName, personnelContact, schedule, assignedRoute, deliveryHistory, availability);

                    controller.updateDeliveryPersonnel(p1);
                    JOptionPane.showMessageDialog(null, "Personnel updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                    loadPersonnelTable();
                    pack();
                }
                catch (NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(null, "Please select a valid personnel to proceed", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //action to delete driver on delete button
        deleteDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int personnelID = Integer.parseInt(txtID.getText());
                    DeliveryPersonnelController controller = new DeliveryPersonnelController();
                    DeliveryPersonnel p1 = new DeliveryPersonnel();
                    p1.setPersonnelID(personnelID);

                    controller.deleteDeliveryPersonnel(p1);
                    JOptionPane.showMessageDialog(null, "Personnel deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                    loadPersonnelTable();
                    pack();
                }
                catch(NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(null, "Please select a valid personnel to proceed", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    //to load all data store
    private void loadPersonnelTable() {
        DeliveryPersonnelDAO dao = new DeliveryPersonnelDAO();
        java.util.List<DeliveryPersonnel> list = dao.getAllPersonnel();

        String[] columnNames = {"ID", "Name", "Contact", "Schedule", "Route", "Deliveries","Availability"};
        String[][] data = new String[list.size()][columnNames.length];
        for (int i = 0; i < list.size(); i++) {
            DeliveryPersonnel personnel = list.get(i);
            adminView.populatePersonnelRow(data, i, personnel);
        }
        AllDriversView.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        pack();
    }
    //main method to load all to the ui
    public static void main(String[] args)
    {
        DeliveryPersonnelView view = new DeliveryPersonnelView();
        view.setTitle("Delivery Personnel Manager");
        view.setSize(800, 600);
        view.setContentPane(view.PersonnelManagerPanel);
        view.setVisible(true);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setLocationRelativeTo(null);
    }
}
