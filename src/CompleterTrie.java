import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompleterTrie implements Completer {

    private CompleterTrieNode root;

    public CompleterTrie() {
        root = new HashTrieNode(null);
    }

    public List<String> complete(String prefix) {
        CompleterTrieNode curr = root;
        List<String> results = new ArrayList<>();
        for (char c: prefix.toCharArray()) {
            curr = curr.search(c);
            // If it does not go all the way return empty results
            if (curr == null) return results;
        }
        List<ScoredMatch> directMatches = curr.getMatches();
        directMatches.forEach(m -> results.add(m.getMatchStr()));
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
        CompleterTrieNode curr = root;
        for (char c: name.toCharArray()) {
            curr = curr.insert(c);
        }
        curr.markEndOfWord();
        curr.addScoredMatch(new ScoredMatch(name, score));
    }
}
