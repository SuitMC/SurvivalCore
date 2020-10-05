package nahubar65.gmail.com.score.plots.command;

import nahubar65.gmail.com.score.caches.UUIDCache;
import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.storages.RegionStorage;
import nahubar65.gmail.com.score.storages.SQLPlotStorage;
import nahubar65.gmail.com.score.utils.Pair;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class PlotCommand extends Command {

    public PlotCommand(String name, UUIDCache<Pair<Location, Location>> uuidCache, SQLPlotStorage sqlPlotStorage, RegionStorage regionStorage) {
        super(name);
        registerNewArgument(new PlotWand(this, uuidCache, sqlPlotStorage, regionStorage));
        registerNewArgument(new PlotClaim(this, uuidCache, sqlPlotStorage));
        registerNewArgument(new PlotList(this, sqlPlotStorage));
        registerNewArgument(new PlotFlag(this, sqlPlotStorage));
        registerNewArgument(new SeeLimit(this, sqlPlotStorage));
        registerNewArgument(new PlotArea(this, sqlPlotStorage));
        registerNewArgument(new PlotMemberManager(this, sqlPlotStorage));
        registerNewArgument(new PlotJoin(this, sqlPlotStorage));
        registerNewArgument(new PlotRemove(this, sqlPlotStorage));
    }

    @Override
    public boolean executeCommand(CommandSender sender, String arg, String[] args) {
        return true;
    }

    @Override
    public boolean useOnlyPlayer() {
        return true;
    }

    @Override
    public List<String> getHelp(CommandSender sender) {
        List<String> help = new ArrayList<>();
        help.add("&7&m===[&r &e* &aParcelas &e* &7&m]===");
        help.add("&b/parcelas wand &6-> &3&oReclama la herramienta de selección de tierras.");
        help.add("&b/parcelas claim &6-> &3&oReclama la selección de tierras.");
        help.add("&b/parcelas delete &a<Id> &6-> &3&oElimina una parcela de tu propiedad.");
        help.add("&b/parcelas flag &a<Id> &6-> &3&oAdministra las flags de la parcela elegida.");
        help.add("&b/parcelas area &a<Id> &6-> &3&oCambia el tamaño del area de la parcela.");
        help.add("&b/parcelas limit &a<Id> &6-> &3&oObserva los limites de tu parcela.");
        help.add("&b/parcelas list &6-> &3&oMuestra la lista de parcelas que tienes.");
        return help;
    }

    @Override
    public boolean zeroArguments() {
        return false;
    }
}