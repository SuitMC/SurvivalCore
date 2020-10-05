package nahubar65.gmail.com.score.regions;

import nahubar65.gmail.com.score.particle.ParticleModel;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RegionUtils {

    public static void sendLimits(ParticleModel particleModel, Region region, Player player) {
        for (Location location : region.getEdges()) {
            particleModel.spawnParticle(location.clone().add(0.5, 0.5, 0.5), player);
        }
    }
}