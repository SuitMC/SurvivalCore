package nahubar65.gmail.com.score.listeners;

import nahubar65.gmail.com.score.events.*;
import nahubar65.gmail.com.score.regions.GlobalRegionContainer;
import nahubar65.gmail.com.score.regions.Region;
import nahubar65.gmail.com.score.regions.RegionFlag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Map;

public class RegionListeners implements Listener {

    private GlobalRegionContainer globalRegionContainer;

    public RegionListeners(GlobalRegionContainer globalRegionContainer){
        this.globalRegionContainer = globalRegionContainer;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onMove(PlayerMoveEvent event) {
        if (!equalsLoc(event.getFrom(), event.getTo())) {
            Region region = findFromLoc(event.getTo());
            Region leftRegion = findFromLoc(event.getFrom());
            if (region != leftRegion) {
                if (leftRegion != null) {
                    RegionLeftEvent regionLeftEvent = new RegionLeftEvent(event.getPlayer(), leftRegion);
                    Bukkit.getPluginManager().callEvent(regionLeftEvent);
                    event.setCancelled(regionLeftEvent.isCancelled());
                } else if (region != null) {
                    RegionEnterEvent regionEnterEvent = new RegionEnterEvent(event.getPlayer(), region);
                    Bukkit.getPluginManager().callEvent(regionEnterEvent);
                    event.setCancelled(regionEnterEvent.isCancelled());
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDamage(PlayerDamageEvent event){
        Region region = findFromLoc(event.getPlayer().getLocation());
        if (region != null) {
            Map.Entry<RegionFlag, Boolean> entry = findFlag(event.getClass(), region);
            if (entry == null)
                return;
            if (entry.getKey().getClazz().equals(event.getClass())) {
                if (!entry.getValue()) {
                    Player player = event.getPlayer();
                    if (!player.hasPermission("score.region.bypass.damage")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDamageByPlayer(PlayerDamageByPlayerEvent event){
        Region region = findFromLoc(event.getPlayer().getLocation());
        if (region != null) {
            Map.Entry<RegionFlag, Boolean> entry = findFlag(event.getClass(), region);
            if (entry == null)
                return;
            if (entry.getKey().getClazz().equals(event.getClass())) {
                if (!entry.getValue()) {
                    Player player = event.getDamager();
                    if (!player.hasPermission("score.region.bypass.pvp")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        Region region = findFromLoc(entity.getLocation());
        if (region != null && entity.getType() != EntityType.PLAYER) {
            Map.Entry<RegionFlag, Boolean> entry = findFlag(event.getClass(), region);
            if (entry == null)
                return;
            if (entry.getKey().getClazz().equals(event.getClass())) {
                event.setCancelled(entry.getValue());
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        Region region = findFromLoc(event.getEntity().getLocation());
        if (region != null) {
            Map.Entry<RegionFlag, Boolean> entry = findFlag(event.getClass(), region);
            if (entry == null)
                return;
            if (entry.getKey().getClazz().equals(event.getClass())) {
                if (!entry.getValue()) {
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

    @EventHandler(priority = EventPriority.LOW)
    public void onEnderPearl(EnderPearlHitEvent event){
        Region region = findFromLoc(event.getTo());
        if (region != null) {
            Map.Entry<RegionFlag, Boolean> entry = findFlag(event.getClass(), region);
            if (entry == null)
                return;
            if (entry.getKey().getClazz().equals(event.getClass())) {
                if (!entry.getValue()) {
                    Player player = (Player) event.getPlayer();
                    if (!player.hasPermission("score.region.bypass.enderpearl")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onProcessCommand(PlayerCommandPreprocessEvent event){
        Player player = event.getPlayer();
        Region region = findFromLoc(player.getLocation());
        if (region != null) {
            Map.Entry<RegionFlag, Boolean> entry = findFlag(event.getClass(), region);
            if (entry == null)
                return;
            if (entry.getKey().getClazz().equals(event.getClass())) {
                if (!entry.getValue()) {
                    if (!player.hasPermission("score.region.bypass.command")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        Region region = findFromLoc(event.getBlock().getLocation());
        if (region != null) {
            Map.Entry<RegionFlag, Boolean> entry = findFlag(event.getClass(), region);
            if (entry == null)
                return;
            if (entry.getKey().getClazz().equals(event.getClass())) {
                if (!entry.getValue()) {
                    if (!player.hasPermission("score.region.bypass.place")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Region region = findFromLoc(block.getLocation());
        if (region != null) {
            Map.Entry<RegionFlag, Boolean> entry = findFlag(event.getClass(), region);
            if (entry == null)
                return;
            if (entry.getKey().getClazz().equals(event.getClass())) {
                if (!entry.getValue()) {
                    if (!player.hasPermission("score.region.bypass.break")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();
        Region region = findFromLoc(entity.getLocation());
        if (region != null) {
            Map.Entry<RegionFlag, Boolean> entry = findFlag(event.getClass(), region);
            if (entry == null)
                return;
            if (entry.getKey().getClazz().equals(event.getClass())) {
                if (!entry.getValue()) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEnter(RegionEnterEvent event) {
        Region region;
        if ((region = event.getRegion()) != null) {
            Map.Entry<RegionFlag, Boolean> entry = findFlag(event.getClass(), region);
            if (entry == null)
                return;
            if (entry.getKey().getClazz().equals(event.getClass())) {
                if (!entry.getValue()) {
                    if (!event.getPlayer().hasPermission("score.region.bypass.enter")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onLeft(RegionLeftEvent event) {
        Region region;
        if ((region = event.getRegion()) != null) {
            Map.Entry<RegionFlag, Boolean> entry = findFlag(event.getClass(), region);
            if (entry == null)
                return;
            if (entry.getKey().getClazz().equals(event.getClass())) {
                if (!entry.getValue()) {
                    if (!event.getPlayer().hasPermission("score.region.bypass.enter")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onSpawn(EntitySpawnEvent event) {
        Region region = findFromLoc(event.getLocation());
        if (region != null) {
            Map.Entry<RegionFlag, Boolean> entry = findFlag(event.getClass(), region);
            if (entry == null)
                return;
            if (entry.getKey().getClazz().equals(event.getClass())) {
                if (!entry.getValue()) {
                    event.setCancelled(true);
                }
            }
        }
    }

    private Region findFromLoc(Location location) {
        for (Region region : globalRegionContainer.get()) {
            if (region.contains(location)) {
                return region;
            }
        }
        return null;
    }

    private Map.Entry<RegionFlag, Boolean> findFlag(Class<?> clazz, Region region){
        for (Map.Entry<RegionFlag, Boolean> entry : region.getFlags().entrySet()) {
            if (entry.getKey().getClazz().equals(clazz)) {
                return entry;
            }
        }
        return null;
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