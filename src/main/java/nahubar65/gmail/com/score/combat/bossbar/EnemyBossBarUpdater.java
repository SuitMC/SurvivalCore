package nahubar65.gmail.com.score.combat.bossbar;

import nahubar65.gmail.com.score.caches.UUIDCache;
import org.inventivetalent.bossbar.BossBarAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EnemyBossBarUpdater implements Runnable {

    private UUIDCache<HealthEnemyBossBar> uuidCache;

    public EnemyBossBarUpdater(UUIDCache<HealthEnemyBossBar> uuidCache){
        this.uuidCache = uuidCache;
    }

    @Override
    public void run() {
        Map<UUID, HealthEnemyBossBar> map = new HashMap<>(uuidCache.get());
        for (Map.Entry<UUID, HealthEnemyBossBar> entry : map.entrySet()) {
            HealthEnemyBossBar bossBar = entry.getValue();
            UUID uuid = entry.getKey();
            if (!bossBar.update()) {
                BossBarAPI.removeBar(bossBar.player());
                uuidCache.remove(uuid);
            }
        }
    }
}