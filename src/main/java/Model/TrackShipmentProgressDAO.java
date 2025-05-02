package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utility.DBConnection;

public class TrackShipmentProgressDAO {

    // Add a new shipment progress record
    public void addShipmentProgress(TrackShipmentProgress progress) {
        long startTime = System.currentTimeMillis();
        String sql = "INSERT INTO TrackShipmentProgress (trackingID, shipmentID, currentLocation, estimatedDeliveryTime, delay, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            long connTime = System.currentTimeMillis();
            System.out.println("[DB Timing] addShipmentProgress - DB connection time: " + (connTime - startTime) + " ms");
            s.setInt(1, progress.getTrackingID());
            s.setInt(2, progress.getShipmentID());
            s.setString(3, progress.getCurrentLocation());
            s.setString(4, progress.getEstimatedDeliveryTime());
            s.setInt(5, progress.getDelay());
            s.setString(6, progress.getStatus());
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

    // Update an existing shipment progress record
    public void updateShipmentProgress(TrackShipmentProgress progress) {
        long startTime = System.currentTimeMillis();
        String sql = "UPDATE TrackShipmentProgress SET shipmentID = ?, currentLocation = ?, estimatedDeliveryTime = ?, delay = ?, status = ? WHERE trackingID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            long connTime = System.currentTimeMillis();
            System.out.println("[DB Timing] updateShipmentProgress - DB connection time: " + (connTime - startTime) + " ms");
            s.setInt(1, progress.getShipmentID());
            s.setString(2, progress.getCurrentLocation());
            s.setString(3, progress.getEstimatedDeliveryTime());
            s.setInt(4, progress.getDelay());
            s.setString(5, progress.getStatus());
            s.setInt(6, progress.getTrackingID());
            long execStart = System.currentTimeMillis();
            s.executeUpdate();
            long execEnd = System.currentTimeMillis();
            System.out.println("[DB Timing] updateShipmentProgress - Query execution time: " + (execEnd - execStart) + " ms");
        } catch (SQLException e) {
            System.out.println("Error updating shipment progress: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("[DB Timing] updateShipmentProgress - Total time: " + (endTime - startTime) + " ms");
    }

    // Delete a shipment progress record
    public void deleteShipmentProgress(int trackingID) {
        long startTime = System.currentTimeMillis();
        String sql = "DELETE FROM TrackShipmentProgress WHERE trackingID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            long connTime = System.currentTimeMillis();
            System.out.println("[DB Timing] deleteShipmentProgress - DB connection time: " + (connTime - startTime) + " ms");
            s.setInt(1, trackingID);
            long execStart = System.currentTimeMillis();
            s.executeUpdate();
            long execEnd = System.currentTimeMillis();
            System.out.println("[DB Timing] deleteShipmentProgress - Query execution time: " + (execEnd - execStart) + " ms");
        } catch (SQLException e) {
            System.out.println("Error deleting shipment progress: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("[DB Timing] deleteShipmentProgress - Total time: " + (endTime - startTime) + " ms");
    }

    // Retrieve all shipment progress records
    public List<TrackShipmentProgress> getAllShipmentProgress() {
        long startTime = System.currentTimeMillis();
        List<TrackShipmentProgress> list = new ArrayList<>();
        String sql = "SELECT * FROM TrackShipmentProgress";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            long connTime = System.currentTimeMillis();
            System.out.println("[DB Timing] getAllShipmentProgress - DB connection time: " + (connTime - startTime) + " ms");
            long execStart = System.currentTimeMillis();
            while (result.next()) {
                TrackShipmentProgress progress = new TrackShipmentProgress();
                progress.setTrackingID(result.getInt("trackingID"));
                progress.setShipmentID(result.getInt("shipmentID"));
                progress.setCurrentLocation(result.getString("currentLocation"));
                progress.setEstimatedDeliveryTime(result.getString("estimatedDeliveryTime"));
                progress.setDelay(result.getInt("delay"));
                progress.setStatus(result.getString("status"));
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

    // Retrieve a shipment progress record by trackingID
    public TrackShipmentProgress getShipmentProgressByTrackingId(int trackingID) {
        long startTime = System.currentTimeMillis();
        String sql = "SELECT * FROM TrackShipmentProgress WHERE trackingID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            long connTime = System.currentTimeMillis();
            System.out.println("[DB Timing] getShipmentProgressByTrackingId - DB connection time: " + (connTime - startTime) + " ms");
            s.setInt(1, trackingID);
            long execStart = System.currentTimeMillis();
            try (ResultSet result = s.executeQuery()) {
                long execEnd = System.currentTimeMillis();
                System.out.println("[DB Timing] getShipmentProgressByTrackingId - Query execution time: " + (execEnd - execStart) + " ms");
                if (result.next()) {
                    TrackShipmentProgress progress = new TrackShipmentProgress();
                    progress.setTrackingID(result.getInt("trackingID"));
                    progress.setShipmentID(result.getInt("shipmentID"));
                    progress.setCurrentLocation(result.getString("currentLocation"));
                    progress.setEstimatedDeliveryTime(result.getString("estimatedDeliveryTime"));
                    progress.setDelay(result.getInt("delay"));
                    progress.setStatus(result.getString("status"));
                    long endTime = System.currentTimeMillis();
                    System.out.println("[DB Timing] getShipmentProgressByTrackingId - Total time: " + (endTime - startTime) + " ms");
                    return progress;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving shipment progress: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("[DB Timing] getShipmentProgressByTrackingId - Total time: " + (endTime - startTime) + " ms");
        return null;
    }
}
