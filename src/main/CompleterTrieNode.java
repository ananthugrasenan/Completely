import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * CompleterTrieNode interface definition
 */
@JsonDeserialize(as = HashTrieNode.class)
public interface CompleterTrieNode {

    CompleterTrieNode insert(Character c);
    CompleterTrieNode search(Character c);
    default boolean isEndOfWord() {
        return false;
    }
    void setEndOfWord(boolean eow);
    void add(String name, int score);
    void add(String name, int score, char[] nameArr, int start);
    void addScoredMatch(ScoredMatch sm);
    List<ScoredMatch> getMatches();
    List<ScoredMatch> searchMatches(String prefix);
}
