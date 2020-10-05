package nahubar65.gmail.com.score.regions;

import nahubar65.gmail.com.score.events.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public enum RegionFlag {

    ENTER(RegionEnterEvent.class, "Entrar", false),
    LEFT(RegionLeftEvent.class, "Salir", false),
    BREAK_BLOCK(BlockBreakEvent.class, "Destruir Bloques", false),
    PLACE_BLOCK(BlockPlaceEvent.class, "Colocar bloques", false),
    ENDER_PEARL(EnderPearlHitEvent.class, "Teletransportación por Perla de Enderman", false),
    DAMAGE(PlayerDamageEvent.class, "Daño", true),
    PVP(PlayerDamageByPlayerEvent.class, "PvP", false),
    ENTITY_DAMAGE(EntityDamageEvent.class, "Daño de entidades", false),
    ENTITY_DAMAGE_BY_ENTITY(EntityDamageByEntityEvent.class, "Dañar a otras entidades", false),
    EXPLODE(EntityExplodeEvent.class, "Explosiones", false),
    INTERACT(PlayerInteractEvent.class, "Interactuar", false),
    ENTITY_SPAWN(EntitySpawnEvent.class, "Spawneo de entidades", false),
    CMD(PlayerCommandPreprocessEvent.class, "Proceso de comandos", true);

    private Class clazz;

    private String name;

    private boolean requiredOp;

    RegionFlag(Class clazz, String name, boolean requiredOp){
        this.clazz = clazz;
        this.name = name;
        this.requiredOp = requiredOp;
    }

    public boolean isRequiredOp() {
        return requiredOp;
    }

    public String getName() {
        return name;
    }

    public Class getClazz(){
        return clazz;
    }

    public static RegionFlag[] defaultFlags() {
        return new RegionFlag[]{BREAK_BLOCK, PLACE_BLOCK, ENDER_PEARL, PVP, EXPLODE, DAMAGE, ENTITY_SPAWN};
    }

    public static RegionFlag[] plotFlags() {
        return new RegionFlag[]{BREAK_BLOCK, PLACE_BLOCK, ENDER_PEARL, PVP, EXPLODE, ENTITY_DAMAGE};
    }
}