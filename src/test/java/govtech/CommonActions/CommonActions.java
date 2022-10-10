package govtech.CommonActions;

import govtech.Helpers.Database;

import java.sql.*;

public class CommonActions {
    public static ResultSet setUpAndQueryDataBase(String query) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet results = stmt.executeQuery();
        Database.closeConnection();
        return results;
    }

    public static ResultSet setUpAndQueryDataBaseByNatId(String query, String natId) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, natId);
        ResultSet results = stmt.executeQuery();
        Database.closeConnection();
        return results;
    }

    public static void setUpDatabaseAndDeleteEntry(String query, String natId) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, natId);
        stmt.executeUpdate();
        Database.closeConnection();
    }

    public static void setUpDatabaseAndDelete(String query) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.executeUpdate();
        Database.closeConnection();
    }

}
