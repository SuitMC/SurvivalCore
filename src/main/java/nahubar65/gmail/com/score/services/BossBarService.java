package nahubar65.gmail.com.score.services;

import nahubar65.gmail.com.score.SurvivalCore;
import nahubar65.gmail.com.score.caches.UUIDCache;
import nahubar65.gmail.com.score.combat.bossbar.EnemyBossBarUpdater;
import nahubar65.gmail.com.score.combat.bossbar.HealthEnemyBossBar;
import nahubar65.gmail.com.score.listeners.EnemyBossBarListener;
import nahubar65.gmail.com.score.loader.EventLoader;
import nahubar65.gmail.com.score.storages.PlayerInfoStorage;
import org.bukkit.Bukkit;

public class BossBarService implements Service {

    private UUIDCache<HealthEnemyBossBar> uuidCache;

    private EnemyBossBarUpdater enemyBossBarUpdater;

    private SurvivalCore survivalCore;

    private EventLoader eventLoader;

    public BossBarService(SurvivalCore survivalCore, PlayerInfoStorage settingsStorage){
        this.uuidCache = new UUIDCache<>();
        this.eventLoader = new EventLoader(survivalCore,
                new EnemyBossBarListener(uuidCache, settingsStorage));
        this.enemyBossBarUpdater = new EnemyBossBarUpdater(uuidCache);
        this.survivalCore = survivalCore;
    }

    @Override
    public void start() {
        eventLoader.load();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(survivalCore, enemyBossBarUpdater, 0, 05);
    }

    @Override
    public void stop() {

    }
}