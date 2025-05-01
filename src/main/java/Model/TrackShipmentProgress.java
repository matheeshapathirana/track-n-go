package Model;

public class TrackShipmentProgress {
    private int shipmentID;
    private String currentLocation;
    private String estimatedDeliveryTime;
    private Integer delays;
    private String status;

    public TrackShipmentProgress(int shipmentID, String currentLocation, String estimatedDeliveryTime, Integer delays, String status) {
        this.shipmentID = shipmentID;
        this.currentLocation = currentLocation;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.delays = delays;
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

    public Integer getDelays() {
        return delays;
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

    public void setDelays(Integer delays) {
        this.delays = delays;
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

    public void recordDelay(Integer newDelay) {
        this.delays = newDelay;
    }

    public String getShipmentStatus(String newStatus) {
        return "Shipment ID: " + shipmentID + "\n" +
                "Current Location: " + currentLocation + "\n" +
                "Estimated Delivery Time: " + estimatedDeliveryTime + "\n" +
                "Delay Reason: " + delays + "\n" +
                "Status: " + status;
    }
}
