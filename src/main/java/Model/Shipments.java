package Model;

import java.sql.Timestamp;

public class Shipments {
    private int shipmentID;
    private String receiverName;
    private String shipmentStatus;
    private Integer assignedDriverID;
    private Timestamp createdOn;
    private Integer userid;
    private String estimatedDeliveryTime;
    private Integer urgent;
    private String currentLocation;
    private String delay;

    public Shipments(int shipmentID, String receiverName, String shipmentStatus, Integer assignedDriverID, Timestamp createdOn, Integer userid) {
        this.shipmentID = shipmentID;
        this.receiverName = receiverName;
        this.shipmentStatus = shipmentStatus;
        this.assignedDriverID = assignedDriverID;
        this.createdOn = createdOn;
        this.userid = userid;
    }

    public Shipments(String receiverName, String shipmentStatus, Integer assignedDriverID, Integer userid, String estimatedDeliveryTime) {
        this.receiverName = receiverName;
        this.shipmentStatus = shipmentStatus;
        this.assignedDriverID = assignedDriverID;
        this.userid = userid;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public Shipments(String receiverName, String shipmentStatus, Integer assignedDriverID, Integer userid, String estimatedDeliveryTime, String currentLocation) {
        this.receiverName = receiverName;
        this.shipmentStatus = shipmentStatus;
        this.assignedDriverID = assignedDriverID;
        this.userid = userid;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.currentLocation = currentLocation;
    }

    public Shipments(int shipmentID, String receiverName, String shipmentStatus, Integer assignedDriverID, Timestamp createdOn, Integer userid, Integer urgent, String currentLocation, String estimatedDeliveryTime, String delay) {
        this.shipmentID = shipmentID;
        this.receiverName = receiverName;
        this.shipmentStatus = shipmentStatus;
        this.assignedDriverID = assignedDriverID;
        this.createdOn = createdOn;
        this.userid = userid;
        this.urgent = urgent;
        this.currentLocation = currentLocation;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.delay = delay;
    }

    public int getShipmentID() {
        return shipmentID;
    }
    public String getReceiverName() {
        return receiverName;
    }
    public String getShipmentStatus() {
        return shipmentStatus;
    }
    public Integer getAssignedDriverID() {
        return assignedDriverID;
    }
    public Timestamp getCreatedOn() {
        return createdOn;
    }
    public Integer getUserid() {
        return userid;
    }
    public String getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }
    public Integer getUrgent() { return urgent; }
    public String getCurrentLocation() { return currentLocation; }
    public String getDelay() { return delay; }

    public void setShipmentStatus(String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }
    public void setUserid(Integer userid) {
        this.userid = userid;
    }
    public void setUrgent(Integer urgent) {
        this.urgent = urgent;
    }
}