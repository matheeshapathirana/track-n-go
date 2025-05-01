package Controller;

import Model.TrackShipmentProgress;
import Model.TrackShipmentProgressDAO;

public class TrackShipmentProgressController {
    private TrackShipmentProgressDAO dao;

    public TrackShipmentProgressController() {
        dao = new TrackShipmentProgressDAO();
    }

    public void updateShipmentProgress(String shipmentID, String location, String deliveryTime, String delay, String status) {
        try {
            TrackShipmentProgress progress = new TrackShipmentProgress();
            progress.setShipmentID(Integer.parseInt(shipmentID));
            progress.setCurrentLocation(location);
            progress.setEstimatedDeliveryTime(deliveryTime);
            progress.setDelay(Integer.parseInt(delay));
            progress.setStatus(status);
            dao.updateShipmentProgress(progress);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for shipmentID or delay: " + e.getMessage());
        }
    }
}
