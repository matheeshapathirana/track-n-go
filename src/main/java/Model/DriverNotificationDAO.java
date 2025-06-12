package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import Utility.DBConnection;

public class DriverNotificationDAO {
    public void addNotification(int personnelId, String message) {
        String sql = "INSERT INTO DriverNotifications (personnelID, message) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, personnelId);
            stmt.setString(2, message);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public java.util.List<DriverNotification> getNotificationsByDriverId(int driverId) {
        java.util.List<DriverNotification> notifications = new java.util.ArrayList<>();
        String sql = "SELECT * FROM DriverNotifications WHERE personnelID = ? ORDER BY sentOn DESC";
        try (java.sql.Connection conn = Utility.DBConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, driverId);
            java.sql.ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DriverNotification notification = new DriverNotification(
                    rs.getInt("notificationID"),
                    rs.getInt("personnelID"),
                    rs.getString("message"),
                    rs.getTimestamp("sentOn")
                );
                notifications.add(notification);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notifications;
    }

    public void clearAllNotificationsForDriver(int driverId) {
        String sql = "DELETE FROM DriverNotifications WHERE personnelID = ?";
        try (java.sql.Connection conn = Utility.DBConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, driverId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getNotificationIdByMessageAndTimestamp(int driverId, String message, String sentOn) {
        String sql = "SELECT notificationID FROM DriverNotifications WHERE personnelID = ? AND message = ? AND sentOn = ?";
        try (java.sql.Connection conn = Utility.DBConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, driverId);
            stmt.setString(2, message);
            stmt.setString(3, sentOn);
            java.sql.ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("notificationID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void deleteNotification(int notificationId) {
        String sql = "DELETE FROM DriverNotifications WHERE notificationID = ?";
        try (java.sql.Connection conn = Utility.DBConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, notificationId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
