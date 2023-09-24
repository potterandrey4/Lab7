package org.example.db;

import org.example.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.db.DatabaseHandler.getDBConnection;


// класс-обработчик user'a
public class UserInstructions {
    private static final Logger logger = LoggerFactory.getLogger(UserInstructions.class);

    private static final Connection connection = getDBConnection();

    // шаблон запроса на создание user
    private static final String ADD_USER_REQUEST = "INSERT INTO users (username, pswd) VALUES (?, ?) RETURNING id";


    // добавление пользователя
    public static int addUser(User user) {
        int generatedId = -1;
        try {
            if (userExist(user) >= 0) {
                logger.warn("Пользователь с такими данными уже существует");
            }
            else {
                PreparedStatement addStatement = connection.prepareStatement(ADD_USER_REQUEST);
                addStatement.setString(1, user.getName());
                addStatement.setString(2, user.getPswd());

                try (ResultSet resultSet = addStatement.executeQuery()) {
                    if (resultSet.next()) {
                        generatedId = resultSet.getInt("id");
                        logger.info("Пользователь успешно добавлен. Сгенерированный ID: " + generatedId);
                    }
                }
            }
        } catch (SQLException e) {
            logger.warn("Произошла ошибка в бд: " + e.getMessage());
        }
        return generatedId;
    }


    // проверка на существование пользователя и возврат его Id, или -1 если пользователь не найден
    public static int userExist(User user) {
        int uId = -1;
        try {
            String query = "SELECT id FROM Users WHERE username = ? AND pswd = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getPswd());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        uId = resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            logger.warn("Произошла ошибка в бд: " + e.getMessage());
        }
        return uId;
    }



}
