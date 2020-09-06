package nahubar65.gmail.com.score.utils;

import nahubar65.gmail.com.score.events.EnderPearlHitEvent;
import nahubar65.gmail.com.score.events.PlayerDamageByPlayerEvent;
import nahubar65.gmail.com.score.events.PlayerDamageEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public enum RegionFlag {

    MOVE(PlayerMoveEvent.class),
    BREAK_BLOCK(BlockBreakEvent.class),
    PLACE_BLOCK(BlockPlaceEvent.class),
    ENDER_PEARL(EnderPearlHitEvent.class),
    DAMAGE(PlayerDamageEvent.class),
    PVP(PlayerDamageByPlayerEvent.class),
    ENTITY_DAMAGE(EntityDamageEvent.class),
    ENTITY_DAMAGE_BY_ENTITY(EntityDamageByEntityEvent.class),
    CMD(PlayerCommandPreprocessEvent.class);

    private Class clazz;

    RegionFlag(Class clazz){
        this.clazz = clazz;
    }

    public Class getClazz(){
        return clazz;
    }
}