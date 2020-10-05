package nahubar65.gmail.com.score.plots;

import nahubar65.gmail.com.score.regions.Region;
import nahubar65.gmail.com.score.storages.Storage;
import nahubar65.gmail.com.score.utils.Pair;
import nahubar65.gmail.com.score.vault.VaultEconomy;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

public class SimplePlotManager implements PlotManager {

    private Storage<UUID, List<PlotRegion>> plotRegionStorage;

    private int pricePerBlock;

    private VaultEconomy vaultEconomy;

    public SimplePlotManager(int pricePerBlock, Storage<UUID, List<PlotRegion>> plotRegionStorage){
        this.plotRegionStorage = plotRegionStorage;
        this.pricePerBlock = pricePerBlock;
        this.vaultEconomy = new VaultEconomy();
    }

    @Override
    public boolean giveRegion(UUID uuid, Pair<Location, Location> locationPair, String name) {
        PlotRegion plotRegion = PlotRegion.createPlot(MemberManager.newMemberManager(uuid), new Region(name, locationPair.getKey(), locationPair.getValue()));
        double price = calculatePrice(plotRegion.getRegion());
        if (vaultEconomy.hasMoney(uuid, price)) {
            plotRegionStorage.find(uuid).ifPresent(list -> {
                list.add(plotRegion);
                plotRegionStorage.add(uuid, list);
            });
            vaultEconomy.remove(uuid, price);
            return true;
        }
        return false;
    }

    @Override
    public void giveRegion(UUID uuid, PlotRegion plotRegion) {
        List<PlotRegion> plotRegionList = plotRegionStorage.find(uuid).get();
        plotRegionList.add(plotRegion);
        plotRegionStorage.add(uuid, plotRegionList);
    }

    @Override
    public void removeRegion(UUID uuid, PlotRegion plotRegion) {
        List<PlotRegion> plotRegionList = plotRegionStorage.find(uuid).get();
        plotRegionList.remove(plotRegion);
        plotRegionStorage.add(uuid, plotRegionList);
    }

    @Override
    public double calculatePrice(Region region) {
        return region.getTotalBlockSize() * pricePerBlock;
    }
}