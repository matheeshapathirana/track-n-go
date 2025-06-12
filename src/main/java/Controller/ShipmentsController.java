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

    public void addShipment(String receiverName, String status, Integer driverID, Integer userid, String estimatedDeliveryTime, int urgent) {
        Shipments shipment = new Shipments(receiverName, status, driverID, userid, estimatedDeliveryTime);
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

    public List<Shipments> getShipmentsByStatus(String status) {
        return shipmentsDAO.getShipmentsByStatus(status);
    }

    public void updateShipmentFields(int shipmentID, String currentLocation, String estimatedDeliveryTime, String delay, int urgent) {
        Model.Shipments shipment = null;
        for (Model.Shipments s : getAllShipments()) {
            if (s.getShipmentID() == shipmentID) {
                shipment = s;
                break;
            }
        }
        Integer userId = (shipment != null) ? shipment.getUserid() : null;
        Integer driverId = (shipment != null) ? shipment.getAssignedDriverID() : null;
        shipmentsDAO.updateShipmentFields(shipmentID, currentLocation, estimatedDeliveryTime, delay, urgent);
        if (userId != null) {
            Model.CustomerNotificationDAO notificationDAO = new Model.CustomerNotificationDAO();
            String message = "Your shipment (ID: " + shipmentID + ") has been updated by the driver.";
            notificationDAO.addNotification("user", userId, message);
        }
        if (driverId != null) {
        }
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