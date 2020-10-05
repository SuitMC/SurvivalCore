package nahubar65.gmail.com.score.storages;

import nahubar65.gmail.com.score.configuration.Configuration;
import nahubar65.gmail.com.score.effect.ParticleEffect;
import nahubar65.gmail.com.score.effect.ParticleEffectType;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ParticleEffectStorage implements Storage<String, ParticleEffect> {

    private final Map<String, ParticleEffect> particleEffectMap = new HashMap<>();

    private Configuration configuration;

    public ParticleEffectStorage(Configuration configuration){
        this.configuration = configuration;
    }

    @Override
    public Map<String, ParticleEffect> get() {
        return particleEffectMap;
    }

    @Override
    public Optional<ParticleEffect> find(String key) {
        return Optional.ofNullable(particleEffectMap.get(key));
    }

    @Override
    public Optional<ParticleEffect> findFromData(String key) {
        ConfigurationSection section = configuration.getConfigurationSection("list."+key);
        if (section != null) {
            return ParticleEffectType.deserializeFromConfig(section.getValues(true), true);
        }
        return Optional.empty();
    }

    @Override
    public void save(String key) {
        find(key).ifPresent(particleEffect -> {
            configuration.set("list."+key, particleEffect.serialize());
            configuration.save();
        });
    }

    @Override
    public void saveObject(String key, ParticleEffect value) {

    }

    @Override
    public void remove(String key) {
        find(key).ifPresent(particleEffect -> {
            particleEffectMap.remove(key, particleEffect);
            configuration.set("list."+key, null);
            configuration.save();
            particleEffect.stop();
        });
    }

    @Override
    public void add(String key, ParticleEffect value) {
        particleEffectMap.put(key, value);
    }

    public void add(ParticleEffect value){
        ConfigurationSection section = configuration.getConfigurationSection("list");
        if (section == null) {
            add("0", value);
            save("0");
            return;
        }
        add(section.getKeys(false).size()+"", value);
        save(section.getKeys(false).size()+"");
    }

    @Override
    public void saveAll() {
        for (String key : particleEffectMap.keySet()) {
            save(key);
        }
    }

    @Override
    public void loadAll() {
        ConfigurationSection section = configuration.getConfigurationSection("list");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                findFromData(key).ifPresent(particleEffect -> {
                    add(key, particleEffect);
                });
            }
        }
    }
}