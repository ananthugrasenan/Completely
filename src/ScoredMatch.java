public class ScoredMatch implements Comparable<ScoredMatch> {
    Integer score;
    String matchStr;

    public ScoredMatch(String matchStr, Integer score) {
        this.score = score;
        this.matchStr = matchStr;
    }

    public int compareTo(ScoredMatch other) {
        // return score.compareTo(other.score);
        return other.getScore().compareTo(score);
    }

    public Integer getScore() {
        return score;
    }

    public String getMatchStr() {
        return matchStr;
    }
}
