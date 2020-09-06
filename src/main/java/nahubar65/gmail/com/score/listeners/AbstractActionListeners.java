package nahubar65.gmail.com.score.listeners;

import nahubar65.gmail.com.score.actions.AbstractAction;
import nahubar65.gmail.com.score.actions.ItemActionFactory;
import nahubar65.gmail.com.score.actions.armorstands.ArmorStandActionFactory;
import org.bukkit.GameMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class AbstractActionListeners implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent event){
        if (event.getItem() != null && event.getPlayer() != null) {
            ItemStack itemStack = event.getItem();
            AbstractAction action = ItemActionFactory.getAction(itemStack);
            if(action != null) {
                action.performSpecialAction(event.getPlayer(), event);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDrop(PlayerDropItemEvent event){
        if (event.getPlayer() != null) {
            ItemStack itemStack = event.getItemDrop().getItemStack();
            AbstractAction action = ItemActionFactory.getAction(itemStack);
            if(action != null) {
                ItemActionFactory.removeAction(itemStack);
                event.getItemDrop().remove();
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDeath(PlayerDeathEvent event){
        if (event.getEntity() != null) {
            for (ItemStack drop : event.getDrops()) {
                AbstractAction action = ItemActionFactory.getAction(drop);
                if(action != null) {
                    ItemActionFactory.removeAction(drop);
                    event.getDrops().remove(drop);
                }
            }
        }
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractAtEntityEvent entityEvent){
        if (entityEvent.getRightClicked() instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand) entityEvent.getRightClicked();
            AbstractAction action = ArmorStandActionFactory.getAction(armorStand);
            if (action != null) {
                action.performSpecialAction(entityEvent.getPlayer(), entityEvent);
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.ARMOR_STAND) {
            if (event.getDamager().getType() == EntityType.PLAYER) {
                Player player = (Player) event.getDamager();
                ArmorStand armorStand = (ArmorStand) event.getEntity();
                AbstractAction action = ArmorStandActionFactory.getAction(armorStand);
                if (action != null) {
                    if (player.getGameMode() == GameMode.CREATIVE && player.isSneaking()) {
                        ArmorStandActionFactory.removeAction(armorStand);
                        armorStand.remove();
                        event.setCancelled(true);
                        return;
                    }
                    action.performSpecialAction(player, event);
                }
            }
        }
    }
}