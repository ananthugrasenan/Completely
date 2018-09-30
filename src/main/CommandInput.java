import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class CommandInput {
    public static void main(String... args) {

        Completer myCompleter = new CompleterTrieDict();
        // print help
        help();

        Scanner scan = new Scanner(System.in);
        String line;
        while(scan.hasNext() && !(line = scan.nextLine()).equals("q!")) {
            String[] userArgs = line.trim().split(" ");
            if (userArgs.length < 1) continue;
            switch (userArgs[0]) {
                case "add": case "a":
                    if (userArgs.length >= 3) {
                        myCompleter.add(userArgs[1], Integer.parseInt(userArgs[2]));
                        System.out.println("Added " + userArgs[1]);
                    }
                    else {
                        System.out.println("Missing arguments for " + userArgs[0]);
                    }
                    break;
                case "complete": case "c":
                    if (userArgs.length >= 2) {
                        System.out.println("Matches: " + myCompleter.complete(userArgs[1]));
                    }
                    else {
                        System.out.println("Missing arguments for " + userArgs[0]);
                    }
                    break;
                case "load": case "l":
                    if (userArgs.length >= 2) {
                        try {
                            myCompleter.loadCsv(userArgs[1]);
                            System.out.println("Loaded " + userArgs[1]);
                        } catch (RuntimeException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    else {
                            System.out.println("Missing arguments for " + userArgs[0]);
                    }
                    break;
                case "clear":
                    myCompleter = new CompleterTrieDict();
                    System.out.println("Cleared dictionary");
                    break;
                case "help": case "h":
                    help();
                    break;
                case "write": case "w":
                    if (userArgs.length >= 2) {
                        ObjectMapper mapper = new ObjectMapper();
                        File jsonFile = new File(userArgs[1]);
                        try {
                            mapper.writeValue(jsonFile, myCompleter);
                            System.out.println("Wrote dictionary to " + userArgs[1]);
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    else {
                        System.out.println("Missing arguments for " + userArgs[0]);
                    }
                    break;
                case "read": case "r":
                    if (userArgs.length >= 2) {
                        File jsonFile = new File(userArgs[1]);
                        ObjectMapper readMapper = new ObjectMapper();
                        try {
                            myCompleter = readMapper.readValue(jsonFile, CompleterTrieDict.class);
                            System.out.println("Read dictionary from " + userArgs[1]);
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    else {
                        System.out.println("Missing arguments for " + userArgs[0]);
                    }
                    break;
                case "": break;
                default:
                    System.out.println("Unknown command " + line);
            }
        }
    }

    static void help() {
        System.out.println("User commands: ");
        System.out.println("add [word] [score]");
        System.out.println("\tExample: add chair 54");
        System.out.println("load [path]");
        System.out.println("\tExample: load resources/wordscores01.csv");
        System.out.println("complete [prefix]");
        System.out.println("\tExample: complete ch");
        System.out.println("clear");
        System.out.println("\tExample: clear");
        System.out.println("write [path]");
        System.out.println("\tExample: write tmp/dict1.json");
        System.out.println("read [path]");
        System.out.println("\tExample: read tmp/dict1.json");
        System.out.println("Enter 'q!' to quit");
        System.out.println("--------------------");
    }
}
