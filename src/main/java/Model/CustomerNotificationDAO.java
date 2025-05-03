package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerNotificationDAO {
    private Connection conn;

    public CustomerNotificationDAO() {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://192.9.163.129:3306/trackngo",
                    "user",
                    "user2006"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CustomerNotification> getNotificationsByCustomerID(int customerID) {
        List<CustomerNotification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM customer_notifications WHERE customer_id = ? ORDER BY timestamp DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CustomerNotification notification = new CustomerNotification(
                        rs.getInt("notification_id"),
                        rs.getInt("customer_id"),
                        rs.getString("message"),
                        rs.getString("timestamp")
                );
                notifications.add(notification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notifications;
    }

    public void addNotification(int customerId, String message) {
        String sql = "INSERT INTO customer_notifications (customer_id, message) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.setString(2, message);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

