package org.example.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.db.DatabaseHandler.getDBConnection;

public class TablesChecker {

    private static final Connection connection = getDBConnection();

    public static boolean tableExists(String tableName) {

        String sql = "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = ?)";
        try {
            PreparedStatement existStatement = connection.prepareStatement(sql);
            existStatement.setString(1, tableName);
            ResultSet resultSet = existStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
