package nahubar65.gmail.com.score.listeners;

import nahubar65.gmail.com.score.caches.UUIDCache;
import nahubar65.gmail.com.score.combat.bossbar.HealthEnemyBossBar;
import nahubar65.gmail.com.score.events.PlayerDamageByPlayerEvent;
import nahubar65.gmail.com.score.players.PlayerInfo;
import nahubar65.gmail.com.score.storages.PlayerInfoStorage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.inventivetalent.bossbar.BossBarAPI;

import java.util.Optional;
import java.util.UUID;

public class EnemyBossBarListener implements Listener {

    private UUIDCache<HealthEnemyBossBar> uuidCache;

    private PlayerInfoStorage settingsStorage;

    public EnemyBossBarListener(UUIDCache<HealthEnemyBossBar> uuidCache, PlayerInfoStorage settingsStorage) {
        this.uuidCache = uuidCache;
        this.settingsStorage = settingsStorage;
    }

    @EventHandler
    public void onHit(PlayerDamageByPlayerEvent event){
        if (!event.isCancelled()) {
            Player player = event.getDamager();
            UUID uuid = player.getUniqueId();
            Optional<PlayerInfo> playerSettings = settingsStorage.find(uuid);
            playerSettings.ifPresent(settings -> {
                if (settings.useEnemyHealthBossBar()) {
                    if (uuidCache.exists(player.getUniqueId())) {
                        HealthEnemyBossBar bossBar = uuidCache.find(uuid);
                        if (bossBar.enemy() != event.getPlayer()) {
                            uuidCache.add(uuid, new HealthEnemyBossBar(player, event.getPlayer()));
                            return;
                        }
                        bossBar.setCooldown(10);
                    } else {
                        uuidCache.add(uuid, new HealthEnemyBossBar(player, event.getPlayer()));
                    }
                }
            });
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        Player killer = player.getKiller();
        UUID playerUUID = player.getUniqueId();
        if (killer != null) {
            UUID killerUUID = killer.getUniqueId();
            if (uuidCache.exists(killerUUID)) {
                BossBarAPI.removeBar(killer);
                uuidCache.remove(killerUUID);
            }
        }
        if (uuidCache.exists(playerUUID)) {
            uuidCache.remove(playerUUID);
        }
    }
}