package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://pixel-host.zapto.org:3306/trackngo";
    private static final String USER = "user";
    private static final String PASSWORD = "user2006";
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected.");
            } catch (Exception e) {
                System.out.println("Database connection error: " + e.getMessage());
            }
        }
        return connection;
    }
}
