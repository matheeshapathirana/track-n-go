package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utility.DBConnection;

public class TrackShipmentProgressDAO {

    // Add a new shipment progress record
    public void addShipmentProgress(TrackShipmentProgress progress) {
        String sql = "INSERT INTO TrackShipmentProgress (trackingID, shipmentID, currentLocation, estimatedDeliveryTime, delay, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            s.setInt(1, progress.getTrackingID());
            s.setInt(2, progress.getShipmentID());
            s.setString(3, progress.getCurrentLocation());
            s.setString(4, progress.getEstimatedDeliveryTime());
            s.setInt(5, progress.getDelay());
            s.setString(6, progress.getStatus());
            s.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding shipment progress: " + e.getMessage());
        }
    }

    // Update an existing shipment progress record
    public void updateShipmentProgress(TrackShipmentProgress progress) {
        String sql = "UPDATE TrackShipmentProgress SET shipmentID = ?, currentLocation = ?, estimatedDeliveryTime = ?, delay = ?, status = ? WHERE trackingID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            s.setInt(1, progress.getShipmentID());
            s.setString(2, progress.getCurrentLocation());
            s.setString(3, progress.getEstimatedDeliveryTime());
            s.setInt(4, progress.getDelay());
            s.setString(5, progress.getStatus());
            s.setInt(6, progress.getTrackingID());
            s.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating shipment progress: " + e.getMessage());
        }
    }

    // Delete a shipment progress record
    public void deleteShipmentProgress(int trackingID) {
        String sql = "DELETE FROM TrackShipmentProgress WHERE trackingID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            s.setInt(1, trackingID);
            s.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting shipment progress: " + e.getMessage());
        }
    }

    // Retrieve all shipment progress records
    public List<TrackShipmentProgress> getAllShipmentProgress() {
        List<TrackShipmentProgress> list = new ArrayList<>();
        String sql = "SELECT * FROM TrackShipmentProgress";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
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
        } catch (SQLException e) {
            System.out.println("Error retrieving shipment progress list: " + e.getMessage());
        }
        return list;
    }

    // Retrieve a shipment progress record by trackingID
    public TrackShipmentProgress getShipmentProgressByTrackingId(int trackingID) {
        String sql = "SELECT * FROM TrackShipmentProgress WHERE trackingID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            s.setInt(1, trackingID);
            try (ResultSet result = s.executeQuery()) {
                if (result.next()) {
                    TrackShipmentProgress progress = new TrackShipmentProgress();
                    progress.setTrackingID(result.getInt("trackingID"));
                    progress.setShipmentID(result.getInt("shipmentID"));
                    progress.setCurrentLocation(result.getString("currentLocation"));
                    progress.setEstimatedDeliveryTime(result.getString("estimatedDeliveryTime"));
                    progress.setDelay(result.getInt("delay"));
                    progress.setStatus(result.getString("status"));
                    return progress;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving shipment progress: " + e.getMessage());
        }
        return null;
    }
}
