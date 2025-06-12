package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utility.DBConnection;

public class DeliveryPersonnelDAO {
    public void addPersonnel(DeliveryPersonnel p) {
        long startTime = System.currentTimeMillis();
        String sql = "INSERT INTO DeliveryPersonnel(personnelID, personnelName, personnelContact, schedule, assignedRoute, deliveryHistory, availability) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getPersonnelID());
            stmt.setString(2, p.getPersonnelName());
            stmt.setString(3, p.getPersonnelContact());
            stmt.setString(4, p.getSchedule());
            stmt.setString(5, p.getAssignedRoute());
            stmt.setString(6, p.getDeliveryHistory());
            stmt.setString(7, p.getAvailability());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding personnel: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("[DB Timing] addPersonnel - Total time: " + (endTime - startTime) + " ms");
    }

    public void updatePersonnel(DeliveryPersonnel p) {
        long startTime = System.currentTimeMillis();
        String sql = "UPDATE DeliveryPersonnel SET personnelContact = ?, schedule = ?, assignedRoute = ?, deliveryHistory = ?, availability = ? WHERE personnelID = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement s = conn.prepareStatement(sql))
        {
            long connTime = System.currentTimeMillis();
            System.out.println("[DB Timing] updatePersonnel - DB connection time: " + (connTime - startTime) + " ms");
            s.setString(2, p.getPersonnelName());
            s.setString(1, p.getPersonnelContact());
            s.setString(2, p.getSchedule());
            s.setString(3, p.getAssignedRoute());
            s.setString(4, p.getDeliveryHistory());
            s.setString(5, p.getAvailability());
            s.setInt(6, p.getPersonnelID());
            long execStart = System.currentTimeMillis();
            s.executeUpdate();
            long execEnd = System.currentTimeMillis();
            System.out.println("[DB Timing] updatePersonnel - Query execution time: " + (execEnd - execStart) + " ms");
        } catch (SQLException e) {
            System.out.println("Error updating personnel: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("[DB Timing] updatePersonnel - Total time: " + (endTime - startTime) + " ms");
    }

    public void deletePersonnel(int personnelID) {
        long startTime = System.currentTimeMillis();
        String sql = "DELETE FROM DeliveryPersonnel WHERE personnelID = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement s = conn.prepareStatement(sql))
        {
            long connTime = System.currentTimeMillis();
            System.out.println("[DB Timing] deletePersonnel - DB connection time: " + (connTime - startTime) + " ms");
            s.setInt(1, personnelID);
            long execStart = System.currentTimeMillis();
            s.executeUpdate();
            long execEnd = System.currentTimeMillis();
            System.out.println("[DB Timing] deletePersonnel - Query execution time: " + (execEnd - execStart) + " ms");
        } catch (SQLException e) {
            System.out.println("Error deleting personnel: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("[DB Timing] deletePersonnel - Total time: " + (endTime - startTime) + " ms");
    }

    public List <DeliveryPersonnel> getAllPersonnel() {
        long startTime = System.currentTimeMillis();
        List<DeliveryPersonnel> list = new ArrayList<>();
        String sql = "SELECT dp.*, u.username FROM DeliveryPersonnel dp JOIN Users u ON dp.personnelID = u.userID";

        try(Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql)){
            long connTime = System.currentTimeMillis();
            System.out.println("[DB Timing] getAllPersonnel - DB connection time: " + (connTime - startTime) + " ms");
            long execStart = System.currentTimeMillis();
            while(result.next()){
                DeliveryPersonnel p = new DeliveryPersonnel();
                p.setPersonnelID(result.getInt("personnelID"));
                p.setPersonnelName(result.getString("username"));
                p.setPersonnelContact(result.getString("personnelContact"));
                p.setSchedule(result.getString("schedule"));
                p.setAssignedRoute(result.getString("assignedRoute"));
                p.setDeliveryHistory(result.getString("deliveryHistory"));
                p.setAvailability(result.getString("availability"));
                p.setCreatedOn(result.getString("createdOn"));
                p.setLastUpdated(result.getString("lastUpdated"));
                list.add(p);
            }
            long execEnd = System.currentTimeMillis();
            System.out.println("[DB Timing] getAllPersonnel - Query execution time: " + (execEnd - execStart) + " ms");
        }
        catch(SQLException e)
        {
            System.out.println("Error getting personnel list: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("[DB Timing] getAllPersonnel - Total time: " + (endTime - startTime) + " ms");
        return list;
    }
}
