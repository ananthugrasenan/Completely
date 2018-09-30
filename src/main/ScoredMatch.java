import com.fasterxml.jackson.annotation.JsonProperty;

public class ScoredMatch implements Comparable<ScoredMatch> {
    private Integer score;
    private String matchStr;

    public ScoredMatch(
            @JsonProperty("matchStr") String matchStr,
            @JsonProperty("score") Integer score) {
        this.score = score;
        this.matchStr = matchStr;
    }

    public int compareTo(ScoredMatch other) {
        // reversed Integer compareTo
        return other.getScore().compareTo(score);
    }

    public Integer getScore() {
        return score;
    }

    public String getMatchStr() {
        return matchStr;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != getClass()) return false;
        if (this == o) return true;
        ScoredMatch sm = (ScoredMatch) o;
        return getMatchStr().equals(sm.getMatchStr());
    }

    @Override
    public int hashCode(){
        return getMatchStr().hashCode();
    }
}
