import java.util.List;

/**
 * Defines the Completer interface
 */
public interface Completer {
    int MATCH_SIZE = 10;

    /**
     * Provide a list of completions
     * given a prefix
     *
     */
    List<String> complete(String prefix);

    /**
     * Add the name, score pair to the
     * dictionary
     */
    void add(String name, int score);

    /**
     * Load name, score pairs in the
     * specified csv file to the dictionary
     */
    void loadCsv(String csvFile);
}
