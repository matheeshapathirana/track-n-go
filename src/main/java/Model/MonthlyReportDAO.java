package model;

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
            // Total Deliveries
            String sqlDeliveries = "SELECT COUNT(*) FROM shipments WHERE YEAR(delivery_date) = ? AND MONTHNAME(delivery_date) = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlDeliveries)) {
                stmt.setInt(1, year);
                stmt.setString(2, month);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) report.setTotalDeliveries(rs.getInt(1));
            }

            // Delayed Deliveries
            String sqlDelayed = "SELECT COUNT(*) FROM shipments WHERE YEAR(delivery_date) = ? AND MONTHNAME(delivery_date) = ? AND is_delayed = true";
            try (PreparedStatement stmt = conn.prepareStatement(sqlDelayed)) {
                stmt.setInt(1, year);
                stmt.setString(2, month);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) report.setDelayedDeliveries(rs.getInt(1));
            }

            // Average Rating
            String sqlRating = "SELECT AVG(rating) FROM customer_feedback WHERE YEAR(feedback_date) = ? AND MONTHNAME(feedback_date) = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlRating)) {
                stmt.setInt(1, year);
                stmt.setString(2, month);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) report.setAverageRating(rs.getDouble(1));
            }

            // Total Shipments
            String sqlShipments = "SELECT COUNT(*) FROM shipments WHERE YEAR(delivery_date) = ? AND MONTHNAME(delivery_date) = ?";
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