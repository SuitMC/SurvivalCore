package nahubar65.gmail.com.score.particle;

import fr.mrmicky.fastparticle.FastParticle;
import fr.mrmicky.fastparticle.ParticleType;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class SimpleParticleModelImpl implements ParticleModel {

    private ParticleType particleType;

    private int amount;

    private float offSetX = 0;

    private float offSetY = 0;

    private float offSetZ = 0;

    private boolean colored;

    public SimpleParticleModelImpl(ParticleType particleType, int amount, double offSetX, double offSetY, double offSetZ, boolean colored){
        this.particleType = particleType;
        this.amount = amount;
        this.offSetX = (float) offSetX;
        this.offSetY = (float) offSetY;
        this.offSetZ = (float) offSetZ;
        this.colored = colored;
        if (offSetX <= 0 && colored) {
            this.offSetX = (float) 0.001;
        }
    }

    public SimpleParticleModelImpl(ParticleType particleType, int amount){
        this.particleType = particleType;
        this.amount = amount;
    }

    @Override
    public ParticleType getParticleType() {
        return particleType;
    }

    @Override
    public int amount() {
        return amount;
    }

    @Override
    public float offSetX() {
        return offSetX;
    }

    @Override
    public float offSetY() {
        return offSetY;
    }

    @Override
    public float offSetZ() {
        return offSetZ;
    }

    @Override
    public boolean isColored() {
        return colored;
    }

    @Override
    public void setColored(boolean colored) {
        this.colored = colored;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("offSetX", offSetX);
        objectMap.put("offSetY", offSetY);
        objectMap.put("offSetZ", offSetZ);
        objectMap.put("amount", amount);
        objectMap.put("particleType", particleType.name());
        objectMap.put("colored", colored);
        return objectMap;
    }

    public void spawnParticle(Location location, Player player) {
        float extra = colored ? 1 : 0;
        FastParticle.spawnParticle(player, particleType, location, amount, offSetX, offSetY, offSetZ, extra, 0);
    }

    public void spawnParticle(Location location) {
        float extra = colored ? 1 : 0;
        FastParticle.spawnParticle(location.getWorld(), particleType, location, amount, offSetX, offSetY, offSetZ, extra, 0);
    }
}