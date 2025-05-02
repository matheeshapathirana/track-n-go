package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utility.DBConnection;

//setting up the connection to the db created
public class DeliveryPersonnelDAO {
    //add drivers using sql
    public void addPersonnel(DeliveryPersonnel p) {
        String sql = "INSERT INTO DeliveryPersonnel(personnelName, personnelContact, schedule, assignedRoute, deliveryHistory,availability) VALUES (?, ?, ?, ?, ?,?)";
        try (Connection conn = DBConnection.getConnection(); //opens a connection to the sql database
             PreparedStatement s = conn.prepareStatement(sql)) {
            s.setString(1, p.getPersonnelName());
            s.setString(2, p.getPersonnelContact());
            s.setString(3, p.getSchedule());
            s.setString(4, p.getAssignedRoute());
            s.setString(5, p.getDeliveryHistory());
            s.setString(6, p.getAvailability());
            s.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding personnel: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //updating delivery personnel
    public void updatePersonnel(DeliveryPersonnel p) {
        String sql = "UPDATE DeliveryPersonnel SET personnelName = ?, personnelContact = ?, schedule = ?, assignedRoute = ?, deliveryHistory = ?, availability = ? WHERE personnelID = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement s = conn.prepareStatement(sql))
        {
            s.setString(1, p.getPersonnelName());
            s.setString(2, p.getPersonnelContact());
            s.setString(3, p.getSchedule());
            s.setString(4, p.getAssignedRoute());
            s.setString(5, p.getDeliveryHistory());
            s.setString(6, p.getAvailability());
            s.setInt(7, p.getPersonnelID());
            s.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating personnel: " + e.getMessage());
        }
    }

    //deleting personnel
    public void deletePersonnel(int personnelID) {
        String sql = "DELETE FROM DeliveryPersonnel WHERE personnelID = ?";
        try(Connection conn = DBConnection.getConnection();
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

    //getting all saved records from the database
    public List <DeliveryPersonnel> getAllPersonnel() {
        List<DeliveryPersonnel> list = new ArrayList<>();
        String sql = "SELECT * FROM DeliveryPersonnel";

        try(Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql)){

            while(result.next()){
                DeliveryPersonnel p = new DeliveryPersonnel();
                p.setPersonnelID(result.getInt("personnelID"));
                p.setPersonnelName(result.getString("personnelName"));
                p.setPersonnelContact(result.getString("personnelContact"));
                p.setSchedule(result.getString("schedule"));
                p.setAssignedRoute(result.getString("assignedRoute"));
                p.setDeliveryHistory(result.getString("deliveryHistory"));
                p.setAvailability(result.getString("availability"));

                list.add(p);
            }
        }
        catch(SQLException e)
        {
            System.out.println("Error getting personnel list: " + e.getMessage());
        }
        return list;
    }
}
