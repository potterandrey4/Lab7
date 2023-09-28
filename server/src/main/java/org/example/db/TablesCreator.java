package org.example.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.example.db.DatabaseHandler.getDBConnection;

// класс для создания таблиц
public class TablesCreator {

    private static final Logger logger = LoggerFactory.getLogger(TablesCreator.class);


    // метод, создающий таблицы
    private static boolean executeSQLScript(String createTableSQL) throws SQLException {
        Statement statement = null;
        try {
            Connection dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            statement.execute(createTableSQL);
            return true;
        } catch (SQLException e) {
            logger.info(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return false;
    }

    // создание таблицы с User'ами
    public static void createDbUserTable() throws SQLException {

        String createTableSQL = "create table users " +
                "(id serial primary key not null," +
                "username varchar(50) not null," +
                "pswd varchar(100) not null);";

        if (executeSQLScript(createTableSQL)) {
            logger.info("Таблица users была создана");
        }
    }

    // создание таблицы с Worker'ами
    public static void createDbWorkerTable() throws SQLException {

        String createTableSQL = "create table worker " +
                "(" +
                "user_id integer," +
                "foreign key (user_id) references users (id)," +
                "id serial primary key not null," +
                "workername character(100) not null," +
                "coord_x double precision not null," +
                "coord_y float not null," +
                "creation_time character(100) not null," +
                "salary bigint not null," +
                "worker_position character(20) not null," +
                "worker_status character(20)," +
                "organisation_name character(100)," +
                "organisation_type character(30)," +
                "organisation_annualturnover float" +
                ");";

        if (executeSQLScript(createTableSQL)) {
            logger.info("Таблица workers была создана");
        }

    }
}
