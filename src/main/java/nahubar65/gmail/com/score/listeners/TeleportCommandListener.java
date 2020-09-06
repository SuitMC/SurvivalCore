package nahubar65.gmail.com.score.listeners;

import nahubar65.gmail.com.score.caches.UUIDCache;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

public class TeleportCommandListener implements Listener {

    private UUIDCache<Object> uuidCache;

    public TeleportCommandListener(UUIDCache<Object> uuidCache){
        this.uuidCache = uuidCache;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        if(this.uuidCache.exists(uuid)){
            if(!equalsLoc(event.getFrom(), event.getTo())){
                this.uuidCache.remove(uuid);
            }
        }
    }

    public boolean equalsLoc(Location location, Location other) {
        if (location.getWorld() != other.getWorld()) {
            return false;
        } else if (Math.toIntExact((long) location.getX()) != Math.toIntExact((long) other.getX())) {
            return false;
        } else if (Math.toIntExact((long) location.getY()) != Math.toIntExact((long) other.getY())) {
            return false;
        } else if (Math.toIntExact((long) location.getZ()) != Math.toIntExact((long) other.getZ())) {
            return false;
        }
        return true;
    }
}