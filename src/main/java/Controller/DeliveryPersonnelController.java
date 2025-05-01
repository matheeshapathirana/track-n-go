package Controller;

import Model.DeliveryPersonnel;
import Model.DeliveryPersonnelDAO;

public class DeliveryPersonnelController {
    private DeliveryPersonnelDAO DAO = new DeliveryPersonnelDAO();
    //to add the personal controller using the code used in DAO package
    public void addDeliveryPersonnel(DeliveryPersonnel p) {
        DAO.addPersonnel(p);
    }
    //to update personnel
    public void updateDeliveryPersonnel(DeliveryPersonnel p) {
        DAO.updatePersonnel(p);
    }
    //to delete personnel
    public void deleteDeliveryPersonnel(DeliveryPersonnel p) {
        DAO.deletePersonnel(p.getPersonnelID());
    }

}
