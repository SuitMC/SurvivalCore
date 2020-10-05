package nahubar65.gmail.com.score.level;

public class SimpleRank implements Rank {

    private int rank;

    public SimpleRank(int rank) {
        this.rank = rank;
    }

    @Override
    public int getRank() {
        return rank;
    }
}