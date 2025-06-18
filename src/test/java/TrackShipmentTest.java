import Controller.DriverShipmentsController;
import Model.DriverShipmentsDAO;
import Model.Shipments;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DriverShipmentsControllerTest {

    private static DriverShipmentsDAOStub stubDao;
    private static DriverShipmentsController controller;

    @BeforeAll
    static void beforeAll() throws Exception {
        stubDao = new DriverShipmentsDAOStub();
        controller = new DriverShipmentsController();
        // Use reflection to set the private driverShipmentsDAO field
        java.lang.reflect.Field daoField = DriverShipmentsController.class.getDeclaredField("driverShipmentsDAO");
        daoField.setAccessible(true);
        daoField.set(controller, stubDao);
    }

    @Test
    void testGetShipmentsForDriver_returnsShipmentsList() {
        int driverId = 1;
        Shipments shipment1 = new Shipments(1, "Sender1", "Receiver1", 1, new java.sql.Timestamp(System.currentTimeMillis()), 1, 1, "Content1", "Status1", "Address1");
        Shipments shipment2 = new Shipments(2, "Sender2", "Receiver2", 2, new java.sql.Timestamp(System.currentTimeMillis()), 2, 2, "Content2", "Status2", "Address2");
        stubDao.setShipmentsForDriver(driverId, Arrays.asList(shipment1, shipment2));

        List<Shipments> result = controller.getShipmentsForDriver(driverId);

        assertEquals(2, result.size());
        assertTrue(result.contains(shipment1));
        assertTrue(result.contains(shipment2));
    }

    @Test
    void testGetShipmentsForDriver_returnsEmptyList() {
        int driverId = 99;
        stubDao.setShipmentsForDriver(driverId, Collections.emptyList());

        List<Shipments> result = controller.getShipmentsForDriver(driverId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @AfterEach
    void afterEach() {
        stubDao.data.clear();
    }

    @AfterAll
    static void afterAll() {
        stubDao = null;
        controller = null;
    }

    // Simple stub for DriverShipmentsDAO
    static class DriverShipmentsDAOStub extends DriverShipmentsDAO {
        private final java.util.Map<Integer, List<Shipments>> data = new java.util.HashMap<>();

        void setShipmentsForDriver(int driverId, List<Shipments> shipments) {
            data.put(driverId, shipments);
        }

        @Override
        public List<Shipments> getShipmentsByDriverId(int driverId) {
            return data.getOrDefault(driverId, new ArrayList<>());
        }
    }
}