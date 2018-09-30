import java.util.Scanner;

public class CommandInput {
    public static void main(String... args) {

        Completer myCompleter = new CompleterTrieDict();
        System.out.println("User commands: ");
        System.out.println("add [word] [score]");
        System.out.println("\tExample: add chair 54");
        System.out.println("load [path]");
        System.out.println("\tExample: load resources/wordscores01.csv");
        System.out.println("complete [prefix]");
        System.out.println("\tExample: complete ch");
        System.out.println("clear");
        System.out.println("\tExample: clear");
        System.out.println("Enter 'q!' to quit");
        System.out.println("--------------------");
        System.out.println();

        Scanner scan = new Scanner(System.in);
        String line;
        while(scan.hasNext() && !(line = scan.nextLine()).equals("q!")) {
            String[] userArgs = line.split(" ");
            switch (userArgs[0]) {
                case "add":
                    myCompleter.add(userArgs[1], Integer.parseInt(userArgs[2]));
                    System.out.println("Added " + userArgs[1]);
                    break;
                case "complete":
                    System.out.println("Matches: " + myCompleter.complete(userArgs[1]));
                    break;
                case "load":
                    myCompleter.loadCsv(userArgs[1]);
                    System.out.println("Loaded " + userArgs[1]);
                    break;
                case "clear":
                    myCompleter = new CompleterTrieDict();
                    System.out.println("Cleared dictionary");
                    break;
                default:
                    System.out.println("Unknown command " + line);
            }
        }
    }
}
