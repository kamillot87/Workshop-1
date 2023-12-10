package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.*;

public class TaskManager {
    public static void main(String[] args) throws IOException {
        mainMenu("tasks.csv");
    }

    public static void mainMenu(String fileTrack) throws IOException {
        while (true) {
            System.out.println(ConsoleColors.BLUE + "Please select an option: " + ConsoleColors.RESET);
            System.out.println("add\nremove\nlist\nexit"); //wyświetlane menu
            Scanner menuButtonScanner = new Scanner(System.in);
            String menuButton = menuButtonScanner.next();
            switch (menuButton) {
                case "add":
                    adder(fileTrack);
                    break;
                case "remove":
                    remover(fileTrack);
                    break;
                case "list":
                    list(fileTrack); // zrobione
                    break;
                case "exit": //zrobione
                    System.out.println(ConsoleColors.RED_BOLD + "Goodbye" + ConsoleColors.RESET);
                    break;
                default:
                    System.out.println("wrong command");
                    break;
            }
            if (menuButton.equalsIgnoreCase("exit")) break;
        }
    }

    public static void list(String track) throws IOException {
        String url = track;
        File data = new File(url);
        List<String> list = Files.readAllLines(Path.of(url));
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + " : " + list.get(i));
        }
    }

    public static void adder(String track) throws IOException {
        Scanner newData = new Scanner(System.in);
        String toAdd = "\n";
        FileWriter dataToAdd = new FileWriter(track, true);
        System.out.println("Task description: ");
        toAdd += newData.nextLine() + ", ";

        System.out.println("Please add a deadline for completing the task:");
        while (true) {
            try {
                String date = "";
                String blocker = "";
                System.out.println("Year");
                int year = Integer.parseInt(newData.next());
                if (year < 1900) {
                    blocker = "blad";
                }
                System.out.println("Month - number");
                int month = Integer.parseInt(newData.next());
                if (month > 12) {
                    blocker = "blad";
                }
                System.out.println("Day - number");
                int day = Integer.parseInt(newData.next());
                if (day > 31) {
                    blocker = "blad";
                }
                String chekcerOfDate = String.valueOf(year + month + day + blocker);
                if (NumberUtils.isNumber(chekcerOfDate)) { // sprawdzamy czy uzytkownik podal wartosci liczbowe
                    toAdd += year + "-" + month + "-" + day + ", ";
                    break;
                } else {
                    System.out.println("you set wrong data");
                }
            } catch (IllegalArgumentException a) {
                System.out.println("!!!set date by numbers!!!");
            }
        }
        System.out.println("Is you task important: true/false");
        while (true) {
            String booleanSet = newData.next();
            if (booleanSet.equalsIgnoreCase("true")) {
                toAdd += "true";
                break;
            } else if (booleanSet.equalsIgnoreCase("false")) {
                toAdd += "false";
                break;
            } else {
                System.out.println("you enter wrong data");
            }
        }
        dataToAdd.write(toAdd);
        dataToAdd.close();
    }

    public static void remover(String track) throws IOException {
        List<String> list = Files.readAllLines(Path.of(track));
        Scanner input = new Scanner(System.in);
        System.out.println("Set which task do you want to remove: ");
        int positionToRemove= list.size()+1;
        try {
            positionToRemove = input.nextInt();
        }catch (InputMismatchException b) {
            System.out.println("You set wrong mark");
        }
        PrintWriter newListWithoutRemovedFile = new PrintWriter(track);
        try {
            list.remove(positionToRemove);// nadpisujemy listę
        } catch (IndexOutOfBoundsException a) {
            System.out.println("You set index out of bound");
        }
        for (String a : list) {
            newListWithoutRemovedFile.println(a);
        }
        newListWithoutRemovedFile.close();
    }
}

