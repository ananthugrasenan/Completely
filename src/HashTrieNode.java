import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class HashTrieNode implements CompleterTrieNode {

    boolean endOfWord;
    CompleterTrieNode parent;
    HashMap<Character, CompleterTrieNode> children;
    List<ScoredMatch> matches;

    public HashTrieNode(CompleterTrieNode parent) {
        endOfWord = false;
        this.parent = parent;
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

    public void addScoredMatch(String match, int score) {
        if (matches.size() < CompleterTrieNode.MATCH_SIZE) {
            addAndSortScoredMatch(match, score);
        }
        else {
            ScoredMatch sm = matches.get(matches.size()-1);
            if (sm.score < score) {
                matches.remove(matches.size()-1);
                addAndSortScoredMatch(match, score);
            }
        }
    }

    private void addAndSortScoredMatch(String match, int score) {
        matches.add(new ScoredMatch(match, score));
        Collections.sort(matches);
        if (getParent() != null) getParent().addScoredMatch(match, score);
    }

    public List<ScoredMatch> getMatches() {
        return matches;
    }

    public CompleterTrieNode getParent() {
        return parent;
    }
}

