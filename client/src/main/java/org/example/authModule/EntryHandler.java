package org.example.authModule;

import org.example.RequestReader;
import org.example.ResponseSender;
import org.example.User;
import org.example.io.InputHandler;
import org.example.io.OutputHandler;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EntryHandler {

    private static int UID = -1;

    public static User generateUser() {
        User user = new User();
        OutputHandler.println("Введите логин");
        user.setName(InputHandler.get());
        OutputHandler.println("Введите пароль");
        user.setPswd(hashPassword(InputHandler.getPswd()));

        return user;
    }


    public static int logIn() {
        try {
            User user = generateUser();
            user.setId(-1);
            ResponseSender.sendMsgWithUser(user);

            String strUId = null;
            while (strUId == null) {
                strUId = RequestReader.read();
            }
            return UID = Integer.parseInt(strUId);

        } catch (NumberFormatException e) {
            OutputHandler.println("Сервер временно недоступен. Попробуйте позже.");
        }
        return -1;
    }


    public static int signIn() {
        try {
            User user = generateUser();
            user.setId(-2);
            ResponseSender.sendMsgWithUser(user);

            String strUid = RequestReader.read();
            while (strUid == null) {
                strUid = RequestReader.read();
            }

            return UID = Integer.parseInt(strUid);

        } catch (NumberFormatException e) {
            OutputHandler.println("Сервер временно недоступен. Попробуйте позже.");
        }
        return -1;
    }


    public static int getUID() {
        return UID;
    }

    public static void auth() {
        while (UID < 0) {
            if (StartChoice.ask()) {
                UID = EntryHandler.signIn();
            } else {
                UID = EntryHandler.logIn();
            }
        }
        OutputHandler.println("Вы авторизованы");
    }

    private static String hashPassword(String password) {
        try {
            // Создаем объект MessageDigest с алгоритмом MD2
            MessageDigest md = MessageDigest.getInstance("MD2");

            // Преобразуем пароль в массив байтов
            byte[] passwordBytes = password.getBytes();

            // Вычисляем хэш пароля
            byte[] hashBytes = md.digest(passwordBytes);

            // Преобразуем хэш в строку шестнадцатеричного представления
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
