package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utility.DBConnection;

public class CustomerNotificationDAO {
    private Connection conn;

    public CustomerNotificationDAO() {
        conn = DBConnection.getConnection();
    }

    public List<CustomerNotification> getNotificationsByUserID(int userId) {
        List<CustomerNotification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM Notifications WHERE recipientID = ? ORDER BY createdOn DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CustomerNotification notification = new CustomerNotification(
                        rs.getInt("notificationID"),
                        null, // recipientType removed
                        rs.getInt("recipientID"),
                        rs.getString("message"),
                        rs.getString("createdOn")
                );
                notifications.add(notification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notifications;
    }

    public void addNotification(String recipientType, int recipientId, String message) {
        String sql = "INSERT INTO Notifications (recipientID, message) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, recipientId);
            stmt.setString(2, message);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch all notifications (for admin or global view)
    public List<CustomerNotification> getAllNotifications() {
        List<CustomerNotification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM Notifications ORDER BY createdOn DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CustomerNotification notification = new CustomerNotification(
                        rs.getInt("notificationID"),
                        null, // recipientType removed
                        rs.getInt("recipientID"),
                        rs.getString("message"),
                        rs.getString("createdOn")
                );
                notifications.add(notification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    public int getNotificationIdByMessageAndUser(String message, int userId) {
        String sql = "SELECT notificationID FROM Notifications WHERE message = ? AND recipientID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, message);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("notificationID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if no matching notification is found
    }

    public int getNotificationIdByMessageAndTimestamp(String message, String timestamp, int userId) {
        String sql = "SELECT notificationID FROM Notifications WHERE message = ? AND createdOn = ? AND recipientID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, message);
            stmt.setString(2, timestamp);
            stmt.setInt(3, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("notificationID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if no matching notification is found
    }

    public void deleteNotification(int notificationId) {
        String sql = "DELETE FROM Notifications WHERE notificationID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, notificationId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearAllNotificationsForUser(int userId) {
        String sql = "DELETE FROM Notifications WHERE recipientID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected by clear all: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
