import java.util.List;

public interface CompleterTrieNode {

    CompleterTrieNode insert(Character c);
    CompleterTrieNode search(Character c);
    default boolean isEndOfWord() {
        return false;
    }
    void markEndOfWord();
    void addScoredMatch(ScoredMatch sm);
    List<ScoredMatch> getMatches();
}
