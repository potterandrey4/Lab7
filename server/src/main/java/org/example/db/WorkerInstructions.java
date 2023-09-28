package org.example.db;


import org.example.collection.classes.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import static org.example.db.DatabaseHandler.getDBConnection;

public class WorkerInstructions {

    private static final Connection connection = getDBConnection();
    private static final Logger logger = LoggerFactory.getLogger(WorkerInstructions.class);


    // добавление worker'a
    public static int addWorker(Worker worker) {
        if (!TablesChecker.tableExists("worker")) {
            try {
                TablesCreator.createDbWorkerTable();
            } catch (SQLException e) {
                logger.warn("Не удалось восстановить таблицу worker");
            }
        }

        String ADD_WORKER_REQUEST = "INSERT INTO worker (user_id, workername, coord_x, coord_y," +
                "creation_time, salary, worker_position, worker_status," +
                "organisation_name, organisation_type, organisation_annualturnover)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" +
                "RETURNING id;";

        int generatedId = -1;
        try {
            PreparedStatement statement = connection.prepareStatement(ADD_WORKER_REQUEST);

            statement.setInt(1, worker.getUserId());
            statement.setString(2, worker.getName());
            statement.setDouble(3, worker.getCoordinates().getX());
            statement.setFloat(4, worker.getCoordinates().getY());
            statement.setString(5, String.valueOf(worker.getCreationDate()));
            statement.setLong(6, worker.getSalary());
            statement.setString(7, String.valueOf(worker.getPosition()));
            statement.setString(8, String.valueOf(worker.getStatus()));
            statement.setString(9, worker.getOrganization().getFullName());
            statement.setString(10, String.valueOf(worker.getOrganization().getType()));
            statement.setFloat(11, worker.getOrganization().getAnnualTurnover());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    generatedId = resultSet.getInt("id");
                    logger.info("Worker успешно добавлен");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return generatedId;
    }


    // проверка на существование worker'a
    public static boolean workerExist(int id) {
        if (!TablesChecker.tableExists("worker")) {
            try {
                TablesCreator.createDbWorkerTable();
            } catch (SQLException e) {
                logger.warn("Не удалось восстановить таблицу humans");
            }
        }

        boolean flag = false;
        try {
            String query = "SELECT EXISTS(SELECT 1 FROM worker WHERE id = ?)";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        flag = resultSet.getBoolean(1);
                    }
                }
            }
        } catch (SQLException e) {
            logger.warn("Произошла ошибка в бд: " + e.getMessage());
        }
        return flag;
    }


    // получение всех worker'ов с БД
    public static LinkedList<Worker> getAllWorkers() {
        if (!TablesChecker.tableExists("users")) {
            try {
                TablesCreator.createDbUserTable();
            } catch (SQLException e) {
                logger.warn("Не удалось восстановить таблицу user");
            }
        }

        if (!TablesChecker.tableExists("worker")) {
            try {
                TablesCreator.createDbWorkerTable();
            } catch (SQLException e) {
                logger.warn("Не удалось восстановить таблицу worker");
            }
        }
        LinkedList<Worker> workers = new LinkedList<>();

        String query = "SELECT * FROM worker";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Worker worker = new Worker();

                    worker.setUserId(resultSet.getInt("user_id"));
                    worker.setId(resultSet.getInt("id"));
                    worker.setName(resultSet.getString("workername").trim());
                    worker.setCoordinates(
                            new Coordinates(
                                    resultSet.getDouble("coord_x"),
                                    resultSet.getFloat("coord_y"))
                    );
                    worker.setCreationDate(resultSet.getString("creation_time").trim());
                    worker.setSalary(resultSet.getInt("salary"));
                    worker.setPosition(Position.valueOf(resultSet.getString("worker_position").trim()));
                    worker.setStatus(Status.valueOf(resultSet.getString("worker_status").trim()));
                    worker.setOrganization(
                            new Organization(
                                    resultSet.getString("organisation_name"),
                                    resultSet.getFloat("organisation_annualturnover"),
                                    OrganizationType.valueOf(resultSet.getString("organisation_type").trim())
                            )
                    );

                    workers.add(worker);
                }
            }
        } catch (SQLException e) {
            logger.error("Произошла ошибка при чтении worker" + e.getMessage());
        }

        return workers;
    }


    // изменение worker'a (доступ имеется только у создателя)
    public static boolean updateWorker(Worker worker) {
        String query = "UPDATE worker SET workername = ?, coord_x = ?, coord_y = ?, creation_time = ?, " +
                "salary = ?, worker_position = ?, worker_status = ?, organisation_name = ?, " +
                "organisation_type = ?, organisation_annualturnover = ? WHERE id = ?;";

        int changedRows = 0;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, worker.getName());
            statement.setDouble(2, worker.getCoordinates().getX());
            statement.setFloat(3, worker.getCoordinates().getY());
            statement.setString(4, String.valueOf(worker.getCreationDate()));
            statement.setLong(5, worker.getSalary());
            statement.setString(6, String.valueOf(worker.getPosition()));
            statement.setString(7, String.valueOf(worker.getStatus()));
            statement.setString(8, worker.getOrganization().getFullName());
            statement.setString(9, String.valueOf(worker.getOrganization().getType()));
            statement.setFloat(10, worker.getOrganization().getAnnualTurnover());
            statement.setInt(11, worker.getId());

            changedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Произошла ошибка при изменении worker" + e.getMessage());
        }
        return changedRows == 1;
    }


    // удаление всех worker'ов для конкретного пользователя
    public static boolean deleteAllWorkersForUser(int userId, int count) {
        if (!TablesChecker.tableExists("worker")) {
            try {
                TablesCreator.createDbWorkerTable();
            } catch (SQLException e) {
                logger.warn("Не удалось восстановить таблицу worker");
            }
        }

        int rowsDeleted = 0;

        try {
            String query = "DELETE FROM worker WHERE id IN (SELECT id FROM worker WHERE user_id = ? LIMIT ?)";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, userId);
                statement.setInt(2, count);

                rowsDeleted = statement.executeUpdate();
            }
        } catch (SQLException e) {
            logger.warn("Произошла ошибка в бд: " + e.getMessage());
        }

        return rowsDeleted > 0;
    }

    // заготовка удалятора с 2мя аргументами
    private static boolean deleteWithTwoArgs(String query, int userId, int id) {
        if (!TablesChecker.tableExists("worker")) {
            try {
                TablesCreator.createDbWorkerTable();
            } catch (SQLException e) {
                logger.warn("Не удалось восстановить таблицу worker");
            }
        }

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setInt(2, userId);

            int rowsAffected = statement.executeUpdate();

            // Проверяем, была ли удалена хотя бы одна запись
            if (rowsAffected > 0) {
                return true; // Успешное удаление
            } else {
                return false; // Запись не была найдена или не принадлежит пользователю
            }

        } catch (SQLException e) {
            logger.warn("Произошла ошибка в бд: " + e.getMessage());
            return false;

        }
    }


    // удаление worker'a (доступ имеется только у создателя)
    public static boolean deleteWorker(int userId, int id) {
        String query = "DELETE FROM worker WHERE id = ? AND user_id = ?;";
        return deleteWithTwoArgs(query, userId, id);
    }

    public static boolean removeGreaterWorkers(int userId, int id) {
        String query = "DELETE FROM worker WHERE id >= ? AND user_id = ?;";
        return deleteWithTwoArgs(query, userId, id);
    }
}
