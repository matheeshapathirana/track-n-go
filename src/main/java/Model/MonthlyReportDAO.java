package Model;

import Model.MonthlyReport;
import Utility.DBConnection;

import java.sql.*;

public class MonthlyReportDAO {
    private Connection conn;

    public MonthlyReportDAO() {
        this.conn = DBConnection.getConnection();
    }

    public MonthlyReport generateReport(int year, String month) {
        MonthlyReport report = new MonthlyReport();

        try {
            // Total Deliveries (Delivered status)
            String sqlDeliveries = "SELECT COUNT(*) FROM Shipments WHERE YEAR(createdOn) = ? AND MONTHNAME(createdOn) = ? AND shipmentStatus = 'Delivered'";
            try (PreparedStatement stmt = conn.prepareStatement(sqlDeliveries)) {
                stmt.setInt(1, year);
                stmt.setString(2, month);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) report.setTotalDeliveries(rs.getInt(1));
            }

            // Delayed Deliveries (Delayed status from TrackShipmentProgress)
            String sqlDelayed = "SELECT COUNT(*) FROM TrackShipmentProgress tsp JOIN Shipments s ON tsp.shipmentID = s.shipmentID WHERE YEAR(s.createdOn) = ? AND MONTHNAME(s.createdOn) = ? AND tsp.status = 'Delayed'";
            try (PreparedStatement stmt = conn.prepareStatement(sqlDelayed)) {
                stmt.setInt(1, year);
                stmt.setString(2, month);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) report.setDelayedDeliveries(rs.getInt(1));
            }

            // Average Rating (skip if no customer_feedback table)
            // report.setAverageRating(0.0); // Set to 0 or fetch if table exists

            // Total Shipments (all statuses)
            String sqlShipments = "SELECT COUNT(*) FROM Shipments WHERE YEAR(createdOn) = ? AND MONTHNAME(createdOn) = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlShipments)) {
                stmt.setInt(1, year);
                stmt.setString(2, month);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) report.setTotalShipments(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }
}