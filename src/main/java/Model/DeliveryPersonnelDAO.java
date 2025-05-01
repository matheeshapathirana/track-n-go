package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//setting up the connection to the db created
public class DeliveryPersonnelDAO {
    //opens a connection to the sql database
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://pixel-host.zapto.org:3306/trackngo";
        String user = "user";
        String password = "user2006";
        return DriverManager.getConnection(url, user, password);
    }
    //add drivers using sql
    public void addPersonnel(DeliveryPersonnel p) {
        String sql = "INSERT INTO DeliveryPersonnel(personnelName, personnelContact, schedule, assignedRoute, deliveryHistory) VALUES (?, ?, ?, ?, ?)";
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

    //getting all saved records
    public List <DeliveryPersonnel> getAllPersonnel() {
        List<DeliveryPersonnel> list = new ArrayList<>();
        String sql = "SELECT * FROM DeliveryPersonnel";

        try(Connection conn = getConnection();
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
