import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
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

    /**
     * Simple test to serialize deserialize to file
     * @throws IOException
     */
    @Test
    void simpleSerializeToFileTest() throws IOException {
        CompleterTrieDict myCompleter = new CompleterTrieDict();
        myCompleter.loadCsv("resources/wordscores01.csv");
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File("tmp/dict1.json");
        mapper.writeValue(jsonFile, myCompleter);
        // Read back from json file
        ObjectMapper readMapper = new ObjectMapper();
        CompleterTrieDict readBackCompleter = readMapper.readValue(jsonFile, CompleterTrieDict.class);
        assertEquals(readBackCompleter.complete("a"),
                Arrays.asList("analyst", "aquarium", "ally", "ask"));
        assertEquals(readBackCompleter.complete("e"),
                Arrays.asList("elapse", "ex", "exchange"));
        assertEquals(readBackCompleter.complete("ex"),
                Arrays.asList("ex", "exchange"));
        assertEquals(readBackCompleter.complete("el"),
                Arrays.asList("elapse"));
        jsonFile.delete();
    }

    /**
     * Test matches with underscores
     */
    @Test
    void underscoreCompleteTest1() {
        Completer myCompleter = new CompleterTrieDict();
        myCompleter.loadCsv("resources/wordscores02.csv");
        myCompleter.loadCsv("resources/wordscores03.csv");
        assertEquals(myCompleter.complete("ban"),
                Arrays.asList("band", "banquet", "bank", "banner", "banana", "bang", "ban", "banish"));
        myCompleter.loadCsv("resources/wordscores04.csv");
        assertEquals(myCompleter.complete("ban"),
                Arrays.asList("banana_reveal", "unrest_banner", "band", "ban_dose", "seem_banquet", "banquet",
                        "computer_bang", "commerce_ban", "bank", "banner"));
        myCompleter.add("ban_this_please", 95);
        myCompleter.add("do_not_ban_me", 97);
        assertEquals(myCompleter.complete("ban"),
                Arrays.asList("do_not_ban_me", "ban_this_please", "banana_reveal",
                        "unrest_banner", "band", "ban_dose", "seem_banquet", "banquet",
                        "computer_bang", "commerce_ban"));
    }
}
