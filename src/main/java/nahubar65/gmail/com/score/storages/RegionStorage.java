package nahubar65.gmail.com.score.storages;

import nahubar65.gmail.com.score.SurvivalCore;
import nahubar65.gmail.com.score.configuration.Configuration;
import nahubar65.gmail.com.score.regions.GlobalRegionContainer;
import nahubar65.gmail.com.score.regions.Region;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.util.*;

public class RegionStorage implements Storage<String, Region> {

    private Map<String, Region> warpMap;

    private Configuration configuration;

    private GlobalRegionContainer globalRegionContainer;

    public RegionStorage(SurvivalCore survivalCore, GlobalRegionContainer globalRegionContainer){
        this.warpMap = new HashMap<>();
        this.configuration = new Configuration(survivalCore, new File(survivalCore.getFolder(), "regions.yml"));
        this.globalRegionContainer = globalRegionContainer;
    }

    @Override
    public Map<String, Region> get() {
        return warpMap;
    }

    @Override
    public Optional<Region> find(String key) {
        return Optional.ofNullable(warpMap.get(key));
    }

    public Optional<Region> findFromLoc(Location location){
        for (Region value : warpMap.values()) {
            if (value != null && value.contains(location)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Region> findFromData(String key) {
        ConfigurationSection configurationSection = configuration.getConfigurationSection("regions."+key);
        if (configurationSection != null) {
            return Optional.of(Region.deserialize(configurationSection.getValues(true)));
        }
        return Optional.empty();
    }

    @Override
    public void save(String key) {
        find(key).ifPresent(warp -> configuration.set(key, warp.serialize()));
        configuration.save();
    }

    @Override
    public void saveObject(String key, Region value) {

    }

    @Override
    public void remove(String key) {
        warpMap.remove(key);
        configuration.set("regions."+key, null);
    }

    @Override
    public void add(String key, Region value) {
        get().put(key, value);
        if (!globalRegionContainer.get().contains(value))
            globalRegionContainer.get().add(value);
    }

    @Override
    public void saveAll() {
        for (String s : warpMap.keySet()) {
            save(s);
        }
    }

    @Override
    public void loadAll() {
        ConfigurationSection configurationSection = configuration.getConfigurationSection("regions");
        if (configurationSection != null) {
            for (String key : configurationSection.getKeys(false)) {
                findFromData(key).ifPresent(region -> add(key, region));
            }
        }

    }
}