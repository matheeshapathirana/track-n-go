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

    // Constructor
    public Shipments(int shipmentID, String receiverName, String shipmentStatus, Integer assignedDriverID, Timestamp createdOn, Integer userid) {
        this.shipmentID = shipmentID;
        this.receiverName = receiverName;
        this.shipmentStatus = shipmentStatus;
        this.assignedDriverID = assignedDriverID;
        this.createdOn = createdOn;
        this.userid = userid;
    }

    // Constructor for adding new shipments
    public Shipments(String receiverName, String shipmentStatus, Integer assignedDriverID, Integer userid, String estimatedDeliveryTime) {
        this.receiverName = receiverName;
        this.shipmentStatus = shipmentStatus;
        this.assignedDriverID = assignedDriverID;
        this.userid = userid;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    // Getters
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

    // Setters
    public void setShipmentStatus(String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }
    public void setAssignedDriverID(Integer assignedDriverID) {
        this.assignedDriverID = assignedDriverID;
    }
    public void setUserid(Integer userid) {
        this.userid = userid;
    }
    public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }
}