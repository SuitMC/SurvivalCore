package nahubar65.gmail.com.score.utils;

import org.bukkit.Location;

public interface Cuboid {

    String getName();

    void setName(String name);

    boolean contains(int x, int y, int z);

    boolean contains(Location l);

    int getTotalBlockSize();

    int getXWidth();

    int getZWidth();

    int getHeight();
}