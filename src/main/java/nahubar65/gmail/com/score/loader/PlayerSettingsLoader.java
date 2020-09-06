package nahubar65.gmail.com.score.loader;

import nahubar65.gmail.com.score.configuration.Configuration;
import nahubar65.gmail.com.score.players.PlayerInfo;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Optional;

public class PlayerSettingsLoader {

    public Optional<PlayerInfo> load(Configuration configuration){
        ConfigurationSection section = configuration.getConfigurationSection("settings");
        PlayerInfo playerSettings = null;
        if (section != null) {
            playerSettings = PlayerInfo.deserialize(section.getValues(true));
        }
        return Optional.ofNullable(playerSettings);
    }
}