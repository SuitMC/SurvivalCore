package nahubar65.gmail.com.score.effect;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public interface ParticleEffect extends Runnable, ConfigurationSerializable {

    boolean started();

    void start(int ticks);

    void stop();

    Location getLocation();

    ParticleEffectType getType();
}