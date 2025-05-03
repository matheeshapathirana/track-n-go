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
        String sql = "SELECT * FROM Notifications WHERE recipientType = 'User' AND recipientID = ? ORDER BY createdOn DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CustomerNotification notification = new CustomerNotification(
                        rs.getInt("notificationID"),
                        rs.getString("recipientType"),
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
        String sql = "INSERT INTO Notifications (recipientType, recipientID, message) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, recipientType);
            stmt.setInt(2, recipientId);
            stmt.setString(3, message);
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
                        rs.getString("recipientType"),
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
}

