package Model;

import Utility.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDeliveriesDAO {

    public List<TrackShipmentProgress> getTrackShipmentProgressByUserId(int userId) {
        List<TrackShipmentProgress> progressList = new ArrayList<>();
        String query = "SELECT * FROM Shipments WHERE userid = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TrackShipmentProgress progress = new TrackShipmentProgress();
                progress.setTrackingID(rs.getInt("shipmentID")); // Use shipmentID as trackingID
                progress.setShipmentID(rs.getInt("shipmentID"));
                progress.setCurrentLocation(rs.getString("currentLocation"));
                java.sql.Timestamp estTime = rs.getTimestamp("estimatedDeliveryTime");
                progress.setEstimatedDeliveryTime(estTime != null ? estTime.toString() : null);
                progress.setDelay(rs.getObject("delay") != null ? rs.getInt("delay") : 0);
                progress.setStatus(rs.getString("shipmentStatus"));
                progress.setUserid(rs.getObject("userid") != null ? rs.getInt("userid") : 0);
                progressList.add(progress);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return progressList;
    }
}
