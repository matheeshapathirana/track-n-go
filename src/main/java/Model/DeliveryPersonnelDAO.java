package Model;

import java.sql.*;

//setting up the connection to the db created
public class DeliveryPersonnelDAO {
    //opens a connection to the sql database
    private Connection getConnection() throws SQLException {
        String password = "user2006";
        String url = "jdbc:mysql://pixel-host.zapto.org:3306/trackngo";
        String user = "user";
        return DriverManager.getConnection(url, user, password);
    }
    //add drivers using sql
    public void addPersonnel(DeliveryPersonnel p) {
        String sql = "INSERT INTO DeliveryPersonnel(personnelName, personnelContact, schedule, assignedRoute, deliveryHistory) VALUES (?,?,?,?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            s.setString(1, p.getPersonnelName());
            s.setString(2, p.getPersonnelContact());
            s.setString(3, p.getSchedule());
            s.setString(4, p.getAssignedRoute());
            s.setString(5, p.getDeliveryHistory());
            s.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding personnel: " + e.getMessage());
        }
    }

    //updating delivery personnel
    public void updatePersonnel(DeliveryPersonnel p) {
        String sql = "UPDATE DeliveryPersonnel SET personnelName = ?, personnelContact = ?, schedule = ?, assignedRoute = ?, deliveryHistory = ? WHERE personnelID = ?";
        try(Connection conn = getConnection();
            PreparedStatement s = conn.prepareStatement(sql))
        {
            s.setString(1, p.getPersonnelName());
            s.setString(2, p.getPersonnelContact());
            s.setString(3, p.getSchedule());
            s.setString(4, p.getAssignedRoute());
            s.setString(5, p.getDeliveryHistory());
            s.setInt(6, p.getPersonnelID());
            s.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating personnel: " + e.getMessage());
        }
    }

    //deleting personnel
    public void deletePersonnel(int personnelID) {
        String sql = "DELETE FROM DeliveryPersonnel WHERE personnelID = ?";
        try(Connection conn = getConnection();
            PreparedStatement s = conn.prepareStatement(sql))
        {
            s.setInt(1, personnelID);
            s.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error deleting personnel: " + e.getMessage());
        }

    }
}
