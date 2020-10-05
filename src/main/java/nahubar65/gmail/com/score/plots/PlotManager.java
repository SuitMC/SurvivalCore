package nahubar65.gmail.com.score.plots;

import nahubar65.gmail.com.score.regions.Region;
import nahubar65.gmail.com.score.utils.Pair;
import org.bukkit.Location;

import java.util.UUID;

public interface PlotManager {

    boolean giveRegion(UUID uuid, Pair<Location, Location> locationPair, String name);

    void giveRegion(UUID uuid, PlotRegion plotRegion);

    void removeRegion(UUID uuid, PlotRegion plotRegion);

    double calculatePrice(Region region);
}