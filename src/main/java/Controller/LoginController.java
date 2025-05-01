package Controller;

import Utility.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    // Method for user login
    public boolean login(String email, String password) {
        boolean isAuthenticated = false;

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM Users WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                isAuthenticated = true; // User is authenticated
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }

        return isAuthenticated;
    }

    // Method for user registration
    public boolean register(String username, String email, String password) {
        boolean isRegistered = false;

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO Users (email, username, password) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, username);
            stmt.setString(3, password);

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
