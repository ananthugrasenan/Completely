import java.util.ArrayList;
import java.util.List;

public class CompleterTrie implements Completer {

    CompleterTrieNode root;

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

    public void load() {

    }

    public void add(String name, int score) {
        CompleterTrieNode curr = root;
        for (char c: name.toCharArray()) {
            curr = curr.insert(c);
        }
        curr.markEndOfWord();
        curr.addScoredMatch(name, score);
    }
}
