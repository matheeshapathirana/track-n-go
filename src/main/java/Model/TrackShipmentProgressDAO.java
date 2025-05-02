package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utility.DBConnection;

public class TrackShipmentProgressDAO {

    // Add a new shipment progress record
    public void addShipmentProgress(TrackShipmentProgress progress) {
        String sql = "INSERT INTO TrackShipmentProgress (shipmentID, currentLocation, estimatedDeliveryTime, delay, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            s.setInt(1, progress.getShipmentID());
            s.setString(2, progress.getCurrentLocation());
            s.setString(3, progress.getEstimatedDeliveryTime());
            s.setInt(4, progress.getDelay());
            s.setString(5, progress.getStatus());
            s.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding shipment progress: " + e.getMessage());
        }
    }

    // Update an existing shipment progress record
    public void updateShipmentProgress(TrackShipmentProgress progress) {
        String sql = "UPDATE TrackShipmentProgress SET currentLocation = ?, estimatedDeliveryTime = ?, delay = ?, status = ? WHERE shipmentID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            s.setString(1, progress.getCurrentLocation());
            s.setString(2, progress.getEstimatedDeliveryTime());
            s.setInt(3, progress.getDelay());
            s.setString(4, progress.getStatus());
            s.setInt(5, progress.getShipmentID());
            s.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating shipment progress: " + e.getMessage());
        }
    }

    // Delete a shipment progress record
    public void deleteShipmentProgress(int shipmentID) {
        String sql = "DELETE FROM TrackShipmentProgress WHERE shipmentID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            s.setInt(1, shipmentID);
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

    // Retrieve a shipment progress record by shipmentID
    public TrackShipmentProgress getShipmentProgressById(int shipmentID) {
        String sql = "SELECT * FROM TrackShipmentProgress WHERE shipmentID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            s.setInt(1, shipmentID);
            try (ResultSet result = s.executeQuery()) {
                if (result.next()) {
                    TrackShipmentProgress progress = new TrackShipmentProgress();
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
