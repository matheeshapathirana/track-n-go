package Controller;

import Model.CustomerNotification;
import Model.CustomerNotificationDAO;
import View.CustomerNotificationView;

import java.util.List;

public class CustomerNotificationController {
    private CustomerNotificationView view;
    private CustomerNotificationDAO dao;

    public CustomerNotificationController(CustomerNotificationView view, int customerId) {
        this.view = view;
        this.dao = new CustomerNotificationDAO();

        List<CustomerNotification> notifications = dao.getNotificationsByCustomerID(customerId);
        view.displayNotifications(notifications);
    }
}
