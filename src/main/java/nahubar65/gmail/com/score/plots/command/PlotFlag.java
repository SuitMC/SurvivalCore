package nahubar65.gmail.com.score.plots.command;

import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.menu.FlagMenu;
import nahubar65.gmail.com.score.menu.Menu;
import nahubar65.gmail.com.score.plots.MemberManager;
import nahubar65.gmail.com.score.plots.PlotRegion;
import nahubar65.gmail.com.score.storages.Storage;
import nahubar65.gmail.com.score.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class PlotFlag extends CommandArgument {

    private Storage<UUID, List<PlotRegion>> storage;

    private Menu flagMenu;

    public PlotFlag(Command command, Storage<UUID, List<PlotRegion>> storage) {
        super(command);
        this.storage = storage;
        this.flagMenu = new FlagMenu();
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {
        if (args.length > 0) {
            String arg1 = args[0];
            Player player = (Player) sender;
            PlotRegion region = Utils.findFromName(arg1, storage);
            MemberManager memberManager = region.getMemberManager();
            if (region != null) {
                if (memberManager.exists(player.getUniqueId()) || player.hasPermission("score.region.bypass")) {
                    Map<String, Object> objectMap = new HashMap<>();
                    objectMap.put("player", player);
                    objectMap.put("region", region);
                    player.openInventory(flagMenu.getInventory(objectMap));
                }
            }
        }
    }

    @Override
    public String identifier() {
        return "flag";
    }

    @Override
    public String usage() {
        return "&aUso: &b/parcelas flag &a<Id> &e<list/set &3<true/false>&e>";
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