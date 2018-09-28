public class CommandInterface {
    public static void main(String... args) {

        Completer myCompleter = new CompleterTrieDict();
        myCompleter.loadCsv("resources/wordscores01.csv");
        // myCompleter.loadCsv("resources/wordscores02.csv");
        // myCompleter.loadCsv("resources/wordscores03.csv");
        // myCompleter.loadCsv("resources/wordscores04.csv");

        System.out.println(myCompleter.complete("e"));
        System.out.println(myCompleter.complete("em"));
        System.out.println(myCompleter.complete("emp"));
        System.out.println(myCompleter.complete("empl"));
        
    }
}
