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
        String query = "SELECT * FROM TrackShipmentProgress WHERE userid = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TrackShipmentProgress progress = new TrackShipmentProgress();
                progress.setTrackingID(rs.getInt("trackingID"));
                progress.setShipmentID(rs.getInt("shipmentID"));
                progress.setCurrentLocation(rs.getString("currentLocation"));
                progress.setEstimatedDeliveryTime(rs.getTimestamp("estimatedDeliveryTime").toString());
                progress.setDelay(rs.getInt("delay"));
                progress.setStatus(rs.getString("status"));
                progress.setUserid(rs.getInt("userid"));
                progressList.add(progress);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return progressList;
    }
}
