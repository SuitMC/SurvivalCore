package nahubar65.gmail.com.score.plots;

import nahubar65.gmail.com.score.regions.Region;
import nahubar65.gmail.com.score.regions.RegionFlag;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;

public interface PlotRegion extends ConfigurationSerializable {

    Region getRegion();

    void setRegion(Region region);

    MemberManager getMemberManager();

    PlotRequestSender getPlotRequestSender();

    @Override
    default Map<String, Object> serialize() {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("memberManager", getMemberManager().serialize());
        objectMap.put("region", getRegion().serialize());
        return objectMap;
    }

    static PlotRegion deserialize(Map<String, Object> objectMap) {
        if(!objectMap.containsKey("region") || !objectMap.containsKey("memberManager"))
            throw new IllegalArgumentException("Parameters empty in the objectMap");
        MemberManager memberManager = MemberManager.deserialize((Map<String, Object>) objectMap.get("memberManager"));
        Region region = Region.deserialize((Map<String, Object>) objectMap.get("region"));
        return new SimplePlotRegion(region, memberManager);
    }

    static PlotRegion createPlot(MemberManager memberManager, Region region) {
        SimplePlotRegion plotRegion = new SimplePlotRegion(
                region,
                memberManager);
        for (RegionFlag regionFlag : RegionFlag.plotFlags()) {
            plotRegion.getRegion().getFlags().put(regionFlag, false);
        }
        return plotRegion;
    }
}