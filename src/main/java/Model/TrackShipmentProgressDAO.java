package Model;

import Utility.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrackShipmentProgressDAO {
    public void addShipmentProgress(TrackShipmentProgress progress) {
        long startTime = System.currentTimeMillis();
        String sql = "UPDATE Shipments SET currentLocation = ?, estimatedDeliveryTime = ?, delay = ?, shipmentStatus = ? WHERE shipmentID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            long connTime = System.currentTimeMillis();
            System.out.println("[DB Timing] addShipmentProgress - DB connection time: " + (connTime - startTime) + " ms");
            s.setString(1, progress.getCurrentLocation());
            s.setString(2, progress.getEstimatedDeliveryTime());
            s.setInt(3, progress.getDelay());
            s.setString(4, progress.getStatus());
            s.setInt(5, progress.getShipmentID());
            long execStart = System.currentTimeMillis();
            s.executeUpdate();
            long execEnd = System.currentTimeMillis();
            System.out.println("[DB Timing] addShipmentProgress - Query execution time: " + (execEnd - execStart) + " ms");
        } catch (SQLException e) {
            System.out.println("Error adding shipment progress: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("[DB Timing] addShipmentProgress - Total time: " + (endTime - startTime) + " ms");
    }

    public void updateShipmentProgress(TrackShipmentProgress progress) {
        addShipmentProgress(progress);
    }

    public List<TrackShipmentProgress> getAllShipmentProgress() {
        long startTime = System.currentTimeMillis();
        List<TrackShipmentProgress> list = new ArrayList<>();
        String sql = "SELECT * FROM Shipments";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            long connTime = System.currentTimeMillis();
            System.out.println("[DB Timing] getAllShipmentProgress - DB connection time: " + (connTime - startTime) + " ms");
            long execStart = System.currentTimeMillis();
            while (result.next()) {
                TrackShipmentProgress progress = new TrackShipmentProgress();
                progress.setTrackingID(result.getInt("shipmentID")); // Use shipmentID as trackingID
                progress.setShipmentID(result.getInt("shipmentID"));
                progress.setCurrentLocation(result.getString("currentLocation"));
                Timestamp estTime = result.getTimestamp("estimatedDeliveryTime");
                progress.setEstimatedDeliveryTime(estTime != null ? estTime.toString() : null);
                progress.setDelay(result.getObject("delay") != null ? result.getInt("delay") : 0);
                progress.setStatus(result.getString("shipmentStatus"));
                progress.setUserid(result.getObject("userid") != null ? result.getInt("userid") : 0);
                list.add(progress);
            }
            long execEnd = System.currentTimeMillis();
            System.out.println("[DB Timing] getAllShipmentProgress - Query execution time: " + (execEnd - execStart) + " ms");
        } catch (SQLException e) {
            System.out.println("Error retrieving shipment progress list: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("[DB Timing] getAllShipmentProgress - Total time: " + (endTime - startTime) + " ms");
        return list;
    }
}
