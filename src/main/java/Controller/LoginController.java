package Controller;

import Utility.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {
    public String login(String email, String password) {
        String userRole = null;
        long startTime = System.currentTimeMillis();
        try (Connection conn = DBConnection.getConnection()) {
            long connTime = System.currentTimeMillis();
            System.out.println("[DB Timing] login - DB connection time: " + (connTime - startTime) + " ms");
            String query = "SELECT role FROM Users WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);

            long queryStart = System.currentTimeMillis();
            ResultSet rs = stmt.executeQuery();
            long queryEnd = System.currentTimeMillis();
            System.out.println("[DB Timing] login - Query execution time: " + (queryEnd - queryStart) + " ms");
            if (rs.next()) {
                userRole = rs.getString("role");
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("[DB Timing] login - Total login time: " + (endTime - startTime) + " ms");
        return userRole;
    }

    public boolean register(String username, String email, String password, String role) {
        boolean isRegistered = false;
        long startTime = System.currentTimeMillis();
        try (Connection conn = DBConnection.getConnection()) {
            long connTime = System.currentTimeMillis();
            System.out.println("[DB Timing] register - DB connection time: " + (connTime - startTime) + " ms");
            String query = "INSERT INTO Users (email, username, password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.setString(4, role);

            long execStart = System.currentTimeMillis();
            int rowsInserted = stmt.executeUpdate();
            long execEnd = System.currentTimeMillis();
            System.out.println("[DB Timing] register - Query execution time: " + (execEnd - execStart) + " ms");
            if (rowsInserted > 0) {
                isRegistered = true;
            }
        } catch (Exception e) {
            System.out.println("Registration error: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("[DB Timing] register - Total registration time: " + (endTime - startTime) + " ms");
        return isRegistered;
    }
}
