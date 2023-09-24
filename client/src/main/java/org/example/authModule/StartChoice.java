package org.example.authModule;

import org.example.io.InputHandler;
import org.example.io.OutputHandler;

public class StartChoice {

    public static boolean ask() {
        OutputHandler.println("У вас уже есть аккаунт? [y/n]");
        String answer = InputHandler.get();
        if (answer.equalsIgnoreCase("y")) {
            return true;
        } else if (answer.equalsIgnoreCase("n")) {
            return false;
        }
        else {
            ask();
        }
        return false;
    }

}
