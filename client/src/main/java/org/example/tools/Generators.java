package org.example.tools;

import org.example.io.InputHandler;
import org.example.io.OutputHandler;
import org.example.collection.classes.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.example.collection.classes.Position.getPositionByTitle;
import static org.example.collection.classes.Status.getStatusByTitle;

public class Generators {

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

    public static Coordinates generateCoordinates() {
        Coordinates coordinates = new Coordinates();

        double x = universalInputForDouble("Введите число с плавающей точкой X: ");
        coordinates.setX(x);

        float y = (float) universalInputForDouble("Введите число с плавающей точкой Y: ");
        coordinates.setY(y);

        return coordinates;
    }


    public static Organization generateOrganization() {
        OutputHandler.println("Необходимо ввести информацию об организации");

        OutputHandler.println("Введите имя организации");
        String fullName;
        ArrayList<String> listName = new ArrayList<String>();


        while (true) {
            fullName = InputHandler.get();
            if (fullName.isBlank()) {
                fullName = null;
                break;
            } else {
                break;
            }
        }

        float annualTurnover = (float) universalInputForDouble("Введите годовой оборот компании (больше 0)");;
        while (annualTurnover <= 0) {
            OutputHandler.printErr("Годовой оборот должен быть > 0");
            annualTurnover = (float) universalInputForDouble("Введите годовой оборот компании (больше 0)");
        }

        OrganizationType[] list = OrganizationType.values();
        OutputHandler.println("Введите тип организации: " + Arrays.toString(list));

        OrganizationType organizationType;
        while (true) {
            organizationType = OrganizationType.getOrganizationTypeByTitle(InputHandler.get());
            if (organizationType == null) {
                OutputHandler.printErr("Выберете тип организации из списка");
            } else {
                break;
            }
        }
        return new Organization(fullName, annualTurnover, organizationType);
    }


    public static Position generatePosition() {
        Position[] list = Position.values();
        OutputHandler.println("Какую позицию занимает сотрудник? " + Arrays.toString(list));

        while (true) {
            Position position = getPositionByTitle(InputHandler.get());
            if (position != null) {
                return position;
            }
            else {
                OutputHandler.printErr("Выберете статус из списка");
            }
        }
    }


    public static long generateSalary() {
        long x;
        while (true) {
            x = (long) universalInputForDouble("Введите зарплату (больше 0):");
            if (x <= 0) {
                OutputHandler.printErr("Зарплата должна быть больше 0");
            } else {
                break;
            }
        }
        return x;
    }


    public static Status generateStatus() {
        Status[] list = Status.values();
        OutputHandler.println("Какой статус у сотрудника? " + Arrays.toString(list));

        while (true) {
            Status status = getStatusByTitle(InputHandler.get());
            if (status != null) {
                return status;
            }
            else {
                OutputHandler.printErr("Выберете статус из списка");
            }
        }
    }


    public static String generateString() {
        String ret;
        while (true) {
            String str = InputHandler.get();
            if (!str.isBlank()) {
                ret = str;
                break;
            } else {
                OutputHandler.printErr("Некорректные входные данные! Введите непустую строку");
            }
        }
        return ret;
    }


    public static double universalInputForDouble(String print) {
        double x;
        while (true) {
            OutputHandler.println(print);
            String strX = InputHandler.get();
            if (!strX.isBlank()) {
                try {
                    x = parse(strX);
                    break;
                } catch (NumberFormatException e) {
                    OutputHandler.printErr("Некорректные входные данные!");
                }
            } else {
                OutputHandler.printErr("Некорректные входные данные!");
            }
        }
        return x;
    }

    public static double parse(String x) {
        return Double.parseDouble(x.replace(",", "."));
    }

}
