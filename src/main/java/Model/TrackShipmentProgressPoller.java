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
    scheduler.scheduleAtFixedRate(this::poll, 0, 3, TimeUnit.SECONDS); // Poll every 3 seconds
}

private void poll() {
    String sql = "SELECT trackingID, estimatedDeliveryTime, delay, status, userid FROM TrackShipmentProgress";
    try (Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            int trackingID = rs.getInt("trackingID");
            String estimatedDeliveryTime = rs.getString("estimatedDeliveryTime");
            int delay = rs.getInt("delay");
            String status = rs.getString("status");
            int userid = rs.getInt("userid");

            TrackShipmentProgressSnapshot prev = lastSnapshot.get(trackingID);
            if (prev != null) {
                if (!Objects.equals(prev.estimatedDeliveryTime, estimatedDeliveryTime)) {
                    notificationDAO.addNotification(null, userid, "Estimated arival time changed to: " + estimatedDeliveryTime);
                }
                if (prev.delay != delay) {
                    notificationDAO.addNotification(null, userid, "A delay has occoured: " + delay +" days");
                }
                if (!Objects.equals(prev.status, status)) {
                    notificationDAO.addNotification(null, userid, "Status had been changed to:" + status);
                }
            }
            lastSnapshot.put(trackingID, new TrackShipmentProgressSnapshot(estimatedDeliveryTime, delay, status));
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
