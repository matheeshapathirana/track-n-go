package Model;

public class TrackShipmentProgress {
    private int shipmentID;
    private String currentLocation;
    private String estimatedDeliveryTime;
    private String delayReason;
    private String status;

    public TrackShipmentProgress(int shipmentID, String currentLocation, String estimatedDeliveryTime, String delayReason, String status) {
        this.shipmentID = shipmentID;
        this.currentLocation = currentLocation;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.delayReason = delayReason;
        this.status = status;
    }

    public int getShipmentID() {
        return shipmentID;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public String getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public String getDelayReason() {
        return delayReason;
    }

    public String getStatus() {
        return status;
    }

    public void setShipmentID(int shipmentID) {
        this.shipmentID = shipmentID;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public void setDelayReason(String delayReason) {
        this.delayReason = delayReason;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void updateLocation(String newLocation) {
        this.currentLocation = newLocation;
    }

    public void updateEstimatedTime(String newEstimatedTime) {
        this.estimatedDeliveryTime = newEstimatedTime;
    }

    public void recordDelay(String newDelayReason) {
        this.delayReason = newDelayReason;
    }

    public String getShipmentStatus(String newStatus) {
        return "Shipment ID: " + shipmentID + "\n" +
                "Current Location: " + currentLocation + "\n" +
                "Estimated Delivery Time: " + estimatedDeliveryTime + "\n" +
                "Delay Reason: " + delayReason + "\n" +
                "Status: " + status;
    }
}
