package nahubar65.gmail.com.score.regions;

import nahubar65.gmail.com.score.utils.Cuboid;
import nahubar65.gmail.com.score.utils.Pair;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;

public class Region implements Cuboid, CustomizableRegion, ConfigurationSerializable {

    protected int x1, y1, z1;

    protected int x2, y2, z2;

    protected World world;

    protected String name;

    protected Location min;

    protected Location max;

    protected final Map<RegionFlag, Boolean> flags = new HashMap<>();

    private Set<Location> edges;

    public Region(String name, Location l1, Location l2) {
        if(l2.getWorld() != l1.getWorld())
            throw new IllegalArgumentException("¡Region with name "+name+" have a two different worlds!");
        this.x1 = Math.min(l1.getBlockX(), l2.getBlockX());
        this.y1 = Math.min(l1.getBlockY(), l2.getBlockY());
        this.z1 = Math.min(l1.getBlockZ(), l2.getBlockZ());
        this.x2 = Math.max(l1.getBlockX(), l2.getBlockX());
        this.y2 = Math.max(l1.getBlockY(), l2.getBlockY());
        this.z2 = Math.max(l1.getBlockZ(), l2.getBlockZ());
        this.min = l1;
        this.max = l2;
        this.world = l1.getWorld();
        this.name = name;
        this.edges = new HashSet<>();
        refreshEdges();
    }

    public Region(Location l1, Location l2) {
        if(l2.getWorld() != l1.getWorld())
            throw new IllegalArgumentException("¡The world of locations cannot be different!");
        this.min = l1;
        this.max = l2;
        this.world = l1.getWorld();
        this.edges = new HashSet<>();
        refreshEdges();
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

    @Override
    public void setMin(Location min) {
        this.min = min;
        refreshEdges();
    }

    @Override
    public void setMax(Location max) {
        this.max = max;
        refreshEdges();
    }

    @Override
    public Location getMin(){
        return min;
    }

    @Override
    public Location getMax() {
        return max;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> objectMap = new HashMap<>();
        if (min != null && max != null) {
            objectMap.put("name", name);
            objectMap.put("1", min.serialize());
            objectMap.put("2", max.serialize());
            Map<String, Boolean> map = new HashMap<>();
            for (Map.Entry<RegionFlag, Boolean> flag : flags.entrySet()) {
                map.put(flag.getKey().name(), flag.getValue());
            }
            objectMap.put("flags", map);
        }
        return objectMap;
    }

    @Override
    public Map<RegionFlag, Boolean> getFlags() {
        return flags;
    }

    @Override
    public Pair<RegionFlag, Boolean> getFlag(RegionFlag regionFlag){
        boolean value = flags.containsKey(regionFlag) ? flags.get(regionFlag) : false;
        return new Pair<>(regionFlag, value);
    }

    @Override
    public void addFlag(RegionFlag regionFlag, boolean b){
        flags.put(regionFlag, b);
    }

    @Override
    public void setDefaultFlags() {
        for (RegionFlag regionFlag : RegionFlag.defaultFlags()) {
            flags.put(regionFlag, false);
        }
    }

    @Override
    public Set<Location> getEdges(){
        return edges;
    }

    private void refreshEdges(){
        edges.clear();
        this.x1 = Math.min(min.getBlockX(), max.getBlockX());
        this.y1 = Math.min(min.getBlockY(), max.getBlockY());
        this.z1 = Math.min(min.getBlockZ(), max.getBlockZ());
        this.x2 = Math.max(min.getBlockX(), max.getBlockX());
        this.y2 = Math.max(min.getBlockY(), max.getBlockY());
        this.z2 = Math.max(min.getBlockZ(), max.getBlockZ());
        for(double y = y1; y <= y2; y++) {
            for (double x = x1; x <= x2; x++) {
                edges.add(new Location(world, x, y, min.getZ()));
            }
            for (double z = z1; z <= z2; z++) {
                edges.add(new Location(world, min.getX(), y, z));
            }
            for (double x = x2; x >= x1; x--) {
                edges.add(new Location(world, x, y, max.getZ()));
            }
            for (double z = z2; z >= z1; z--) {
                edges.add(new Location(world, max.getX(), y, z));
            }
        }
    }

    public static Region deserialize(Map<String, Object> values) {
        Region region = null;
        if (values.containsKey("1") && values.containsKey("2")) {
            region = new Region((String) values.get("name"), Location.deserialize((Map<String, Object>) values.get("1")), Location.deserialize((Map<String, Object>) values.get("2")));
            if (values.containsKey("flags")) {
                Map<String, Boolean> flags = (Map<String, Boolean>) values.get("flags");
                for (Map.Entry<String, Boolean> flag : flags.entrySet()) {
                    try {
                        RegionFlag regionFlag = RegionFlag.valueOf(flag.getKey());
                        region.flags.put(regionFlag, flag.getValue());
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        }
        return region;
    }
}