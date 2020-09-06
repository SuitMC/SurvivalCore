package nahubar65.gmail.com.score.listeners;

import nahubar65.gmail.com.score.events.PlayerDamageByPlayerEvent;
import nahubar65.gmail.com.score.events.PlayerDamageEvent;
import nahubar65.gmail.com.score.storages.RegionStorage;
import nahubar65.gmail.com.score.utils.Region;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Map;
import java.util.Optional;

public class RegionListeners implements Listener {

    private RegionStorage regionStorage;

    public RegionListeners(RegionStorage regionStorage){
        this.regionStorage = regionStorage;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        if (!equalsLoc(event.getFrom(), event.getTo())) {
            Optional<Region> regionOptional = regionStorage.findFromLoc(event.getTo());
            if (regionOptional.isPresent()) {
                Region region = regionOptional.get();
                Map<Class<?>, Boolean> flags = region.getFlags();
                if (flags.containsKey(event.getClass())) {
                    Player player = event.getPlayer();
                    if (!flags.get(event.getClass())) {
                        if (!player.hasPermission("score.region.bypass.move.*") || !player.hasPermission("score.region.bypass.move."+region.getName())) {
                            event.setTo(event.getFrom());
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(PlayerDamageEvent event){
        Optional<Region> regionOptional = regionStorage.findFromLoc(event.getPlayer().getLocation());
        if (regionOptional.isPresent()) {
            Region region = regionOptional.get();
            Map<Class<?>, Boolean> flags = region.getFlags();
            if (flags.containsKey(event.getClass())) {
                if (!flags.get(event.getClass())) {
                    Player player = event.getPlayer();
                    if (!player.hasPermission("score.region.bypass.damage")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDamageByPlayer(PlayerDamageByPlayerEvent event){
        Optional<Region> regionOptional = regionStorage.findFromLoc(event.getPlayer().getLocation());
        if (regionOptional.isPresent()) {
            Region region = regionOptional.get();
            Map<Class<?>, Boolean> flags = region.getFlags();
            if (flags.containsKey(event.getClass())) {
                if (!flags.get(event.getClass())) {
                    Player player = event.getDamager();
                    if (!player.hasPermission("score.region.bypass.pvp")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
        Optional<Region> regionOptional = regionStorage.findFromLoc(event.getEntity().getLocation());
        if (regionOptional.isPresent()) {
            Region region = regionOptional.get();
            Map<Class<?>, Boolean> flags = region.getFlags();
            if (flags.containsKey(event.getClass())) {
                event.setCancelled(flags.get(event.getClass()));
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        Optional<Region> regionOptional = regionStorage.findFromLoc(event.getEntity().getLocation());
        if (regionOptional.isPresent()) {
            Region region = regionOptional.get();
            Map<Class<?>, Boolean> flags = region.getFlags();
            if (flags.containsKey(event.getClass())) {
                if (!flags.get(event.getClass())) {
                    if (event.getDamager() instanceof Player) {
                        Player player = (Player) event.getDamager();
                        if (!player.hasPermission("score.region.bypass.damageentities")) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onProcessCommand(PlayerCommandPreprocessEvent event){
        Player player = event.getPlayer();
        Optional<Region> regionOptional = regionStorage.findFromLoc(player.getLocation());
        if (regionOptional.isPresent()) {
            Region region = regionOptional.get();
            Map<Class<?>, Boolean> flags = region.getFlags();
            if (flags.containsKey(event.getClass())) {
                if (!flags.get(event.getClass())) {
                    if (!player.hasPermission("score.region.bypass.command")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Optional<Region> regionOptional = regionStorage.findFromLoc(block.getLocation());
        if (regionOptional.isPresent()) {
            Region region = regionOptional.get();
            Map<Class<?>, Boolean> flags = region.getFlags();
            if (flags.containsKey(event.getClass())) {
                if (!flags.get(event.getClass())) {
                    if (!player.hasPermission("score.region.bypass.place")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Optional<Region> regionOptional = regionStorage.findFromLoc(block.getLocation());
        if (regionOptional.isPresent()) {
            Region region = regionOptional.get();
            Map<Class<?>, Boolean> flags = region.getFlags();
            if (flags.containsKey(event.getClass())) {
                if (!flags.get(event.getClass())) {
                    if (!player.hasPermission("score.region.bypass.break")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    private boolean equalsLoc(Location location, Location other) {
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