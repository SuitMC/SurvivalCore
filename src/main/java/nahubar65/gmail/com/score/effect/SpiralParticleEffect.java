package nahubar65.gmail.com.score.effect;

import fr.mrmicky.fastparticle.FastParticle;
import nahubar65.gmail.com.score.SurvivalCore;
import nahubar65.gmail.com.score.particle.ParticleModel;
import nahubar65.gmail.com.score.particle.SimpleParticleModelImpl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SpiralParticleEffect implements ParticleEffect {

    private Location location;

    private double radius;

    private ParticleModel particleModel;

    private double angle;

    private int taskID;

    private double y;

    private int numberOfCircumferences;

    private boolean wait = false;

    private boolean increaseY = true;

    private double limit;

    private int spaceBetweenParticles = 1;

    private Object particleSender;

    private int ticks;

    private SpiralParticleEffect(double radius, ParticleModel particleModel, Location center, double limit){
        this.location = center.clone();
        this.radius = radius;
        this.limit = limit;
        this.particleModel = particleModel;
    }

    private SpiralParticleEffect(double radius, ParticleModel particleModel, Location center, double limit, int spaceBetweenParticles){
        this.location = center.clone();
        this.radius = radius;
        this.limit = limit;
        this.spaceBetweenParticles = spaceBetweenParticles;
        this.particleModel = particleModel;
    }

    public SpiralParticleEffect(World world, double radius, ParticleModel particleModel, Location center, double limit, int spaceBetweenParticles){
        this(radius, particleModel, center, limit, spaceBetweenParticles);
        this.particleSender = world;
    }

    public SpiralParticleEffect(Player player, double radius, ParticleModel particleModel, Location center, double limit, int spaceBetweenParticles){
        this(radius, particleModel, center, limit, spaceBetweenParticles);
        this.particleSender = player;
    }

    public SpiralParticleEffect(World world, double radius, ParticleModel particleModel, Location center, double limit){
        this(radius, particleModel, center, limit);
        this.particleSender = world;
    }

    public SpiralParticleEffect(Player player, double radius, ParticleModel particleModel, Location center, double limit){
        this(radius, particleModel, center, limit);
        this.particleSender = player;
    }

    public void start(int ticks) {
        this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(SurvivalCore.plugin(), this, 0, 1);
        this.ticks = ticks;
    }

    @Override
    public void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

    @Override
    public void run() {
        double angleToDegrees = Math.toDegrees(angle);
        if(angleToDegrees >= (360 * spaceBetweenParticles)) {
            numberOfCircumferences +=1;
            angle = 0;
            if (!wait) {
                wait = true;
            } else {
                wait = false;
            }
        }
        increaseY = (numberOfCircumferences % 4 == 0);
        double cos = radius * Math.cos(angle);
        double sin = radius * Math.sin(angle);
        location.add(cos, y, sin);
        if (particleModel.isColored()) {
            spawnParticle(location);
        }else{
            spawnParticle(location);
        }
        location.subtract(cos, y, sin);
        angle+=0.1 * spaceBetweenParticles;
        if (wait) return;
        if (increaseY) {
            y+=0.02 * limit;
        }else{
            y-=0.02 * limit;
        }
    }

    private void spawnParticle(Location location){
        if (particleSender instanceof Player) {
            Player player = (Player) particleSender;
            FastParticle.spawnParticle(player, particleModel.getParticleType(), location, particleModel.amount(), particleModel.offSetX(), particleModel.offSetY(), particleModel.offSetZ(), 1, 0);
        } else if (particleSender instanceof World) {
            World world = (World) particleSender;
            FastParticle.spawnParticle(world, particleModel.getParticleType(), location, particleModel.amount(), particleModel.offSetX(), particleModel.offSetY(), particleModel.offSetZ(), 0, 0);
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("location", location.serialize());
        objectMap.put("radius", radius);
        objectMap.put("limit", limit);
        objectMap.put("spaceBetweenParticles", spaceBetweenParticles);
        objectMap.put("particleModel", particleModel.serialize());
        objectMap.put("ticks", ticks);
        objectMap.put("type", ParticleEffectType.SPIRAL.name());
        return objectMap;
    }

    public static Optional<ParticleEffect> deserialize(Map<String, Object> objectMap, boolean start){
        MemorySection locationSection = (MemorySection) objectMap.get("location");
        Location location = Location.deserialize(locationSection.getValues(true));
        double radius = NumberConversions.toDouble(objectMap.get("radius"));
        double limit = NumberConversions.toDouble(objectMap.get("limit"));
        int spaceBetweenParticles = NumberConversions.toInt(objectMap.get("spaceBetweenParticles"));
        World world = location.getWorld();
        MemorySection particleModelSection = (MemorySection) objectMap.get("particleModel");
        Optional<SimpleParticleModelImpl> simpleParticleModelOptional = ParticleModel.deserialize(particleModelSection.getValues(true));
        if (simpleParticleModelOptional.isPresent()) {
            ParticleEffect particleEffect = new SpiralParticleEffect(world, radius, simpleParticleModelOptional.get(), location, limit, spaceBetweenParticles);
            if (start) {
                particleEffect.start((int) objectMap.get("ticks"));
            }
            return Optional.of(particleEffect);
        }
        return Optional.empty();
    }
}