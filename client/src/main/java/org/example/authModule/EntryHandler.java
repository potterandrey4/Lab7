package org.example.authModule;

import org.example.RequestReader;
import org.example.ResponseSender;
import org.example.User;
import org.example.io.InputHandler;
import org.example.io.OutputHandler;

public class EntryHandler {

    private static int UID = -1;

    public static User generateUser() {
        User user = new User();
        OutputHandler.println("Введите логин");
        user.setName(InputHandler.get());
        OutputHandler.println("Введите пароль");
        user.setPswd(InputHandler.getPswd());

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
}
