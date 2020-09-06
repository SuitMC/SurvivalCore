package nahubar65.gmail.com.score.survivalcommands.core;

import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.storages.WarpStorage;
import nahubar65.gmail.com.score.survivalcommands.warps.SetSpawn;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class SCoreCommand extends Command {

    public SCoreCommand(String name, WarpStorage warpStorage) {
        super(name);
        registerNewArgument(new SetSpawn(this, warpStorage));
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
        help.add("&9/score &bsetspawn &6-> &a&oColoca el spawn del survival.");
        return help;
    }

    @Override
    public boolean zeroArguments() {
        return false;
    }
}