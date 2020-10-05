package nahubar65.gmail.com.score.utils;

import nahubar65.gmail.com.score.actions.AbstractAction;
import nahubar65.gmail.com.score.actions.ItemActionFactory;
import nahubar65.gmail.com.score.plots.PlotRegion;
import nahubar65.gmail.com.score.regions.Region;
import nahubar65.gmail.com.score.storages.Storage;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static int getCuboidSize(Location loc1, Location loc2) {
        return new Region(loc1, loc2).getTotalBlockSize();
    }

    public static void giveAbstractActionItem(Player player, AbstractAction abstractAction, ItemStack itemStack){
        player.getInventory().addItem(ItemActionFactory.setSpecialItemAction(itemStack, abstractAction));
    }

    public static List<UUID> uuidListFromString(List<String> stringList) {
        List<UUID> uuidList = new ArrayList<>();
        for (String s : stringList) {
            uuidList.add(UUID.fromString(s));
        }
        return uuidList;
    }

    public static List<String> stringListFromUuid(List<UUID> uuidList) {
        List<String> stringList = new ArrayList<>();
        for (UUID uuid : uuidList) {
            stringList.add(uuid.toString());
        }
        return stringList;
    }

    public static <T> List<T> fromArray(T[] array){
        List<T> list = new ArrayList<>();
        for (T t : array) {
            list.add(t);
        }
        return list;
    }

    public static PlotRegion findFromName(String name, UUID uuid, Storage<UUID, List<PlotRegion>> storage) {
        Optional<List<PlotRegion>> optionalPlotRegions = storage.find(uuid);
        if (optionalPlotRegions.isPresent()) {
            List<PlotRegion> plotRegionList = optionalPlotRegions.get();
            for (PlotRegion region : plotRegionList) {
                if (region.getRegion().getName().equalsIgnoreCase(name)) {
                    return region;
                }
            }
        }
        return null;
    }

    public static PlotRegion findFromName(String name, Storage<UUID, List<PlotRegion>> storage) {
        for (List<PlotRegion> value : storage.get().values()) {
            for (PlotRegion region : value) {
                if (region.getRegion().getName().equalsIgnoreCase(name)) {
                    return region;
                }
            }
        }
        return null;
    }
}