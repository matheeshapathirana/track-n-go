package Controller;

import Utility.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    // Method for user login
    // Returns the user's role if authenticated, otherwise null
    public String login(String email, String password) {
        String userRole = null;
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT role FROM Users WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userRole = rs.getString("role"); // Get the user's role
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return userRole;
    }

    // Method for user registration
    public boolean register(String username, String email, String password, String role) {
        boolean isRegistered = false;

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO Users (email, username, password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.setString(4, role);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                isRegistered = true; // Registration successful
            }
        } catch (Exception e) {
            System.out.println("Registration error: " + e.getMessage());
        }

        return isRegistered;
    }
}
