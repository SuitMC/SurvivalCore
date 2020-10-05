package nahubar65.gmail.com.score.storages;

import nahubar65.gmail.com.score.configuration.Configuration;
import nahubar65.gmail.com.score.regions.Region;
import nahubar65.gmail.com.score.utils.Warp;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class WarpStorage implements Storage<String, Warp>{

    private Map<String, Warp> warpMap;

    private Configuration configuration;

    private RegionStorage regionStorage;

    public WarpStorage(Configuration configuration, RegionStorage regionStorage){
        this.warpMap = new HashMap<>();
        this.configuration = configuration;
        this.regionStorage = regionStorage;
    }

    @Override
    public Map<String, Warp> get() {
        return warpMap;
    }

    @Override
    public Optional<Warp> find(String key) {
        return Optional.ofNullable(warpMap.get(key));
    }

    @Override
    public Optional<Warp> findFromData(String key) {
        ConfigurationSection configurationSection = configuration.getConfigurationSection("warps."+key);
        if (configurationSection != null) {
            Map<String, Object> objectMap = configurationSection.getValues(true);
            AtomicReference<Region> atomicReference = new AtomicReference<>();
            if (objectMap.containsKey("regionName")) {
                regionStorage.find((String) objectMap.get("regionName")).ifPresent(region1 -> {
                    atomicReference.set(region1);
                });
                return Warp.deserialize(objectMap, atomicReference.get());
            }
            return Warp.deserialize(objectMap);
        }
        return Optional.empty();
    }

    @Override
    public void save(String key) {
        find(key).ifPresent(warp -> configuration.set("warps."+key, warp.serialize()));
        configuration.save();
    }

    @Override
    public void saveObject(String key, Warp value) {

    }

    @Override
    public void remove(String key) {
        warpMap.remove(key);
        configuration.set(key, null);
    }

    @Override
    public void add(String key, Warp value) {
        get().put(key, value);
    }

    @Override
    public void saveAll() {
        for (String s : warpMap.keySet()) {
            save(s);
        }
    }

    @Override
    public void loadAll() {
        ConfigurationSection configurationSection = configuration.getConfigurationSection("warps");
        if (configurationSection == null) {
            return;
        }
        for (String key : configuration.getConfigurationSection("warps").getKeys(false)) {
            findFromData(key).ifPresent(warp -> add(key, warp));
        }
    }
}