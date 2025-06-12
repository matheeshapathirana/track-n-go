package Model;

import Utility.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShipmentsDAO {
    public void addShipment(Shipments shipment) {
        long startTime = System.currentTimeMillis();
        String sql = "INSERT INTO Shipments (receiverName, shipmentStatus, assignedDriverID, userid, estimatedDeliveryTime) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            long connTime = System.currentTimeMillis();
            System.out.println("[DB Timing] addShipment - DB connection time: " + (connTime - startTime) + " ms");

            s.setString(1, shipment.getReceiverName());
            s.setString(2, shipment.getShipmentStatus());
            if (shipment.getAssignedDriverID() != null) {
                s.setInt(3, shipment.getAssignedDriverID());
            } else {
                s.setNull(3, Types.INTEGER);
            }
            if (shipment.getUserid() != null) {
                s.setInt(4, shipment.getUserid());
            } else {
                s.setNull(4, Types.INTEGER);
            }
            s.setString(5, shipment.getEstimatedDeliveryTime());

            long execStart = System.currentTimeMillis();
            s.executeUpdate();
            long execEnd = System.currentTimeMillis();
            System.out.println("[DB Timing] addShipment - Query execution time: " + (execEnd - execStart) + " ms");
        } catch (SQLException e) {
            System.out.println("Error adding shipment: " + e.getMessage());
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("[DB Timing] addShipment - Total time: " + (endTime - startTime) + " ms");
    }

    public void updateShipment(Shipments shipment) {
        long startTime = System.currentTimeMillis();
        String sql = "UPDATE Shipments SET receiverName = ?, shipmentStatus = ?, assignedDriverID = ?, userid = ? WHERE shipmentID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            long connTime = System.currentTimeMillis();
            System.out.println("[DB Timing] updateShipment - DB connection time: " + (connTime - startTime) + " ms");

            s.setString(1, shipment.getReceiverName());
            s.setString(2, shipment.getShipmentStatus());
            if (shipment.getAssignedDriverID() != null) {
                s.setInt(3, shipment.getAssignedDriverID());
            } else {
                s.setNull(3, Types.INTEGER);
            }
            if (shipment.getUserid() != null) {
                s.setInt(4, shipment.getUserid());
            } else {
                s.setNull(4, Types.INTEGER);
            }
            s.setInt(5, shipment.getShipmentID());

            long execStart = System.currentTimeMillis();
            s.executeUpdate();
            long execEnd = System.currentTimeMillis();
            System.out.println("[DB Timing] updateShipment - Query execution time: " + (execEnd - execStart) + " ms");
        } catch (SQLException e) {
            System.out.println("Error updating shipment: " + e.getMessage());
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("[DB Timing] updateShipment - Total time: " + (endTime - startTime) + " ms");
    }

    public void deleteShipment(int shipmentID) {
        long startTime = System.currentTimeMillis();
        String sql = "DELETE FROM Shipments WHERE shipmentID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            long connTime = System.currentTimeMillis();
            System.out.println("[DB Timing] deleteShipment - DB connection time: " + (connTime - startTime) + " ms");

            s.setInt(1, shipmentID);

            long execStart = System.currentTimeMillis();
            s.executeUpdate();
            long execEnd = System.currentTimeMillis();
            System.out.println("[DB Timing] deleteShipment - Query execution time: " + (execEnd - execStart) + " ms");
        } catch (SQLException e) {
            System.out.println("Error deleting shipment: " + e.getMessage());
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("[DB Timing] deleteShipment - Total time: " + (endTime - startTime) + " ms");
    }

    public List<Shipments> getAllShipments() {
        long startTime = System.currentTimeMillis();
        List<Shipments> list = new ArrayList<>();
        String sql = "SELECT * FROM Shipments";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            long connTime = System.currentTimeMillis();
            System.out.println("[DB Timing] getAllShipments - DB connection time: " + (connTime - startTime) + " ms");

            long execStart = System.currentTimeMillis();
            while (result.next()) {
                Shipments shipment = new Shipments(
                        result.getInt("shipmentID"),
                        result.getString("receiverName"),
                        result.getString("shipmentStatus"),
                        result.getObject("assignedDriverID") != null ? result.getInt("assignedDriverID") : null,
                        result.getTimestamp("createdOn"),
                        result.getObject("userid") != null ? result.getInt("userid") : null,
                        result.getObject("urgent") != null ? result.getInt("urgent") : null,
                        result.getString("currentLocation"),
                        result.getString("estimatedDeliveryTime"),
                        result.getObject("delay") != null ? String.valueOf(result.getObject("delay")) : null
                );
                list.add(shipment);
            }
            long execEnd = System.currentTimeMillis();
            System.out.println("[DB Timing] getAllShipments - Query execution time: " + (execEnd - execStart) + " ms");
        } catch (SQLException e) {
            System.out.println("Error getting shipments list: " + e.getMessage());
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("[DB Timing] getAllShipments - Total time: " + (endTime - startTime) + " ms");
        return list;
    }

    public List<Shipments> getShipmentsByStatus(String status) {
        long startTime = System.currentTimeMillis();
        List<Shipments> list = new ArrayList<>();
        String sql = "SELECT * FROM Shipments WHERE shipmentStatus = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            long connTime = System.currentTimeMillis();
            System.out.println("[DB Timing] getShipmentsByStatus - DB connection time: " + (connTime - startTime) + " ms");

            s.setString(1, status);
            ResultSet result = s.executeQuery();

            long execStart = System.currentTimeMillis();
            while (result.next()) {
                Shipments shipment = new Shipments(
                        result.getInt("shipmentID"),
                        result.getString("receiverName"),
                        result.getString("shipmentStatus"),
                        result.getObject("assignedDriverID") != null ? result.getInt("assignedDriverID") : null,
                        result.getTimestamp("createdOn"),
                        result.getObject("userid") != null ? result.getInt("userid") : null,
                        result.getObject("urgent") != null ? result.getInt("urgent") : null,
                        result.getString("currentLocation"),
                        result.getString("estimatedDeliveryTime"),
                        result.getObject("delay") != null ? String.valueOf(result.getObject("delay")) : null
                );
                list.add(shipment);
            }
            long execEnd = System.currentTimeMillis();
            System.out.println("[DB Timing] getShipmentsByStatus - Query execution time: " + (execEnd - execStart) + " ms");
        } catch (SQLException e) {
            System.out.println("Error getting shipments by status: " + e.getMessage());
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("[DB Timing] getShipmentsByStatus - Total time: " + (endTime - startTime) + " ms");
        return list;
    }

    public void updateShipmentFields(int shipmentID, String currentLocation, String estimatedDeliveryTime, String delay, int urgent) {
        long startTime = System.currentTimeMillis();
        String sql = "UPDATE Shipments SET currentLocation = ?, estimatedDeliveryTime = ?, delay = ?, urgent = ? WHERE shipmentID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            s.setString(1, currentLocation);
            s.setString(2, estimatedDeliveryTime);
            s.setString(3, delay);
            s.setInt(4, urgent);
            s.setInt(5, shipmentID);
            s.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating shipment fields: " + e.getMessage());
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("[DB Timing] updateShipmentFields - Total time: " + (endTime - startTime) + " ms");
    }
}