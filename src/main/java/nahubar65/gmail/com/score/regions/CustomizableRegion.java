package nahubar65.gmail.com.score.regions;

import nahubar65.gmail.com.score.utils.Pair;

import java.util.Map;

public interface CustomizableRegion {

    Map<RegionFlag, Boolean> getFlags();

    Pair<RegionFlag, Boolean> getFlag(RegionFlag regionFlag);

    void addFlag(RegionFlag regionFlag, boolean b);

    void setDefaultFlags();
}