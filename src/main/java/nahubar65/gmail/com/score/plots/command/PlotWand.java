package nahubar65.gmail.com.score.plots.command;

import nahubar65.gmail.com.score.actions.AbstractAction;
import nahubar65.gmail.com.score.actions.ItemActionFactory;
import nahubar65.gmail.com.score.caches.UUIDCache;
import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.nms.ActionBarSender;
import nahubar65.gmail.com.score.plots.PlotRegion;
import nahubar65.gmail.com.score.services.RegionService;
import nahubar65.gmail.com.score.storages.RegionStorage;
import nahubar65.gmail.com.score.storages.Storage;
import nahubar65.gmail.com.score.utils.ItemUtils;
import nahubar65.gmail.com.score.utils.Pair;
import nahubar65.gmail.com.score.regions.Region;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class PlotWand extends CommandArgument {

    private UUIDCache<Pair<Location, Location>> uuidCache;

    private Storage<UUID, List<PlotRegion>> storage;

    private RegionStorage regionStorage;

    public PlotWand(Command command, UUIDCache<Pair<Location, Location>> uuidCache, Storage<UUID, List<PlotRegion>> storage, RegionStorage regionStorage) {
        super(command);
        this.uuidCache = uuidCache;
        this.storage = storage;
        this.regionStorage = regionStorage;
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {
        Player player = (Player) sender;
        ItemStack wand = ItemActionFactory.setSpecialItemAction(
                ItemUtils.setName(new ItemStack(Material.WOOD_HOE), color("&bHerramienta de reclamo de tierras")),
                new AbstractAction() {
                    @Override
                    public void performSpecialAction(Player player, Event event) {
                        if (event instanceof PlayerInteractEvent) {
                            PlayerInteractEvent event1 = (PlayerInteractEvent) event;
                            Pair<Location, Location> locationPair;
                            if (uuidCache.exists(player.getUniqueId())) {
                                locationPair = uuidCache.find(player.getUniqueId());
                            } else {
                                locationPair = new Pair<>();
                            }
                            if (event1.getClickedBlock() != null) {
                                Block block = event1.getClickedBlock();
                                for (List<PlotRegion> value : storage.get().values()) {
                                    for (PlotRegion plotRegion : value) {
                                        if (plotRegion.getRegion().contains(block.getLocation())) {
                                            player.sendMessage(color("&cNo puedes colocar eso ahí!"));
                                            return;
                                        }
                                    }
                                }
                                for (Region value : regionStorage.get().values()) {
                                    if (value.contains(block.getLocation())) {
                                        player.sendMessage(color("&cNo puedes colocar eso ahí!"));
                                        return;
                                    }
                                }
                                switch (event1.getAction()) {
                                    case LEFT_CLICK_BLOCK:
                                        player.sendMessage(color("&b[&e-&b] &aSeleccion 1 colocada."));
                                        locationPair.setKey(block.getLocation());
                                        break;
                                    case RIGHT_CLICK_BLOCK:
                                        player.sendMessage(color("&b[&e-&b] &aSeleccion 2 colocada."));
                                        locationPair.setValue(block.getLocation());
                                        break;
                                    default:
                                        break;
                                }
                                uuidCache.add(player.getUniqueId(), locationPair);
                                Location min = locationPair.getKey();
                                Location max = locationPair.getValue();
                                if (min != null && max != null) {
                                    player.sendMessage(color("&b[&e-&b] &aUse &e/parcela claim &apara reclamar la selección."));
                                    double price = RegionService.getPlotManager().calculatePrice(new Region(min, max));
                                    player.sendMessage(color("&3Costo total del terreno: &a$" + price));
                                    ActionBarSender.sendActionbar(player, color("&eCosto: &a$" + price));
                                }
                            }
                        }
                    }
                });
        player.getInventory().addItem(wand);
    }

    @Override
    public String identifier() {
        return "wand";
    }

    @Override
    public String usage() {
        return "/plot wand";
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