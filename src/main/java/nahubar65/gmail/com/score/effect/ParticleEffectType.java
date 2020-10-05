package nahubar65.gmail.com.score.effect;

import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;

public enum ParticleEffectType {

    SPIRAL(SpiralParticleEffect.class),
    CIRCLE(CircleParticleEffect.class);

    private Class<? extends ParticleEffect> clazz;

    ParticleEffectType(Class<? extends ParticleEffect> clazz){
        this.clazz = clazz;
    }

    public Class<? extends ParticleEffect> getClazz() {
        return clazz;
    }

    public static Optional<ParticleEffect> deserializeFromConfig(Map<String, Object> objectMap, boolean start) {
        ParticleEffectType type = valueOf(((String) objectMap.get("type")).toUpperCase());
        try {
            Optional<ParticleEffect> particleEffect = (Optional<ParticleEffect>) type.clazz.getMethod("deserialize", Map.class, boolean.class).invoke(null, objectMap, start);
            return particleEffect;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}