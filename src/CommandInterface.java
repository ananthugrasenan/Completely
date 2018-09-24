public class CommandInterface {
    public static void main(String... args) {

        Completer myCompleter = new CompleterTrie();
        myCompleter.loadCsv("resources/wordscores01.csv");
        myCompleter.loadCsv("resources/wordscores02.csv");
        myCompleter.loadCsv("resources/wordscores03.csv");
        myCompleter.loadCsv("resources/wordscores04.csv");

        System.out.println(myCompleter.complete("em"));
        
    }
}
