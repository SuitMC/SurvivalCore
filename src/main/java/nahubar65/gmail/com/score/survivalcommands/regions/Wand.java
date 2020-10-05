package nahubar65.gmail.com.score.survivalcommands.regions;

import nahubar65.gmail.com.score.caches.UUIDCache;
import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.actions.AbstractAction;
import nahubar65.gmail.com.score.actions.ItemActionFactory;
import nahubar65.gmail.com.score.utils.Pair;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Wand extends CommandArgument {

    private UUIDCache<Pair<Location, Location>> uuidCache;

    public Wand(Command command, UUIDCache<Pair<Location, Location>> uuidCache){
        super(command);
        this.uuidCache = uuidCache;
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {
        Player player = (Player) sender;
        player.getInventory().addItem(ItemActionFactory.setSpecialItemAction(new ItemStack(Material.WOOD_HOE),
                new AbstractAction() {
                    @Override
                    public void performSpecialAction(Player player, Event event) {
                        if (event instanceof PlayerInteractEvent) {
                            PlayerInteractEvent playerInteractEvent = (PlayerInteractEvent) event;
                            if (playerInteractEvent.getItem() != null && playerInteractEvent.getClickedBlock() != null) {
                                Block block = playerInteractEvent.getClickedBlock();
                                Pair<Location, Location> pair = uuidCache.find(player.getUniqueId());
                                if (pair == null) {
                                    pair = new Pair<>();
                                }
                                if (playerInteractEvent.getPlayer().hasPermission("score.admin.*")) {
                                    if (playerInteractEvent.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_BLOCK) {
                                        pair.setKey(block.getLocation());
                                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eSeleccion 2 colocada."));
                                    } else if (playerInteractEvent.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) {
                                        pair.setValue(block.getLocation());
                                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eSeleccion 1 colocada."));
                                    } else if (playerInteractEvent.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_AIR){
                                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b¡Has eliminado tu selección!"));
                                        player.getInventory().removeItem(playerInteractEvent.getItem());
                                        uuidCache.remove(player.getUniqueId());
                                    }
                                    uuidCache.add(player.getUniqueId(), pair);
                                }
                            }
                        }
                    }
                }));
    }

    @Override
    public String identifier() {
        return "wand";
    }

    @Override
    public String usage() {
        return "/score wand";
    }

    @Override
    public boolean useOnlyPlayer() {
        return true;
    }

    @Override
    public boolean canExecute(CommandSender commandSender) {
        return true;
    }

    @Override
    public void ifCantExecute(CommandSender commandSender) {

    }
}