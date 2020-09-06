package nahubar65.gmail.com.score.survivalcommands.warps;

import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.storages.RegionStorage;
import nahubar65.gmail.com.score.storages.WarpStorage;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class WarpCommand extends Command {

    public WarpCommand(String name, WarpStorage warpStorage, RegionStorage regionStorage) {
        super(name);
        registerNewArgument(new DelWarp(this, warpStorage));
        registerNewArgument(new SetWarp(this, warpStorage));
        registerNewArgument(new SetRegion(this, regionStorage, warpStorage));
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
        help.add("&9/score &bsetwarp <Nombre> &6-> &a&oColoca un nuevo warp.");
        help.add("&9/score &bdelwarp <Nombre> &6-> &a&oElimina un warp existente.");
        return help;
    }

    @Override
    public boolean zeroArguments() {
        return false;
    }
}