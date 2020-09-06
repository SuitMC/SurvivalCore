package nahubar65.gmail.com.score.storages;

import nahubar65.gmail.com.score.caches.UUIDCache;
import nahubar65.gmail.com.score.configuration.Configuration;
import nahubar65.gmail.com.score.players.PlayerInfo;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PlayerInfoStorage implements Storage<UUID, PlayerInfo> {

    private Map<UUID, PlayerInfo> uuidPlayerSettingsMap;

    private UUIDCache<Configuration> configurationCache;

    private File folder;

    public PlayerInfoStorage(File folder){
        this.folder = folder;
        this.uuidPlayerSettingsMap = new HashMap<>();
        this.configurationCache = new UUIDCache<>();
    }

    @Override
    public Map<UUID, PlayerInfo> get() {
        return uuidPlayerSettingsMap;
    }

    @Override
    public Optional<PlayerInfo> find(UUID key) {
        return Optional.ofNullable(uuidPlayerSettingsMap.get(key));
    }

    @Override
    public Optional<PlayerInfo> findFromData(UUID key) {
        return Optional.empty();
    }

    public void load(Configuration configuration, UUID uuid){
        ConfigurationSection section = configuration.getConfigurationSection("settings");
        if (section == null) {
            section = configuration.createSection("settings");
        }
        this.configurationCache.add(uuid, configuration);
        this.uuidPlayerSettingsMap.put(uuid, PlayerInfo.deserialize(section.getValues(true)));
    }

    @Override
    public void save(UUID key) {
        Configuration configuration = configurationCache.find(key);
        if (configuration != null) {
            configuration.set("settings", uuidPlayerSettingsMap.get(key).serialize());
        }
        configuration.save();
    }

    @Override
    public void saveObject(UUID key, PlayerInfo value) {

    }

    @Override
    public void remove(UUID key) {
        save(key);
        configurationCache.find(key).save();
        uuidPlayerSettingsMap.remove(key);
        configurationCache.remove(key);
    }

    @Override
    public void add(UUID key, PlayerInfo value) {

    }

    @Override
    public void saveAll() {
        for (UUID uuid : uuidPlayerSettingsMap.keySet()) {
            save(uuid);
        }
    }

    @Override
    public void loadAll() {

    }
}