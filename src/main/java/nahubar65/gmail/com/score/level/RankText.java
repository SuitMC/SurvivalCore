package nahubar65.gmail.com.score.level;

public class RankText implements Rank {

    private String prefix;

    private int rank;

    public RankText(String prefix, int rank) {
        this.prefix = prefix;
        this.rank = rank;
    }

    public String getPrefix() {
        return prefix;
    }


    @Override
    public int getRank() {
        return rank;
    }
}