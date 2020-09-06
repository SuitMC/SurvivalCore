package nahubar65.gmail.com.score.effect;

import fr.mrmicky.fastparticle.FastParticle;
import fr.mrmicky.fastparticle.ParticleType;
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

public class CircleParticleEffect implements ParticleEffect {

    private int taskID;

    private Location location;

    private double angle = 0;

    private double radius;

    private ParticleModel particleModel;

    private Object particleSender;

    private double limit = 0;

    private int ticks;

    private CircleParticleEffect(double radius, Location location, ParticleModel particleModel){
        this.particleModel = particleModel;
        this.location = location;
        this.radius = radius;
    }

    public CircleParticleEffect(Player player, double radius, Location location, ParticleModel particleModel, double limit){
        this(radius, location, particleModel);
        this.particleSender = player;
        if (limit > 0)
            this.limit = limit;
    }

    public CircleParticleEffect(World world, double radius, Location location, ParticleModel particleModel, double limit){
        this(radius, location, particleModel);
        this.particleSender = world;
        if (limit > 0)
            this.limit = limit;
    }

    @Override
    public void run() {
        angle += 0.1;
        double cos = radius * Math.cos(angle) * limit;
        double sin = radius * Math.sin(angle) * limit;
        if (limit > 0) {
            cos = cos * limit;
            cos = cos * limit;
        }
        location.add(cos, 0, sin);
        if (particleModel.isColored()) {
            spawnParticle(location);
        }else{
            spawnParticle(location);
        }
        location.subtract(cos, 0, sin);
    }

    public void start(int ticks){
        this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(SurvivalCore.plugin(), this, 0, ticks);
        this.ticks = ticks;
    }

    public void stop(){
        Bukkit.getScheduler().cancelTask(taskID);
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
        objectMap.put("particleModel", particleModel.serialize());
        objectMap.put("ticks", ticks);
        objectMap.put("type", ParticleEffectType.CIRCLE.name());
        return objectMap;
    }

    public static Optional<ParticleEffect> deserialize(Map<String, Object> objectMap, boolean start){
        MemorySection locationSection = (MemorySection) objectMap.get("location");
        Location location = Location.deserialize(locationSection.getValues(true));
        double radius = NumberConversions.toDouble(objectMap.get("radius"));
        World world = location.getWorld();
        MemorySection particleModelSection = (MemorySection) objectMap.get("particleModel");
        Optional<SimpleParticleModelImpl> simpleParticleModelOptional = ParticleModel.deserialize(particleModelSection.getValues(true));
        if (simpleParticleModelOptional.isPresent()) {
            ParticleEffect particleEffect = new CircleParticleEffect(world, radius, location, simpleParticleModelOptional.get(), (Double) objectMap.get("limit"));
            if (start) {
                particleEffect.start((Integer) objectMap.get("ticks"));
            }
            return Optional.of(particleEffect);
        }
        return Optional.empty();
    }
}