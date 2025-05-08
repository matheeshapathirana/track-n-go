package View;

import Controller.LoginController;
import Utility.DBConnection;
import Model.UsersDAO;

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
    }

    private void setupListeners() {
        btnlogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = txtloginemail.getText();
                String password = new String(txtloginpassword.getPassword());
                LoginController controller = new LoginController();
                String role = controller.login(email, password); // Get the user's role
                if (role != null) {
                    JOptionPane.showMessageDialog(loginbackpanel, "Login successful!");
                    if (role.equals("admin")) {
                        View.adminView.main(new String[]{});
                        dispose();
                    } else if (role.equals("user")) {
                        // Get username and userId from DB
                        UsersDAO usersDAO = new UsersDAO();
                        String username = usersDAO.getUsernameByEmail(email);
                        int customerId = usersDAO.getUserIdByEmail(email);
                        userView view = new userView(customerId, username);
                        JFrame frame = new JFrame("User View");
                        frame.setContentPane(view.getMainPanel());
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                        dispose();
                    }
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
                // Default role is 'user'. Do not set admin role automatically.
                String role = "user";
                LoginController controller = new LoginController();
                boolean registered = controller.register(username, email, password, role);
                if (registered) {
                    JOptionPane.showMessageDialog(registerbackpanel, "Registration successful!");
                    if (role.equals("admin")) {
                        // Open adminView if role is admin
                        View.adminView.main(new String[]{});
                        dispose(); // Close the login window
                    }
                } else {
                    JOptionPane.showMessageDialog(registerbackpanel, "Registration failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        loginView view = new loginView();
        if (view.tabs != null) {
            view.setContentPane(view.tabs);
        } else if (view.loginbackpanel != null) {
            view.setContentPane(view.loginbackpanel);
        } else if (view.registerbackpanel != null) {
            view.setContentPane(view.registerbackpanel);
        } else {
            throw new IllegalStateException("No valid content pane found! All are null.");
        }
        view.setTitle("Login/Register");
        view.setSize(600, 400);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setLocationRelativeTo(null);
        view.setupListeners();
        view.setVisible(true);
    }
}
