package org.example.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class GetCredentials {
    private static String username;
    private static String password;

    private static final Logger logger = LoggerFactory.getLogger(GetCredentials.class);

    public GetCredentials() {
    }

    public void prepareCredentials() {
        try {
            String env = "CREDENTIALS";
            String pathCredentilas;
            pathCredentilas = System.getenv(env);

            Scanner scanner = new Scanner(new FileReader(pathCredentilas));
            username = scanner.nextLine().trim();
            password = scanner.nextLine().trim();
        } catch (FileNotFoundException e) {
            logger.error("Файл с данными для входа в БД не найден. Завершение работы.");
            System.exit(-1);
        }
        catch (NoSuchElementException e) {
            logger.error("В файле не найдены данные для входа. Завершение работы.");
            System.exit(-1);
        }
    }

    public String getUserName() {
        return username;
    }

    public String getUserPassword() {
        return password;
    }
}
