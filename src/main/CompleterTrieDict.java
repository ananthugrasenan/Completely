import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Completer implementation with Trie
 */
public class CompleterTrieDict implements Completer {

    private HashMap<String, CompleterTrieNode> rootNodes;

    public CompleterTrieDict() {
        rootNodes = new HashMap<>();
        rootNodes.put("default", new HashTrieNode());
        rootNodes.put("underscore", new HashTrieNode());
    }

    public List<String> complete(String prefix) {
        // Search in main trie root
        CompleterTrieNode defaultRoot = getRootNode("default");
        List<ScoredMatch> matches = defaultRoot.searchMatches(prefix);
        // Search in underscored trie root
        CompleterTrieNode underscoredRoot = getRootNode("underscore");
        List<ScoredMatch> underscoredMatches = underscoredRoot.searchMatches(prefix);
        // Combine, sort and pick top matches
        matches.addAll(underscoredMatches);
        Collections.sort(matches);
        List<String> results = matches.stream().limit(MATCH_SIZE)
                .map(m -> m.getMatchStr()).collect(Collectors.toList());
        return results;
    }

    public void loadCsv(String csvFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
            reader.lines().forEach(line -> {
                String[] pair = line.split(",");
                if (pair.length >= 2)
                    add(pair[0], Integer.parseInt(pair[1]));
            });
            reader.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void add(String name, int score) {
        CompleterTrieNode defaultRoot = getRootNode("default");
        char[] nameArr = name.toCharArray();
        defaultRoot.add(name, score, nameArr, 0);
        // Handle underscore
        if (name.length() > 1) {
            int index = 0;
            while ((index = name.indexOf('_', index)) > -1
                    && index < name.length() - 1) {
                CompleterTrieNode underscoredRoot = getRootNode("underscore");
                underscoredRoot.add(name, score, nameArr, ++index);
            }
        }
    }

    CompleterTrieNode getRootNode(String rootName) {
        if (rootName != null && rootNodes.containsKey(rootName)) {
            return rootNodes.get(rootName);
        }
        return rootNodes.get("default");
    }

    public Map<String, CompleterTrieNode> getAllRoots() {
        return rootNodes;
    }
}
