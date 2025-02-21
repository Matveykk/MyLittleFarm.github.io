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

        String sql = "SELECT * " +
                "FROM users_data " +
                "WHERE username='" + username + "'";
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
        String sql = "INSERT INTO users_data(username, name) " +
                "VALUES ('" + username + "', '" + name + "')";

        try {
            statement.execute(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void updateCarrotCount(String username, int carrotCount) {
        String sql = "UPDATE users_data"
                + " SET carrot_count=" + carrotCount
                + " WHERE username='" + username + "'";
        try {
            statement.execute(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public static int getUserCarrots(String username) {
        int carrotCount = 0;
        if(hasUsername(username)) {
            String sql = "SELECT carrot_count " +
                    "FROM users_data " +
                    "WHERE username='" + username + "'";
            try {
                ResultSet resultSet = statement.executeQuery(sql);
                resultSet.next();
                carrotCount = resultSet.getInt("carrot_count");
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return carrotCount;
    }
}
