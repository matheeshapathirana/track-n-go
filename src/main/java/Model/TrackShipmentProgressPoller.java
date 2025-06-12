package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Utility.DBConnection;

public class TrackShipmentProgressPoller {
    private final Connection conn;
    private final CustomerNotificationDAO notificationDAO;
    private final Map<Integer, TrackShipmentProgressSnapshot> lastSnapshot = new HashMap<>();

    public TrackShipmentProgressPoller() {
        conn = DBConnection.getConnection();
        notificationDAO = new CustomerNotificationDAO();
    }

    public void startPolling() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::poll, 0, 3, TimeUnit.SECONDS);
    }

    private void poll() {
        String sql = "SELECT shipmentID, estimatedDeliveryTime, delay, shipmentStatus, userid FROM Shipments";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int shipmentID = rs.getInt("shipmentID");
                String estimatedDeliveryTime = rs.getString("estimatedDeliveryTime");
                int delay = rs.getObject("delay") != null ? rs.getInt("delay") : 0;
                String status = rs.getString("shipmentStatus");
                int userid = rs.getObject("userid") != null ? rs.getInt("userid") : 0;

                TrackShipmentProgressSnapshot prev = lastSnapshot.get(shipmentID);
                if (prev != null) {
                    if (!Objects.equals(prev.estimatedDeliveryTime, estimatedDeliveryTime)) {
                        notificationDAO.addNotification(null, userid, "Estimated arrival time changed to: " + estimatedDeliveryTime);
                    }
                    if (prev.delay != delay) {
                        notificationDAO.addNotification(null, userid, "A delay has occurred: " + delay + " days");
                    }
                    if (!Objects.equals(prev.status, status)) {
                        notificationDAO.addNotification(null, userid, "Status has been changed to: " + status);
                    }
                }
                lastSnapshot.put(shipmentID, new TrackShipmentProgressSnapshot(estimatedDeliveryTime, delay, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static class TrackShipmentProgressSnapshot {
        String estimatedDeliveryTime;
        int delay;
        String status;

        TrackShipmentProgressSnapshot(String estimatedDeliveryTime, int delay, String status) {
            this.estimatedDeliveryTime = estimatedDeliveryTime;
            this.delay = delay;
            this.status = status;
        }
    }
}
