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


    // шаблон запроса на добавление worker'a
    private static final String ADD_WORKER_REQUEST = "INSERT INTO worker (user_id, workername, coord_x, coord_y," +
            "creation_time, salary, worker_position, worker_status," +
            "organisation_name, organisation_type, organisation_annualturnover)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    // добавление worker'a
    public static boolean addWorker(int userId, Worker worker) {
        if (!TablesChecker.tableExists("worker")) {
            try {
                TablesCreator.createDbWorkerTable();
            } catch (SQLException e) {
                logger.warn("Не удалось восстановить таблицу worker");
            }
        }

        boolean flag = false;
        int id = worker.getId();
        try {
            if (workerExist(id)) {
                logger.warn("Пользователь с такими данными уже существует");
            } else {
                PreparedStatement statement = connection.prepareStatement(ADD_WORKER_REQUEST);

                statement.setInt(1, userId);
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

                statement.executeUpdate();
                statement.close();
                logger.info("Worker успешно добавлен");
                flag = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flag;
    }


    // проверка на существование worker'a
    private static boolean workerExist(int id) {
        if (!TablesChecker.tableExists("worker")) {
            try {
                TablesCreator.createDbWorkerTable();
            } catch (SQLException e) {
                logger.warn("Не удалось восстановить таблицу worker");
            }
        }

        boolean flag = false;
        try {
            String query = "SELECT EXISTS(SELECT 1 FROM Users WHERE id = ?)";

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
    public static boolean updateWorker(int userId, Worker worker) {
        return deleteWorker(userId, worker.getId()) && addWorker(userId, worker);
    }


    // удаление всех worker'ов для конкретного пользователя
    public static boolean deleteAllWorkersForUser(int userId) {
        if (!TablesChecker.tableExists("worker")) {
            try {
                TablesCreator.createDbWorkerTable();
            } catch (SQLException e) {
                logger.warn("Не удалось восстановить таблицу worker");
            }
        }
        String deleteTableSQL = "DELETE FROM worker WHERE user_id = ?";
        boolean flag = false;
        try {
            PreparedStatement statement = connection.prepareStatement(deleteTableSQL);
            statement.setInt(1, userId);
            statement.executeUpdate();
            statement.close();
            flag = true;
        } catch (SQLException e) {
            logger.warn("Произошла ошибка в бд: " + e.getMessage());
        }
        return flag;
    }


    // удаление всех worker'ов
    public static boolean deleteAllWorkers() {
        String deleteTableSQL = "DROP CASCADE worker";
        boolean flag = false;
        try {
            PreparedStatement statement = connection.prepareStatement(deleteTableSQL);
            statement.executeUpdate();
            statement.close();
            flag = true;
        } catch (SQLException e) {
            logger.warn("Произошла ошибка в бд: " + e.getMessage());
        }
        return flag;
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

        boolean flag = false;
        if (workerExist(id)) {
            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, userId);
                statement.setInt(2, id);
                statement.executeUpdate();
                statement.close();
                flag = true;
            } catch (SQLException e) {
                logger.warn("Произошла ошибка в бд: " + e.getMessage());
            }
        }
        return flag;
    }


    // удаление worker'a (доступ имеется только у создателя)
    public static boolean deleteWorker(int userId, int id) {
        String query = "DELETE FROM worker WHERE id = ? AND user_id = ?";
        return deleteWithTwoArgs(query, userId, id);
    }

    public static boolean removeGreaterWorkers(int userId, int id) {
        String query = "DELETE FROM worker WHERE id >= ? AND user_id = ?";
        return deleteWithTwoArgs(query, userId, id);
    }
}
