package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utility.DBConnection;

//setting up the connection to the db created
public class DeliveryPersonnelDAO {
    //add drivers using sql
    public void addPersonnel(DeliveryPersonnel p) {
        long startTime = System.currentTimeMillis();
        String sql = "INSERT INTO DeliveryPersonnel(personnelName, personnelContact, schedule, assignedRoute, deliveryHistory,availability) VALUES (?, ?, ?, ?, ?,?)";
        try (Connection conn = DBConnection.getConnection(); //opens a connection to the sql database
             PreparedStatement s = conn.prepareStatement(sql)) {
            long connTime = System.currentTimeMillis();
            System.out.println("[DB Timing] addPersonnel - DB connection time: " + (connTime - startTime) + " ms");
            s.setString(1, p.getPersonnelName());
            s.setString(2, p.getPersonnelContact());
            s.setString(3, p.getSchedule());
            s.setString(4, p.getAssignedRoute());
            s.setString(5, p.getDeliveryHistory());
            s.setString(6, p.getAvailability());
            long execStart = System.currentTimeMillis();
            s.executeUpdate();
            long execEnd = System.currentTimeMillis();
            System.out.println("[DB Timing] addPersonnel - Query execution time: " + (execEnd - execStart) + " ms");
        } catch (SQLException e) {
            System.out.println("Error adding personnel: " + e.getMessage());
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("[DB Timing] addPersonnel - Total time: " + (endTime - startTime) + " ms");
    }

    //updating delivery personnel
    public void updatePersonnel(DeliveryPersonnel p) {
        long startTime = System.currentTimeMillis();
        String sql = "UPDATE DeliveryPersonnel SET personnelName = ?, personnelContact = ?, schedule = ?, assignedRoute = ?, deliveryHistory = ?, availability = ? WHERE personnelID = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement s = conn.prepareStatement(sql))
        {
            long connTime = System.currentTimeMillis();
            System.out.println("[DB Timing] updatePersonnel - DB connection time: " + (connTime - startTime) + " ms");
            s.setString(1, p.getPersonnelName());
            s.setString(2, p.getPersonnelContact());
            s.setString(3, p.getSchedule());
            s.setString(4, p.getAssignedRoute());
            s.setString(5, p.getDeliveryHistory());
            s.setString(6, p.getAvailability());
            s.setInt(7, p.getPersonnelID());
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

    //deleting personnel
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

    //getting all saved records from the database
    public List <DeliveryPersonnel> getAllPersonnel() {
        long startTime = System.currentTimeMillis();
        List<DeliveryPersonnel> list = new ArrayList<>();
        String sql = "SELECT * FROM DeliveryPersonnel";

        try(Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql)){
            long connTime = System.currentTimeMillis();
            System.out.println("[DB Timing] getAllPersonnel - DB connection time: " + (connTime - startTime) + " ms");
            long execStart = System.currentTimeMillis();
            while(result.next()){
                DeliveryPersonnel p = new DeliveryPersonnel();
                p.setPersonnelID(result.getInt("personnelID"));
                p.setPersonnelName(result.getString("personnelName"));
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
