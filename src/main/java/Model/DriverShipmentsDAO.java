package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Utility.DBConnection;

public class DriverShipmentsDAO {
    public List<Shipments> getShipmentsByDriverId(int driverId) {
        List<Shipments> shipments = new ArrayList<>();
        String query = "SELECT * FROM Shipments WHERE assignedDriverID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, driverId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Shipments shipment = new Shipments(
                    rs.getInt("shipmentID"),
                    rs.getString("receiverName"),
                    rs.getString("shipmentStatus"),
                    rs.getObject("assignedDriverID") != null ? rs.getInt("assignedDriverID") : null,
                    rs.getTimestamp("createdOn"),
                    rs.getObject("userid") != null ? rs.getInt("userid") : null,
                    rs.getObject("urgent") != null ? rs.getInt("urgent") : null,
                    rs.getString("currentLocation"),
                    rs.getString("estimatedDeliveryTime"),
                    rs.getObject("delay") != null ? String.valueOf(rs.getObject("delay")) : null
                );
                shipments.add(shipment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shipments;
    }
}
