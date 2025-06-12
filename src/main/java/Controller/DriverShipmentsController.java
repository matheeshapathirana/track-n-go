package Controller;

import Model.DriverShipmentsDAO;
import Model.Shipments;
import java.util.List;

public class DriverShipmentsController {
    private DriverShipmentsDAO driverShipmentsDAO;

    public DriverShipmentsController() {
        this.driverShipmentsDAO = new DriverShipmentsDAO();
    }

    public List<Shipments> getShipmentsForDriver(int driverId) {
        return driverShipmentsDAO.getShipmentsByDriverId(driverId);
    }
}
