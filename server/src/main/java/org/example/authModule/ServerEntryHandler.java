package org.example.authModule;

import org.example.ResponseSender;
import org.example.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import static org.example.db.TablesChecker.tableExists;
import static org.example.db.TablesCreator.createDbUserTable;
import static org.example.db.UserInstructions.addUser;
import static org.example.db.UserInstructions.userExist;

public class ServerEntryHandler {

    private static final Logger logger = LoggerFactory.getLogger(ServerEntryHandler.class);

    public void defineExistUser(User user) {
        switch (user.getId()) {
            case -1 -> logIn(user);
            case -2 -> signIn(user);
            default -> {ResponseSender.sendCommand(null);
            }
        }
    }


    private void logIn(User user) {
        if (!tableExists("users")) {
            try {
                createDbUserTable();
            } catch (SQLException e) {
                logger.warn("Произошла ошибка создания несуществующей таблицы");
            }
        }

        int uId = addUser(user);
        ResponseSender.sendCommand(String.valueOf(uId));
    }

    private void signIn(User user) {
        if (!tableExists("users")) {
            try {
                createDbUserTable();
            } catch (SQLException e) {
                logger.warn("Произошла ошибка создания несуществующей таблицы");
            }
        }

        int uId = userExist(user);
        ResponseSender.sendCommand(String.valueOf(uId));
    }


}
