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
import org.bukkit.util.io.BukkitObjectInputStream;

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

    private double spaceBetweenParticles = 0;

    private int ticks;

    private boolean started;

    private CircleParticleEffect(double radius, Location location, ParticleModel particleModel){
        this.particleModel = particleModel;
        this.location = location;
        this.radius = radius;
    }

    public CircleParticleEffect(Player player, double radius, Location location, ParticleModel particleModel, double spaceBetweenParticles){
        this(radius, location, particleModel);
        this.particleSender = player;
        if (spaceBetweenParticles > 0)
            this.spaceBetweenParticles = spaceBetweenParticles;
    }

    public CircleParticleEffect(World world, double radius, Location location, ParticleModel particleModel, double spaceBetweenParticles){
        this(radius, location, particleModel);
        this.particleSender = world;
        if (spaceBetweenParticles > 0)
            this.spaceBetweenParticles = spaceBetweenParticles;
    }

    @Override
    public void run() {
        double angleToDegrees = Math.toDegrees(angle);
        if(angleToDegrees >= (360 * spaceBetweenParticles))
            angle = 0;
        double cos = radius * Math.cos(angle);
        double sin = radius * Math.sin(angle);
        location.add(cos, 0, sin);
        spawnParticle(location);
        location.subtract(cos, 0, sin);
        angle += 0.1 * spaceBetweenParticles;
    }

    @Override
    public boolean started() {
        return started;
    }

    public void start(int ticks) {
        this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(SurvivalCore.plugin(), this, 0, 1);
        this.ticks = ticks;
        this.started = true;
    }

    @Override
    public void stop() {
        if (started) {
            Bukkit.getScheduler().cancelTask(taskID);
        }
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public ParticleEffectType getType() {
        return ParticleEffectType.CIRCLE;
    }

    private void spawnParticle(Location location){
        float extra = particleModel.isColored() ? 1 : 0;
        if (particleSender instanceof Player) {
            Player player = (Player) particleSender;
            FastParticle.spawnParticle(player, particleModel.getParticleType(), location, particleModel.amount(), particleModel.offSetX(), particleModel.offSetY(), particleModel.offSetZ(), extra, 0);
        } else if (particleSender instanceof World) {
            World world = (World) particleSender;
            FastParticle.spawnParticle(world, particleModel.getParticleType(), location, particleModel.amount(), particleModel.offSetX(), particleModel.offSetY(), particleModel.offSetZ(), extra, 0);
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("location", location.serialize());
        objectMap.put("radius", radius);
        objectMap.put("limit", spaceBetweenParticles);
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