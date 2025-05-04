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
        String sql = "SELECT * FROM Notifications WHERE recipientType = 'user' AND recipientID = ? ORDER BY createdOn DESC";

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

    // Add a notification for a shipment progress event, matching recipientID to userid in TrackShipmentProgress
    public void addNotificationForShipmentProgress(int trackingID, String message) {
        // Get userid from TrackShipmentProgress
        int userId = -1;
        String sql = "SELECT userid FROM TrackShipmentProgress WHERE trackingID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, trackingID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("userid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (userId != -1 && userId != 0) {
            addNotification("user", userId, message);
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

    public int getNotificationIdByMessageAndUser(String message, int userId) {
        String sql = "SELECT notificationID FROM Notifications WHERE message = ? AND recipientID = ? AND recipientType = 'User'";
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
        String sql = "SELECT notificationID FROM Notifications WHERE message = ? AND createdOn = ? AND recipientID = ? AND recipientType = 'User'";
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
        String sql = "DELETE FROM Notifications WHERE recipientType = 'User' AND recipientID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected by clear all: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- Polling logic for TrackShipmentProgress changes ---
    private List<TrackShipmentProgress> lastProgressCache = new ArrayList<>();

    /**
     * Polls the TrackShipmentProgress table for changes in estimatedDeliveryTime, delay, or status.
     * If a change is detected, sends a notification to the user associated with the shipment.
     * Call this method periodically (e.g., with a ScheduledExecutorService).
     */
    public void pollAndNotifyShipmentProgressChanges() {
        List<TrackShipmentProgress> currentProgress = new ArrayList<>();
        // Fetch all current shipment progress
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM TrackShipmentProgress")) {
            while (rs.next()) {
                TrackShipmentProgress progress = new TrackShipmentProgress();
                progress.setTrackingID(rs.getInt("trackingID"));
                progress.setShipmentID(rs.getInt("shipmentID"));
                progress.setCurrentLocation(rs.getString("currentLocation"));
                progress.setEstimatedDeliveryTime(rs.getString("estimatedDeliveryTime"));
                progress.setDelay(rs.getInt("delay"));
                progress.setStatus(rs.getString("status"));
                try { progress.setUserid(rs.getInt("userid")); } catch (Exception ignore) {}
                currentProgress.add(progress);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // Compare with last cache
        for (TrackShipmentProgress curr : currentProgress) {
            TrackShipmentProgress prev = lastProgressCache.stream()
                .filter(p -> p.getTrackingID() == curr.getTrackingID())
                .findFirst().orElse(null);
            if (prev != null) {
                if (!safeEquals(curr.getEstimatedDeliveryTime(), prev.getEstimatedDeliveryTime())) {
                    notifyUserOfChange(curr, "estimatedDeliveryTime");
                }
                if (curr.getDelay() != prev.getDelay()) {
                    notifyUserOfChange(curr, "delay");
                }
                if (!safeEquals(curr.getStatus(), prev.getStatus())) {
                    notifyUserOfChange(curr, "status");
                }
            }
        }
        lastProgressCache = currentProgress;
    }

    // Helper to compare strings safely
    private boolean safeEquals(String a, String b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.equals(b);
    }

    // Helper to send notification to user associated with the shipment
    private void notifyUserOfChange(TrackShipmentProgress progress, String changeType) {
        int userId = progress.getUserid(); // Use userid from TrackShipmentProgress
        if (userId == -1 || userId == 0) {
            userId = getAssignedDriverIdForShipment(progress.getShipmentID()); // fallback
        }
        if (userId != -1 && userId != 0) {
            String message = "";
            switch (changeType) {
                case "estimatedDeliveryTime":
                    message = "estimated delivery time changed to " + progress.getEstimatedDeliveryTime();
                    break;
                case "delay":
                    message = "shipment is delayed by " + progress.getDelay();
                    break;
                case "status":
                    message = "shipment status changed: " + progress.getStatus();
                    break;
            }
            if (!message.isEmpty()) {
                addNotification("user", userId, message);
            }
        }
    }

    // Helper to get assignedDriverID (userId) for a shipment
    private int getAssignedDriverIdForShipment(int shipmentID) {
        String sql = "SELECT assignedDriverID FROM Shipments WHERE shipmentID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, shipmentID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("assignedDriverID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
