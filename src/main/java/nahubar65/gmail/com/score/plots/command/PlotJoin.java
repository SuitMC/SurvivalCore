package nahubar65.gmail.com.score.plots.command;

import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.plots.PlotRegion;
import nahubar65.gmail.com.score.storages.Storage;
import nahubar65.gmail.com.score.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class PlotJoin extends CommandArgument {

    private Storage<UUID, List<PlotRegion>> storage;

    public PlotJoin(Command command, Storage<UUID, List<PlotRegion>> storage) {
        super(command);
        this.storage = storage;
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {
        Player player = (Player) sender;
        if (args.length > 0) {
            String arg1 = args[0];
            PlotRegion plotRegion = Utils.findFromName(arg1, storage);
            if (plotRegion != null) {
                plotRegion.getPlotRequestSender().accept(player.getUniqueId());
            }
        }
    }

    @Override
    public String identifier() {
        return "join";
    }

    @Override
    public String usage() {
        return "/parcelas join";
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