package org.example.tools;

import org.example.tools.io.InputHandler;
import org.example.tools.io.OutputHandler;

public class GenerateBoolean {
    public static boolean yesNo(){
        String str = InputHandler.get();
        boolean booleanVar = true;
        while (!(str.equalsIgnoreCase("yes") || str.equalsIgnoreCase("no"))) {
            OutputHandler.printErr("Вам нужно ввести только yes или no");
            str = InputHandler.get();
        }
        if (str.equalsIgnoreCase("yes")) {
            booleanVar = true;
        } else if (str.equalsIgnoreCase("no")) {
            booleanVar = false;
        }
        return booleanVar;
    }
}
