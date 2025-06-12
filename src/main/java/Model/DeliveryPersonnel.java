package Model;

public class DeliveryPersonnel {
    private int personnelID;
    private String personnelName;
    private String personnelContact;
    private String schedule;
    private String assignedRoute;
    private String deliveryHistory;
    private String availability;
    private String createdOn;
    private String lastUpdated;

    //constructor
    public DeliveryPersonnel(int personnelID, String personnelName , String personnelContact, String schedule, String assignedRoute, String deliveryHistory, String availability, String createdOn, String lastUpdated) {
        this.personnelID = personnelID;
        this.personnelName = personnelName;
        this.personnelContact = personnelContact;
        this.schedule = schedule;
        this.assignedRoute = assignedRoute;
        this.deliveryHistory = deliveryHistory;
        this.availability = availability;
        this.createdOn = createdOn;
        this.lastUpdated = lastUpdated;
    }

    //overloaded constructor for backward compatibility and DAO usage
    public DeliveryPersonnel(int personnelID, String personnelName, String personnelContact, String schedule, String assignedRoute, String deliveryHistory, String availability) {
        this.personnelID = personnelID;
        this.personnelName = personnelName;
        this.personnelContact = personnelContact;
        this.schedule = schedule;
        this.assignedRoute = assignedRoute;
        this.deliveryHistory = deliveryHistory;
        this.availability = availability;
    }

    //default constructor for delete in views
    public DeliveryPersonnel() {
    }

    //setters and getters
    public int getPersonnelID() {
        return personnelID;
    }
    public String getPersonnelName() {
        return personnelName;
    }
    public String getPersonnelContact() {
        return personnelContact;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getAssignedRoute() {
        return assignedRoute;
    }

    public String getDeliveryHistory() {
        return deliveryHistory;
    }

    public String getAvailability() {
        return availability;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    //setters
    public void setPersonnelID(int personnelID) {
        this.personnelID = personnelID;
    }
    public void setPersonnelName(String personnelName) {
        this.personnelName = personnelName;
    }
    public void setPersonnelContact(String personnelContact) {
        this.personnelContact = personnelContact;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public void setAssignedRoute(String assignedRoute) {
        this.assignedRoute = assignedRoute;
    }

    public void setDeliveryHistory(String deliveryHistory) {
        this.deliveryHistory = deliveryHistory;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    //to string for table in view

    @Override
    public String toString() {
        return "DeliveryPersonnel{" +
                "personnelID=" + personnelID +
                ", personnelName='" + personnelName + '\'' +
                ", personnelContact='" + personnelContact + '\'' +
                ", schedule='" + schedule + '\'' +
                ", assignedRoute='" + assignedRoute + '\'' +
                ", deliveryHistory='" + deliveryHistory + '\'' +
                ", availability='" + availability + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                '}';
    }
}

