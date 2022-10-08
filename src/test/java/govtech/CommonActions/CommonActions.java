package govtech.CommonActions;

import java.sql.*;

public class CommonActions {
    public static ResultSet setUpAndQueryDataBase(String query) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/testdb?user=user&password=pwd");
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet results = stmt.executeQuery();
        connection.close();
        return results;
    }
    public static ResultSet setUpAndQueryDataBaseByNatId(String query, String natId) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/testdb?user=user&password=pwd");
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, natId);
        ResultSet results = stmt.executeQuery();
        connection.close();
        return results;
    }

    public static void setUpDatabaseAndDeleteEntry(String query, String natId) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/testdb?user=user&password=pwd");
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, natId);
        stmt.executeUpdate();
        connection.close();
    }

    public static void setUpDatabaseAndDelete(String query) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/testdb?user=user&password=pwd");
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.executeUpdate();
        connection.close();
    }

}
