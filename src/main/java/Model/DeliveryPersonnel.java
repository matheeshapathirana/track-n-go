package Model;

public class DeliveryPersonnel {
    private int personnelID;
    private String personnelName;
    private String personnelContact;
    private String schedule;
    private String assignedRoute;
    private String deliveryHistory;

    //constructor
    public DeliveryPersonnel(int personnelID, String personnelName, String personnelContact, String schedule, String assignedRoute, String deliveryHistory) {
        this.personnelID = personnelID;
        this.personnelName = personnelName;
        this.personnelContact = personnelContact;
        this.schedule = schedule;
        this.assignedRoute = assignedRoute;
        this.deliveryHistory = deliveryHistory;
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

    //to string for table in view
    @Override
    public String toString() {
        return "DeliveryPersonnel : " +
                "Personnel ID = " + personnelID +
                ", Personnel Name = '" + personnelName + '\'' +
                ", Personnel Contact = '" + personnelContact + '\'' +
                ", Schedule = '" + schedule + '\'' +
                ", Assigned Route = '" + assignedRoute + '\'' +
                ", Delivery History = '" + deliveryHistory;
    }
}

