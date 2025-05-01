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

    private void clearFields() {
        txtID.setText("");
        txtName.setText("");
        txtContact.setText("");
        txtSchedule.setText("");
        txtRoute.setText("");
        txtAreaHistory.setText("");
    }
    public DeliveryPersonnelView() {
        //to prevent user input bcs userID is autoincrement
        txtID.setEditable(false);
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

                    DeliveryPersonnelController controller = new DeliveryPersonnelController();
                    DeliveryPersonnel p1 = new DeliveryPersonnel(0, personnelName, personnelContact, schedule, assignedRoute, deliveryHistory);

                    controller.addDeliveryPersonnel(p1);
                    JOptionPane.showMessageDialog(null, "Personnel added successfully");
                    clearFields();
                    loadPersonnelTable();

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

                    DeliveryPersonnelController controller = new DeliveryPersonnelController();
                    DeliveryPersonnel p1 = new DeliveryPersonnel(personnelID, personnelName, personnelContact, schedule, assignedRoute, deliveryHistory);

                    controller.updateDeliveryPersonnel(p1);
                    JOptionPane.showMessageDialog(null, "Personnel updated successfully");
                    clearFields();
                    loadPersonnelTable();
                }
                catch (NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(null, "Please enter a valid ID to proceed");
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
                    JOptionPane.showMessageDialog(null, "Personnel deleted successfully");
                    clearFields();
                    loadPersonnelTable();
                }
                catch(NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(null, "Please enter a valid ID to proceed");
                }
            }
        });
    }
    //to load all data store
    private void loadPersonnelTable() {
        DeliveryPersonnelDAO dao = new DeliveryPersonnelDAO();
        java.util.List<DeliveryPersonnel> list = dao.getAllPersonnel();

        String[] columnNames = {"ID", "Name", "Contact", "Schedule", "Route", "Deliveries"};
        String[][] data = new String[list.size()][columnNames.length];
        for (int i = 0; i < list.size(); i++) {
            DeliveryPersonnel personnel = list.get(i);
            data[i][0]=String.valueOf(personnel.getPersonnelID());
            data[i][1]=personnel.getPersonnelName();
            data[i][2]=personnel.getPersonnelContact();
            data[i][3]=personnel.getSchedule();
            data[i][4]=personnel.getAssignedRoute();
            data[i][5]=personnel.getDeliveryHistory();
        }
        AllDriversView.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }
    public static void main(String[] args)
    {
        DeliveryPersonnelView view = new DeliveryPersonnelView();
        view.setTitle("Delivery Personnel Manager");
        view.setSize(600, 600);
        view.setContentPane(view.PersonnelManagerPanel);
        view.setVisible(true);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
