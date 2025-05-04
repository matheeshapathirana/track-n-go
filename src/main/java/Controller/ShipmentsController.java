package Controller;

import Model.Shipments;
import Model.ShipmentsDAO;
import java.util.List;

public class ShipmentsController {
    private ShipmentsDAO shipmentsDAO;

    public ShipmentsController() {
        this.shipmentsDAO = new ShipmentsDAO();
    }

    public void addShipment(String senderName, String receiverName, String status, Integer driverID) {
        Shipments shipment = new Shipments(senderName, receiverName, status, driverID);
        shipmentsDAO.addShipment(shipment);
    }

    public void updateShipment(int shipmentID, String senderName, String receiverName, String status, Integer driverID) {
        Shipments shipment = new Shipments(shipmentID, senderName, receiverName, status, driverID, null, null);
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