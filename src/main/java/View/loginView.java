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
        // Ensure GUI components are initialized before adding listeners
        // This is important if using IntelliJ GUI Designer
        // The following code should be called after setContentPane in main
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
                        // Open adminView if role is admin
                        View.adminView.main(new String[]{});
                        dispose(); // Close the login window
                    } else if (role.equals("user")) {
                        // Open userView if role is user
                        View.userView.main(new String[]{});
                        dispose(); // Close the login window
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
        view.setContentPane(view.tabs);
        view.setTitle("Login/Register");
        view.setSize(600, 400);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setLocationRelativeTo(null);
        view.setupListeners(); // Ensure listeners are set up after GUI is initialized
        view.setVisible(true);
    }
}
