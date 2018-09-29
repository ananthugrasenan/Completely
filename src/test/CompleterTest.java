import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompleterTest {

    /**
     * Simple tests to check if newly added words
     * show up properly ranked
     */
    @Test
    void simpleAddTest1() {
        Completer myCompleter = new CompleterTrieDict();
        myCompleter.loadCsv("resources/wordscores01.csv");
        assertEquals(myCompleter.complete("e"),
                Arrays.asList("elapse", "ex", "exchange"));
        assertEquals(myCompleter.complete("empl"),
                new ArrayList<>());
        myCompleter.add("employee", 40);
        assertEquals(myCompleter.complete("empl"),
                Arrays.asList("employee"));
        assertEquals(myCompleter.complete("e"),
                Arrays.asList("elapse", "employee", "ex", "exchange"));
        myCompleter.add("event", 80);
        assertEquals(myCompleter.complete("e"),
                Arrays.asList("event", "elapse", "employee", "ex", "exchange"));
    }

    /**
     * Test to verify if resources/wordscores01.csv is
     * loaded properly and expected results returned
     */
    @Test
    void simpleCompleteTest1() {
        Completer myCompleter = new CompleterTrieDict();
        myCompleter.loadCsv("resources/wordscores01.csv");
        assertEquals(myCompleter.complete("a"),
                Arrays.asList("analyst", "aquarium", "ally", "ask"));
        assertEquals(myCompleter.complete("e"),
                Arrays.asList("elapse", "ex", "exchange"));
        assertEquals(myCompleter.complete("ex"),
                Arrays.asList("ex", "exchange"));
        assertEquals(myCompleter.complete("el"),
                Arrays.asList("elapse"));
    }
}
