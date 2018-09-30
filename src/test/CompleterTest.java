import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    /**
     * Simple serialize deserialize test
     * @throws IOException
     */
    @Test
    void simpleSerializeTest() throws IOException {
        CompleterTrieDict myCompleter = new CompleterTrieDict();
        myCompleter.loadCsv("resources/wordscores01.csv");
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(myCompleter);
        assertTrue(result.contains("aquarium"));
        assertTrue(result.contains("elapse"));
        // Read back
        ObjectMapper readMapper = new ObjectMapper();
        CompleterTrieDict readBackCompleter = readMapper.readValue(result, CompleterTrieDict.class);
        assertEquals(readBackCompleter.complete("a"),
                Arrays.asList("analyst", "aquarium", "ally", "ask"));
        assertEquals(readBackCompleter.complete("e"),
                Arrays.asList("elapse", "ex", "exchange"));
        assertEquals(readBackCompleter.complete("ex"),
                Arrays.asList("ex", "exchange"));
        assertEquals(readBackCompleter.complete("el"),
                Arrays.asList("elapse"));
    }
}
