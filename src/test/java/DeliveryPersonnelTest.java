import Model.DeliveryPersonnel;
import Model.DeliveryPersonnelDAO;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //used to execute tests in order using @Order(1) etc
public class DeliveryPersonnelTest {

    static DeliveryPersonnelDAO dao;
    static int testID = 9999;

    @BeforeAll
    public static void setupAll() {
        System.out.println("Setting up DAO before all tests");
        dao = new DeliveryPersonnelDAO();
    }

    @BeforeEach
    public void setupEach() {
        System.out.println("Starting new test");
    }

    @Test
    @Order(1) //exectute this test 1st
    public void testAddPersonnel() {
        DeliveryPersonnel dp = new DeliveryPersonnel(
                testID,
                "testUser",
                "0712345678",
                "9 AM - 12 PM",
                "Colombo South",
                "None",
                "Available"
        );
        dao.addPersonnel(dp);
        Assertions.assertTrue(true); // Assume insert successful if no error
    }

    @Test
    @Order(2) //execute this test 2nd
    public void testGetAllPersonnel() {
        List<DeliveryPersonnel> list = dao.getAllPersonnel();
        boolean found = list.stream().anyMatch(p -> p.getPersonnelID() == testID);
        Assertions.assertTrue(found, "Added personnel should be in the list");
    }

    @Test
    @Order(3)
    public void testDeletePersonnel() {
        dao.deletePersonnel(testID);
        List<DeliveryPersonnel> list = dao.getAllPersonnel();
        boolean found = list.stream().anyMatch(p -> p.getPersonnelID() == testID);
        Assertions.assertFalse(found, "Personnel should be deleted");
    }

    @AfterEach
    public void tearDownEach() {
        System.out.println("Finished test.\n");
    }

    @AfterAll
    public static void tearDownAll() {
        System.out.println("All tests finished.");
    }
}

//tets mightfail because of a foreign key issue
