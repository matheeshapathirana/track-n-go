package Controller;

import Model.CustomerNotificationDAO;
import View.userView;

import java.util.List;

public class CustomerNotificationController {
    private userView view;
    private CustomerNotificationDAO dao;

    public CustomerNotificationController(userView view, int customerId) {
        this.view = view;
        this.dao = new CustomerNotificationDAO();
        // Load notifications into the user view
        view.loadCustomerNotifications(customerId);
    }
}
