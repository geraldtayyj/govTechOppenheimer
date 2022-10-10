package govtech.Helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection conn = null;
    private Database() throws SQLException {}

    public static Connection getConnection() throws SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/testdb?user=user&password=pwd");
        }
        return conn;
    }

    public static void closeConnection() throws SQLException {
        conn.close();
    }

}
