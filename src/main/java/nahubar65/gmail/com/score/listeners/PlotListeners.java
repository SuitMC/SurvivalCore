package nahubar65.gmail.com.score.listeners;

import nahubar65.gmail.com.score.decorator.TextDecorator;
import nahubar65.gmail.com.score.events.*;
import nahubar65.gmail.com.score.plots.PlotRegion;
import nahubar65.gmail.com.score.regions.Region;
import nahubar65.gmail.com.score.regions.RegionFlag;
import nahubar65.gmail.com.score.storages.Storage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PlotListeners implements Listener {

    private Storage<UUID, List<PlotRegion>> storage;

    public PlotListeners(Storage<UUID, List<PlotRegion>> storage) {
        this.storage = storage;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerDamage(PlayerDamageEvent event){
        Optional<PlotRegion> regionOptional = findFromLoc(event.getPlayer().getLocation());
        regionOptional.ifPresent(plotRegion -> {
            Region region = plotRegion.getRegion();
            Map<RegionFlag, Boolean> flags = region.getFlags();
            if (flags.containsKey(event.getClass())) {
                if (!flags.get(event.getClass())) {
                    Player player = event.getPlayer();
                    if (!bypass("score.region.bypass.damage", plotRegion, player.getUniqueId())) {
                        event.setCancelled(true);
                    }
                }
            }
        });
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerDamageByPlayer(PlayerDamageByPlayerEvent event){
        Optional<PlotRegion> regionOptional = findFromLoc(event.getPlayer().getLocation());
        regionOptional.ifPresent(plotRegion -> {
            Map.Entry<RegionFlag, Boolean> entry = findFlag(event.getClass(), plotRegion.getRegion());
            if (entry == null)
                return;
            if (entry.getKey().getClazz().equals(event.getClass())) {
                if (!entry.getValue()) {
                    Player player = event.getDamager();
                    if (!bypass("score.region.bypass.pvp", plotRegion, player.getUniqueId())) {
                        event.setCancelled(true);
                    }
                }
            }
        });
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDamage(EntityDamageEvent event){
        Optional<PlotRegion> regionOptional = findFromLoc(event.getEntity().getLocation());
        regionOptional.ifPresent(plotRegion -> {
            Map.Entry<RegionFlag, Boolean> entry = findFlag(event.getClass(), plotRegion.getRegion());
            if (entry == null)
                return;
            if (entry.getKey().getClazz().equals(event.getClass())) {
                event.setCancelled(entry.getValue());
            }
        });
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onExplode(EntityExplodeEvent event){
        Entity entity = event.getEntity();
        Optional<PlotRegion> regionOptional = findFromLoc(entity.getLocation());
        regionOptional.ifPresent(plotRegion -> {
            Map.Entry<RegionFlag, Boolean> entry = findFlag(event.getClass(), plotRegion.getRegion());
            if (entry == null)
                return;
            if (entry.getKey().getClazz().equals(event.getClass())) {
                if (!entry.getValue()) {
                    event.setCancelled(true);
                }
            }
        });
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        Optional<PlotRegion> regionOptional = findFromLoc(event.getEntity().getLocation());
        regionOptional.ifPresent(plotRegion -> {
            Map.Entry<RegionFlag, Boolean> entry = findFlag(event.getClass(), plotRegion.getRegion());
            if (entry == null)
                return;
            if (entry.getKey().getClazz().equals(event.getClass())) {
                if (!entry.getValue()) {
                    if (event.getDamager() instanceof Player) {
                        Player player = (Player) event.getDamager();
                        if (!bypass("score.region.bypass.damageentities", plotRegion, player.getUniqueId())) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        });
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onProcessCommand(PlayerCommandPreprocessEvent event){
        Player player = event.getPlayer();
        Optional<PlotRegion> regionOptional = findFromLoc(player.getLocation());
        regionOptional.ifPresent(plotRegion -> {
            Map.Entry<RegionFlag, Boolean> entry = findFlag(event.getClass(), plotRegion.getRegion());
            if (entry == null)
                return;
            if (entry.getKey().getClazz().equals(event.getClass())) {
                if (!entry.getValue()) {
                    if (!bypass("score.region.bypass.command", plotRegion, player.getUniqueId())) {
                        event.setCancelled(true);
                    }
                }
            }
        });
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Optional<PlotRegion> regionOptional = findFromLoc(block.getLocation());
        regionOptional.ifPresent(plotRegion -> {
            Map.Entry<RegionFlag, Boolean> entry = findFlag(event.getClass(), plotRegion.getRegion());
            if (entry == null)
                return;
            if (entry.getKey().getClazz().equals(event.getClass())) {
                if (!entry.getValue()) {
                    if (!bypass("score.region.bypass.place", plotRegion, player.getUniqueId())) {
                        event.setCancelled(true);
                    }
                }
            }
        });
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEnderPearl(EnderPearlHitEvent event){
        Player player = event.getPlayer();
        Location location = event.getTo();
        Optional<PlotRegion> regionOptional = findFromLoc(location);
        regionOptional.ifPresent(plotRegion -> {
            Map.Entry<RegionFlag, Boolean> entry = findFlag(event.getClass(), plotRegion.getRegion());
            if (entry == null)
                return;
            if (entry.getKey().getClazz().equals(event.getClass())) {
                if (!entry.getValue()) {
                    if (!bypass("score.region.bypass.enderpearl", plotRegion, player.getUniqueId())) {
                        event.setCancelled(true);
                    }
                }
            }
        });
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onSpawn(EntitySpawnEvent event) {
        Optional<PlotRegion> regionOptional = findFromLoc(event.getLocation());
        regionOptional.ifPresent(plotRegion -> {
            Map.Entry<RegionFlag, Boolean> entry = findFlag(event.getClass(), plotRegion.getRegion());
            if (entry == null)
                return;
            if (entry.getKey().getClazz().equals(event.getClass())) {
                if (!entry.getValue()) {
                    event.setCancelled(true);
                }
            }
        });
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Optional<PlotRegion> regionOptional = findFromLoc(block.getLocation());
        regionOptional.ifPresent(plotRegion -> {
            Map.Entry<RegionFlag, Boolean> entry = findFlag(event.getClass(), plotRegion.getRegion());
            if (entry == null)
                return;
            if (entry.getKey().getClazz().equals(event.getClass())) {
                if (!entry.getValue()) {
                    if (!bypass("score.region.bypass.break", plotRegion, player.getUniqueId())) {
                        event.setCancelled(true);
                    }
                }
            }
        });
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEnter(RegionEnterEvent event) {
        if (event.getRegion() != null) {
            Region region = event.getRegion();
            Optional<PlotRegion> plotRegionOptional = findFromRegion(region);
            plotRegionOptional.ifPresent(plotRegion -> {
                Map.Entry<RegionFlag, Boolean> entry = findFlag(event.getClass(), region);
                if (entry == null)
                    return;
                if (entry.getKey().getClazz().equals(event.getClass())) {
                    if (!entry.getValue()) {
                        if (!bypass("score.region.bypass.enter", plotRegion, event.getPlayer().getUniqueId())) {
                            event.setCancelled(true);
                        }
                    }
                }
            });
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onLeft(RegionLeftEvent event) {
        if (event.getRegion() != null) {
            Region region = event.getRegion();
            Optional<PlotRegion> plotRegionOptional = findFromRegion(region);
            plotRegionOptional.ifPresent(plotRegion -> {
                Map.Entry<RegionFlag, Boolean> entry = findFlag(event.getClass(), region);
                if (entry == null)
                    return;
                if (entry.getKey().getClazz().equals(event.getClass())) {
                    if (!entry.getValue()) {
                        if (!bypass("score.region.bypass.left", plotRegion, event.getPlayer().getUniqueId())) {
                            event.setCancelled(true);
                        }
                    }
                }
            });
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void messageOnEnter(RegionEnterEvent event) {
        if (event.getRegion() != null) {
            Region region = event.getRegion();
            if (!event.isCancelled()) {
                findFromRegion(region).ifPresent(plotRegion -> {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(plotRegion.getMemberManager().getOwner());
                    if(offlinePlayer != null) {
                        Player player = event.getPlayer();
                        player.sendMessage(TextDecorator.color("&7Has entrado en la propiedad de &6" + offlinePlayer.getName() + "&7."));
                        if (region.getFlag(RegionFlag.PVP).getValue()) {
                            player.sendMessage(TextDecorator.color("&cZona Insegura. &7(PvP)"));
                        }
                    }
                });
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void messageOnLeft(RegionLeftEvent event) {
        if (event.getRegion() != null) {
            Region region = event.getRegion();
            if (!event.isCancelled()) {
                findFromRegion(region).ifPresent(plotRegion -> {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(plotRegion.getMemberManager().getOwner());
                    if(offlinePlayer != null)
                        event.getPlayer().sendMessage(TextDecorator.color("&7Has abandonado la propiedad de &6" + offlinePlayer.getName() + "&7."));
                });
            }
        }
    }

    private Optional<PlotRegion> findFromLoc(Location location) {
        for (List<PlotRegion> value : storage.get().values()) {
            for (PlotRegion plotRegion : value) {
                if (plotRegion.getRegion().contains(location))
                    return Optional.of(plotRegion);
            }
        }
        return Optional.empty();
    }

    private Optional<PlotRegion> findFromRegion(Region region) {
        for (List<PlotRegion> value : storage.get().values()) {
            for (PlotRegion plotRegion : value) {
                if (plotRegion.getRegion().equals(region)) {
                    return Optional.of(plotRegion);
                }
            }
        }
        return Optional.empty();
    }

    private boolean bypass(String permission, PlotRegion region, UUID uuid) {
        return Bukkit.getPlayer(uuid).hasPermission(permission) || region.getMemberManager().isOwner(uuid) || region.getMemberManager().isMember(uuid);
    }

    private Map.Entry<RegionFlag, Boolean> findFlag(Class<?> clazz, Region region){
        for (Map.Entry<RegionFlag, Boolean> entry : region.getFlags().entrySet()) {
            if (entry.getKey().getClazz().equals(clazz)) {
                return entry;
            }
        }
        return null;
    }
}