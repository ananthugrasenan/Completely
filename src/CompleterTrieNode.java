import java.util.List;

public interface CompleterTrieNode {

    CompleterTrieNode insert(Character c);
    CompleterTrieNode search(Character c);
    default boolean isEndOfWord() {
        return false;
    }
    void markEndOfWord();
    void add(String name, int score);
    void add(String name, int score, char[] nameArr, int start);
    void addScoredMatch(ScoredMatch sm);
    List<ScoredMatch> getMatches();
    List<ScoredMatch> searchMatches(String prefix);
}
