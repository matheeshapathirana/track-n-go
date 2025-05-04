package Model;

import java.util.ArrayList;
import java.util.List;

public class NotifyDeliveryPersonnel
{
    private int notificationID;
    private String notificationMessage;
    private String notificationTimestamp;

    //constructor with parameters
    public NotifyDeliveryPersonnel(String notificationTimestamp, String notificationMessage, int notificationID)
    {
        this.notificationTimestamp = notificationTimestamp;
        this.notificationMessage = notificationMessage;
        this.notificationID = notificationID;
    }

    //Default constructor

    public NotifyDeliveryPersonnel()
    {

    }

    //Getters
    public int getNotificationID()
    {
        return notificationID;
    }
    public String getNotificationMessage()
    {
        return notificationMessage;
    }
    public String getNotificationTimestamp()
    {
        return notificationTimestamp;
    }

    //Setters
    public void setNotificationID(int notificationID)
    {
        this.notificationID = notificationID;
    }
    public void setNotificationMessage(String notificationMessage)
    {
        this.notificationMessage = notificationMessage;
    }
    public void setNotificationTimestamp(String notificationTimestamp)
    {
        this.notificationTimestamp = notificationTimestamp;
    }

    //Methods
    public String sendNotification(DeliveryPersonnel personnel, String message)
    {
        if(personnel != null)
        {
            return "Notification sent to " + personnel.getPersonnelName() + " at" + personnel.getPersonnelContact() + " with message: " + message;
        }
        return "Error: Delivery Personnel not found!";
    }

    public List<String> notifyAll(List<DeliveryPersonnel> personnelList, String message)
    {
        List<String> notfications = new ArrayList<>();
        for(DeliveryPersonnel person: personnelList)
        {
            String result = sendNotification(person, message);
        }
        return notfications;
    }

    @Override
    public String toString()
    {
        return "NotifyDeliveryPersonnel{" + "notificationID=" + notificationID + ", notificationMessage='" + notificationMessage + '\'' + ", notificationTimestamp='" + notificationTimestamp + '\'' + '}';
    }
}
