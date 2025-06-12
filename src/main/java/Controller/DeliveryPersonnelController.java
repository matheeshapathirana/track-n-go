package Controller;

import Model.DeliveryPersonnel;
import Model.DeliveryPersonnelDAO;

public class DeliveryPersonnelController {
    private DeliveryPersonnelDAO DAO = new DeliveryPersonnelDAO();
    public void addDeliveryPersonnel(DeliveryPersonnel p) {
        DAO.addPersonnel(p);
    }
    public void updateDeliveryPersonnel(DeliveryPersonnel p) {
        DAO.updatePersonnel(p);
    }
    public void deleteDeliveryPersonnel(DeliveryPersonnel p) {
        DAO.deletePersonnel(p.getPersonnelID());
    }
    public java.util.List<DeliveryPersonnel> getAllPersonnel() {
        return DAO.getAllPersonnel();
    }
}
