package Model;

import java.sql.Timestamp;

public class Shipments {
    private int shipmentID;
    private String senderName;
    private String receiverName;
    private String shipmentStatus;
    private Integer assignedDriverID;
    private Timestamp createdOn;
    private Timestamp lastUpdated;

    // Constructor
    public Shipments(int shipmentID, String senderName, String receiverName, String shipmentStatus, Integer assignedDriverID, Timestamp createdOn, Timestamp lastUpdated) {
        this.shipmentID = shipmentID;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.shipmentStatus = shipmentStatus;
        this.assignedDriverID = assignedDriverID;
        this.createdOn = createdOn;
        this.lastUpdated = lastUpdated;
    }

    // Constructor for adding new shipments
    public Shipments(String senderName, String receiverName, String shipmentStatus, Integer assignedDriverID) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.shipmentStatus = shipmentStatus;
        this.assignedDriverID = assignedDriverID;
    }

    // Getters
    public int getShipmentID() {
        return shipmentID;
    }
    public String getSenderName() {
        return senderName;
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
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    // Setters
    public void setShipmentStatus(String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }
    public void setAssignedDriverID(Integer assignedDriverID) {
        this.assignedDriverID = assignedDriverID;
    }
}