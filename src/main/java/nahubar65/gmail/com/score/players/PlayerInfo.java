package nahubar65.gmail.com.score.players;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class PlayerInfo implements ConfigurationSerializable {

    private boolean useEnemyHealthBossBar = true;

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("useEnemyHealthBossBar", useEnemyHealthBossBar);
        return objectMap;
    }

    public static PlayerInfo deserialize(Map<String, Object> objectMap){
        PlayerInfo playerSettings = new PlayerInfo();
        if (objectMap.containsKey("useEnemyHealthBossBar")) {
            playerSettings.setUseEnemyHealthBossBar((Boolean) objectMap.get("useEnemyHealthBossBar"));
        }
        return playerSettings;
    }

    public boolean useEnemyHealthBossBar() {
        return useEnemyHealthBossBar;
    }

    public void setUseEnemyHealthBossBar(boolean useEnemyHealthBossBar) {
        this.useEnemyHealthBossBar = useEnemyHealthBossBar;
    }
}