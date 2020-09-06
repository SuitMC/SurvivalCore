package nahubar65.gmail.com.score.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Warp implements ConfigurationSerializable {

    private Location spawnPoint;

    private String name;

    private World world;

    private Region region;

    public Warp(Location spawnPoint, String name, Location l1, Location l2) {
        this.region = new Region(name, l1, l2);
        this.spawnPoint = spawnPoint;
        this.name = name;
    }

    public Warp(Location spawnPoint, String name) {
        this.world = spawnPoint.getWorld();
        this.spawnPoint = spawnPoint;
        this.name = name;
    }

    public Location getSpawnPoint() {
        return spawnPoint;
    }

    public void setSpawnPoint(Location spawnPoint){
        this.spawnPoint = spawnPoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("name", getName());
        if (region != null) {
            objectMap.put("regionName", region.getName());
        }
        objectMap.put("spawnPoint", spawnPoint.serialize());
        return objectMap;
    }

    public static Optional<Warp> deserialize(Map<String, Object> objectMap){
        Warp warp = null;
        try {
            String name = (String) objectMap.get("name");
            MemorySection memorySection = (MemorySection) objectMap.get("spawnPoint");
            Location spawnPoint = Location.deserialize(memorySection.getValues(true));
            warp = new Warp(spawnPoint, name);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return Optional.ofNullable(warp);
    }

    public static Optional<Warp> deserialize(Map<String, Object> objectMap, Region region){
        Warp warp = null;
        try {
            String name = (String) objectMap.get("name");
            MemorySection memorySection = (MemorySection) objectMap.get("spawnPoint");
            Location spawnPoint = Location.deserialize(memorySection.getValues(true));
            warp = new Warp(spawnPoint, name);
            warp.region = region;
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return Optional.ofNullable(warp);
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}