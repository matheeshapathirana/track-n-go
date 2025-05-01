package View;

import Controller.LoginController;
import Utility.DBConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class loginView extends JFrame {
    private JTabbedPane tabs;
    private JFormattedTextField txtloginemail;
    private JPasswordField txtloginpassword;
    private JButton btnlogin;
    private JPasswordField txtregisterpassword;
    private JTextField txtregisterusername;
    private JTextField txtregisteremail;
    private JButton btnregister;
    private JLabel lblloginemail;
    private JLabel lblloginpassword;
    private JLabel lblregisterusername;
    private JLabel lblregisteremail;
    private JLabel lblregisterpassword;
    private JPanel registerbackpanel;
    private JPanel loginbackpanel;

    public loginView() {
        Connection conn = DBConnection.getConnection();

        btnlogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = txtloginemail.getText();
                String password = new String(txtloginpassword.getPassword());
                LoginController controller = new LoginController();
                boolean success = controller.login(email, password);
                if (success) {
                    JOptionPane.showMessageDialog(loginbackpanel, "Login successful!");
                } else {
                    JOptionPane.showMessageDialog(loginbackpanel, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnregister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtregisterusername.getText();
                String email = txtregisteremail.getText();
                String password = new String(txtregisterpassword.getPassword());
                LoginController controller = new LoginController();
                boolean registered = controller.register(username, email, password);
                if (registered) {
                    JOptionPane.showMessageDialog(registerbackpanel, "Registration successful!");
                } else {
                    JOptionPane.showMessageDialog(registerbackpanel, "Registration failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        loginView view = new loginView();
        view.setContentPane(view.tabs);
        view.setTitle("Login/Register");
        view.setSize(600, 400);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }
}
