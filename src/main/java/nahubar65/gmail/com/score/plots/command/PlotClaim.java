package nahubar65.gmail.com.score.plots.command;

import nahubar65.gmail.com.score.SurvivalCore;
import nahubar65.gmail.com.score.caches.UUIDCache;
import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.decorator.TextDecorator;
import nahubar65.gmail.com.score.plots.PlotManager;
import nahubar65.gmail.com.score.confirmableaction.ConfirmableAction;
import nahubar65.gmail.com.score.plots.PlotOptions;
import nahubar65.gmail.com.score.prompt.ConfirmableActionPrompt;
import nahubar65.gmail.com.score.services.RegionService;
import nahubar65.gmail.com.score.storages.SQLPlotStorage;
import nahubar65.gmail.com.score.utils.Pair;
import nahubar65.gmail.com.score.utils.Utils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlotClaim extends CommandArgument {

    private UUIDCache<Pair<Location, Location>> uuidCache;

    private SQLPlotStorage sqlPlotStorage;

    private final PlotManager plotSeller;

    public PlotClaim(Command command, UUIDCache<Pair<Location, Location>> uuidCache, SQLPlotStorage sqlPlotStorage) {
        super(command);
        this.uuidCache = uuidCache;
        this.sqlPlotStorage = sqlPlotStorage;
        this.plotSeller = RegionService.getPlotManager();
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {
        Player player = (Player) sender;
        Pair<Location, Location> locationPair = uuidCache.find(player.getUniqueId());
        UUID uuid = player.getUniqueId();
        if (args.length > 0) {
            String arg1 = args[0];
            ConfirmableAction confirmableAction = new ConfirmableAction() {
                @Override
                public void onConfirm() {
                    PlotOptions plotOptions = RegionService.getPlotOptions();
                    if (plotOptions.bypassRegionLimit(player)) {
                        if (Utils.findFromName(arg1, sqlPlotStorage) != null) {
                            player.sendRawMessage(color("&cEl nombre &9" + arg1 + " &cno esta disponible."));
                            return;
                        }
                        if (plotSeller.giveRegion(uuid, locationPair, arg1)) {
                            player.sendRawMessage(color("&b[&e-&b] &a¡Region adquirida con exito!"));
                        } else {
                            player.sendRawMessage(color("&c¡No posees suficiente dinero para reclamar este terreno!"));
                        }
                        uuidCache.remove(uuid);
                    } else {
                        player.sendRawMessage(TextDecorator.color("&cHas alcanzado el limite de parcelas. &6(" + plotOptions.getMaxMembersPerRegion() + ")"));
                        player.sendRawMessage(TextDecorator.color("&bCompra &aVIP &ben la tienda para poder aumentar el limite de regiones."));
                    }
                }

                @Override
                public void orElse() {

                }
            };
            new ConfirmableActionPrompt(confirmableAction, SurvivalCore.getFactory(), player).start(15, SurvivalCore.plugin());
        } else {
            player.sendMessage(color("&cUso incorrecto! "+usage()));
        }
    }

    @Override
    public String identifier() {
        return "claim";
    }

    @Override
    public String usage() {
        return color("&aUso: &b/parcelas claim &e<Nombre>");
    }

    @Override
    public boolean useOnlyPlayer() {
        return true;
    }

    @Override
    public boolean canExecute(CommandSender commandSender) {
        Player player = (Player) commandSender;
        Pair<Location, Location> locationPair = uuidCache.find(player.getUniqueId());
        return locationPair != null || (locationPair != null && locationPair.getKey() != null && locationPair.getValue() != null);
    }

    @Override
    public void ifCantExecute(CommandSender commandSender) {
        commandSender.sendMessage(color("&cDebe marcar una selección de terreno para poder reclamar un terreno!"));
        commandSender.sendMessage(color("&b[&aTip&b] &eUsa: /plot wand &6para reclamar la herramienta de selección de tierras."));
    }
}