package nahubar65.gmail.com.score.plots.command;

import nahubar65.gmail.com.score.SurvivalCore;
import nahubar65.gmail.com.score.actions.AbstractAction;
import nahubar65.gmail.com.score.caches.UUIDCache;
import nahubar65.gmail.com.score.colors.CustomColors;
import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.nms.ActionBarSender;
import nahubar65.gmail.com.score.particle.ParticleModel;
import nahubar65.gmail.com.score.plots.PlotRegion;
import nahubar65.gmail.com.score.plots.PlotManager;
import nahubar65.gmail.com.score.confirmableaction.ConfirmableAction;
import nahubar65.gmail.com.score.prompt.ConfirmableActionPrompt;
import nahubar65.gmail.com.score.regions.Region;
import nahubar65.gmail.com.score.regions.RegionUtils;
import nahubar65.gmail.com.score.services.RegionService;
import nahubar65.gmail.com.score.sounds.Sounds;
import nahubar65.gmail.com.score.storages.Storage;
import nahubar65.gmail.com.score.utils.ItemUtils;
import nahubar65.gmail.com.score.utils.Pair;
import nahubar65.gmail.com.score.utils.Utils;
import nahubar65.gmail.com.score.vault.VaultEconomy;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;

public class PlotArea extends CommandArgument {

    private Storage<UUID, List<PlotRegion>> storage;

    private UUIDCache<Pair<Location, Location>> uuidCache;

    public PlotArea(Command command, Storage<UUID, List<PlotRegion>> storage) {
        super(command);
        this.storage = storage;
        this.uuidCache = new UUIDCache<>();
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {
        Player player = (Player) sender;
        if (args.length > 0) {
            String arg1 = args[0];
            PlotRegion plotRegion = Utils.findFromName(arg1, player.getUniqueId(), storage);
            if (plotRegion != null) {
                giveItem(player, plotRegion.getRegion());
                sender.sendMessage(color("&b[&6-&b] &aClick derecho para colocar el punto minimo."));
                sender.sendMessage(color("&b[&6-&b] &aClick izquierdo para colocar el punto maximo."));
            } else {
                sender.sendMessage(color("&c¡Region invalida!"));
            }
        } else {
            player.sendMessage(color("&cUso incorrecto! "+usage()));
        }
    }

    @Override
    public String identifier() {
        return "area";
    }

    @Override
    public String usage() {
        return "&aUso: &b/parcelas area <&aId>";
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

    private void giveItem(Player player, Region region) {
        ItemStack itemStack = new ItemStack(Material.WOOD_HOE);
        Utils.giveAbstractActionItem(player, new AbstractAction() {
            @Override
            public void performSpecialAction(Player player, Event event) {
                if (event instanceof PlayerInteractEvent) {
                    PlayerInteractEvent interactEvent = (PlayerInteractEvent) event;
                    if (interactEvent.getClickedBlock() != null) {
                        Block block = interactEvent.getClickedBlock();
                        UUID uuid = player.getUniqueId();
                        Pair<Location, Location> locationPair = uuidCache.exists(uuid) ? uuidCache.find(uuid) : new Pair<>();
                        String message = color("&b[&6-&b] &aPunto <?> colocado correctamente.");
                        switch (interactEvent.getAction()) {
                            case RIGHT_CLICK_BLOCK:
                                locationPair.setKey(block.getLocation());
                                message = message.replace("<?>", "Minimo");
                                if (!player.isConversing()) {
                                    player.sendMessage(message);
                                } else {
                                    player.sendRawMessage(message);
                                }
                                uuidCache.add(uuid, locationPair);
                                break;
                            case LEFT_CLICK_BLOCK:
                                locationPair.setValue(block.getLocation());
                                message = message.replace("<?>", "Maximo");
                                if (!player.isConversing()) {
                                    player.sendMessage(message);
                                } else {
                                    player.sendRawMessage(message);
                                }
                                uuidCache.add(uuid, locationPair);
                                break;
                        }
                        if (locationPair.getKey() != null && locationPair.getValue() != null) {
                            Region region1 = new Region(locationPair.getKey(), locationPair.getValue());
                            PlotManager plotSeller = RegionService.getPlotManager();
                            double price = 0;
                            if (region1.getTotalBlockSize() > region.getTotalBlockSize()) {
                                price = plotSeller.calculatePrice(region);
                                player.sendMessage(color("&bCosto del terreno extra: &a$"
                                        + (plotSeller.calculatePrice(region1) - price)));
                                ActionBarSender.sendActionbar(player, color("&eCosto extra: &a$"+price));
                            }
                            double finalPrice = price;
                            new ConfirmableActionPrompt(new ConfirmableAction() {
                                private VaultEconomy vaultEconomy = new VaultEconomy();

                                @Override
                                public void onConfirm() {
                                    confirm(finalPrice, locationPair, vaultEconomy, region, uuid, player);
                                    uuidCache.remove(uuid);
                                }

                                @Override
                                public void orElse() {

                                }
                            }, SurvivalCore.getFactory(), player).start();
                        }
                    }
                }
            }
        }, ItemUtils.setName(itemStack, color("&bHerramienta de reclamo de tierras")));
    }

    private void confirm(double finalPrice, Pair<Location, Location> locationPair, VaultEconomy vaultEconomy, Region region, UUID uuid, Player player){
        if (vaultEconomy.hasMoney(uuid, finalPrice)) {
            vaultEconomy.remove(uuid, finalPrice);
            region.setMin(locationPair.getKey());
            region.setMax(locationPair.getValue());
            player.sendRawMessage(color("&a¡Transacción realizada con exíto!"));
            Sounds.LEVEL_UP.play(player);
            new BukkitRunnable() {

                private int cooldown = 0;

                @Override
                public void run() {
                    if (cooldown >= 30) {
                        this.cancel();
                        return;
                    }
                    RegionUtils.sendLimits(ParticleModel.coloredParticle(CustomColors.GREEN), region, player);
                    cooldown += 1;
                }
            }.runTaskTimer(SurvivalCore.plugin(), 0, 10);
        } else {
            player.sendRawMessage(color("&c¡No tienes suficiente dinero!"));
        }
    }
}