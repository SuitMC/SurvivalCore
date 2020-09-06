package nahubar65.gmail.com.score.effect;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public interface ParticleEffect extends Runnable, ConfigurationSerializable {
    
    void start(int ticks);

    void stop();
}