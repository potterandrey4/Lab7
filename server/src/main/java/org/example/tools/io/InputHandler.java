package org.example.tools.io;

import java.util.Scanner;

public class InputHandler {
    private static final Scanner scanner = new Scanner(System.in);

    public static String get() {
        return scanner.nextLine();
    }

    public static boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public static void close() {
        scanner.close();
    }
}

