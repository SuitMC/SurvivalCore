package nahubar65.gmail.com.score.listeners;

import nahubar65.gmail.com.score.events.EnderPearlHitEvent;
import nahubar65.gmail.com.score.events.PlayerDamageByPlayerEvent;
import nahubar65.gmail.com.score.events.PlayerDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class CustomEventListeners implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTeleport(PlayerTeleportEvent event){
        if (!event.isCancelled()) {
            if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
                EnderPearlHitEvent enderPearlHitEvent = new EnderPearlHitEvent(event.getPlayer(), event.getTo(), event.getFrom());
                Bukkit.getPluginManager().callEvent(enderPearlHitEvent);
                event.setCancelled(enderPearlHitEvent.isCancelled());
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageEvent event){
        if (event.getEntity() instanceof Player) {
            PlayerDamageEvent playerDamageEvent = new PlayerDamageEvent((Player) event.getEntity(), event.getDamage());
            Bukkit.getPluginManager().callEvent(playerDamageEvent);
            event.setCancelled(playerDamageEvent.isCancelled());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamageByEntity(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            PlayerDamageByPlayerEvent playerDamageEvent = new PlayerDamageByPlayerEvent((Player) event.getEntity(), event.getDamage(), (Player) event.getDamager());
            Bukkit.getPluginManager().callEvent(playerDamageEvent);
            event.setCancelled(playerDamageEvent.isCancelled());
        }
    }
}