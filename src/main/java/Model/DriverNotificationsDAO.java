package Model;

import Utility.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverNotificationsDAO {

    // Get all notifications for a specific personnelID
    public List<DriverNotification> getNotifications(int personnelID) {
        List<DriverNotification> notificationsList = new ArrayList<>();
        String sql = "SELECT notificationID, personnelID, message, sentOn FROM DriverNotifications WHERE personnelID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, personnelID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    notificationsList.add(new DriverNotification(
                            rs.getInt("notificationID"),
                            rs.getInt("personnelID"),
                            rs.getString("message"),
                            rs.getTimestamp("sentOn")
                    ));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error while fetching notifications for personnelID: " + personnelID);
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list in case of failure
        }

        return notificationsList; // Return the populated list
    }

    // Add a new notification
    public boolean addNotification(int personnelID, String message) {
        String sql = "INSERT INTO DriverNotifications (personnelID, message) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, personnelID);
            stmt.setString(2, message);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error while adding notification for personnelID: " + personnelID);
            e.printStackTrace();
            return false; // Return false in case of failure
        }
    }

    // Remove a notification by notificationID
    public boolean deleteNotification(int notificationID) {
        String sql = "DELETE FROM DriverNotifications WHERE notificationID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, notificationID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error while deleting notification with ID: " + notificationID);
            e.printStackTrace();
            return false; // Return false in case of failure
        }
    }
}