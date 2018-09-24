import java.util.List;

public interface CompleterTrieNode {
    int MATCH_SIZE = 10;

    CompleterTrieNode insert(Character c);
    CompleterTrieNode search(Character c);
    default boolean isEndOfWord() {
        return false;
    }
    void markEndOfWord();
    void addScoredMatch(String match, int score);
    List<ScoredMatch> getMatches();
}
