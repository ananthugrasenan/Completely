import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Implementation of CompleterTrieNode with HashMap to
 * hold children.
 */
public class HashTrieNode implements CompleterTrieNode {

    private boolean endOfWord;
    private HashMap<Character, CompleterTrieNode> children;
    private List<ScoredMatch> matches;

    public HashTrieNode() {
        endOfWord = false;
        this.children = new HashMap<>();
        this.matches = new ArrayList<>();
    }

    @Override
    public CompleterTrieNode insert(Character c) {
        CompleterTrieNode node = search(c);
        if (node == null) {
            node = new HashTrieNode();
            children.put(c, node);
        }
        return node;
    }

    @Override
    public CompleterTrieNode search(Character c) {
        return children.get(c);
    }

    @Override
    public boolean isEndOfWord() {
        return endOfWord;
    }

    @Override
    public void setEndOfWord(boolean eow) {
        this.endOfWord = eow;
    }

    public void addScoredMatch(ScoredMatch sm) {
        if (matches.contains(sm)) {
            matches.remove(sm);
        }
        else if (matches.size() >= Completer.MATCH_SIZE) {
            ScoredMatch lowest = matches.get(matches.size()-1);
            if (lowest.getScore() < sm.getScore()) {
                matches.remove(matches.size()-1);
            }
        }
        addAndSortScoredMatch(sm);
    }

    private void addAndSortScoredMatch(ScoredMatch sm) {
        matches.add(sm);
        Collections.sort(matches);
    }

    public List<ScoredMatch> searchMatches(String prefix) {
        CompleterTrieNode curr = this;
        if (prefix != null && !prefix.trim().isEmpty()) {
            for (char c : prefix.toCharArray()) {
                curr = curr.search(c);
                // If it does not go all the way return empty results
                if (curr == null) return new ArrayList<>();
            }
        }
        // Return a new list with contents
        return new ArrayList<>(curr.getMatches());
    }

    public void add(String name, int score) {
        add(name, score, name.toCharArray(), 0);
    }

    public void add(String name, int score, char[] nameArr, int start) {
        CompleterTrieNode curr = this;
        ScoredMatch sm = new ScoredMatch(name, score);
        for (int i=start; i<nameArr.length; i++) {
            // Call insert to get to node at the next
            // lower level and then add the match there
            curr = curr.insert(nameArr[i]);
            curr.addScoredMatch(sm);
        }
        curr.setEndOfWord(true);
    }

    public List<ScoredMatch> getMatches() {
        return matches;
    }

    public void setMatches(List<ScoredMatch> matches) {
        this.matches = matches;
    }

   public HashMap<Character, CompleterTrieNode> getChildren() {
        return children;
    }

    public void setChildren(HashMap<Character, CompleterTrieNode> children) {
        this.children = children;
    }

}

