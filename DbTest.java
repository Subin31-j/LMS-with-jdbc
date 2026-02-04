package advanced.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbTest {
    private static final String URL = "jdbc:postgresql://localhost:5432/librarydb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "0000";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
