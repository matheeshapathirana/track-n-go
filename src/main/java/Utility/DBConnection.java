package Utility;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBConnection {
    private static final String URL = "jdbc:mysql://pixel-host.zapto.org:3306/trackngo";
    private static final String USER = "user";
    private static final String PASSWORD = "user2006";
    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(10); // You can adjust pool size as needed
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (Exception e) {
            System.out.println("Database connection error: " + e.getMessage());
            return null;
        }
    }
}
