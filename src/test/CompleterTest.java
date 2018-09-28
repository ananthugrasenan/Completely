import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompleterTest {

    @Test
    public void simpleAddTest1() {
        Completer myCompleter = new CompleterTrieDict();
        myCompleter.loadCsv("resources/wordscores01.csv");
        assertEquals(myCompleter.complete("e"),
                Arrays.asList("elapse", "ex", "exchange"));
        assertEquals(myCompleter.complete("empl"),
                Arrays.asList());
        myCompleter.add("employee", 80);
        assertEquals(myCompleter.complete("empl"),
                Arrays.asList("employee"));
        assertEquals(myCompleter.complete("e"),
                Arrays.asList("employee", "elapse", "ex", "exchange"));
    }

    @Test
    public void simpleCompleteTest1() {
        Completer myCompleter = new CompleterTrieDict();
        myCompleter.loadCsv("resources/wordscores01.csv");
        assertEquals(myCompleter.complete("e"),
                Arrays.asList("elapse", "ex", "exchange"));
        assertEquals(myCompleter.complete("ex"),
                Arrays.asList("ex", "exchange"));
        assertEquals(myCompleter.complete("el"),
                Arrays.asList("elapse"));
    }
}
