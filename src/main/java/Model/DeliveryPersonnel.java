package Model;

public class DeliveryPersonnel {
    private int personnelID;
    private String personnelName;
    private String personnelContact;
    private String schedule;
    private String assignedRoute;
    private String deliveryHistory;
    private String availability;

    //constructor
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

    public void setPersonnelID(int personnelID) {
        this.personnelID = personnelID;
    }

    public String getPersonnelName() {
        return personnelName;
    }

    public void setPersonnelName(String personnelName) {
        this.personnelName = personnelName;
    }

    public String getPersonnelContact() {
        return personnelContact;
    }

    public void setPersonnelContact(String personnelContact) {
        this.personnelContact = personnelContact;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getAssignedRoute() {
        return assignedRoute;
    }

    public void setAssignedRoute(String assignedRoute) {
        this.assignedRoute = assignedRoute;
    }

    public String getDeliveryHistory() {
        return deliveryHistory;
    }

    public void setDeliveryHistory(String deliveryHistory) {
        this.deliveryHistory = deliveryHistory;
    }
    public String getAvailability() {
        return availability;
    }
    public void setAvailability(String availability) {
        this.availability = availability;
    }

    //to string for table in view
    @Override
    public String toString() {
        return "DeliveryPersonnel : " +
                "Personnel ID = " + personnelID +
                ", Personnel Name = '" + personnelName + '\'' +
                ", Personnel Contact = '" + personnelContact + '\'' +
                ", Schedule = '" + schedule + '\'' +
                ", Assigned Route = '" + assignedRoute + '\'' +
                ", Delivery History = '" + deliveryHistory + '\'' +
                "Availability = " + availability;
    }
}

