package nahubar65.gmail.com.score.plots;

import nahubar65.gmail.com.score.configuration.Configuration;
import nahubar65.gmail.com.score.loader.Loadable;
import nahubar65.gmail.com.score.storages.Storage;
import org.bukkit.entity.Player;

import java.util.*;

public final class PlotOptions implements Loadable {

    private Configuration configuration;

    private int maxRegionsPerPlayer = 3;

    private int maxMembersPerRegion = 4;

    private int pricePerBlock = 2;

    private Storage<UUID, List<PlotRegion>> storage;

    private Map<String, Integer> registeredRegionPermissions;

    private Map<String, Integer> registeredMemberPermissions;

    public PlotOptions(Configuration configuration, Storage<UUID, List<PlotRegion>> storage) {
        this.configuration = configuration;
        this.storage = storage;
    }

    @Override
    public void load() {
        this.maxRegionsPerPlayer = configuration.getInt("regions.max-regions-per-player");
        this.pricePerBlock = configuration.getInt("regions.price-per-block");
        this.maxMembersPerRegion = configuration.getInt("regions.max-members-per-region");
        this.registeredRegionPermissions = new HashMap<>();
        this.registeredMemberPermissions = new HashMap<>();
        for (String s : configuration.getStringList("regions.region-bypass-permissions")) {
            Integer integer = Integer.valueOf(s);
            this.registeredRegionPermissions.put("score.plot.bypasslimit."+integer, integer);
        }
        for (String s : configuration.getStringList("regions.region-member-bypass-permissions")) {
            Integer integer = Integer.valueOf(s);
            this.registeredMemberPermissions.put("score.plot.members.bypasslimit."+integer, integer);
        }
    }

    public boolean bypassRegionLimit(Player player) {
        int numberOfRegions = storage.find(player.getUniqueId()).get().size();
        int maxRegionsPerPlayer1 = maxRegionsPerPlayer;
        for (Map.Entry<String, Integer> stringIntegerEntry : registeredRegionPermissions.entrySet()) {
            if (player.hasPermission(stringIntegerEntry.getKey())) {
                maxRegionsPerPlayer1 = stringIntegerEntry.getValue();
            }
        }
        return numberOfRegions < maxRegionsPerPlayer1;
    }

    public boolean bypassMemberLimit(Player player, MemberManager memberManager) {
        List<UUID> all = memberManager.all();
        all.remove(memberManager.getOwner());
        int numberOfMembers = all.size();
        int maxMembersPerRegion1 = maxMembersPerRegion;
        for (Map.Entry<String, Integer> stringIntegerEntry : registeredRegionPermissions.entrySet()) {
            if (player.hasPermission(stringIntegerEntry.getKey())) {
                maxMembersPerRegion1 = stringIntegerEntry.getValue();
            }
        }
        return player.hasPermission("score.plot.bypass.all") || numberOfMembers < maxMembersPerRegion1;
    }

    public int getMaxMembersPerRegion() {
        return maxMembersPerRegion;
    }

    public int getMaxRegionsPerPlayer() {
        return maxRegionsPerPlayer;
    }
}