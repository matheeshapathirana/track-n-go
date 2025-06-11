package Controller;

import Model.Shipments;
import Model.ShipmentsDAO;
import java.util.List;

public class ShipmentsController {
    private ShipmentsDAO shipmentsDAO;

    public ShipmentsController() {
        this.shipmentsDAO = new ShipmentsDAO();
    }

    public void addShipment(String receiverName, String status, Integer driverID, Integer userid, String estimatedDeliveryTime) {
        Shipments shipment = new Shipments(receiverName, status, driverID, userid, estimatedDeliveryTime);
        shipmentsDAO.addShipment(shipment);
    }

    public void updateShipment(int shipmentID, String receiverName, String status, Integer driverID, Integer userid) {
        Shipments shipment = new Shipments(shipmentID, receiverName, status, driverID, null, userid);
        shipmentsDAO.updateShipment(shipment);
    }

    public void deleteShipment(int shipmentID) {
        shipmentsDAO.deleteShipment(shipmentID);
    }

    public List<Shipments> getAllShipments() {
        return shipmentsDAO.getAllShipments();
    }

    public List<Shipments> getShipmentsByStatus(String status) {
        return shipmentsDAO.getShipmentsByStatus(status);
    }
}