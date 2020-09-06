package nahubar65.gmail.com.score.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;

public class Region implements Cuboid, ConfigurationSerializable {

    private int x1, y1, z1;

    private int x2, y2, z2;

    private World world;

    private String name;

    private Location bound1;

    private Location bound2;

    private final Map<Class<?>, Boolean> flags = new HashMap<>();

    public Region(String name, Location l1, Location l2) {
        if(l2.getWorld() != l1.getWorld())
            throw new IllegalArgumentException("¡Warp with name "+name+" have a two different worlds!");
        this.x1 = Math.min(l1.getBlockX(), l2.getBlockX());
        this.y1 = Math.min(l1.getBlockY(), l2.getBlockY());
        this.z1 = Math.min(l1.getBlockZ(), l2.getBlockZ());
        this.x2 = Math.max(l1.getBlockX(), l2.getBlockX());
        this.y2 = Math.max(l1.getBlockY(), l2.getBlockY());
        this.z2 = Math.max(l1.getBlockZ(), l2.getBlockZ());
        this.bound1 = l1;
        this.bound2 = l2;
        this.world = l1.getWorld();
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean contains(int x, int y, int z) {
        return x >= this.x1 && x <= this.x2 && y >= this.y1 && y <= this.y2 && z >= this.z1 && z <= this.z2;
    }

    @Override
    public boolean contains(Location l) {
        if (!this.world.getName().equals(l.getWorld().getName())) return false;
        return this.contains(l.getBlockX(), l.getBlockY(), l.getBlockZ());
    }

    @Override
    public int getTotalBlockSize() {
        return this.getHeight() * this.getXWidth() * this.getZWidth();
    }

    @Override
    public int getXWidth() {
        return this.x2 - this.x1 + 1;
    }

    @Override
    public int getZWidth() {
        return this.z2 - this.z1 + 1;
    }

    @Override
    public int getHeight() {
        return this.y2 - this.y1 + 1;
    }

    public Location getFirstBound(){
        return bound1;
    }

    public Location getSecondBound() {
        return bound2;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> objectMap = new HashMap<>();
        if (bound1 != null && bound2 != null) {
            objectMap.put("name", name);
            objectMap.put("1", bound1.serialize());
            objectMap.put("2", bound2.serialize());
            Map<String, Boolean> map = new HashMap<>();
            for (Map.Entry<Class<?>, Boolean> flag : flags.entrySet()) {
                map.put(flag.getKey().getSimpleName(), flag.getValue());
            }
            objectMap.put("flags", flags);
        }
        return objectMap;
    }

    public static Optional<Region> deserialize(Map<String, Object> values) {
        Region region = null;
        if (values.containsKey("1") && values.containsKey("2")) {
            region = new Region((String) values.get("name"), Location.deserialize((Map<String, Object>) values.get("1")), Location.deserialize((Map<String, Object>) values.get("2")));
            if (values.containsKey("flags")) {
                Map<String, Boolean> flags = (HashMap<String, Boolean>) values.get("flags");
                for (Map.Entry<String, Boolean> flag : flags.entrySet()) {
                    try {
                        Class<?> clazz = Class.forName(flag.getKey());
                        region.flags.put(clazz, flag.getValue());
                    } catch (ClassNotFoundException e) {
                        continue;
                    }
                }
            }
        }
        return Optional.ofNullable(region);
    }

    public Map<Class<?>, Boolean> getFlags() {
        return flags;
    }

    public void addFlag(RegionFlag regionFlag, boolean b){
        flags.put(regionFlag.getClazz(), b);
    }
}