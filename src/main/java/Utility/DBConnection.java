package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://pixel-host.zapto.org:3306/trackngo";
    private static final String USER = "user";
    private static final String PASSWORD = "user2006";
    private static Connection connection = null;

    public static synchronized Connection getConnection() {
        try {
            // Check if the connection is null or closed
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected.");
            }
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace(); // Log the stack trace for debugging
        }
        return connection;
    }
}
