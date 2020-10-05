package nahubar65.gmail.com.score.plots.command;

import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.menu.MemberListMenu;
import nahubar65.gmail.com.score.menu.MemberManagerMenu;
import nahubar65.gmail.com.score.menu.Menu;
import nahubar65.gmail.com.score.plots.MemberManager;
import nahubar65.gmail.com.score.plots.PlotRegion;
import nahubar65.gmail.com.score.storages.Storage;
import nahubar65.gmail.com.score.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlotMemberManager extends CommandArgument {

    private Storage<UUID, List<PlotRegion>> storage;

    private Menu memberManagerMenu;

    public PlotMemberManager(Command command, Storage<UUID, List<PlotRegion>> storage) {
        super(command);
        this.storage = storage;
        this.memberManagerMenu = new MemberManagerMenu();
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {
        if (args.length > 0) {
            String arg1 = args[0];
            Player player = (Player) sender;
            PlotRegion plotRegion = Utils.findFromName(arg1, storage);
            if (plotRegion != null) {
                MemberManager memberManager = plotRegion.getMemberManager();
                if (memberManager.exists(player.getUniqueId())) {
                    Map<String, Object> objectMap = new HashMap<>();
                    objectMap.put("player", player);
                    objectMap.put("region", plotRegion);
                    player.openInventory(memberManagerMenu.getInventory(objectMap));
                }
            }
        }
    }

    @Override
    public String identifier() {
        return "membermanager";
    }

    @Override
    public String usage() {
        return "/parcelas membermanager";
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