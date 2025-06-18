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

    public void addShipment(String receiverName, String status, Integer driverID, Integer userid, String estimatedDeliveryTime, int urgent, String currentLocation) {
        Shipments shipment = new Shipments(receiverName, status, driverID, userid, estimatedDeliveryTime, currentLocation);
        shipment.setUrgent(urgent);
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

    public void updateShipmentStatusAndFields(int shipmentID, String currentLocation, String estimatedDeliveryTime, String delay, int urgent, String status) {
        shipmentsDAO.updateShipmentFields(shipmentID, currentLocation, estimatedDeliveryTime, delay, urgent);
        Shipments shipment = null;
        for (Shipments s : getAllShipments()) {
            if (s.getShipmentID() == shipmentID) {
                shipment = s;
                break;
            }
        }
        if (shipment != null && status != null && !status.isEmpty()) {
            shipment.setShipmentStatus(status);
            shipmentsDAO.updateShipment(shipment);
        }
    }
}