package nahubar65.gmail.com.score.utils;

import nahubar65.gmail.com.score.regions.RegionFlag;
import org.bukkit.Location;

import java.util.Map;
import java.util.Set;

public interface Cuboid {

    String getName();

    void setName(String name);

    boolean contains(int x, int y, int z);

    boolean contains(Location l);

    int getTotalBlockSize();

    int getXWidth();

    int getZWidth();

    int getHeight();

    void setMin(Location location);

    void setMax(Location location);

    Location getMin();

    Location getMax();

    Set<Location> getEdges();
}