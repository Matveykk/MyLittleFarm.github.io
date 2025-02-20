package database;

import utils.database.ConnectionManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WorkWithDB {
    static Connection conn = ConnectionManager.open();

    static Statement statement;

    static {
        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean hasUsername(String username) {

        String sql = "SELECT * FROM userdata WHERE username='" + username + "'";
        String hasStr = null;

        try {
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            hasStr = resultSet.getString("username");
        }
        catch (SQLException e) {
            return false;
        }

            if (hasStr != null) {
                return true;
            }

        return false;
    }

    public static void addUser(String username, String name) {
        String sql = "INSERT INTO users_data(username, name) VALUES ('" + username + "', '" + name + "')";

        try {
            statement.execute(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
