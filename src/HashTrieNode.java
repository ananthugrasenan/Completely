import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class HashTrieNode implements CompleterTrieNode {

    private boolean endOfWord;
    private CompleterTrieNode parent;
    private HashMap<Character, CompleterTrieNode> children;
    private List<ScoredMatch> matches;

    public HashTrieNode(CompleterTrieNode parent) {
        endOfWord = false;
        this.parent = parent;
        this.children = new HashMap<>();
        this.matches = new ArrayList<>();
    }

    @Override
    public CompleterTrieNode insert(Character c) {
        CompleterTrieNode node = search(c);
        if (node == null) {
            node = new HashTrieNode(this);
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
    public void markEndOfWord() {
        endOfWord = true;
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
        if (getParent() != null) getParent().addScoredMatch(sm);
    }

    public List<ScoredMatch> getMatches() {
        return matches;
    }

    public CompleterTrieNode getParent() {
        return parent;
    }
}

