package nahubar65.gmail.com.score.level;

public interface Rank {

    int getRank();

    default boolean major(Rank rank) {
        return getRank() > rank.getRank();
    }

    default boolean minor(Rank rank) {
        return  getRank() < rank.getRank();
    }

    default Rank getMajor(){
        return new SimpleRank(getRank() + 1);
    }

    default Rank getMinor(){
        return new SimpleRank(getRank() - 1);
    }
}