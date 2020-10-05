package nahubar65.gmail.com.score.particle;

import fr.mrmicky.fastparticle.ParticleType;
import nahubar65.gmail.com.score.colors.Color;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;

import java.util.Map;
import java.util.Optional;

public interface ParticleModel extends ConfigurationSerializable {

    ParticleType getParticleType();

    int amount();

    float offSetX();

    float offSetY();

    float offSetZ();

    boolean isColored();

    void setColored(boolean colored);

    void spawnParticle(Location location, Player player);

    void spawnParticle(Location location);

    static ParticleModel coloredParticle(Color color){
        return new SimpleParticleModelImpl(ParticleType.REDSTONE, 0, color.getRed(), color.getGreen(), color.getBlue(), true);
    }

    static Optional<SimpleParticleModelImpl> deserialize(Map<String, Object> objectMap){
        SimpleParticleModelImpl model = null;
        try {
            float offSetX = 0;
            float offSetY = 0;
            float offSetZ = 0;
            if (objectMap.containsKey("offSetX") && objectMap.containsKey("offSetY") && objectMap.containsKey("offSetZ")) {
                offSetX = NumberConversions.toFloat(objectMap.get("offSetX"));
                offSetY = NumberConversions.toFloat(objectMap.get("offSetY"));
                offSetZ = NumberConversions.toFloat(objectMap.get("offSetZ"));
            }
            int amount = (int) objectMap.get("amount");
            ParticleType particleType = ParticleType.valueOf((String) objectMap.get("particleType"));
            boolean colored = (boolean) objectMap.get("colored");
            model = new SimpleParticleModelImpl(particleType, amount, offSetX, offSetY, offSetZ, colored);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Optional.ofNullable(model);
    }
}